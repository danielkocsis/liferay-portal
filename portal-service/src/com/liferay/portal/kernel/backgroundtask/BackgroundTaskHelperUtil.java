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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BackgroundTask;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class BackgroundTaskHelperUtil {

	public static String getLockKey(
		BackgroundTaskExecutor backgroundTaskExecutor,
		BackgroundTask backgroundTask) {

		String lockKey = StringPool.BLANK;

		switch (backgroundTaskExecutor.getIsolationLevel()) {
			case BackgroundTaskConstants.ISOLATION_LEVEL_CLASS :
				lockKey = backgroundTask.getTaskExecutorClassName();
				break;
			case BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY :
				lockKey =
					backgroundTask.getTaskExecutorClassName() +
						StringPool.POUND + backgroundTask.getCompanyId();
				break;
			case BackgroundTaskConstants.ISOLATION_LEVEL_GROUP :
				lockKey =
					backgroundTask.getTaskExecutorClassName() +
						StringPool.POUND + backgroundTask.getGroupId();
				break;
			default:
				lockKey =
					backgroundTask.getTaskExecutorClassName() +
						StringPool.POUND +
							backgroundTaskExecutor.getIsolationLevel();
		}

		return lockKey;
	}

}