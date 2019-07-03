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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

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
		return null;
	}

	@Override
	public String getModelDisplayName(
		PortletPreferences model, Locale locale) {
		return null;
	}

	@Reference
	private Language _language;

}
