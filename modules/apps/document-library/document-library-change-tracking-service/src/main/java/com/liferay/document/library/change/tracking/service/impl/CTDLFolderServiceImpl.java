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

package com.liferay.document.library.change.tracking.service.impl;

import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.document.library.change.tracking.service.base.CTDLFolderServiceBaseImpl;
import com.liferay.document.library.change.tracking.service.persistence.CTDLFolderFinderOverride;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The implementation of the ctdl folder remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.document.library.change.tracking.service.CTDLFolderService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTDLFolderServiceBaseImpl
 */
public class CTDLFolderServiceImpl extends CTDLFolderServiceBaseImpl {

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		if (queryDefinition.isIncludeOwner() &&
			(queryDefinition.getOwnerUserId() != 0)) {

			queryDefinition.setOwnerUserId(getUserId());
		}

		if (!ModelResourcePermissionHelper.contains(
				_dlFolderModelResourcePermission, getPermissionChecker(),
				groupId, folderId, ActionKeys.VIEW)) {

			return Collections.emptyList();
		}

		List<Object> objects = _dlFolderFinder.filterFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);

		Stream<Object> stream = objects.stream();

		objects = stream.map(
			this::_populateDLFileEntry
		).collect(
			Collectors.toList()
		);

		return objects;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.document.library.change.tracking.service.CTDLFolderServiceUtil</code> to access the ctdl folder remote service.
	 */
	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		if (!ModelResourcePermissionHelper.contains(
				_dlFolderModelResourcePermission, getPermissionChecker(),
				groupId, folderId, ActionKeys.VIEW)) {

			return 0;
		}

		if (queryDefinition.isIncludeOwner() &&
			(queryDefinition.getOwnerUserId() != 0)) {

			queryDefinition.setOwnerUserId(getUserId());
		}

		return _dlFolderFinder.filterCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition);
	}

	private Object _populateDLFileEntry(Object object) {
		if (!(object instanceof DLFileEntry)) {
			return object;
		}

		DLFileEntry fileEntry = (DLFileEntry)object;

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				PrincipalThreadLocal.getUserId(), fileEntry.getFileEntryId());

		if (!ctEntryOptional.isPresent()) {
			return object;
		}

		Optional<DLFileVersion> fileVersionOptional = ctEntryOptional.map(
			CTEntry::getModelClassPK
		).map(
			_dlFileVersionLocalService::fetchDLFileVersion
		);

		if (!fileVersionOptional.isPresent()) {
			return object;
		}

		DLFileVersion fileVersion = fileVersionOptional.get();

		fileEntry.setModifiedDate(fileVersion.getModifiedDate());
		fileEntry.setFileName(fileVersion.getFileName());
		fileEntry.setExtension(fileVersion.getExtension());
		fileEntry.setMimeType(fileVersion.getMimeType());
		fileEntry.setTitle(fileVersion.getTitle());
		fileEntry.setDescription(fileVersion.getDescription());
		fileEntry.setExtraSettings(fileVersion.getExtraSettings());
		fileEntry.setVersion(fileVersion.getVersion());
		fileEntry.setSize(fileVersion.getSize());
		fileEntry.setLastPublishDate(fileVersion.getLastPublishDate());

		return fileEntry;
	}

	private static volatile ModelResourcePermission<DLFolder>
		_dlFolderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CTDLFolderServiceImpl.class, "_dlFolderModelResourcePermission",
				DLFolder.class);

	@ServiceReference(type = CTManager.class)
	private CTManager _ctManager;

	@ServiceReference(type = DLFileVersionLocalService.class)
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = CTDLFolderFinderOverride.class)
	private CTDLFolderFinderOverride _dlFolderFinder;

}