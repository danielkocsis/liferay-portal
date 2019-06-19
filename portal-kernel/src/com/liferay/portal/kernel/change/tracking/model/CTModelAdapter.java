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

package com.liferay.portal.kernel.change.tracking.model;

import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Preston Crary
 */
public interface CTModelAdapter
	<T extends BaseModel<T>, C extends BaseModel<C>> {

	public long getCTCollectionId(T model);

	public default long getGroupId(T model) {
		return 0;
	}

	public Class<T> getModelClass();

	public long getModelPrimaryKey(C modelCT);

	public long getPrimaryKey(T model);

	public String getPrimaryKeyColumnDBName();

	public String getPrimaryKeyColumnName();

	public void populateModel(T model, C modelCT);

	public void populateModelCT(T model, C modelCT);

	public void setModelCTCollectionId(T model, long ctCollectionId);

	public void setModelCTCTCollectionId(C modelCT, long ctCollectionId);

}