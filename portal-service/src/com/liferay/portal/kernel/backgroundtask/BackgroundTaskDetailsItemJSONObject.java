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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
* @author Andrew Betts
*/
public class BackgroundTaskDetailsItemJSONObject
	implements BackgroundTaskJSONObject {

	public BackgroundTaskDetailsItemJSONObject(JSONObject jsonObject) {
		_errorMessage = jsonObject.getString("errorMessage");
		_errorStrongMessage = jsonObject.getString("errorStrongMessage");
		_info = jsonObject.getString("info");
	}

	public BackgroundTaskDetailsItemJSONObject(
		String errorMessage, String errorStrongMessage, String info) {

		_errorMessage = errorMessage;
		_errorStrongMessage = errorStrongMessage;
		_info = info;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof BackgroundTaskDetailsItemJSONObject)) {
			return false;
		}

		BackgroundTaskDetailsItemJSONObject
			backgroundTaskDetailsItemJSONObject =
				(BackgroundTaskDetailsItemJSONObject)object;

		if (!Validator.equals(
				_errorMessage,
				backgroundTaskDetailsItemJSONObject._errorMessage) ||
			!Validator.equals(
				_errorStrongMessage,
				backgroundTaskDetailsItemJSONObject._errorStrongMessage) ||
			!Validator.equals(
				_info, backgroundTaskDetailsItemJSONObject._info)) {

			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _errorMessage);

		hash = HashUtil.hash(hash, _errorStrongMessage);

		return HashUtil.hash(hash, _info);
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("errorMessage", _errorMessage);
		jsonObject.put("errorStrongMessage", _errorStrongMessage);
		jsonObject.put("info", _info);

		return jsonObject;
	}

	@Override
	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("errorMessage", LanguageUtil.get(locale, _errorMessage));
		jsonObject.put(
			"errorStrongMessage",
			LanguageUtil.get(locale, _errorStrongMessage));
		jsonObject.put("info", LanguageUtil.get(locale, _info));

		return jsonObject;
	}

	@Override
	public String toJSONString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toString();
	}

	@Override
	public String toString() {
		return toJSONString();
	}

	private final String _errorMessage;
	private final String _errorStrongMessage;
	private final String _info;

}