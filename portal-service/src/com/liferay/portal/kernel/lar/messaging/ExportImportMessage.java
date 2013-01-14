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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author Daniel Kocsis
 */
public class ExportImportMessage implements Serializable {

	public static final int MESSAGE_TYPE_ERROR = 0;

	public static final int MESSAGE_TYPE_SUCCESS = 1;

	public ExportImportMessage() {
	}

	public ExportImportMessage(String message) throws JSONException {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(message);

		_className = jsonObj.getString(_CLASS_NAME);
		_classPK = jsonObj.getString(_CLASS_PK);
		_command = jsonObj.getString(_COMMAND);
		_message = jsonObj.getString(_MESSAGE);
		_messageType = jsonObj.getInt(_MESSAGE_TYPE);

		_timestamp = GetterUtil.getDate(
			jsonObj.getString(_TIMESTAMP), _getDateFormat());
	}

	public ExportImportMessage(
		String className, String classPK, String command, String message,
		int messageType) {

		this(className, classPK, command, message, messageType, null);
	}

	public ExportImportMessage(
		String className, String classPK, String command, String message,
		int messageType, Date timestamp) {

		_className = className;
		_classPK = classPK;
		_command = command;
		_message = message;
		_messageType = messageType;

		_timestamp = timestamp;

		if (_timestamp == null) {
			_timestamp = new Date();
		}
	}

	public String getClassName() {
		return _className;
	}

	public String getClassPk() {
		return _classPK;
	}

	public String getCommand() {
		return _command;
	}

	public String getMessage() {
		return _message;
	}

	public int getMessageType() {
		return _messageType;
	}

	public Date getTimestamp() {
		return _timestamp;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setMessageType(int messageType) {
		_messageType = messageType;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put(_CLASS_PK, _classPK);
		jsonObj.put(_CLASS_NAME, _className);
		jsonObj.put(_COMMAND, _command);
		jsonObj.put(_MESSAGE, _message);
		jsonObj.put(_MESSAGE_TYPE, _messageType);
		jsonObj.put(_TIMESTAMP, _getDateFormat().format(new Date()));

		return jsonObj;
	}

	private DateFormat _getDateFormat() {
		return DateFormatFactoryUtil.getSimpleDateFormat(_DATE_FORMAT);
	}

	private static final String _CLASS_NAME = "className";

	private static final String _CLASS_PK = "classPK";

	private static final String _COMMAND = "command";

	private static final String _DATE_FORMAT = "yyyyMMddkkmmssSSS";

	private static final String _MESSAGE = "message";

	private static final String _MESSAGE_TYPE = "messageType";

	private static final String _TIMESTAMP = "timestamp";

	private static final long serialVersionUID = 1986083020110801800L;

	private String _className;
	private String _classPK;
	private String _command;
	private String _message;
	private int _messageType;
	private Date _timestamp;

}