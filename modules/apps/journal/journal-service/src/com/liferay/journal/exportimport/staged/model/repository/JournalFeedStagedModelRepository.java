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

import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.journal.lar.JournalCreationStrategy;
import com.liferay.journal.lar.JournalCreationStrategyFactory;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalFeed"},
	service = StagedModelRepository.class
)
public class JournalFeedStagedModelRepository
	extends BaseStagedModelRepository<JournalFeed> {

	@Override
	public JournalFeed addStagedModel(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortalException {

		String feedId = journalFeed.getFeedId();

		boolean autoFeedId = false;

		if (Validator.isNumber(feedId) ||
			(_journalFeedLocalService.fetchFeed(
				portletDataContext.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		long userId = portletDataContext.getUserId(journalFeed.getUserUuid());

		try {
			JournalCreationStrategy creationStrategy =
				JournalCreationStrategyFactory.getInstance();

			long authorId = creationStrategy.getAuthorUserId(
				portletDataContext, journalFeed);

			if (authorId !=
					JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {

				userId = authorId;
			}
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		ServiceContext serviceContext = getServiceContext(
			portletDataContext, journalFeed);

		return _journalFeedLocalService.addFeed(
			userId, journalFeed.getGroupId(), feedId, autoFeedId,
			journalFeed.getName(), journalFeed.getDescription(),
			journalFeed.getDDMStructureKey(), journalFeed.getDDMTemplateKey(),
			journalFeed.getDDMRendererTemplateKey(), journalFeed.getDelta(),
			journalFeed.getOrderByCol(), journalFeed.getOrderByType(),
			journalFeed.getTargetLayoutFriendlyUrl(),
			journalFeed.getTargetPortletId(), journalFeed.getContentField(),
			journalFeed.getFeedFormat(), journalFeed.getFeedVersion(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(JournalFeed feed) throws PortalException {
		_journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFeed feed = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (feed != null) {
			deleteStagedModel(feed);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public JournalFeed fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFeedLocalService.fetchJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFeed> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFeed>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _journalFeedLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public JournalFeed saveStagedModel(JournalFeed journalFeed)
		throws PortalException {

		return _journalFeedLocalService.updateJournalFeed(journalFeed);
	}

	@Override
	public JournalFeed updateStagedModel(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(
			portletDataContext, journalFeed);

		try {
			JournalCreationStrategy creationStrategy =
				JournalCreationStrategyFactory.getInstance();

			boolean addGroupPermissions = creationStrategy.addGroupPermissions(
				portletDataContext, journalFeed);

			serviceContext.setAddGroupPermissions(addGroupPermissions);

			boolean addGuestPermissions = creationStrategy.addGuestPermissions(
				portletDataContext, journalFeed);

			serviceContext.setAddGuestPermissions(addGuestPermissions);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		return _journalFeedLocalService.updateFeed(
			journalFeed.getGroupId(), journalFeed.getFeedId(),
			journalFeed.getName(), journalFeed.getDescription(),
			journalFeed.getDDMStructureKey(), journalFeed.getDDMTemplateKey(),
			journalFeed.getDDMRendererTemplateKey(), journalFeed.getDelta(),
			journalFeed.getOrderByCol(), journalFeed.getOrderByType(),
			journalFeed.getTargetLayoutFriendlyUrl(),
			journalFeed.getTargetPortletId(), journalFeed.getContentField(),
			journalFeed.getFeedFormat(), journalFeed.getFeedVersion(),
			serviceContext);
	}

	protected ServiceContext getServiceContext(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalFeed);

		try {
			JournalCreationStrategy creationStrategy =
				JournalCreationStrategyFactory.getInstance();

			boolean addGroupPermissions = creationStrategy.addGroupPermissions(
				portletDataContext, journalFeed);

			serviceContext.setAddGroupPermissions(addGroupPermissions);

			boolean addGuestPermissions = creationStrategy.addGuestPermissions(
				portletDataContext, journalFeed);

			serviceContext.setAddGuestPermissions(addGuestPermissions);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(journalFeed.getUuid());
		}

		return serviceContext;
	}

	@Reference
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	private JournalFeedLocalService _journalFeedLocalService;

}