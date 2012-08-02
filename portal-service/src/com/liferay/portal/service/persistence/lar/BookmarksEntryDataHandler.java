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

import com.liferay.portal.kernel.lar.DataHandlerContext;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.service.persistence.BaseDataHandler;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;

/**
 * @author Mate Thurzo
 */
public interface BookmarksEntryDataHandler
	extends BaseDataHandler<BookmarksEntry> {

	public LarDigestItem doDigest(
			BookmarksEntry entry, DataHandlerContext context)
		throws Exception;

	public void doImportData(LarDigestItem item, DataHandlerContext context)
		throws Exception;

	public BookmarksEntry getEntity(String classPK);

}
