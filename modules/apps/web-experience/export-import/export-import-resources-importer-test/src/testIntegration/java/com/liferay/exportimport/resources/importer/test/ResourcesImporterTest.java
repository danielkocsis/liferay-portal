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

package com.liferay.exportimport.resources.importer.test;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;

import java.net.URL;

import java.util.List;

import com.liferay.portal.kernel.test.util.TestPropsValues;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class ResourcesImporterTest {

	private Bundle _bundle;

	@Before
	public void setUp() throws Exception {
		URL resource = ResourcesImporterTest.class.getResource(
			"dependencies/test.war");

		resource = new URL(
			resource.toExternalForm() +
				"?Web-ContextPath=/test-resource-importer");

		URL url = new URL("webbundle", null, resource.toString());

		_bundle = _bundleContext.installBundle(url.toString());

		_bundle.start();

		Thread.sleep(10 * 1000L);
	}

	@Before
	public void tearDown() throws Exception {
		_bundle.uninstall();
	}

	@Test
	public void testImportResourcesWAR() throws Exception {
		_importedGroup = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId());

		// Verify the deployment

		Assert.assertNotNull(_importedGroup);

		long privateLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_importedGroup, true);

		Assert.assertEquals(0, privateLayoutsCount);

		long publicLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_importedGroup, false);

		Assert.assertEquals(4, publicLayoutsCount);

		long journalArticlesCount =
			JournalArticleLocalServiceUtil.getArticlesCount(
				_importedGroup.getGroupId());

		Assert.assertEquals(2, journalArticlesCount);

		long fileEntriesCount = DLFileEntryLocalServiceUtil.getFileEntriesCount(
			_importedGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(2, fileEntriesCount);

		Layout homeLayout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_importedGroup.getGroupId(), false, "/home");

		Assert.assertTrue(homeLayout.isTypePortlet());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)homeLayout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(2, portlets.size());
	}

	@ArquillianResource
	private BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Group _importedGroup;

}