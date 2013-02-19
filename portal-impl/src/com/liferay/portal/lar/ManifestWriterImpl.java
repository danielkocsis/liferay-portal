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
import com.liferay.portal.kernel.lar.ManifestWriter;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.GroupedModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 */
public class ManifestWriterImpl implements ManifestWriter {

	public void add(ManifestEntry entry) {
		String modelClassName = entry.getModelClassName();

		Set<ManifestEntry> models = null;

		if (_manifestMap.containsKey(modelClassName)) {
			models = _manifestMap.get(modelClassName);
		}
		else {
			models = new HashSet<ManifestEntry>();
		}

		models.add(entry);

		_manifestMap.put(modelClassName, models);
	}

	public Document getManifestDocument() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("manifest");

		for (Map.Entry<String, Set<ManifestEntry>> modelGroup :
				_manifestMap.entrySet()) {

			Element modelGroupElement = rootElement.addElement("model-group");

			modelGroupElement.addAttribute("className", modelGroup.getKey());

			for (ManifestEntry manifestEntry : modelGroup.getValue()) {
				Element modelElement = modelGroupElement.addElement("model");

				Map<String, String> modelAttributes =
					manifestEntry.getModelAttributes();

				if (modelAttributes != null) {
					for (Map.Entry<String, String> attribute :
							modelAttributes.entrySet()) {

						modelElement.addAttribute(
							attribute.getKey(), attribute.getValue());
					}
				}
			}
		}

		return document;
	}

	protected long getModelGroupId(ClassedModel classedModel) {
		GroupedModel groupedModel = (GroupedModel)classedModel;

		return groupedModel.getGroupId();
	}

	private Map<String, Set<ManifestEntry>> _manifestMap =
		new HashMap<String, Set<ManifestEntry>>();

}