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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public abstract class BaseDataHandlerTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		group = ServiceTestUtil.addGroup();

		expectedResultMap = new HashMap<String, Set<Long>>();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testPortletExport() throws Exception {
		initExportData();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			group.getCompanyId(), group.getGroupId(), getParameters(),
			new HashSet<String>(), getStartDate(), getEndDate(), zipWriter);

		portletDataContext.setPlid(LayoutConstants.DEFAULT_PLID);
		portletDataContext.setOldPlid(LayoutConstants.DEFAULT_PLID);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDataContext.getCompanyId(), getPortletId());

		if (portlet == null) {
			Assert.fail();
		}

		// Data

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			Assert.fail();
		}

		String data = null;

		try {
			data = portletDataHandler.exportData(
				portletDataContext, getPortletId(),
				new PortletPreferencesImpl());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		validatePortletExportData(data);
	}

	protected void addExpectedResult(String elementName, long classPk) {
		if (expectedResultMap.containsKey(elementName)) {
			expectedResultMap.get(elementName).add(classPk);
		}
		else {
			Set<Long> classPks = new HashSet<Long>();

			classPks.add(classPk);

			expectedResultMap.put(elementName, classPks);
		}
	}

	protected Date getEndDate() {
		return new Date();
	}

	protected Map<String, String[]> getParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	protected abstract String getPortletDataElementName();

	protected abstract String getPortletId();

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.DAY);
	}

	protected abstract void initExportData() throws Exception;

	protected void validatePortletExportData(String data) throws Exception {
		Document document = SAXReaderUtil.read(data);

		Element portletDataElement = document.getRootElement().element(
			getPortletDataElementName());

		for (Map.Entry<String, Set<Long>> expectedResults :
				expectedResultMap.entrySet()) {

			Element portletEntityElement = portletDataElement.element(
				expectedResults.getKey());

			List<Element> portletEntityElements = portletDataElement.elements();

			for (Element exportedEntity : portletEntityElements) {

			}
		}
	}

	protected Group group;

	protected Map<String, Set<Long>> expectedResultMap;

}