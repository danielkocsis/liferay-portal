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

package com.liferay.portal.lar.digest;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;

/**
 *
 * @author Daniel Kocsis
 */
public class LarDigest {

	public LarDigest() {
		_digestXML = SAXReaderUtil.createDocument();

		Element rootElement = _digestXML.addElement("root");

		Element headerElement = rootElement.addElement("header");

		headerElement.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		headerElement.addAttribute("export-date", Time.getRFC822());
	}

	public void addChildEntry(Element child, Element parent) {

		try {
			Element itemsEl = parent.element(
				LarDigesterConstants.NODE_ITEMS_LABEL);

			itemsEl.add(child);
		}
		catch (Exception ex) {
			_log.error(ex, ex);
		}
	}

	public Element addEntry(
		int action, String path, String type, String classPK) {

		Element newElement = SAXReaderUtil.createElement(
			LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL);

		newElement.add(
			createElement(LarDigesterConstants.NODE_PATH_LABEL, path));

		newElement.add(
			createElement(LarDigesterConstants.NODE_ACTION_LABEL,
				String.valueOf(action)));

		newElement.add(
			createElement(LarDigesterConstants.NODE_TYPE_LABEL, type));

		newElement.add(
			createElement(LarDigesterConstants.NODE_CLASS_PK_LABEL, classPK));

		newElement.addElement(LarDigesterConstants.NODE_ITEMS_LABEL);

		return newElement;
	}

	public List<Node> getAllNodes() {
		return _digestXML.content();
	}

	public List<Node> getEntriesByAction(int action) {
		String xPath = "//item/action='" + action + "'";
		return SAXReaderUtil.selectNodes(xPath, getAllNodes());
	}

	public List<Node> getEntriesByClassPK(String classPK) {
		String xPath = "//item/classPK='" + classPK + "'";
		return SAXReaderUtil.selectNodes(xPath, getAllNodes());
	}

	public Element getRootEntry() {
		return _digestXML.getRootElement();
	}

	public void setAttribute(Element element, String name, String value) {
		element.addAttribute(name, value);
	}

	@Override
	public String toString() {
		String content = StringPool.BLANK;

		try {
			content = _digestXML.formattedString();
		}
		catch (Exception ex) {
			_log.error(ex);
		}

		return content;
	}

	protected Element createElement(String title, String value) {
		Element attributeEl = SAXReaderUtil.createElement(title);

		attributeEl.addText(value);

		return attributeEl;
	}

	private static Log _log = LogFactoryUtil.getLog(LarDigest.class);

	private Document _digestXML;
}