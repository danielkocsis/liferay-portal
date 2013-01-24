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

package com.liferay.portal.kernel.lar.messaging;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.model.StagedModel;

/**
 * @author Mate Thurzo
 */
public class ExportImportMessageUtil {

	public static void sendExportErrorMessage(
		StagedModel stagedModel, String message) {

		ExportImportMessage exportMessage =
			ExportImportMessageFactoryUtil.getErrorMessage(
				stagedModel, message);

		MessageBusUtil.sendMessage(DestinationNames.EXPORT, exportMessage);
	}

	public static void sendExportErrorMessage(
		String portletId, String message) {

		ExportImportMessage exportMessage =
			ExportImportMessageFactoryUtil.getErrorMessage(portletId, message);

		MessageBusUtil.sendMessage(DestinationNames.EXPORT, exportMessage);
	}

	public static void sendExportMessage(
		StagedModel stagedModel, String message) {

		ExportImportMessage exportMessage =
			ExportImportMessageFactoryUtil.getMessage(stagedModel, message);

		MessageBusUtil.sendMessage(DestinationNames.EXPORT, exportMessage);
	}

	public static void sendExportMessage(String portletId, String message) {
		ExportImportMessage exportMessage =
			ExportImportMessageFactoryUtil.getMessage(portletId, message);

		MessageBusUtil.sendMessage(DestinationNames.EXPORT, exportMessage);
	}

	public static void sendImportErrorMessage(
		String portletId, String message) {

		ExportImportMessage importMessage =
			ExportImportMessageFactoryUtil.getErrorMessage(portletId, message);

		MessageBusUtil.sendMessage(DestinationNames.IMPORT, importMessage);
	}

	public static void sendImportMessage(String portletId, String message) {
		ExportImportMessage importMessage =
				ExportImportMessageFactoryUtil.getMessage(portletId, message);

		MessageBusUtil.sendMessage(DestinationNames.IMPORT, importMessage);
	}

}