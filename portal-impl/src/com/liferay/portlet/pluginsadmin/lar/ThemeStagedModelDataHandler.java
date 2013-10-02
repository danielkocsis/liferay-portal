/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.pluginsadmin.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.ThemeLocalServiceUtil;

import java.util.List;

/**
 * @author Mate Thurzo
 */
public class ThemeStagedModelDataHandler
	extends BaseStagedModelDataHandler<ThemeStagingWrapper> {

	public static final String[] CLASS_NAMES =
		{ThemeStagingWrapper.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(ThemeStagingWrapper themeStagingWrapper) {
		return themeStagingWrapper.getThemeId();
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element rootElement,
		Element referenceElement) {

		boolean importThemeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.THEME_REFERENCE);

		if (!importThemeSettings) {
			return true;
		}

		String elementName = referenceElement.getName();

		if (elementName.equals("missing-reference")) {
			String classPK = referenceElement.attributeValue("class-pk");

			List<Theme> themes = ThemeLocalServiceUtil.getThemes(
				portletDataContext.getCompanyId());

			for (Theme theme : themes) {
				if (theme.getThemeId().equals(classPK)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			ThemeStagingWrapper themeStagingWrapper)
		throws Exception {
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			ThemeStagingWrapper themeStagingWrapper)
		throws Exception {
	}

}