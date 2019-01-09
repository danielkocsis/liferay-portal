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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Daniel Kocsis
 */
public class LayoutSetVersionCreateDateComparator
	extends OrderByComparator<LayoutSetVersion> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public LayoutSetVersionCreateDateComparator() {
		this(false);
	}

	public LayoutSetVersionCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		LayoutSetVersion layoutSetVersion1,
		LayoutSetVersion layoutSetVersion2) {

		int value = DateUtil.compareTo(
			layoutSetVersion1.getCreateDate(),
			layoutSetVersion2.getCreateDate());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}