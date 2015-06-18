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

package com.liferay.portlet.exportimport.staging.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Jorge Ferrer
 */
public class StagingPermissionUtil {

	public static StagingPermission getStagingPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			StagingPermissionUtil.class);

		StagingPermission stagingPermission =
			_instance._serviceTracker.getService();

		if (stagingPermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("StagingPermissionUtil has not been initialized");
			}

			return null;
		}

		return stagingPermission;
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		return getStagingPermission().hasPermission(
			permissionChecker, group, className, classPK, portletId, actionId);
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		return getStagingPermission().hasPermission(
			permissionChecker, groupId, className, classPK, portletId,
			actionId);
	}

	private StagingPermissionUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(StagingPermission.class);

		_serviceTracker.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingPermissionUtil.class);

	private static final StagingPermissionUtil _instance =
		new StagingPermissionUtil();

	private final ServiceTracker<StagingPermission, StagingPermission>
		_serviceTracker;

}