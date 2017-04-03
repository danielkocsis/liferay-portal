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

package com.liferay.exportimport.lar.internal.helper;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.lar.helper.LARHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = LARHelper.class)
public class LARHelperImpl implements LARHelper {

	@Override
	public Element addExportDataRootElement(
		PortletDataContext portletDataContext) {

		Document document = SAXReaderUtil.createDocument();

		Class<?> clazz = getClass();

		Element rootElement = document.addElement(clazz.getSimpleName());

		portletDataContext.setExportDataRootElement(rootElement);

		return rootElement;
	}

	@Override
	public Element addImportDataRootElement(
			PortletDataContext portletDataContext, String data)
		throws DocumentException {

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		portletDataContext.setImportDataRootElement(rootElement);

		long groupId = GetterUtil.getLong(
			rootElement.attributeValue("group-id"));

		if (groupId != 0) {
			portletDataContext.setSourceGroupId(groupId);
		}

		return rootElement;
	}

	@Override
	public String getExportDataRootElementString(Element rootElement) {
		if (rootElement == null) {
			return StringPool.BLANK;
		}

		try {
			Document document = rootElement.getDocument();

			return document.formattedString();
		}
		catch (IOException ioe) {
			return StringPool.BLANK;
		}
	}

}