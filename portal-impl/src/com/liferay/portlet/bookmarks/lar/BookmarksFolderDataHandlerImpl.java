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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedDataHandlerImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class BookmarksFolderDataHandlerImpl
	extends StagedDataHandlerImpl<BookmarksFolder>
	implements BookmarksFolderDataHandler {

	public void export(
			BookmarksFolder folder, PortletDataContext context,
			Element parentElement)
		throws Exception {

		String path = ExportImportPathUtil.getEntityPath(folder);

		if (!context.isPathNotProcessed(path) ||
			!context.isWithinDateRange(folder.getModifiedDate())) {

			return;
		}

		List<BookmarksFolder> children = BookmarksFolderUtil.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		if (children != null) {
			for (BookmarksFolder childFolder : children) {
				export(childFolder, context, parentElement);
			}
		}

		List<BookmarksEntry> childEntries = BookmarksEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		if (childEntries != null) {
			for (BookmarksEntry childEntry : childEntries) {
				bookmarksEntryDataHandler.export(
					childEntry, context, parentElement);
			}
		}

		Element folderElement = parentElement.addElement("folder");

		context.addClassedModel(
			folderElement, path, folder,
			BookmarksPortletDataHandlerImpl.NAMESPACE);
	}

	@Override
	public void importData(Element entityElement, PortletDataContext context)
		throws Exception {

		BookmarksFolder folder = (BookmarksFolder)context.getZipEntryAsObject(
			entityElement, entityElement.attributeValue("path"));

		String path = ExportImportPathUtil.getEntityPath(folder);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		long userId = context.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = context.createServiceContext(
			entityElement.attributeValue("path"), folder,
			BookmarksPortletDataHandlerImpl.NAMESPACE);

		BookmarksFolder importedFolder = null;

		if (context.isDataStrategyMirror()) {
			BookmarksFolder existingFolder = BookmarksFolderUtil.fetchByUUID_G(
				folder.getUuid(), context.getScopeGroupId());

			if (existingFolder == null) {
				serviceContext.setUuid(folder.getUuid());

				importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
					userId, parentFolderId, folder.getName(),
					folder.getDescription(), serviceContext);
			}
			else {
				importedFolder = BookmarksFolderLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId,
					folder.getName(), folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			importedFolder = BookmarksFolderLocalServiceUtil.addFolder(
				userId, parentFolderId, folder.getName(),
				folder.getDescription(), serviceContext);
		}

		// importDependencies(entry, context);

	}

	protected String getImportFolderPath(
		PortletDataContext context, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			getSourcePortletPath(
				context.getScopeGroupId(), PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

}