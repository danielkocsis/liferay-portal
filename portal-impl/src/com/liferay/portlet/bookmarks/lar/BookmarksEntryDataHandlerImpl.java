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
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class BookmarksEntryDataHandlerImpl
	extends StagedModelDataHandlerImpl<BookmarksEntry>
	implements StagedModelDataHandler<BookmarksEntry> {

	@Override
	public void export(
			BookmarksEntry entry, PortletDataContext context,
			Element parentElement)
		throws Exception {

		String path = ExportImportPathUtil.getEntityPath(entry);

		if (!context.isPathNotProcessed(path) ||
			!context.isWithinDateRange(entry.getModifiedDate())) {

			return;
		}

		Element entryElement = parentElement.addElement("entry");

		context.addClassedModel(
			entryElement, path, entry,
			BookmarksPortletDataHandlerImpl.NAMESPACE);
	}

	@Override
	public void importData(Element entityElement, PortletDataContext context)
		throws Exception {

		BookmarksEntry entry = (BookmarksEntry)context.getZipEntryAsObject(
			entityElement, entityElement.attributeValue("path"));

		String path = ExportImportPathUtil.getEntityPath(entry);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		long userId = context.getUserId(entry.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
				folderIds, entry.getFolderId(), entry.getFolderId());

		ServiceContext serviceContext = context.createServiceContext(
			entityElement.attributeValue("path"), entry,
			BookmarksPortletDataHandlerImpl.NAMESPACE);

		BookmarksEntry importedEntry = null;

		if (context.isDataStrategyMirror()) {
			BookmarksEntry existingEntry = BookmarksEntryUtil.fetchByUUID_G(
				entry.getUuid(), context.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
					userId, context.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
			else {
				importedEntry = BookmarksEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(),
					context.getScopeGroupId(), folderId, entry.getName(),
					entry.getUrl(), entry.getDescription(), serviceContext);
			}
		}
		else {
			importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
				userId, context.getScopeGroupId(), folderId, entry.getName(),
				entry.getUrl(), entry.getDescription(), serviceContext);
		}
	}

}