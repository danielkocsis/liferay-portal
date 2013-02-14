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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ManifestReaderImpl implements ManifestReader {

	public Map<String, String> getAttributes(String className, String classPK) {
		if (_document == null) {
			return null;
		}

		// xPath = /manifest/className/classPk

		StringBundler sb = new StringBundler(8);

		sb.append(StringPool.FORWARD_SLASH);
		sb.append("manifest");
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(className);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append("staged-model[@classPK=");
		sb.append(classPK);
		sb.append("]");

		String xPathExpression = sb.toString();

		XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

		Element stagedModelElement = (Element)xPathSelector.selectSingleNode(
			_document);

		if (stagedModelElement == null) {
			return null;
		}

		Map<String, String> attributesMap = new HashMap<String, String>();

		for (Attribute attribute : stagedModelElement.attributes()) {
			attributesMap.put(attribute.getName(), attribute.getValue());
		}

		return attributesMap;
	}

	public String getPath(String className, String classPK) {
		Map<String, String> attributesMap = getAttributes(className, classPK);

		if ((attributesMap == null) || attributesMap.isEmpty()) {
			return null;
		}

		return attributesMap.get("path");
	}

	public void setManifestDocument(Document document) {
		_document = document;
	}

	private Document _document;

}