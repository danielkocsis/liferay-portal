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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseBackgroundTaskExecutor
	implements BackgroundTaskExecutor {

	@Override
	public BackgroundTaskStatusMessageTranslator
		getBackgroundTaskStatusMessageTranslator() {

		return _backgroundTaskStatusMessageTranslator;
	}

	@Override
	public int getIsolationLevel() {
		if (_isolationLevel == 0) {
			_isolationLevel = BackgroundTaskConstants.ISOLATION_LEVEL_CLASS;
		}

		return _isolationLevel;
	}

	@Override
	public String handleException(BackgroundTask backgroundTask, Exception e) {
		return "Unable to execute background task: " + e.getMessage();
	}

	@Override
	public boolean isSerial() {
		return _serial;
	}

	protected ExportImportConfiguration getExportImportConfiguration(
			BackgroundTask backgroundTask)
		throws PortalException {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId");

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		return exportImportConfiguration;
	}

	protected Locale getLocale(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long userId = MapUtil.getLong(taskContextMap, "userId");

		if (userId > 0) {
			try {
				User user = UserLocalServiceUtil.fetchUser(userId);

				if (user != null) {
					return user.getLocale();
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get the user's locale", e);
				}
			}
		}

		return LocaleUtil.getDefault();
	}

	protected void setBackgroundTaskStatusMessageTranslator(
		BackgroundTaskStatusMessageTranslator
			backgroundTaskStatusMessageTranslator) {

		_backgroundTaskStatusMessageTranslator =
			backgroundTaskStatusMessageTranslator;
	}

	protected void setIsolationLevel(int isolationLevel) {
		_isolationLevel = isolationLevel;
	}

	protected void setSerial(boolean serial) {
		_serial = serial;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseBackgroundTaskExecutor.class);

	private BackgroundTaskStatusMessageTranslator
		_backgroundTaskStatusMessageTranslator;
	private int _isolationLevel;
	private boolean _serial;

}