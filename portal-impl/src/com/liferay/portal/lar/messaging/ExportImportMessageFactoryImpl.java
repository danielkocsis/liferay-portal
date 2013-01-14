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

package com.liferay.portal.lar.messaging;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.lar.messaging.ExportImportMessage;
import com.liferay.portal.kernel.lar.messaging.ExportImportMessageFactory;

import java.util.Date;

/**
 * @author Daniel Kocsis
 */
public class ExportImportMessageFactoryImpl
	implements ExportImportMessageFactory {

	public ExportImportMessage getErrorMessage(
		String className, String classPk, String command, String message) {

		return new ExportImportMessage(
			className, classPk, command, message,
			ExportImportMessage.MESSAGE_TYPE_ERROR);
	}

	public ExportImportMessage getErrorMessage(
		String className, String classPk, String command, String message,
		Date timestamp) {

		return new ExportImportMessage(
			className, classPk, command, message,
			ExportImportMessage.MESSAGE_TYPE_ERROR, timestamp);
	}

	public ExportImportMessage getMessage(String message) throws JSONException {
		return new ExportImportMessage(message);
	}

	public ExportImportMessage getMessage(
			String className, String classPk, String command, String message) {

		return new ExportImportMessage(
			className, classPk, command, message,
			ExportImportMessage.MESSAGE_TYPE_SUCCESS);
	}

	public ExportImportMessage getMessage(
		String className, String classPk, String command, String message,
		Date timestamp) {

		return new ExportImportMessage(
			className, classPk, command, message,
			ExportImportMessage.MESSAGE_TYPE_SUCCESS, timestamp);
	}

}