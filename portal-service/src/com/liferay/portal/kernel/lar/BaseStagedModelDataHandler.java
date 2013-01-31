/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.lar.messaging.ExportImportMessage;
import com.liferay.portal.kernel.lar.messaging.ExportImportMessageFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	implements StagedModelDataHandler<T> {

	public void exportStagedModel(
			PortletDataContext portletDataContext, Element element,
			T stagedModel)
		throws PortletDataException {

		exportStagedModel(
			portletDataContext, new Element[] {element}, stagedModel);
	}

	public void exportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			T stagedModel)
		throws PortletDataException {

		sendStatusMessage(
			DestinationNames.EXPORT, stagedModel, "Export started");

		try {
			doExportStagedModel(portletDataContext, elements, stagedModel);

			sendStatusMessage(
				DestinationNames.EXPORT, stagedModel, "Export finished");
		}
		catch (Exception e) {
			sendStatusMessage(DestinationNames.EXPORT, stagedModel, e);

			throw new PortletDataException(e);
		}
	}

	public abstract String getClassName();

	public void importStagedModel(
			PortletDataContext portletDataContext, String path, T stagedModel)
		throws PortletDataException {

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		sendStatusMessage(
			DestinationNames.IMPORT, stagedModel, "Import started");

		try {
			doImportStagedModel(portletDataContext, path, stagedModel);

			sendStatusMessage(
				DestinationNames.IMPORT, stagedModel, "Import finished");
		}
		catch (Exception e) {
			sendStatusMessage(DestinationNames.IMPORT, stagedModel, e);

			throw new PortletDataException(e);
		}
	}

	protected abstract void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			T stagedModel)
		throws Exception;

	protected abstract void doImportStagedModel(
			PortletDataContext portletDataContext, String path, T stagedModel)
		throws Exception;

	protected void sendStatusMessage(
		String destinationName, StagedModel stagedModel, Object message) {

		if (Validator.isNull(destinationName) || (message == null)) {
			return;
		}

		ExportImportMessage exportImportMessage;

		if (message instanceof Exception) {
			exportImportMessage =
				ExportImportMessageFactoryUtil.getErrorMessage(
					stagedModel, (Exception)message);
		}
		else {
			exportImportMessage = ExportImportMessageFactoryUtil.getMessage(
				stagedModel, String.valueOf(message));
		}

		MessageBusUtil.sendMessage(destinationName, exportImportMessage);
	}

}