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

package com.liferay.portlet.preferences.change.tracking;

import com.liferay.portal.kernel.change.tracking.display.CTDisplayAdapter;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletLocalService;

import java.util.Locale;
import java.util.ResourceBundle;

import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = CTDisplayAdapter.class)
public class PortletPreferencesCTDisplayAdapter
	implements CTDisplayAdapter<PortletPreferences> {

	@Override
	public Class<PortletPreferences> getModelClass() {
		return PortletPreferences.class;
	}

	@Override
	public String getModelDisplayName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, PortletPreferencesCTDisplayAdapter.class);

		return ResourceBundleUtil.getString(
			resourceBundle,
			"model.resource.com.liferay.portal.kernel.model." +
				"PortletPreferences");
	}

	@Override
	public String getModelDisplayName(
		PortletPreferences portletPreferences, Locale locale) {

		Portlet portlet = _portletLocalService.fetchPortletById(
			portletPreferences.getCompanyId(),
			portletPreferences.getPortletId());

		if (portlet == null) {
			return portletPreferences.getPortletId();
		}

		return portlet.getDisplayName();
	}

	@Reference
	private PortletLocalService _portletLocalService;

}