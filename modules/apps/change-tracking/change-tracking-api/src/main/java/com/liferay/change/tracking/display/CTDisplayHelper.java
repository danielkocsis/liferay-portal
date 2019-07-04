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

package com.liferay.change.tracking.display;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Locale;
import java.util.Map;

/**
 * @author Preston Crary
 */
public interface CTDisplayHelper {

	public String getDisplayName(long classNameId, Locale locale);

	public <T extends BaseModel<T>> String getDisplayName(
		T model, Locale locale);

	public String getDisplayName(CTEntry ctEntry, Locale locale);

	public Map<Long, String> getDisplayNames(Locale locale);

}