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

package com.liferay.change.tracking.change.lists.web.internal.portlet.action;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS,
		"mvc.command.name=/", "mvc.command.name=/change_lists/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();

		boolean reset = ParamUtil.getBoolean(renderRequest, "reset");

		if (reset) {
			_resetCheckedOutCTCollection(themeDisplay.getCompanyId(), userId);

			return "/view.jsp";
		}

		Optional<CTCollection> activeCTCollectionOptional =
			_ctEngineManager.getActiveCTCollectionOptional(userId);

		if (!activeCTCollectionOptional.isPresent()) {
			return "/view.jsp";
		}

		CTCollection activeCTCollection = activeCTCollectionOptional.get();

		if (activeCTCollection.isProduction()) {
			return "/view.jsp";
		}

		return "/overview.jsp";
	}

	private void _resetCheckedOutCTCollection(long companyId, long userId) {
		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(companyId);

		CTCollection ctCollection = ctCollectionOptional.get();

		_ctEngineManager.checkoutCTCollection(
			userId, ctCollection.getCtCollectionId());
	}

	@Reference
	private CTEngineManager _ctEngineManager;

}