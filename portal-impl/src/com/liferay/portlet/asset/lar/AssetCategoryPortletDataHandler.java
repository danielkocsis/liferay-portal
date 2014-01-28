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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;

import javax.portlet.PortletPreferences;

/**
 * @author Mate Thurzo
 */
public class AssetCategoryPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_category";

	public AssetCategoryPortletDataHandler() {
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("categories-hierarchy");

		if (exportPortletDataAll || exportCategories || companyGroup) {
			if (_log.isDebugEnabled()) {
				_log.debug("Export categories");
			}

			Element assetVocabulariesElement = rootElement.addElement(
				"vocabularies");

			List<AssetVocabulary> assetVocabularies =
				AssetVocabularyLocalServiceUtil.getGroupVocabularies(
					portletDataContext.getGroupId());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				_portletExporter.exportAssetVocabulary(
					portletDataContext, assetVocabulariesElement,
					assetVocabulary);
			}

			Element categoriesElement = rootElement.addElement("categories");

			List<AssetCategory> assetCategories =
				AssetCategoryUtil.findByGroupId(
					portletDataContext.getGroupId());

			for (AssetCategory assetCategory : assetCategories) {
				_portletExporter.exportAssetCategory(
					portletDataContext, assetVocabulariesElement,
					categoriesElement, assetCategory);
			}
		}

		_portletExporter.exportAssetCategories(portletDataContext, rootElement);

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/categories-hierarchy.xml",
			document.formattedString());
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/categories-hierarchy.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		Element assetVocabulariesElement = rootElement.element("vocabularies");

		List<Element> assetVocabularyElements =
			assetVocabulariesElement.elements("vocabulary");

		Map<Long, Long> assetVocabularyPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		for (Element assetVocabularyElement : assetVocabularyElements) {
			String path = assetVocabularyElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			AssetVocabulary assetVocabulary =
				(AssetVocabulary)portletDataContext.getZipEntryAsObject(path);

			importAssetVocabulary(
				portletDataContext, assetVocabularyPKs, assetVocabularyElement,
				assetVocabulary);
		}

		Element assetCategoriesElement = rootElement.element("categories");

		List<Element> assetCategoryElements = assetCategoriesElement.elements(
			"category");

		Map<Long, Long> assetCategoryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		Map<String, String> assetCategoryUuids =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class + ".uuid");

		for (Element assetCategoryElement : assetCategoryElements) {
			String path = assetCategoryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			AssetCategory assetCategory =
				(AssetCategory)portletDataContext.getZipEntryAsObject(path);

			importAssetCategory(
				portletDataContext, assetVocabularyPKs, assetCategoryPKs,
				assetCategoryUuids, assetCategoryElement, assetCategory);
		}

		Element assetsElement = rootElement.element("assets");

		List<Element> assetElements = assetsElement.elements("asset");

		for (Element assetElement : assetElements) {
			String className = GetterUtil.getString(
				assetElement.attributeValue("class-name"));
			long classPK = GetterUtil.getLong(
				assetElement.attributeValue("class-pk"));
			String[] assetCategoryUuidArray = StringUtil.split(
				GetterUtil.getString(
					assetElement.attributeValue("category-uuids")));

			long[] assetCategoryIds = new long[0];

			for (String assetCategoryUuid : assetCategoryUuidArray) {
				assetCategoryUuid = MapUtil.getString(
					assetCategoryUuids, assetCategoryUuid, assetCategoryUuid);

				AssetCategory assetCategory = AssetCategoryUtil.fetchByUUID_G(
					assetCategoryUuid, portletDataContext.getScopeGroupId());

				if (assetCategory == null) {
					Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
						portletDataContext.getCompanyId());

					assetCategory = AssetCategoryUtil.fetchByUUID_G(
						assetCategoryUuid, companyGroup.getGroupId());
				}

				if (assetCategory != null) {
					assetCategoryIds = ArrayUtil.append(
						assetCategoryIds, assetCategory.getCategoryId());
				}
			}

			portletDataContext.addAssetCategories(
				className, classPK, assetCategoryIds);
		}
	}

}