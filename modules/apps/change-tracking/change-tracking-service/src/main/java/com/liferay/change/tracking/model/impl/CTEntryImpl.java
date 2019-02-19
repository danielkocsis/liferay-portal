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

package com.liferay.change.tracking.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.CTEntryBag;
import com.liferay.change.tracking.service.CTEntryBagLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class CTEntryImpl extends CTEntryBaseImpl {

	public CTEntryImpl() {
	}

	@Override
	public List<CTEntryBag> getCTEntryBags() {
		return CTEntryBagLocalServiceUtil.getCTEntryCTEntryBags(getCtEntryId());
	}

	@Override
	public boolean hasCTEntryBag() {
		int ctEntryCTEntryBagsCount =
			CTEntryBagLocalServiceUtil.getCTEntryCTEntryBagsCount(
				getCtEntryId());

		if (ctEntryCTEntryBagsCount == 0) {
			return false;
		}

		return true;
	}

}