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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
public class BackgroundTaskJSONTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testItemsListFromJSONArray() throws Exception {
		List<BackgroundTaskDetailsItemJSONObject> itemsList = new ArrayList<>();

		itemsList.add(
			new BackgroundTaskDetailsItemJSONObject(
				"error message", "error strong message", "information"));

		Assert.assertEquals(
			itemsList,
			BackgroundTaskJSONTransformer.itemsListFromJSONArray(
				JSONFactoryUtil.createJSONArray(_ITEM_LIST_JSONARRAY)));
	}

	@Test
	public void testSectionsFromJSONArray() throws Exception {
		List<BackgroundTaskDetailsSectionJSONObject> sections =
			new ArrayList<>();

		sections.add(
			new BackgroundTaskDetailsSectionJSONObject(
				"message",
				JSONFactoryUtil.createJSONArray(_ITEM_LIST_JSONARRAY)));

		Assert.assertEquals(
			sections,
			BackgroundTaskJSONTransformer.sectionsFromJSONArray(
				JSONFactoryUtil.createJSONArray(_SECTIONS_JSONARRAY)));
	}

	@Test
	public void testToJSONArrayFromItemsList() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			_ITEM_LIST_JSONARRAY);

		List<BackgroundTaskDetailsItemJSONObject> itemsList = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			itemsList.add(
				new BackgroundTaskDetailsItemJSONObject(
					jsonArray.getJSONObject(i)));
		}

		Assert.assertEquals(
			jsonArray, BackgroundTaskJSONTransformer.toJSONArray(itemsList));
	}

	@Test
	public void testToJSONArrayFromSections() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			_SECTIONS_JSONARRAY);

		List<BackgroundTaskDetailsSectionJSONObject> sections = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			sections.add(
				new BackgroundTaskDetailsSectionJSONObject(
					jsonArray.getJSONObject(i)));
		}

		Assert.assertEquals(
			jsonArray, BackgroundTaskJSONTransformer.toJSONArray(sections));
	}

	private static final String _ITEM_LIST_JSONARRAY =
		"[ { errorMessage : \"error message\", errorStrongMessage : " +
			"\"error strong message\", info : \"information\" } ]";

	private static final String _SECTIONS_JSONARRAY =
		"[ { message : \"message\", itemsList : " + _ITEM_LIST_JSONARRAY +
			" } ]";

}