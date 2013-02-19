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

import com.liferay.portal.kernel.lar.ManifestEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class ManifestWriterTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testAddMultipleStagedModel() throws Exception {
		long groupId = _group.getGroupId();

		BookmarksFolder bookmarksFolder = BookmarksTestUtil.addFolder(
			groupId, "Test");

		ManifestEntry folderManifestEntry = new ManifestEntryImpl();

		folderManifestEntry.setModel(bookmarksFolder);

		_manifest.add(folderManifestEntry);

		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			groupId, true);
		BookmarksEntry bookmarksFolderEntry = BookmarksTestUtil.addEntry(
			groupId, bookmarksFolder.getFolderId(), true);

		ManifestEntry entryManifestEntry = new ManifestEntryImpl();

		entryManifestEntry.setModel(bookmarksEntry);

		ManifestEntry folderEntryManifestEntry = new ManifestEntryImpl();

		folderEntryManifestEntry.setModel(bookmarksFolderEntry);

		_manifest.add(entryManifestEntry);
		_manifest.add(folderEntryManifestEntry);

		Document manifestDocument = _manifest.getManifestDocument();

		System.out.println(manifestDocument.formattedString());

		// Validate root element

		Element rootElement = manifestDocument.getRootElement();

		validateRootElement(rootElement);

		// Validate folders and entries

		List<Element> modelGroups = rootElement.elements("model-group");

		XPath foldersXpath = SAXReaderUtil.createXPath(
			"model[@class-name='" + BookmarksFolder.class.getName() + "']");

		Node node = foldersXpath.selectSingleNode(modelGroups);

		validateModelElement((Element)node, folderManifestEntry);

		XPath entriesXpath = SAXReaderUtil.createXPath(
			"model[@class-name='" + BookmarksEntry.class.getName() + "']");

		List<Node> selectedNodes = entriesXpath.selectNodes(modelGroups);

		SAXReaderUtil.sort(selectedNodes, "number('@classpk')");

		validateModelElement(
			(Element)selectedNodes.get(0), entryManifestEntry);

		validateModelElement(
			(Element)selectedNodes.get(1), folderEntryManifestEntry);
	}

	@Test
	public void testAddStagedModel() throws Exception {
		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		ManifestEntry manifestEntry = new ManifestEntryImpl();

		manifestEntry.setModel(bookmarksEntry);

		_manifest.add(manifestEntry);

		Document manifestDocument = _manifest.getManifestDocument();

		System.out.println(manifestDocument.formattedString());

		Element rootElement = manifestDocument.getRootElement();

		// Validate root element

		validateRootElement(rootElement);

		// Validate entry

		Element bookmarksEntryGroupElement = rootElement.element("model-group");

		if (bookmarksEntryGroupElement.elements().size() != 1) {
			Assert.fail();
		}

		validateModelElement(
			bookmarksEntryGroupElement.element("model"), manifestEntry);
	}

	@Test
	public void testAddStagedModelWithAttributes() throws Exception {
		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		ManifestEntry manifestEntry = new ManifestEntryImpl();

		manifestEntry.setModel(bookmarksEntry);

		manifestEntry.addModelAttribute("preloaded", "true");
		manifestEntry.addModelAttribute("test", "true");

		_manifest.add(manifestEntry);

		Document manifestDocument = _manifest.getManifestDocument();

		System.out.println(manifestDocument.formattedString());

		Element rootElement = manifestDocument.getRootElement();

		validateRootElement(rootElement);

		Element bookmarksEntryGroupElement = rootElement.element("model-group");

		if (bookmarksEntryGroupElement.elements().size() != 1) {
			Assert.fail();
		}

		validateModelElement(
			bookmarksEntryGroupElement.element("model"), manifestEntry);
	}

	protected void validateRootElement(Element rootElement) throws Exception {
		if (!_ROOT_ELEMENT_NAME.equals(rootElement.getName())) {
			Assert.fail();
		}
	}

	protected void validateModelElement(
			Element modelElement, ManifestEntry manifestEntry)
		throws Exception {

		List<Attribute> modelAttributes = modelElement.attributes();

		if (modelAttributes.size() !=
				manifestEntry.getModelAttributes().size()) {

			Assert.fail();
		}

		for (Attribute attribute : modelAttributes) {
			String attributeValue = attribute.getValue();
			String manifestAttributeValue = manifestEntry.getModelAttribute(
				attribute.getName());

			if (!attributeValue.equals(manifestAttributeValue)) {
				Assert.fail();
			}
		}
	}

	private static final String _ATTRIBUTE_CLASSPK = "classPK";
	private static final String _ATTRIBUTE_PATH = "path";

	private static final String _ROOT_ELEMENT_NAME = "manifest";

	private Group _group;
	private ManifestWriterImpl _manifest = new ManifestWriterImpl();

}