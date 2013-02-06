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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BookmarksFolderStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	protected StagedModel addStagedModel(Group group) throws Exception {
		return BookmarksTestUtil.addFolder(group.getGroupId(), "Test Folder");
	}

	protected StagedModel getStagedModel(Group group, String uuid) {
		try {
			return BookmarksFolderLocalServiceUtil.
				getBookmarksFolderByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

}