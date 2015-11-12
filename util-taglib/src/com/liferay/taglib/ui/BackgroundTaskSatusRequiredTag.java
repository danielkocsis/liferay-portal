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

import javax.servlet.jsp.JspException;

/**
 * @author Andrew Betts
 */
public abstract class BackgroundTaskSatusRequiredTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		BackgroundTaskStatusTag backgroundTaskStatusTag =
			(BackgroundTaskStatusTag)findAncestorWithClass(
				this, BackgroundTaskStatusTag.class);

		if (backgroundTaskStatusTag == null) {
			throw new JspException(
				"Requires liferay-ui:background-task-status");
		}

		return super.doEndTag();
	}

}