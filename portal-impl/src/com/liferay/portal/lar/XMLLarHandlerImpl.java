/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.LarHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletModel;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public class XMLLarHandlerImpl implements LarHandler {

	@Override
	public String getExportedContent(
		PortletDataContext portletDataContext, Portlet portlet) {

		Element rootElement = portletDataContext.getExportDataRootElement();

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

	@Override
	public String getExportedContent(
			PortletDataContext portletDataContext, String portletId) {

		Portlet portlet = null;

		try {
			PortletLocalServiceUtil.getPortletById(portletId);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}

		return getExportedContent(portletDataContext, portlet);
	}

	@Override
	public ClassedModel readModel(
		PortletDataContext portletDataContext,
		Class<? extends ClassedModel> clazz, long groupId, String uuid) {

		Element element = getImportDataElement(
			portletDataContext, clazz.getSimpleName(), "uuid", uuid);

		return getStagedModel(portletDataContext, element);
	}

	@Override
	public Map<String, String> readModelAttributes(
		PortletDataContext portletDataContext, ClassedModel classedModel) {

		Element element = getImportDataElement(
			portletDataContext, classedModel);

		Map<String, String> attributes = new HashMap<String, String>();

		for (Attribute attribute : element.attributes()) {
			attributes.put(attribute.getName(), attribute.getValue());
		}

		return attributes;
	}

	@Override
	public List<ClassedModel> readModels(
		PortletDataContext portletDataContext,
		Class<? extends ClassedModel> clazz) {

		Element groupElement = getImportDataGroupElement(
			portletDataContext, clazz.getSimpleName());

		List<Element> modelElements = groupElement.elements();

		List<ClassedModel> models = new ArrayList<ClassedModel>();

		for (Element modelElement : modelElements) {
			models.add(getStagedModel(portletDataContext, modelElement));
		}

		return models;
	}

	@Override
	public Map<String, String> readReferenceAttributes(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		ClassedModel referredModel) {

		Element referrerElement = getImportDataElement(
			portletDataContext, referrerModel);

		long referredPrimaryKey = (Long)referredModel.getPrimaryKeyObj();

		List<Element> referenceElements = getReferenceElements(
			referrerElement, referredModel.getModelClass(), 0, null,
			referredPrimaryKey, null);

		Map<String, String> attributes = new HashMap<String, String>();

		if (!referenceElements.isEmpty()) {
			Element referenceElement = referenceElements.get(0);

			for (Attribute attribute : referenceElement.attributes()) {
				attributes.put(attribute.getName(), attribute.getValue());
			}
		}

		return attributes;
	}

	@Override
	public ClassedModel readReferencedModel(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz, long classPK) {

		Element referrerElement = getImportDataElement(
			portletDataContext, referrerModel);

		Element element = getReferenceDataElement(
			portletDataContext, referrerElement, clazz, classPK);

		return getStagedModel(portletDataContext, element);
	}

	@Override
	public ClassedModel readReferencedModel(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz, long groupId, String uuid) {

		Element referrerElement = getImportDataElement(
			portletDataContext, referrerModel);

		Element element = getReferenceDataElement(
			portletDataContext, referrerElement, clazz, groupId, uuid);

		return getStagedModel(portletDataContext, element);
	}

	@Override
	public List<ClassedModel> readReferencedModels(
		PortletDataContext portletDataContext, ClassedModel referrerModel) {

		return readReferencedModels(portletDataContext, referrerModel, null);
	}

	@Override
	public List<ClassedModel> readReferencedModels(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz) {

		Element referrerElement = getImportDataElement(
			portletDataContext, referrerModel);

		Element referencesElement = referrerElement.element("references");

		List<Element> dataElements = getReferenceDataElements(
			portletDataContext, referencesElement.elements(), clazz);

		List<ClassedModel> classedModels = new ArrayList<ClassedModel>();

		for (Element dataElement : dataElements) {
			classedModels.add(getStagedModel(portletDataContext, dataElement));
		}

		return classedModels;
	}

	@Override
	public void writeModel(
		PortletDataContext portletDataContext, ClassedModel model,
		Map<String, String> parameters) {

		Element element = getExportDataElement(portletDataContext, model);

		for (Map.Entry<String, String> mapEntry : parameters.entrySet()) {
			element.addAttribute(mapEntry.getKey(), mapEntry.getValue());
		}
	}

	@Override
	public void writeReference(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		ClassedModel referredModel, Map<String, String> parameters) {

		Element element = getExportDataElement(
			portletDataContext, referrerModel);

		String referenceType = PortletDataContext.REFERENCE_TYPE_DEPENDENCY;

		if (parameters.containsKey("type")) {
			referenceType = parameters.get("type");
		}

		boolean missing = false;

		if (parameters.containsKey("missing")) {
			referenceType = parameters.get("missing");
		}

		String className = referredModel.getModelClassName();

		if (parameters.containsKey("staged-model-class")) {
			className = parameters.get("staged-model-class");
		}

		String binPath = StringPool.BLANK;

		if (parameters.containsKey("bin-path")) {
			className = parameters.get("bin-path");
		}

		addReferenceElement(
			portletDataContext, referrerModel, element, referredModel,
			className, binPath, referenceType, missing);
	}

	protected Element addExportDataRootElement(
		PortletDataContext portletDataContext) {

		Document document = SAXReaderUtil.createDocument();

		Class<?> clazz = getClass();

		Element rootElement = document.addElement(clazz.getSimpleName());

		portletDataContext.setExportDataRootElement(rootElement);

		return rootElement;
	}

	protected Element addReferenceElement(
		PortletDataContext portletDataContext,
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String className, String binPath,
		String referenceType, boolean missing) {

		Element referenceElement = doAddReferenceElement(
			portletDataContext, referrerClassedModel, element, classedModel,
			className, binPath, referenceType, false);

		String referenceKey = getReferenceKey(classedModel);

		Set<String> references = portletDataContext.getReferences();
		Set<String> missingReferences =
			portletDataContext.getMissingReferences();

		if (missing) {
			if (references.contains(referenceKey)) {
				return referenceElement;
			}

			missingReferences.add(referenceKey);

			doAddReferenceElement(
				portletDataContext, referrerClassedModel, null, classedModel,
				className, binPath, referenceType, true);
		}
		else {
			references.add(referenceKey);

			if (missingReferences.contains(referenceKey)) {
				missingReferences.remove(referenceKey);

				Element missingReferenceElement = getMissingReferenceElement(
					portletDataContext, classedModel);

				Element missingReferencesElement =
					portletDataContext.getMissingReferencesElement();

				missingReferencesElement.remove(missingReferenceElement);
			}
		}

		return referenceElement;
	}

	protected Element doAddReferenceElement(
		PortletDataContext portletDataContext,
		ClassedModel referrerClassedModel, Element element,
		ClassedModel classedModel, String className, String binPath,
		String referenceType, boolean missing) {

		Element referenceElement = null;

		if (missing) {
			Element referencesElement =
				portletDataContext.getMissingReferencesElement();

			referenceElement = referencesElement.addElement(
				"missing-reference");
		}
		else {
			Element referencesElement = element.element("references");

			if (referencesElement == null) {
				referencesElement = element.addElement("references");
			}

			referenceElement = referencesElement.addElement("reference");
		}

		referenceElement.addAttribute("class-name", className);

		referenceElement.addAttribute(
			"class-pk", String.valueOf(classedModel.getPrimaryKeyObj()));

		if (missing) {
			if (classedModel instanceof StagedModel) {
				referenceElement.addAttribute(
					"display-name",
					StagedModelDataHandlerUtil.getDisplayName(
						(StagedModel)classedModel));
			}
			else {
				referenceElement.addAttribute(
					"display-name",
					String.valueOf(classedModel.getPrimaryKeyObj()));
			}
		}

		if (classedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)classedModel;

			referenceElement.addAttribute(
				"group-id", String.valueOf(stagedGroupedModel.getGroupId()));
		}

		if (Validator.isNotNull(binPath)) {
			referenceElement.addAttribute("path", binPath);
		}

		referenceElement.addAttribute("type", referenceType);

		if (missing) {
			referenceElement.addAttribute(
				"referrer-class-name",
				referrerClassedModel.getModelClassName());

			if (referrerClassedModel instanceof PortletModel) {
				Portlet portlet = (Portlet)referrerClassedModel;

				referenceElement.addAttribute(
					"referrer-display-name", portlet.getRootPortletId());
			}
			else if (referrerClassedModel instanceof StagedModel) {
				StagedModel referrerStagedModel =
					(StagedModel)referrerClassedModel;

				referenceElement.addAttribute(
					"referrer-display-name",
					StagedModelDataHandlerUtil.getDisplayName(
						referrerStagedModel));
			}
		}

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			referenceElement.addAttribute("uuid", stagedModel.getUuid());
			referenceElement.addAttribute(
				"company-id", String.valueOf(stagedModel.getCompanyId()));

			Map<String, String> referenceAttributes =
				StagedModelDataHandlerUtil.getReferenceAttributes(
					portletDataContext, stagedModel);

			for (Map.Entry<String, String> referenceAttribute :
					referenceAttributes.entrySet()) {

				referenceElement.addAttribute(
					referenceAttribute.getKey(), referenceAttribute.getValue());
			}
		}

		return referenceElement;
	}

	protected Element getDataElement(
		Element parentElement, String attribute, String value) {

		if (parentElement == null) {
			return null;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("staged-model");
		sb.append("[@");
		sb.append(attribute);
		sb.append("='");
		sb.append(value);
		sb.append("']");

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPath.selectSingleNode(parentElement);
	}

	protected Element getExportDataElement(
		PortletDataContext portletDataContext, ClassedModel classedModel) {

		Class modelClass = classedModel.getModelClass();

		String groupName = modelClass.getName();

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			groupName = stagedModel.getStagedModelType().getClassSimpleName();
		}

		Element groupElement = getExportDataGroupElement(
			portletDataContext, groupName);

		Element element = null;

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			String path = ExportImportPathUtil.getModelPath(stagedModel);

			element = getDataElement(groupElement, "path", path);

			if (element != null) {
				return element;
			}

			element = getDataElement(
				groupElement, "uuid", stagedModel.getUuid());

			if (element != null) {
				return element;
			}
		}

		element = groupElement.addElement("staged-model");

		if (classedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)classedModel;

			element.addAttribute(
				"group-id",String.valueOf(stagedGroupedModel.getGroupId()));
			element.addAttribute("uuid", stagedGroupedModel.getUuid());
		}
		else if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			element.addAttribute("uuid", stagedModel.getUuid());
		}

		return element;
	}

	protected Element getExportDataGroupElement(
		PortletDataContext portletDataContext, String name) {

		Element exportDataRootElement =
			portletDataContext.getExportDataRootElement();

		if (exportDataRootElement == null) {
			exportDataRootElement = addExportDataRootElement(
				portletDataContext);
		}

		Element groupElement = exportDataRootElement.element(name);

		if (groupElement == null) {
			groupElement = exportDataRootElement.addElement(name);
		}

		return groupElement;
	}

	protected Element getImportDataElement(
		PortletDataContext portletDataContext, ClassedModel classedModel) {

		Class modelClass = classedModel.getModelClass();

		String className = modelClass.getSimpleName();

		if (classedModel instanceof StagedModel) {
			StagedModel stagedModel = (StagedModel)classedModel;

			StagedModelType stagedModelType = stagedModel.getStagedModelType();

			className = stagedModelType.getClassName();

			className = className.substring(
				className.lastIndexOf(CharPool.PERIOD));
		}

		long primaryKey = (Long)classedModel.getPrimaryKeyObj();

		return getImportDataElement(
			portletDataContext, className, "class-pk",
			String.valueOf(primaryKey));
	}

	protected Element getImportDataElement(
		PortletDataContext portletDataContext, String name, String attribute,
		String value) {

		Element groupElement = getImportDataGroupElement(
			portletDataContext, name);

		return getDataElement(groupElement, attribute, value);
	}

	protected Element getImportDataGroupElement(
		PortletDataContext portletDataContext, String name) {

		Element importDataRootElement =
			portletDataContext.getExportDataRootElement();

		if (importDataRootElement == null) {
			throw new IllegalStateException(
				"Root data element not initialized");
		}

		if (Validator.isNull(name)) {
			return SAXReaderUtil.createElement("EMPTY-ELEMENT");
		}

		Element groupElement = importDataRootElement.element(name);

		if (groupElement == null) {
			return SAXReaderUtil.createElement("EMPTY-ELEMENT");
		}

		return groupElement;
	}

	protected Element getMissingReferenceElement(
		PortletDataContext portletDataContext, ClassedModel classedModel) {

		StringBundler sb = new StringBundler(5);

		sb.append("missing-reference[@class-name='");
		sb.append(classedModel.getModelClassName());
		sb.append("' and @class-pk='");
		sb.append(String.valueOf(classedModel.getPrimaryKeyObj()));
		sb.append("']");

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		Node node = xPath.selectSingleNode(
			portletDataContext.getMissingReferencesElement());

		return (Element)node;
	}

	protected Element getReferenceDataElement(
		PortletDataContext portletDataContext, Element parentElement,
		Class<?> clazz, long classPK) {

		List<Element> referenceElements = getReferenceElements(
			parentElement, clazz, 0, null, classPK, null);

		List<Element> referenceDataElements = getReferenceDataElements(
			portletDataContext, referenceElements, clazz);

		if (referenceDataElements.isEmpty()) {
			return null;
		}

		return referenceDataElements.get(0);
	}

	protected Element getReferenceDataElement(
		PortletDataContext portletDataContext, Element parentElement,
		Class<?> clazz, long groupId, String uuid) {

		List<Element> referenceElements = getReferenceElements(
			parentElement, clazz, groupId, uuid, 0, null);

		List<Element> referenceDataElements = getReferenceDataElements(
			portletDataContext, referenceElements, clazz);

		if (referenceDataElements.isEmpty()) {
			return null;
		}

		return referenceDataElements.get(0);
	}

	protected List<Element> getReferenceDataElements(
		PortletDataContext portletDataContext, List<Element> referenceElements,
		Class<?> clazz) {

		List<Element> referenceDataElements = new ArrayList<Element>();

		for (Element referenceElement : referenceElements) {
			Element referenceDataElement = null;

			String className = StringPool.BLANK;

			if (clazz != null) {
				className = clazz.getSimpleName();
			}
			else {
				className = referenceElement.attributeValue("class-name");

				className = className.substring(
					className.lastIndexOf(CharPool.PERIOD));
			}

			String path = referenceElement.attributeValue("path");

			if (Validator.isNotNull(path)) {
				referenceDataElement = getImportDataElement(
					portletDataContext, className, "path", path);
			}
			else {
				String groupId = referenceElement.attributeValue("group-id");
				String uuid = referenceElement.attributeValue("uuid");

				StringBuilder sb = new StringBuilder(5);

				sb.append("staged-model[@uuid='");
				sb.append(uuid);

				if (groupId != null) {
					sb.append("' and @group-id='");
					sb.append(groupId);
				}

				sb.append("']");

				XPath xPath = SAXReaderUtil.createXPath(sb.toString());

				Element groupElement = getImportDataGroupElement(
					portletDataContext, clazz.getSimpleName());

				referenceDataElement = (Element)xPath.selectSingleNode(
					groupElement);
			}

			if (referenceDataElement == null) {
				continue;
			}

			referenceDataElements.add(referenceDataElement);
		}

		return referenceDataElements;
	}

	protected List<Element> getReferenceElements(
		Element parentElement, Class<?> clazz, long groupId, String uuid,
		long classPK, String referenceType) {

		if (parentElement == null) {
			return Collections.emptyList();
		}

		Element referencesElement = parentElement.element("references");

		if (referencesElement == null) {
			return Collections.emptyList();
		}

		StringBundler sb = new StringBundler(5);

		sb.append("reference[@class-name='");
		sb.append(clazz.getName());

		if (groupId > 0) {
			sb.append("' and @group-id='");
			sb.append(groupId);
		}

		if (Validator.isNotNull(uuid)) {
			sb.append("' and @uuid='");
			sb.append(uuid);
		}

		if (classPK > 0) {
			sb.append("' and @class-pk='");
			sb.append(classPK);
		}

		if (referenceType != null) {
			sb.append("' and @type='");
			sb.append(referenceType);
		}

		sb.append("']");

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		List<Node> nodes = xPath.selectNodes(referencesElement);

		return ListUtil.fromArray(nodes.toArray(new Element[nodes.size()]));
	}

	protected String getReferenceKey(ClassedModel classedModel) {
		String referenceKey = classedModel.getModelClassName();

		return referenceKey.concat(StringPool.POUND).concat(
			String.valueOf(classedModel.getPrimaryKeyObj()));
	}

	protected StagedModel getStagedModel(
		PortletDataContext portletDataContext, Element element) {

		String path = element.attributeValue("path");

		StagedModel stagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(element, path);

		Attribute classNameAttribute = element.attribute("class-name");

		if ((classNameAttribute != null) &&
			(stagedModel instanceof TypedModel)) {

			String className = classNameAttribute.getValue();

			if (Validator.isNotNull(className)) {
				long classNameId = PortalUtil.getClassNameId(className);

				TypedModel typedModel = (TypedModel)stagedModel;

				typedModel.setClassNameId(classNameId);
			}
		}

		return stagedModel;
	}

}