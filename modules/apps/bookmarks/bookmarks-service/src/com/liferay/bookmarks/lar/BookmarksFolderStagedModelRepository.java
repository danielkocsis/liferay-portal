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

package com.liferay.bookmarks.lar;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.StagedModel;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mate Thurzo
 */
@Component(
	property = {"model.name=com.liferay.bookmarks.model.BookmarksFolder"})
public class BookmarksFolderStagedModelRepository
	implements StagedModelRepository<BookmarksFolder> {

	@Override
	public List<? extends StagedModel> fetchChildStagedModels(
		BookmarksFolder bookmarksFolder) {

		List<StagedModel> childStagedModels = new ArrayList<>();

		childStagedModels.addAll(
			BookmarksFolderLocalServiceUtil.getFolders(
				bookmarksFolder.getGroupId(), bookmarksFolder.getFolderId()));

		childStagedModels.addAll(
			BookmarksEntryLocalServiceUtil.getEntries(
				bookmarksFolder.getGroupId(), bookmarksFolder.getFolderId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return childStagedModels;
	}

	@Override
	public List<? extends StagedModel> fetchParentStagedModels(
		BookmarksFolder bookmarksFolder) {

		List<BookmarksFolder> parentFolders = new ArrayList<>();

		if (bookmarksFolder.getParentFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			try {
				parentFolders.add(bookmarksFolder.getParentFolder());
			}
			catch (Exception e) {
			}
		}

		return parentFolders;
	}

	@Override
	public BookmarksFolder fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<BookmarksFolder> folders =
			BookmarksFolderLocalServiceUtil.
				getBookmarksFoldersByUuidAndCompanyId(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					new StagedModelModifiedDateComparator<BookmarksFolder>());

		if (ListUtil.isEmpty(folders)) {
			return null;
		}

		return folders.get(0);
	}

	@Override
	public BookmarksFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return BookmarksFolderLocalServiceUtil.
			fetchBookmarksFolderByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<BookmarksFolder> fetchStagedModels(long groupId) {
		return BookmarksFolderLocalServiceUtil.getFolders(groupId);
	}

	@Override
	public List<BookmarksFolder> fetchStagedModels(
		PortletDataContext portletDataContext) {

		return null;
	}

}