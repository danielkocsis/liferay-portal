/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.lar.digest.LarDigestItemImpl;
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;

import java.util.List;

/**
 * @author Mate Thurzo
 */
public class BookmarksPortletLarPersistenceImpl
	extends PortletLarPersistenceImpl
	implements BookmarksPortletLarPersistence {

	@Override
	protected void doDigest(
			Portlet object, LarDigest digest,
			PortletDataContext portletDataContext)
		throws Exception {

		/*portletDataContext.addPermissions(
			"com.liferay.portlet.bookmarks",
			portletDataContext.getScopeGroupId());*/

		List<BookmarksFolder> folders = BookmarksFolderUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (BookmarksFolder folder : folders) {
			exportFolder(folder, digest, portletDataContext);
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			portletDataContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksEntry entry : entries) {
			exportEntry(entry, digest, portletDataContext);
		}
	}

	protected void exportEntry(
			BookmarksEntry entry, LarDigest larDigest,
			PortletDataContext portletDataContext)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		long parentForlderId = entry.getFolderId();

		if (parentForlderId > 0) {
			exportParentFolder(larDigest, parentForlderId, portletDataContext);
		}

		bookmarksEntryLarPersistence.digest(
			entry, larDigest, portletDataContext);
	}

	private void exportFolder(
			BookmarksFolder folder, LarDigest digest,
			PortletDataContext portletDataContext)
		throws Exception{

		if (portletDataContext.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				digest, folder.getParentFolderId(), portletDataContext);

			bookmarksFolderLarPersistence.digest(
				folder, digest, portletDataContext);
		}

		List<BookmarksEntry> entries = BookmarksEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksEntry entry : entries) {
			bookmarksEntryLarPersistence.digest(
				entry, digest, portletDataContext);
		}
	}

	private void exportParentFolder(
			LarDigest larDigest, long folderId,
			PortletDataContext portletDataContext)
		throws Exception {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BookmarksFolder folder = BookmarksFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			larDigest, folder.getParentFolderId(), portletDataContext);

		bookmarksFolderLarPersistence.digest(
			folder, larDigest, portletDataContext);
	}

}
