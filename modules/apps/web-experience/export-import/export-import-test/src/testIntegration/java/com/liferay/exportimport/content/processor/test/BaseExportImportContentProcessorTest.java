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

package com.liferay.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class ExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_group2.setFriendlyURL("/test-group");

		GroupLocalServiceUtil.updateGroup(_group2);

		_layout = LayoutTestUtil.addLayout(_group2);

		_layout.setFriendlyURL("/test-layout");

		LayoutLocalServiceUtil.updateLayout(_layout);
	}

	@Test
	public void testValidateContentReferencesForLinkToOtherSite()
		throws Exception {

		ExportImportContentProcessor exportImportContentProcessor =
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessor(
					DummyStagedModel.class.getName());

		String content =
			"<a href=\"http://localhost:8080/web/test-group/test-layout\">" +
				"test</a>";

		exportImportContentProcessor.validateContentReferences(
			_group1.getGroupId(), content);
	}

	@Test
	public void testValidateContentReferencesForLinkToPortletFriendlyURL()
		throws Exception {

		ExportImportContentProcessor exportImportContentProcessor =
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessor(
					DummyStagedModel.class.getName());

		String content =
			"<a href=\"http://localhost:8080/web/test-group/test-layout/-" +
				"/test-portlet\">test</a>";

		exportImportContentProcessor.validateContentReferences(
			_group2.getGroupId(), content);
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@DeleteAfterTestRun
	private Layout _layout;

}