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

package com.liferay.portal.service.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.util.test.TestPropsValues;

import java.util.Map;

/**
 * @author Manuel de la Peña
 */
public class StagingTestUtil {

	public static void addPortletConfigurationAllParameter(
		Map<String, String[]> parameters, boolean stagePortletConfigurationAll,
		ServiceContext serviceContext) {

		_addParameter(
			parameters, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			stagePortletConfigurationAll, serviceContext);
	}

	public static void addPortletDataAllParameter(
		Map<String, String[]> parameters, boolean stagePortletDataAll,
		ServiceContext serviceContext) {

		_addParameter(
			parameters, PortletDataHandlerKeys.PORTLET_DATA_ALL,
			stagePortletDataAll, serviceContext);
	}

	public static void addStagedPortletParameters(
		Map<String, String[]> parameters, String portletId,
		boolean stagePortletConfiguration, boolean stagePortletData,
		boolean stagePortletId, boolean stagePortletSetup,
		ServiceContext serviceContext) {

		parameters.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION +
				StringPool.UNDERLINE + portletId,
			new String[] {String.valueOf(stagePortletConfiguration)});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				portletId,
			new String[] {String.valueOf(stagePortletData)});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_SETUP + StringPool.UNDERLINE +
				portletId,
			new String[] {String.valueOf(stagePortletSetup)});

		serviceContext.setAttribute(
			StagingUtil.getStagedPortletId(portletId), stagePortletId);

		for (String parameterName : parameters.keySet()) {
			serviceContext.setAttribute(
				parameterName, parameters.get(parameterName)[0]);
		}
	}

	public static void enableLocalStaging(
			Group group, boolean branchingPublic, boolean branchingPrivate,
			ServiceContext serviceContext)
		throws PortalException {

		enableLocalStaging(
			TestPropsValues.getUserId(), group, branchingPublic,
			branchingPrivate, serviceContext);
	}

	public static void enableLocalStaging(
			Group group, ServiceContext serviceContext)
		throws PortalException {

		enableLocalStaging(group, false, false, serviceContext);
	}

	public static void enableLocalStaging(
			long userId, Group group, boolean branchingPublic,
			boolean branchingPrivate, ServiceContext serviceContext)
		throws PortalException {

		StagingLocalServiceUtil.enableLocalStaging(
			userId, group, branchingPublic, branchingPrivate, serviceContext);
	}

	private static void _addParameter(
		Map<String, String[]> parameters, String portletDataHandlerKey,
		boolean portletDataHandlerValue, ServiceContext serviceContext) {

		parameters.put(
			portletDataHandlerKey,
			new String[] {String.valueOf(portletDataHandlerValue)});

		serviceContext.setAttribute(
			portletDataHandlerKey, String.valueOf(portletDataHandlerValue));
	}

}