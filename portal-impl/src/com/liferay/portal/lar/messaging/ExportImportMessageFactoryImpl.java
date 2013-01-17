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

import com.liferay.portal.kernel.lar.messaging.ExportImportMessage;
import com.liferay.portal.kernel.lar.messaging.ExportImportMessageFactory;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;

import java.util.Date;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public class ExportImportMessageFactoryImpl
	implements ExportImportMessageFactory {

	public ExportImportMessage getErrorMessage(
		StagedModel stagedModel, String message) {

		ClassedModel classedModel = (ClassedModel)stagedModel;

		String modelClassName = classedModel.getModelClassName();
		String modelClassPk = String.valueOf(classedModel.getPrimaryKeyObj());

		return getErrorMessage(modelClassName, modelClassPk, message);
	}

	public ExportImportMessage getErrorMessage(
		String portletId, String message) {

		return getErrorMessage(
			Portlet.class.getName(), portletId, message, new Date());
	}

	public ExportImportMessage getErrorMessage(
		String className, String classPk, String message) {

		return getErrorMessage(className, classPk, message, new Date());
	}

	public ExportImportMessage getErrorMessage(
		String className, String classPk, String message, Date timestamp) {

		return doGetMessage(
			className, classPk, message, ExportImportMessage.MESSAGE_TYPE_ERROR,
			timestamp);
	}

	public ExportImportMessage getMessage(
		StagedModel stagedModel, String message) {

		ClassedModel classedModel = (ClassedModel)stagedModel;

		String modelClassName = classedModel.getModelClassName();
		String modelClassPk = String.valueOf(classedModel.getPrimaryKeyObj());

		return getMessage(modelClassName, modelClassPk, message, new Date());
	}

	public ExportImportMessage getMessage(String message) {
		ExportImportMessage exportImportmessage = new ExportImportMessage();

		exportImportmessage.setMessage(message);
		exportImportmessage.setTimestamp(new Date());

		return exportImportmessage;
	}

	public ExportImportMessage getMessage(String portletId, String message) {
		return getMessage(
			Portlet.class.getName(), portletId, message, new Date());
	}

	public ExportImportMessage getMessage(
		String className, String classPk, String message) {

		return getMessage(className, classPk, message, new Date());
	}

	public ExportImportMessage getMessage(
		String className, String classPk, String message, Date timestamp) {

		return doGetMessage(
			className, classPk, message,
			ExportImportMessage.MESSAGE_TYPE_SUCCESS, timestamp);
	}

	protected ExportImportMessage doGetMessage(
		String className, String classPk, String message, int messageType,
		Date timestamp) {

		return new ExportImportMessage(
			className, classPk, message, messageType, timestamp);
	}

}