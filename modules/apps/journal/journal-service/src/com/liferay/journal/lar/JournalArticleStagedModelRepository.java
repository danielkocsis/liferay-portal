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

import com.liferay.exportimport.repository.StagedModelRepository;
import com.liferay.exportimport.repository.base.BaseStagedModelRepository;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = {
		JournalArticleStagedModelRepository.class, StagedModelRepository.class
	}
)
public class JournalArticleStagedModelRepository
	extends BaseStagedModelRepository<JournalArticle> {

	@Override
	public void deleteStagedModel(JournalArticle article)
		throws PortalException {

		_journalArticleLocalService.deleteArticle(article);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.
				fetchJournalArticleResourceByUuidAndGroupId(uuid, groupId);

		if (articleResource == null) {
			return;
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			extraData);

		if (Validator.isNotNull(extraData) && extraDataJSONObject.has("uuid")) {
			String articleUuid = extraDataJSONObject.getString("uuid");

			JournalArticle article = fetchStagedModelByUuidAndGroupId(
				articleUuid, groupId);

			deleteStagedModel(article);
		}
		else {
			_journalArticleLocalService.deleteArticle(
				groupId, articleResource.getArticleId(), null);
		}
	}

	public JournalArticle fetchExistingArticle(
		String articleResourceUuid, long groupId, String articleId,
		String newArticleId, boolean preloaded) {

		JournalArticle existingArticle = null;

		if (!preloaded) {
			JournalArticleResource journalArticleResource =
				_journalArticleResourceLocalService.
					fetchJournalArticleResourceByUuidAndGroupId(
						articleResourceUuid, groupId);

			if (journalArticleResource == null) {
				return null;
			}

			return _journalArticleLocalService.fetchArticle(
				groupId, journalArticleResource.getArticleId());
		}

		if (Validator.isNotNull(newArticleId)) {
			existingArticle = _journalArticleLocalService.fetchArticle(
				groupId, newArticleId);
		}

		if ((existingArticle == null) && Validator.isNull(newArticleId)) {
			existingArticle = _journalArticleLocalService.fetchArticle(
				groupId, articleId);
		}

		return existingArticle;
	}

	public JournalArticle fetchExistingArticle(
		String articleUuid, String articleResourceUuid, long groupId,
		String articleId, String newArticleId, double version,
		boolean preloaded) {

		JournalArticle article = fetchExistingArticle(
			articleResourceUuid, groupId, articleId, newArticleId, preloaded);

		if (article != null) {
			article = fetchExistingArticleVersion(
				articleUuid, groupId, article.getArticleId(), version);
		}

		return article;
	}

	public JournalArticle fetchExistingArticleVersion(
		String articleUuid, long groupId, String articleId, double version) {

		JournalArticle existingArticle = fetchStagedModelByUuidAndGroupId(
			articleUuid, groupId);

		if (existingArticle != null) {
			return existingArticle;
		}

		return _journalArticleLocalService.fetchArticle(
			groupId, articleId, version);
	}

	@Override
	public JournalArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return
			_journalArticleLocalService.fetchJournalArticleByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	public List<JournalArticle> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalArticleLocalService.getJournalArticlesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalArticle>());
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(article.getUserUuid());

		Element articleElement =
			portletDataContext.getImportDataStagedModelElement(article);

		String articleResourceUuid = articleElement.attributeValue(
			"article-resource-uuid");

		boolean preloaded = GetterUtil.getBoolean(
			articleElement.attributeValue("preloaded"));

		JournalArticle existingArticle = fetchExistingArticle(
			article.getUuid(), articleResourceUuid,
			portletDataContext.getScopeGroupId(), article.getArticleId(),
			article.getArticleId(), article.getVersion(), preloaded);

		if ((existingArticle == null) || !existingArticle.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingArticle.getTrashHandler();

		try {
			if (trashHandler.isRestorable(
					existingArticle.getResourcePrimKey())) {

				trashHandler.restoreTrashEntry(
					userId, existingArticle.getResourcePrimKey());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public JournalArticle update(JournalArticle stagedModel)
		throws PortletDataException {

		return null;
	}

	@Reference
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}