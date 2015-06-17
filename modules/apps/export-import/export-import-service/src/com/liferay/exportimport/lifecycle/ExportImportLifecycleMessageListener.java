/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.exportimport.lifecycle;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleListener;

import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = MessageListener.class)
public class ExportImportLifecycleMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		_messageBus.registerMessageListener(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC, this);
		_messageBus.registerMessageListener(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC, this);
	}

	@Deactivate
	protected void deactivate() {
		_messageBus.unregisterMessageListener(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC, this);
		_messageBus.unregisterMessageListener(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC, this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Set<ExportImportLifecycleListener> exportImportLifecycleListeners =
			getExportImportLifecycleListeners(message);

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			(ExportImportLifecycleEvent)message.get(
				"exportImportLifecycleEvent");

		for (ExportImportLifecycleListener exportImportLifecycleListener :
				exportImportLifecycleListeners) {

			try {
				exportImportLifecycleListener.onExportImportLifecycleEvent(
					exportImportLifecycleEvent);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to call " +
							exportImportLifecycleListener.getClass(),
						e);
				}
			}
		}
	}

	protected Set<ExportImportLifecycleListener>
		getExportImportLifecycleListeners(Message message) {

		String destinationName = message.getDestinationName();

		if (destinationName.equals(
				DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC)) {

			return ExportImportLifecycleEventListenerRegistryUtil.
				getSyncExportImportLifecycleListeners();
		}

		return ExportImportLifecycleEventListenerRegistryUtil.
			getAsyncExportImportLifecycleListeners();
	}

	@Reference
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportLifecycleMessageListener.class);

	private MessageBus _messageBus;

}