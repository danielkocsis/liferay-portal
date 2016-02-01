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

package com.liferay.blogs.exportimport.staged.model.repository;

import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portlet.blogs.model.BlogsEntry"},
	service = StagedModelRepository.class
)
public class BlogsEntryStagedModelRepository
	extends BaseStagedModelRepository<BlogsEntry> {

	@Override
	public BlogsEntry addStagedModel(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(blogsEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			blogsEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(blogsEntry.getUuid());
		}

		return _blogsEntryLocalService.addEntry(
			userId, blogsEntry.getTitle(), blogsEntry.getSubtitle(),
			blogsEntry.getDescription(), blogsEntry.getContent(),
			blogsEntry.getDisplayDate(), blogsEntry.getAllowPingbacks(),
			blogsEntry.getAllowTrackbacks(),
			StringUtil.split(blogsEntry.getTrackbacks()),
			blogsEntry.getCoverImageCaption(), null, null, serviceContext);
	}

	@Override
	public void deleteStagedModel(BlogsEntry blogsEntry)
		throws PortalException {

		_blogsEntryLocalService.deleteEntry(blogsEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BlogsEntry blogsEntry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (blogsEntry != null) {
			deleteStagedModel(blogsEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_blogsEntryLocalService.deleteEntries(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public BlogsEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<BlogsEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<BlogsEntry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _blogsEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(blogsEntry.getUserUuid());

		BlogsEntry existingBlogsEntry = fetchStagedModelByUuidAndGroupId(
			blogsEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingBlogsEntry == null) ||
			!isStagedModelInTrash(existingBlogsEntry)) {

			return;
		}

		TrashHandler trashHandler = existingBlogsEntry.getTrashHandler();

		try {
			if (trashHandler.isRestorable(existingBlogsEntry.getEntryId())) {
				trashHandler.restoreTrashEntry(
					userId, existingBlogsEntry.getEntryId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public BlogsEntry saveStagedModel(BlogsEntry blogsEntry)
		throws PortalException {

		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	@Override
	public BlogsEntry updateStagedModel(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(blogsEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			blogsEntry);

		return _blogsEntryLocalService.updateEntry(
			userId, blogsEntry.getEntryId(), blogsEntry.getTitle(),
			blogsEntry.getSubtitle(), blogsEntry.getDescription(),
			blogsEntry.getContent(), blogsEntry.getDisplayDate(),
			blogsEntry.getAllowPingbacks(), blogsEntry.getAllowTrackbacks(),
			StringUtil.split(blogsEntry.getTrackbacks()),
			blogsEntry.getCoverImageCaption(), new ImageSelector(),
			new ImageSelector(), serviceContext);
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;

}