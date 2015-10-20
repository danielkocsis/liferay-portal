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

import java.util.List;
import java.util.Locale;

/**
* @author Andrew Betts
*/
public class BackgroundTaskDetailsJSONObject
	implements BackgroundTaskJSONObject {

	public BackgroundTaskDetailsJSONObject(JSONObject jsonObject) {
		_header = jsonObject.getString("header");
		_sections = BackgroundTaskJSONTransformer.sectionsFromJSONArray(
			jsonObject.getJSONArray("sections"));
		_status = jsonObject.getInt("status");
	}

	public BackgroundTaskDetailsJSONObject(
		String header, List<BackgroundTaskDetailsSectionJSONObject> sections,
		int status) {

		_header = header;
		_sections = sections;
		_status = status;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof BackgroundTaskDetailsJSONObject)) {
			return false;
		}

		BackgroundTaskDetailsJSONObject backgroundTaskDetailsJSONObject =
			(BackgroundTaskDetailsJSONObject)object;

		if (!Validator.equals(
				_header, backgroundTaskDetailsJSONObject._header) ||
			!_sections.equals(backgroundTaskDetailsJSONObject._sections) ||
			(_status != backgroundTaskDetailsJSONObject._status)) {

			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _header);

		hash = HashUtil.hash(hash, _sections);

		return HashUtil.hash(hash, _status);
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("header", _header);
		jsonObject.put(
			"sections", BackgroundTaskJSONTransformer.toJSONArray(_sections));
		jsonObject.put("status", _status);

		return jsonObject;
	}

	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("header", LanguageUtil.get(locale, _header));
		jsonObject.put(
			"sections",
			BackgroundTaskJSONTransformer.toJSONArray(_sections, locale));
		jsonObject.put("status", _status);

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

	private final String _header;
	private final List<BackgroundTaskDetailsSectionJSONObject> _sections;
	private final int _status;

}