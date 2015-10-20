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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
* @author Andrew Betts
*/
public class BackgroundTaskDetailsSectionJSONObject
	implements BackgroundTaskJSONObject {

	public BackgroundTaskDetailsSectionJSONObject(JSONObject jsonObject) {
		_message = jsonObject.getString("message");
		_itemsList = BackgroundTaskJSONTransformer.itemsListFromJSONArray(
			jsonObject.getJSONArray("itemsList"));
	}

	public BackgroundTaskDetailsSectionJSONObject(
		String message, JSONArray itemsListJSONArray) {

		_message = message;

		_itemsList = new ArrayList<>();

		for (int i = 0; i < itemsListJSONArray.length(); i++) {
			JSONObject item = itemsListJSONArray.getJSONObject(i);

			BackgroundTaskDetailsItemJSONObject
				backgroundTaskDetailsItemJSONObject =
					new BackgroundTaskDetailsItemJSONObject(item);

			_itemsList.add(backgroundTaskDetailsItemJSONObject);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof BackgroundTaskDetailsSectionJSONObject)) {
			return false;
		}

		BackgroundTaskDetailsSectionJSONObject
			backgroundTaskDetailsSectionJSONObject =
				(BackgroundTaskDetailsSectionJSONObject)object;

		if (!Validator.equals(
				_message, backgroundTaskDetailsSectionJSONObject._message) ||
			!_itemsList.equals(
				backgroundTaskDetailsSectionJSONObject._itemsList)) {

			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _message);

		return HashUtil.hash(hash, _itemsList);
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("message", _message);
		jsonObject.put(
			"itemsList", BackgroundTaskJSONTransformer.toJSONArray(_itemsList));

		return jsonObject;
	}

	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("message", LanguageUtil.get(locale, _message));
		jsonObject.put(
			"itemsList",
			BackgroundTaskJSONTransformer.toJSONArray(_itemsList, locale));

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

	private final List<BackgroundTaskDetailsItemJSONObject> _itemsList;
	private final String _message;

}