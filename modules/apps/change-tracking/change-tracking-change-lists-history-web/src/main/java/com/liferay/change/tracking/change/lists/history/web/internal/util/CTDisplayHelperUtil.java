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

package com.liferay.change.tracking.change.lists.history.web.internal.util;

import com.liferay.change.tracking.display.CTDisplayHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = {})
public class CTDisplayHelperUtil {

	public static CTDisplayHelper getCTDisplayHelper() {
		return _ctDisplayHelper;
	}

	@Reference(unbind = "-")
	protected void setCTDisplayHelper(CTDisplayHelper ctDisplayHelper) {
		_ctDisplayHelper = ctDisplayHelper;
	}

	private static CTDisplayHelper _ctDisplayHelper;

}