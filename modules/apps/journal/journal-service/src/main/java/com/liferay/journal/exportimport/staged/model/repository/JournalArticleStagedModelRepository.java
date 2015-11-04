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

package com.liferay.journal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.journal.lar.JournalPortletDataHandler;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
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
	public JournalArticle addStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(journalArticle.getUserId());

		// Display date

		int[] displayDateValues = processDate(
			journalArticle.getDisplayDate(), user.getTimeZone());

		// Expiration date

		Date expirationDate = journalArticle.getExpirationDate();

		boolean neverExpire = (expirationDate != null) ? false : true;

		int[] expirationDateValues = processDate(
			expirationDate, user.getTimeZone());

		// Review date

		Date reviewDate = journalArticle.getReviewDate();

		boolean neverReview = (reviewDate != null) ? false : true;

		int[] reviewDateValues = processDate(reviewDate, user.getTimeZone());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		File smallFile = (File)serviceContext.getAttribute("smallFile");
		Map<String, byte[]> images =
			(Map<String, byte[]>)serviceContext.getAttribute("images");
		boolean autoArticleId = GetterUtil.getBoolean(
			serviceContext.getAttribute("autoArticleId"));

		return _journalArticleLocalService.addArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getClassNameId(),
			journalArticle.getClassPK(), journalArticle.getArticleId(),
			autoArticleId, journalArticle.getVersion(),
			journalArticle.getTitleMap(), journalArticle.getDescriptionMap(),
			journalArticle.getContent(), journalArticle.getDDMStructureKey(),
			journalArticle.getDDMTemplateKey(), journalArticle.getLayoutUuid(),
			displayDateValues[0], displayDateValues[1], displayDateValues[2],
			displayDateValues[3], displayDateValues[4], expirationDateValues[0],
			expirationDateValues[1], expirationDateValues[2],
			expirationDateValues[3], expirationDateValues[4], neverExpire,
			reviewDateValues[0], reviewDateValues[1], reviewDateValues[2],
			reviewDateValues[3], reviewDateValues[4], neverReview,
			journalArticle.isIndexable(), journalArticle.isSmallImage(),
			journalArticle.getSmallImageURL(), smallFile, images, null,
			serviceContext);
	}

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

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_journalArticleLocalService.deleteArticles(
			portletDataContext.getScopeGroupId());

		_ddmTemplateLocalService.deleteTemplates(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class));

		_ddmStructureLocalService.deleteStructures(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class));
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

	public ExportActionableDynamicQuery getDDMStructureActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmStructureLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long classNameId = PortalUtil.getClassNameId(
						JournalArticle.class);

					dynamicQuery.add(classNameIdProperty.eq(classNameId));
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				DDMStructure.class.getName(), JournalArticle.class.getName()));

		return exportActionableDynamicQuery;
	}

	public ActionableDynamicQuery
		getDDMStructureDefaultValuesActionableDynamicQuery(
			PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_journalArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				JournalArticle.class.getName(), DDMStructure.class.getName()));

		return exportActionableDynamicQuery;
	}

	public ActionableDynamicQuery getDDMTemplateActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmTemplateLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					Property classPKProperty = PropertyFactoryUtil.forName(
						"classPK");

					disjunction.add(classPKProperty.eq(0L));

					DynamicQuery ddmStructureDynamicQuery =
						_ddmStructureLocalService.dynamicQuery();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long ddmStructureClassNameId = PortalUtil.getClassNameId(
						DDMStructure.class);

					dynamicQuery.add(
						classNameIdProperty.eq(ddmStructureClassNameId));

					long articleClassNameId = PortalUtil.getClassNameId(
						JournalArticle.class);

					ddmStructureDynamicQuery.add(
						classNameIdProperty.eq(articleClassNameId));

					ddmStructureDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("structureId"));

					disjunction.add(
						classPKProperty.in(ddmStructureDynamicQuery));

					dynamicQuery.add(disjunction);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				DDMTemplate.class.getName(), DDMStructure.class.getName()));

		return exportActionableDynamicQuery;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_journalArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ExportActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					if (portletDataContext.getBooleanParameter(
							JournalPortletDataHandler.NAMESPACE,
							"version-history")) {

						return;
					}

					Class<?> clazz = getClass();

					DynamicQuery versionArticleDynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							JournalArticle.class, "versionArticle",
							clazz.getClassLoader());

					versionArticleDynamicQuery.setProjection(
						ProjectionFactoryUtil.alias(
							ProjectionFactoryUtil.max("versionArticle.version"),
							"versionArticle.version"));

					// We need to use the "this" default alias to make sure the
					// database engine handles this subquery as a correlated
					// subquery

					versionArticleDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"this.resourcePrimKey",
							"versionArticle.resourcePrimKey"));

					Property versionProperty = PropertyFactoryUtil.forName(
						"version");

					dynamicQuery.add(
						versionProperty.eq(versionArticleDynamicQuery));
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(JournalArticle.class.getName()));

		return exportActionableDynamicQuery;
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
	public JournalArticle saveStagedModel(JournalArticle journalArticle)
		throws PortalException {

		return _journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	@Override
	public JournalArticle updateStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(journalArticle.getUserId());

		// Display date

		int[] displayDateValues = processDate(
			journalArticle.getDisplayDate(), user.getTimeZone());

		// Expiration date

		Date expirationDate = journalArticle.getExpirationDate();

		boolean neverExpire = (expirationDate != null) ? false : true;

		int[] expirationDateValues = processDate(
			expirationDate, user.getTimeZone());

		// Review date

		Date reviewDate = journalArticle.getReviewDate();

		boolean neverReview = (reviewDate != null) ? false : true;

		int[] reviewDateValues = processDate(reviewDate, user.getTimeZone());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		File smallFile = (File)serviceContext.getAttribute("smallFile");
		Map<String, byte[]> images =
			(Map<String, byte[]>)serviceContext.getAttribute("images");

		String articleUuid = journalArticle.getUuid();

		journalArticle = _journalArticleLocalService.updateArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), journalArticle.getTitleMap(),
			journalArticle.getDescriptionMap(), journalArticle.getContent(),
			journalArticle.getDDMStructureKey(),
			journalArticle.getDDMTemplateKey(), journalArticle.getLayoutUuid(),
			displayDateValues[0], displayDateValues[1], displayDateValues[2],
			displayDateValues[3], displayDateValues[4], expirationDateValues[0],
			expirationDateValues[1], expirationDateValues[2],
			expirationDateValues[3], expirationDateValues[4], neverExpire,
			reviewDateValues[0], reviewDateValues[1], reviewDateValues[2],
			reviewDateValues[3], reviewDateValues[4], neverReview,
			journalArticle.isIndexable(), journalArticle.isSmallImage(),
			journalArticle.getSmallImageURL(), smallFile, images, null,
			serviceContext);

		journalArticle.setUuid(articleUuid);

		return saveStagedModel(journalArticle);
	}

	protected int[] processDate(Date date, TimeZone timeZone) {
		int[] dateValues = new int[5];

		if (date == null) {
			return dateValues;
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone);

		calendar.setTime(date);

		dateValues[0] = calendar.get(java.util.Calendar.MONTH);
		dateValues[1] = calendar.get(java.util.Calendar.DATE);
		dateValues[2] = calendar.get(java.util.Calendar.YEAR);
		dateValues[3] = calendar.get(java.util.Calendar.HOUR);
		dateValues[4] = calendar.get(java.util.Calendar.MINUTE);

		if (calendar.get(java.util.Calendar.AM_PM) == java.util.Calendar.PM) {

			// adjusting the hour

			dateValues[3] += 12;
		}

		return dateValues;
	}

	@Reference
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference
	protected void setDDMTEmplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
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

	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}