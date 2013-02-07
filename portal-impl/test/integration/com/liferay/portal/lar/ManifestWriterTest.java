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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
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
import java.util.HashMap;
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

		_manifest.add(bookmarksFolder);

		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			groupId, true);
		BookmarksEntry bookmarksFolderEntry = BookmarksTestUtil.addEntry(
			groupId, bookmarksFolder.getFolderId(), true);

		_manifest.add(bookmarksEntry);
		_manifest.add(bookmarksFolderEntry);

		Document manifestDocument = _manifest.getManifestDocument();

		// Validate root element

		Element rootElement = manifestDocument.getRootElement();

		validateRootElement(rootElement);

		// Validate folder

		Element bookmarksFolderElement = rootElement.element(
			BookmarksFolder.class.getName());

		validateStagedModelElement(
			bookmarksFolderElement, null, bookmarksFolder);

		// Validate entries

		Element bookmarksEntryElement = rootElement.element(
			BookmarksEntry.class.getName());

		validateStagedModelElement(
			bookmarksEntryElement, null, bookmarksEntry, bookmarksFolderEntry);
	}

	@Test
	public void testAddStagedModel() throws Exception {
		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		_manifest.add(bookmarksEntry);

		Document manifestDocument = _manifest.getManifestDocument();

		Element rootElement = manifestDocument.getRootElement();

		// Validate root element

		validateRootElement(rootElement);

		// Validate entry

		Element bookmarksEntryElement = rootElement.element(
			BookmarksEntry.class.getName());

		validateStagedModelElement(bookmarksEntryElement, null, bookmarksEntry);
	}

	@Test
	public void testAddStagedModelWithAttributes() throws Exception {
		BookmarksEntry bookmarksEntry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		Map<String, Map<String, String>> attributes =
			new HashMap<String, Map<String, String>>();

		Map<String, String> entryAttributes = new HashMap<String, String>();

		entryAttributes.put("preloaded", "true");
		entryAttributes.put("test", "true");

		attributes.put(getAttributeKey(bookmarksEntry), entryAttributes);

		_manifest.add(bookmarksEntry, entryAttributes);

		Document manifestDocument = _manifest.getManifestDocument();

		System.out.println(manifestDocument.formattedString());

		Element rootElement = manifestDocument.getRootElement();

		validateRootElement(rootElement);

		Element bookmarksEntryElement = rootElement.element(
			BookmarksEntry.class.getName());

		validateStagedModelElement(
			bookmarksEntryElement, attributes, bookmarksEntry);
	}

	protected String getAttributeKey(ClassedModel classedModel) {
		StringBundler sb = new StringBundler(3);

		sb.append(classedModel.getModelClassName());
		sb.append(StringPool.POUND);
		sb.append(String.valueOf(classedModel.getPrimaryKeyObj()));

		return sb.toString();
	}

	protected String getStagedModelClassPk(StagedModel stagedModel) {
		ClassedModel classedModel = (ClassedModel)stagedModel;

		Object primaryKeyObj = classedModel.getPrimaryKeyObj();

		return String.valueOf(primaryKeyObj);
	}

	protected void validateAttributes(
		Element element, Map<String, String> attributes) {

		if (attributes == null) {
			return;
		}

		List<Attribute> elementAttributes = element.attributes();

		List<Attribute> checkedElementAttributes = new ArrayList<Attribute>();

		for (Attribute attribute : elementAttributes) {
			if (attribute.getName().equals(_ATTRIBUTE_CLASSPK) ||
				attribute.getName().equals(_ATTRIBUTE_PATH)) {

				continue;
			}

			checkedElementAttributes.add(attribute);
		}

		if (checkedElementAttributes.size() != attributes.size()) {
			Assert.fail();
		}

		for (Attribute elementAttribute : checkedElementAttributes) {
			String attributeName = elementAttribute.getName();

			if (!attributes.containsKey(attributeName)) {
				Assert.fail();
			}

			String attributeValue = elementAttribute.getValue();

			if (!attributeValue.equals(attributes.get(attributeName))) {
				Assert.fail();
			}
		}
	}

	protected void validateRootElement(Element rootElement) throws Exception {
		if (!_ROOT_ELEMENT_NAME.equals(rootElement.getName())) {
			Assert.fail();
		}
	}

	protected void validateStagedModelElement(
			Element stagedModelElement,
			Map<String, Map<String, String>> attributes,
			StagedModel... stagedModels)
		throws Exception {

		List<Element> stagedModelsElements = stagedModelElement.elements();

		if (stagedModels.length != stagedModelsElements.size()) {
			Assert.fail();
		}

		for (int i = 0; i < stagedModels.length; i++) {
			StagedModel stagedModel = stagedModels[i];

			String classPk = getStagedModelClassPk(stagedModel);

			for (int j = 0; j < stagedModelsElements.size(); j++) {
				Element element = stagedModelsElements.get(j);

				if (classPk.equals(
						element.attributeValue(_ATTRIBUTE_CLASSPK))) {

					if (attributes != null) {
						validateAttributes(
							element,
							attributes.get(
								getAttributeKey((ClassedModel)stagedModel)));
					}

					stagedModelsElements.remove(j--);
				}
			}
		}

		if (!stagedModelsElements.isEmpty()) {
			Assert.fail();
		}
	}

	private static final String _ATTRIBUTE_CLASSPK = "classPK";
	private static final String _ATTRIBUTE_PATH = "path";

	private static final String _ROOT_ELEMENT_NAME = "manifest";

	private Group _group;
	private ManifestWriterImpl _manifest = new ManifestWriterImpl();

}