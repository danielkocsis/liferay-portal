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

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Date;

/**
 * @author Daniel Kocsis
 */
public class ExportImportMessageFactoryUtil {

	public static ExportImportMessage getErrorMessage(
		String className, String classPk, String command, String message) {

		return getExportImportMessageFactory().getErrorMessage(
			className, classPk, command, message);
	}

	public static ExportImportMessage getErrorMessage(
		String className, String classPk, String command, String message,
		Date timestamp) {

		return getExportImportMessageFactory().getErrorMessage(
				className, classPk, command, message, timestamp);
	}

	public static ExportImportMessageFactory getExportImportMessageFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ExportImportMessageFactoryUtil.class);

		return _exportImportMessageFactory;
	}

	public static ExportImportMessage getMessage(String message)
		throws JSONException {

		return _exportImportMessageFactory.getMessage(message);
	}

	public static ExportImportMessage getMessage(
		String className, String classPk, String command, String message) {

		return getExportImportMessageFactory().getMessage(
			className, classPk, command, message);
	}

	public static ExportImportMessage getMessage(
		String className, String classPk, String command, String message,
		Date timestamp) {

		return getExportImportMessageFactory().getMessage(
			className, classPk, command, message, timestamp);
	}

	public void setExportImportMessageFactory(
		ExportImportMessageFactory exportImportMessageFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exportImportMessageFactory = exportImportMessageFactory;
	}

	private static ExportImportMessageFactory _exportImportMessageFactory;

}