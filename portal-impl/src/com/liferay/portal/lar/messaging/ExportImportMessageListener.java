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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Mate Thurzo
 */
public abstract class ExportImportMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		ExportImportMessage exportImportMessage =
			(ExportImportMessage)message.getPayload();

		logMessage(exportImportMessage);

		doReceiveExportImport(exportImportMessage);
	}

	protected abstract void doReceiveExportImport(
		ExportImportMessage exportImportMessage);

	protected void logMessage(ExportImportMessage exportImportMessage) {
		if (!_log.isInfoEnabled()) {
			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append(exportImportMessage.getTimestamp());
		sb.append(" - ");

		if (exportImportMessage.getMessageType() ==
				ExportImportMessage.MESSAGE_TYPE_SUCCESS) {

			sb.append("SUCCESS");
		}
		else {
			sb.append("ERROR");
		}

		sb.append(" - ");
		sb.append(exportImportMessage.getClassName());
		sb.append(" - ");
		sb.append(exportImportMessage.getClassPk());
		sb.append(" - ");
		sb.append(exportImportMessage.getMessage());

		_log.info(sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExportImportMessageListener.class.getName());

}