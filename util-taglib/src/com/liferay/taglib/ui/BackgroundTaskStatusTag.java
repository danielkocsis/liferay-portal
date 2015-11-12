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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrew Betts
 */
public class BackgroundTaskStatusTag extends IncludeTag {

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
		_backgroundTaskId = 0;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:backgroundTaskStatus:backgroundTaskId",
			_backgroundTaskId);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE =
		"/html/taglib/ui/background_task_status/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/background_task_status/start.jsp";

	private long _backgroundTaskId;

}