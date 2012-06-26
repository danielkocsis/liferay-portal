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
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.lar.digest.LarDigestItemImpl;
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.service.persistence.impl.BaseLarPersistenceImpl;
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
public class BookmarksFolderLarPersistenceImpl
	extends BaseLarPersistenceImpl<BookmarksFolder>
	implements BookmarksFolderLarPersistence {


	public void deserialize(Document document) {
	}

	@Override
	protected void doDigest(
			BookmarksFolder folder, LarDigest digest,
			PortletDataContext portletDataContext)
		throws Exception {

		String path = getFolderPath(portletDataContext, folder);

		if (!isPathProcessed(path)) {
			LarDigestItem digestItem = new LarDigestItemImpl();

			digestItem.setAction(LarDigesterConstants.ACTION_ADD);
			digestItem.setPath(path);
			digestItem.setType(BookmarksFolder.class.getName());
			digestItem.setClassPK(StringUtil.valueOf(folder.getFolderId()));

			digest.write(digestItem);
		}
	}

	private String getFolderPath(
		PortletDataContext portletDataContext, BookmarksFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.BOOKMARKS));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

}
