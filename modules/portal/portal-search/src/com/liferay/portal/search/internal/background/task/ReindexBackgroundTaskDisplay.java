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

package com.liferay.portal.search.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskDetailsJSONObject;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskDisplay;

/**
 * @author Andrew Betts
 */
public class ReindexBackgroundTaskDisplay extends BaseBackgroundTaskDisplay {

	public ReindexBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		super(backgroundTask);
	}

	@Override
	public int getPercentage() {
		return -1;
	}

	@Override
	protected BackgroundTaskDetailsJSONObject createDetails(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	protected String createMessageKey() {
		return null;
	}

}