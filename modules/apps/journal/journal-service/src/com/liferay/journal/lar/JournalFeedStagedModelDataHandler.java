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

package com.liferay.journal.lar;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException;
import com.liferay.journal.exportimport.content.processor.JournalFeedExportImportContentProcessor;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class JournalFeedStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFeed> {

	public static final String[] CLASS_NAMES = {JournalFeed.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		Element feedElement = portletDataContext.getExportDataElement(feed);

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			feed.getGroupId(), PortalUtil.getClassNameId(JournalArticle.class),
			feed.getDDMStructureKey(), true);

		if (ddmStructure != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, feed, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM structure with key " +
						feed.getDDMStructureKey());
			}
		}

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			feed.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
			feed.getDDMTemplateKey());

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, feed, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getDDMTemplateKey());
			}
		}

		DDMTemplate rendererDDMTemplate =
			_ddmTemplateLocalService.fetchTemplate(
				feed.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				feed.getDDMRendererTemplateKey());

		if (rendererDDMTemplate != null) {
			Element rendererDDMTemplateElement =
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, feed, rendererDDMTemplate,
					PortletDataContext.REFERENCE_TYPE_STRONG);

			rendererDDMTemplateElement.addAttribute(
				"rendererDDMTemplate", "true");
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find DDM template with key " +
						feed.getDDMRendererTemplateKey());
			}
		}

		_journalFeedExportImportContentProcessor.replaceExportContentReferences(
			portletDataContext, feed, StringPool.BLANK, true, true);

		portletDataContext.addClassedModel(
			feedElement, ExportImportPathUtil.getModelPath(feed), feed);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFeed feed)
		throws Exception {

		_journalFeedExportImportContentProcessor.replaceImportContentReferences(
			portletDataContext, feed, StringPool.BLANK);

		String feedId = feed.getFeedId();

		Map<String, String> ddmStructureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		String parentDDMStructureKey = MapUtil.getString(
			ddmStructureKeys, feed.getDDMStructureKey(),
			feed.getDDMStructureKey());

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		String parentDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getDDMTemplateKey(),
			feed.getDDMTemplateKey());
		String parentRendererDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, feed.getDDMRendererTemplateKey(),
			feed.getDDMRendererTemplateKey());

		JournalFeed importedFeed = (JournalFeed)feed.clone();

		importedFeed.setGroupId(portletDataContext.getGroupId());
		importedFeed.setDDMStructureKey(parentDDMStructureKey);
		importedFeed.setDDMTemplateKey(parentDDMTemplateKey);
		importedFeed.setDDMRendererTemplateKey(parentRendererDDMTemplateKey);

		try {
			JournalFeed existingFeed = fetchStagedModelByUuidAndGroupId(
				feed.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFeed == null) {
				importedFeed = _stagedModelRepository.addStagedModel(
					portletDataContext, importedFeed);
			}
			else {
				importedFeed.setFeedId(existingFeed.getFeedId());
				importedFeed.setGroupId(existingFeed.getGroupId());

				importedFeed = _stagedModelRepository.updateStagedModel(
					portletDataContext, importedFeed);
			}

			portletDataContext.importClassedModel(feed, importedFeed);

			if (!feedId.equals(importedFeed.getFeedId())) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("A feed with the ID ");
					sb.append(feedId);
					sb.append(" already exists. The new generated ID is ");
					sb.append(importedFeed.getFeedId());
					sb.append(".");

					_log.warn(sb.toString());
				}
			}
		}
		catch (FeedTargetLayoutFriendlyUrlException ftlfurle) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(7);

				sb.append("A feed with the ID ");
				sb.append(feedId);
				sb.append(" cannot be imported because layout with friendly ");
				sb.append("URL ");
				sb.append(feed.getTargetLayoutFriendlyUrl());
				sb.append(" does not exist: ");
				sb.append(ftlfurle.getMessage());

				_log.warn(sb.toString());
			}
		}
	}

	@Override
	protected StagedModelRepository<JournalFeed> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFeedExportImportContentProcessor(
		JournalFeedExportImportContentProcessor
			journalFeedExportImportContentProcessor) {

		_journalFeedExportImportContentProcessor =
			journalFeedExportImportContentProcessor;
	}

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFeed)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<JournalFeed> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedStagedModelDataHandler.class);

	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private JournalFeedExportImportContentProcessor
		_journalFeedExportImportContentProcessor;
	private StagedModelRepository<JournalFeed> _stagedModelRepository;

}