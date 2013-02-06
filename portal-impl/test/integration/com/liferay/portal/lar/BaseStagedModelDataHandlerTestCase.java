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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.xml.ElementImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.dom4j.DocumentHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseStagedModelDataHandlerTestCase extends PowerMockito {
	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_liveGroup = ServiceTestUtil.addGroup();
		_stagingGroup = ServiceTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_liveGroup);

		GroupLocalServiceUtil.deleteGroup(_stagingGroup);
	}

	@Test
	@Transactional
	public void TestStagedModelDataHandler() throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), getStartDate(),
			getEndDate(), zipWriter);

		Element parentElement = new ElementImpl(
			DocumentHelper.createElement("parentElement"));

		StagedModel stagedModel = addStagedModel(_stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, parentElement, stagedModel);

		List<Element> exportedElements = parentElement.elements();

		validateExport(stagedModel, exportedElements);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			zipWriter.getFile());

		UserIdStrategy userIdStrategy = new CurrentUserIdStrategy(
			TestPropsValues.getUser());

		portletDataContext = new PortletDataContextImpl(
			_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), userIdStrategy,
			zipReader);

		StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedElements.get(0));

		validateImport(stagedModel, _liveGroup);
	}

	protected abstract StagedModel addStagedModel(Group group) throws Exception;

	protected Date getEndDate() {
		return new Date();
	}

	protected Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected abstract StagedModel getStagedModel(Group group, String uuid);

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected void validateExport(
			StagedModel stagedModel, List<Element> exportedElements)
		throws Exception {

		Assert.assertEquals(1, exportedElements.size());

		Element exportedElement = exportedElements.get(0);

		Attribute pathAttribute = exportedElement.attribute("path");

		Assert.assertNotNull(pathAttribute);

		String path = pathAttribute.getValue();

		String classPK = path.substring(
			path.lastIndexOf(StringPool.FORWARD_SLASH) + 1,
			path.lastIndexOf(".xml"));

		Object primaryKeyObj = ((ClassedModel)stagedModel).getPrimaryKeyObj();

		Assert.assertEquals(String.valueOf(primaryKeyObj), classPK);
	}

	protected void validateImport(StagedModel stagedModel, Group group)
		throws Exception {

		StagedModel importedModel = getStagedModel(
			group, stagedModel.getUuid());

		Assert.assertNotNull(importedModel);
	}

	private Group _liveGroup;
	private Group _stagingGroup;

}