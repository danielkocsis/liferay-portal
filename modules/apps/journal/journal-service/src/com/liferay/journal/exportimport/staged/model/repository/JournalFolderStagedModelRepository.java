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
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.persistence.JournalFolderUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
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
	property = {"model.class.name=com.liferay.journal.model.JournalFolder"},
	service = StagedModelRepository.class
)
public class JournalFolderStagedModelRepository
	extends BaseStagedModelRepository<JournalFolder> {

	@Override
	public JournalFolder addStagedModel(
			PortletDataContext portletDataContext, JournalFolder journalFolder)
		throws PortalException {

		long userId = portletDataContext.getUserId(journalFolder.getUserUuid());

		String name = getFolderName(
			null, journalFolder.getGroupId(), journalFolder.getParentFolderId(),
			journalFolder.getName(), 2);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalFolder);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(journalFolder.getUuid());
		}

		return _journalFolderLocalService.addFolder(
			userId, journalFolder.getGroupId(),
			journalFolder.getParentFolderId(), name,
			journalFolder.getDescription(), serviceContext);
	}

	@Override
	public void deleteStagedModel(JournalFolder folder) throws PortalException {
		_journalFolderLocalService.deleteFolder(folder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFolder folder = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (folder != null) {
			deleteStagedModel(folder);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_journalFolderLocalService.deleteFolders(
			portletDataContext.getGroupId());
	}

	@Override
	public JournalFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFolderLocalService.fetchJournalFolderByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFolderLocalService.getJournalFoldersByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFolder>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _journalFolderLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !existingFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingFolder.getTrashHandler();

		try {
			if (trashHandler.isRestorable(existingFolder.getFolderId())) {
				trashHandler.restoreTrashEntry(
					userId, existingFolder.getFolderId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public JournalFolder saveStagedModel(JournalFolder journalFolder)
		throws PortalException {

		return _journalFolderLocalService.updateJournalFolder(journalFolder);
	}

	@Override
	public JournalFolder updateStagedModel(
			PortletDataContext portletDataContext, JournalFolder journalFolder)
		throws PortalException {

		long userId = portletDataContext.getUserId(journalFolder.getUserUuid());

		String name = getFolderName(
			journalFolder.getUuid(), journalFolder.getGroupId(),
			journalFolder.getParentFolderId(), journalFolder.getName(), 2);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalFolder);

		return _journalFolderLocalService.updateFolder(
			userId, journalFolder.getGroupId(), journalFolder.getFolderId(),
			journalFolder.getParentFolderId(), name,
			journalFolder.getDescription(), false, serviceContext);
	}

	protected String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count)
		throws PortalException {

		JournalFolder folder = JournalFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
	}

	@Reference
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private JournalFolderLocalService _journalFolderLocalService;

}