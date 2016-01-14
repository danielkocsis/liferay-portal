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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Andrew Betts
 */
public class BackgroundTaskStatusTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public BackgroundTask getBackgroundTask() {
		return _backgroundTask;
	}

	public BackgroundTaskDisplay getBackgroundTaskDisplay() {
		return _backgroundTaskDisplay;
	}

	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	@Override
	public String getEndPage() {
		return _END_PAGE;
	}

	@Override
	public String getStartPage() {
		return _START_PAGE;
	}

	public void setBackgroundTaskId(long backgroundTaskId) {
		_backgroundTaskId = backgroundTaskId;
	}

	@Override
	protected void cleanUp() {
		_backgroundTask = null;
		_backgroundTaskDisplay = null;
		_backgroundTaskId = 0;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (_backgroundTask == null) {
			try {
				_backgroundTask = BackgroundTaskManagerUtil.getBackgroundTask(
					_backgroundTaskId);
			}
			catch (PortalException e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		if (_backgroundTaskDisplay == null) {
			_backgroundTaskDisplay =
				BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(
					_backgroundTaskId);
		}

		setNamespacedAttribute(request, "backgroundTask", _backgroundTask);
		setNamespacedAttribute(
			request, "backgroundTaskDisplay", _backgroundTaskDisplay);
		setNamespacedAttribute(request, "backgroundTaskId", _backgroundTaskId);
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-ui:backgroundTaskStatus:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE =
		"/html/taglib/ui/background_task_status/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/background_task_status/start.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskStatusTag.class);

	private BackgroundTask _backgroundTask;
	private BackgroundTaskDisplay _backgroundTaskDisplay;
	private long _backgroundTaskId;

}