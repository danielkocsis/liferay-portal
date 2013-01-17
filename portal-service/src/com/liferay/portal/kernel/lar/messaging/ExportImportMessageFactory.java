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

import com.liferay.portal.model.StagedModel;

import java.util.Date;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public interface ExportImportMessageFactory {

	public ExportImportMessage getErrorMessage(
		StagedModel stagedModel, String message);

	public ExportImportMessage getErrorMessage(
		String portletId, String message);

	public ExportImportMessage getErrorMessage(
		String className, String classPk, String message);

	public ExportImportMessage getErrorMessage(
		String className, String classPk, String message, Date timestamp);

	public ExportImportMessage getMessage(
		StagedModel stagedModel, String message);

	public ExportImportMessage getMessage(String message);

	public ExportImportMessage getMessage(String portletId, String message);

	public ExportImportMessage getMessage(
		String className, String classPk, String message);

	public ExportImportMessage getMessage(
		String className, String classPk, String message, Date timestamp);

}