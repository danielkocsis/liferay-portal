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

package com.liferay.staging.taglib.action;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=exportImportEntity"
	},
	service = MVCActionCommand.class
)
public class ExportImportEntityMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_log.error("ExportImportEntityMVCActionCommand called!!");

		if (Validator.isNotNull(
				actionRequest.getParameter("classNameClassPK"))) {

			String[] classNameClassPKArray = ParamUtil.getStringValues(
				actionRequest, "classNameClassPK");

			for (String classNameClassPK : classNameClassPKArray) {
				_log.error("###> " + classNameClassPK);
			}
		}

		if (Validator.isNotNull(actionRequest.getParameter("className"))) {
			_log.error(
				"className: " +
					ParamUtil.getString(actionRequest, "className"));

			_log.error(
				"classPK: " + ParamUtil.getLong(actionRequest, "classPK"));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportEntityMVCActionCommand.class);

}