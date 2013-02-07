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

import com.liferay.portal.kernel.lar.ManifestWriter;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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

	public void add(ClassedModel classedModel) {
		add(classedModel, null);
	}

	public void add(ClassedModel classedModel, Map<String, String> attributes) {
		String modelClassName = classedModel.getModelClassName();

		Set<ClassedModel> stagedModels = null;

		if (_manifestMap.containsKey(modelClassName)) {
			stagedModels = _manifestMap.get(modelClassName);
		}
		else {
			stagedModels = new HashSet<ClassedModel>();
		}

		stagedModels.add(classedModel);

		_manifestMap.put(modelClassName, stagedModels);

		if ((attributes != null) && !attributes.isEmpty()) {
			_attributeMap.put(getAttributeKey(classedModel), attributes);
		}
	}

	public Document getManifestDocument() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("manifest");

		for (Map.Entry<String, Set<ClassedModel>> manifestEntry :
				_manifestMap.entrySet()) {

			Element stagedModelsElement = rootElement.addElement(
				manifestEntry.getKey());

			for (ClassedModel stagedModel : manifestEntry.getValue()) {
				Element stagedModelElement = stagedModelsElement.addElement(
					"staged-model");

				long classPK = (Long)stagedModel.getPrimaryKeyObj();

				stagedModelElement.addAttribute(
					"classPK", String.valueOf(classPK));
				stagedModelElement.addAttribute(
					"path",
					StagedModelPathUtil.getPath(
						getModelGroupId(stagedModel),
						stagedModel.getModelClassName(), classPK)
				);

				Map<String, String> stagedModelAttributes = _attributeMap.get(
					getAttributeKey(stagedModel));

				if (stagedModelAttributes != null) {
					for (Map.Entry<String, String> attribute :
							stagedModelAttributes.entrySet()) {

						stagedModelElement.addAttribute(
							attribute.getKey(), attribute.getValue());
					}
				}
			}
		}

		return document;
	}

	protected String getAttributeKey(ClassedModel classedModel) {
		StringBundler sb = new StringBundler(3);

		sb.append(classedModel.getModelClassName());
		sb.append(StringPool.POUND);
		sb.append(String.valueOf(classedModel.getPrimaryKeyObj()));

		return sb.toString();
	}

	protected long getModelGroupId(ClassedModel classedModel) {
		GroupedModel groupedModel = (GroupedModel)classedModel;

		return groupedModel.getGroupId();
	}

	private Map<String, Map<String, String>> _attributeMap =
		new HashMap<String, Map<String, String>>();
	private Map<String, Set<ClassedModel>> _manifestMap =
		new HashMap<String, Set<ClassedModel>>();

}