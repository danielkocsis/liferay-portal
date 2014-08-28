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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.staging.Staging;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class PortletImportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public PortletImportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new PortletExportImportBackgroundTaskStatusMessageTranslator());
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long userId = MapUtil.getLong(taskContextMap, "userId");
		long groupId = MapUtil.getLong(taskContextMap, "groupId");

		StagingUtil.lockGroup(userId, groupId);

		long plid = MapUtil.getLong(taskContextMap, "plid");
		String portletId = MapUtil.getString(taskContextMap, "portletId");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)taskContextMap.get("parameterMap");

		List<FileEntry> attachmentsFileEntries =
			backgroundTask.getAttachmentsFileEntries();

		try {
			for (FileEntry attachmentsFileEntry : attachmentsFileEntries) {
				LayoutLocalServiceUtil.importPortletInfo(
					userId, plid, groupId, portletId, parameterMap,
					attachmentsFileEntry.getContentStream());
			}
		}
		finally {
			StagingUtil.unlockGroup(groupId);
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		JSONObject jsonObject = StagingUtil.getExceptionMessagesJSONObject(
			getLocale(backgroundTask), e, backgroundTask.getTaskContextMap());

		return jsonObject.toString();
	}

	@Override
	public boolean isLocked(BackgroundTask backgroundTask)
		throws PortalException {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long groupId = MapUtil.getLong(taskContextMap, "groupId");

		return LockLocalServiceUtil.isLocked(Staging.class.getName(), groupId);
	}

}