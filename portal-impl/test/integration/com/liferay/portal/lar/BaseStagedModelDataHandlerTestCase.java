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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.xml.ElementImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public void testStagedModelDataHandler() throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), getStartDate(),
			getEndDate(), zipWriter);

		// Export

		Map<String, List<StagedModel>> stagedModels = addDependentStagedModels(
			_stagingGroup);

		StagedModel stagedModel = addStagedModel(_stagingGroup, stagedModels);

		List<StagedModel> stagedModelList = new ArrayList<StagedModel>();
		stagedModelList.add(stagedModel);

		stagedModels.put(getStagedModelClassName(), stagedModelList);

		Element[] stagedModelElements = getStagedModelElements(
			stagedModels.keySet());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModelElements, stagedModel);

		// Validate Export

		validateExport(stagedModels, stagedModelElements);

		// Import

		/*ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			zipWriter.getFile());

		UserIdStrategy userIdStrategy = new CurrentUserIdStrategy(
			TestPropsValues.getUser());

		portletDataContext = new PortletDataContextImpl(
			_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), userIdStrategy,
			zipReader);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedElements.get(0));

		validateImport(stagedModel, _liveGroup);*/
	}

	protected abstract Map<String, List<StagedModel>> addDependentStagedModels(
		Group group) throws Exception;

	protected abstract StagedModel addStagedModel(
			Group group, Map<String, List<StagedModel>> relatedStagedModels)
		throws Exception;

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

	protected abstract StagedModel getStagedModel(String uuid, Group group);
	protected abstract String getStagedModelClassName();

	protected Element[] getStagedModelElements(
		Set<String> relatedStagedModelClassNames) {

		List<Element> stagedModelElements = new ArrayList<Element>();

		for (String relatedStagedModelClassName :
				relatedStagedModelClassNames) {

			Element relatedStagedModelElement = new ElementImpl(
				DocumentHelper.createElement(relatedStagedModelClassName));

			stagedModelElements.add(relatedStagedModelElement);
		}

		return stagedModelElements.toArray(
			new Element[stagedModelElements.size()]);
	}

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected boolean isHierarchicalModel() {
		return false;
	}

	protected void validateExport(
			Map<String, List<StagedModel>> stagedModels,
			Element[] exportedElements)
		throws Exception {

		for (Element exportedElement : exportedElements) {
			String className = exportedElement.getName();

			List<StagedModel> stagedModelList = stagedModels.get(className);

			List<Element> modelElements = exportedElement.elements();

			if (modelElements.isEmpty() ||
				(modelElements.size() != stagedModelList.size())) {

				Assert.fail();
			}

			for (Element modelElement : modelElements) {
				String path = modelElement.attributeValue("path");

				if (Validator.isNull(path)) {
					Assert.fail(
						"Path cannot be null for exported StagedModel.");
				}

				String classPK = path.substring(
					path.lastIndexOf(StringPool.FORWARD_SLASH) + 1,
					path.lastIndexOf(".xml"));

				Iterator<StagedModel> iterator = stagedModelList.iterator();

				while (iterator.hasNext()) {
					StagedModel stagedModel = iterator.next();

					Object primaryKeyObj =
						((ClassedModel)stagedModel).getPrimaryKeyObj();

					if (classPK.equals(String.valueOf(primaryKeyObj))) {
						iterator.remove();
					}
				}
			}

			if (!stagedModelList.isEmpty()) {
				Assert.fail();
			}
		}
	}

	protected void validateImport(StagedModel stagedModel, Group group)
		throws Exception {

		StagedModel importedModel = getStagedModel(
			stagedModel.getUuid(), group);

		Assert.assertNotNull(importedModel);
	}

	private Group _liveGroup;
	private Group _stagingGroup;

}