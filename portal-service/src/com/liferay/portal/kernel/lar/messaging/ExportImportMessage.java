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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Portlet;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

/**
 * Message object to send and receive status updates from the export/import
 * framework
 *
 * @author Daniel Kocsis
 * @author Mate Thurzo
 * @see    ExportImportMessageLevel
 * @see    ExportImportMessageFactoryUtil
 * @since  6.2
 */
public class ExportImportMessage implements Serializable {

	/**
	 * Empty constructor for serialization support.
	 *
	 * To produce an ExportImportMessage object please use the {@link
	 * ExportImportMessageFactoryUtil}
	 */
	public ExportImportMessage() {
	}

	public ExportImportMessage(String message) throws JSONException {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(message);

		_className = jsonObj.getString(_CLASS_NAME);
		_classPK = jsonObj.getString(_CLASS_PK);
		_exception =
			(Exception)JSONFactoryUtil.deserialize(
				jsonObj.getString(_EXCEPTION));
		_message = jsonObj.getString(_MESSAGE);
		_messageLevel =
			(ExportImportMessageLevel)JSONFactoryUtil.deserialize(
				jsonObj.getString(_MESSAGE_LEVEL));

		_timestamp = GetterUtil.getDate(
			jsonObj.getString(_TIMESTAMP), _DATE_FORMAT);
	}

	/**
	 * Constuctor for producing an ExportImportMessage. Although for creating
	 * message objects of this type using the {@link
	 * ExportImportMessageFactoryUtil} is advised.
	 *
	 * @param className the className of the entity or portlet where this
	 *        message comes from
	 * @param classPK the classPk of the entity or a portlet ID where this
	 *        message comes from
	 * @param exception an exception object if this is an error message
	 * @param message a custom message to encapsualte for user-friendliness
	 * @param messageLevel a message level: ERROR, WARNING or INFO
	 * @param timestamp a timestamp when the message is created
	 */
	public ExportImportMessage(
		String className, String classPK, Exception exception, String message,
		ExportImportMessageLevel messageLevel, Date timestamp) {

		_className = className;
		_classPK = classPK;
		_exception = exception;
		_message = message;
		_messageLevel = messageLevel;
		_timestamp = timestamp;
	}

	/**
	 * Returns the class name stored in this message.
	 *
	 * @return the class name stored in this message
	 */
	public String getClassName() {
		return _className;
	}

	/**
	 * Returns the classPk stored in this message.
	 *
	 * @return the class primary key stored in this message
	 */
	public String getClassPk() {
		return _classPK;
	}

	/**
	 * Return the Exception stored in this message if available. Otherwise it
	 * returns <code>null</code>.
	 *
	 * @return <code>null</code> if there is no Exception stored in the message
	 *         or the Exception object itself
	 */
	public Exception getException() {
		return _exception;
	}

	/**
	 * Return the custom message stored in this ExportImportMessage. If there is
	 * an Exception stored in the message as well this method return the
	 * Exception's message.
	 *
	 * @return the message stored in the ExportImportMessage, if this object
	 *         stores an Exception as well, it return that Exception's message.
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * Returns the level of the message like ERROR, WARNING or INFO.
	 *
	 * @return the level of the message.
	 * @see    {@link ExportImportMessageLevel}
	 */
	public ExportImportMessageLevel getMessageLevel() {
		return _messageLevel;
	}

	/**
	 * Returns the timestamp when the message was created.
	 *
	 * @return the timestamp when the message was created.
	 */
	public Date getTimestamp() {
		return _timestamp;
	}

	/**
	 * Returns if this message is sent from a staged model export/import process
	 * or not.
	 *
	 * @return return <code>true</code> if this message is sent from a staged
	 *         model export/import process, <code>false</code> otherwise
	 */
	public boolean isModelType() {
		if (!isPortletType()) {
			return true;
		}

		return false;
	}

	/**
	 * Returns if this message is sent from a portlet export/import process or
	 * not.
	 *
	 * @return <code>true</code> if this message is sent from a portlet
	 *         export/import process, <code>false</code> otherwise
	 */
	public boolean isPortletType() {
		if (_className.equals(Portlet.class.getName())) {
			return true;
		}

		return false;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setMessageLevel(ExportImportMessageLevel messageLevel) {
		_messageLevel = messageLevel;
	}

	public void setTimestamp(Date timestamp) {
		_timestamp = timestamp;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put(_CLASS_PK, _classPK);
		jsonObj.put(_CLASS_NAME, _className);
		jsonObj.put(_EXCEPTION, JSONFactoryUtil.serializeException(_exception));
		jsonObj.put(_MESSAGE, _message);
		jsonObj.put(_MESSAGE_LEVEL, JSONFactoryUtil.serialize(_messageLevel));
		jsonObj.put(_TIMESTAMP, _DATE_FORMAT.format(new Date()));

		return jsonObj;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append(_timestamp);
		sb.append(" - ");
		sb.append(_messageLevel.toString());
		sb.append(" - ");
		sb.append(_className);
		sb.append(" - ");
		sb.append(_classPK);
		sb.append(" - ");
		sb.append(_message);

		return sb.toString();
	}

	private static final String _CLASS_NAME = "className";

	private static final String _CLASS_PK = "classPK";

	private static final DateFormat _DATE_FORMAT =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddkkmmssSSS");

	private static final String _EXCEPTION = "exception";

	private static final String _MESSAGE = "message";

	private static final String _MESSAGE_LEVEL = "messageLevel";

	private static final String _TIMESTAMP = "timestamp";

	private static final long serialVersionUID = 1986083020110801800L;

	private String _className;
	private String _classPK;
	private Exception _exception;
	private String _message;
	private ExportImportMessageLevel _messageLevel;
	private Date _timestamp;

}