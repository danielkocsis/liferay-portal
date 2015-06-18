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

package com.liferay.portlet.exportimport.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportProcessCallbackRegistryUtil {

	public static ExportImportProcessCallbackRegistry
		getExportImportProcessCallbackRegistry() {

		PortalRuntimePermission.checkGetBeanProperty(
			ExportImportProcessCallbackRegistryUtil.class);

		ExportImportProcessCallbackRegistry
			exportImportProcessCallbackRegistry =
				_instance._serviceTracker.getService();

		if (exportImportProcessCallbackRegistry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"ExportImportProcessCallbackRegistryUtil has not been " +
						"initialized");
			}

			return null;
		}

		return exportImportProcessCallbackRegistry;
	}

	public static void registerCallback(Callable<?> callable) {
		getExportImportProcessCallbackRegistry().registerCallback(callable);
	}

	private ExportImportProcessCallbackRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ExportImportProcessCallbackRegistry.class);

		_serviceTracker.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportProcessCallbackRegistryUtil.class);

	private static final ExportImportProcessCallbackRegistryUtil _instance =
		new ExportImportProcessCallbackRegistryUtil();

	private final ServiceTracker
		<ExportImportProcessCallbackRegistry,
			ExportImportProcessCallbackRegistry> _serviceTracker;

}