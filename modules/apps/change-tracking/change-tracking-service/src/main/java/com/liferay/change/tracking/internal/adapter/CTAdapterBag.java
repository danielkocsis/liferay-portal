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

package com.liferay.change.tracking.internal.adapter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.model.CTModelAdapter;
import com.liferay.portal.kernel.change.tracking.service.CTServiceAdapter;
import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Preston Crary
 */
public class CTAdapterBag<T extends BaseModel<T>, C extends BaseModel<C>> {

	public CTAdapterBag(
		CTServiceAdapter<T, C> ctServiceAdapter,
		CTModelAdapter<T, C> ctModelAdapter, long classNameId) {

		_ctServiceAdapter = ctServiceAdapter;
		_ctModelAdapter = ctModelAdapter;
		_classNameId = classNameId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public CTModelAdapter<T, C> getCTModelAdapter() {
		return _ctModelAdapter;
	}

	public CTServiceAdapter<T, C> getCTServiceAdapter() {
		return _ctServiceAdapter;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{classNameId=", _classNameId, ", modelClass=",
			_ctModelAdapter.getModelClass(), "}");
	}

	private final long _classNameId;
	private final CTModelAdapter<T, C> _ctModelAdapter;
	private final CTServiceAdapter<T, C> _ctServiceAdapter;

}