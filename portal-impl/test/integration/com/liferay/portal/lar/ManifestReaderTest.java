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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ManifestReader;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.InputStream;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class ManifestReaderTest {

	@Test
	public void testGetAttributes() throws Exception {
		ManifestReader manifestReader = getManifestreader("manifest.xml");

		Map<String, String> attributesMap = manifestReader.getAttributes(
				JournalArticle.class.getName(), "12345");

		Assert.assertNull(attributesMap);

		attributesMap = manifestReader.getAttributes(
			BookmarksFolder.class.getName(), "12405");

		Assert.assertEquals(2, attributesMap.size());

		Assert.assertEquals(
			"/groups/12401/com.liferay.portlet.bookmarks.model." +
				"BookmarksFolder/12405.xml", attributesMap.get("path"));

		Assert.assertEquals("12405", attributesMap.get("classPK"));

		attributesMap = manifestReader.getAttributes(
			BookmarksEntry.class.getName(), "12415");

		Assert.assertEquals(2, attributesMap.size());

		Assert.assertEquals(
			"/groups/12401/com.liferay.portlet.bookmarks.model." +
				"BookmarksEntry/12415.xml", attributesMap.get("path"));

		Assert.assertEquals("12415", attributesMap.get("classPK"));

		attributesMap = manifestReader.getAttributes(
			BookmarksEntry.class.getName(), "12410");

		Assert.assertEquals(3, attributesMap.size());

		Assert.assertEquals(
			"/groups/12401/com.liferay.portlet.bookmarks.model." +
				"BookmarksEntry/12410.xml", attributesMap.get("path"));

		Assert.assertEquals("12410", attributesMap.get("classPK"));

		Assert.assertEquals("true", attributesMap.get("preloaded"));

	}

	@Test
	public void testGetPath() throws Exception {
		ManifestReader manifestReader = getManifestreader("manifest.xml");

		String path = manifestReader.getPath(
				JournalArticle.class.getName(), "12345");

		Assert.assertNull(path);

		path = manifestReader.getPath(BookmarksFolder.class.getName(), "12405");

		Assert.assertEquals(
			"/groups/12401/com.liferay.portlet.bookmarks.model." +
				"BookmarksFolder/12405.xml", path);

		path = manifestReader.getPath(BookmarksEntry.class.getName(), "12415");

		Assert.assertEquals(
			"/groups/12401/com.liferay.portlet.bookmarks.model." +
				"BookmarksEntry/12415.xml", path);

		path = manifestReader.getPath(BookmarksEntry.class.getName(), "12410");

		Assert.assertEquals(
			"/groups/12401/com.liferay.portlet.bookmarks.model." +
				"BookmarksEntry/12410.xml", path);
	}

	protected ManifestReader getManifestreader(String fileName)
		throws Exception {

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		Document document = SAXReaderUtil.read(inputStream);

		ManifestReader manifestreader = new ManifestReaderImpl();

		manifestreader.setManifestDocument(document);

		return manifestreader;
	}

}