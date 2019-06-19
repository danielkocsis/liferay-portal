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

package com.liferay.portal.kernel.change.tracking;

import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

/**
 * @author Preston Crary
 */
public interface CTAdapter<T extends BaseModel<T>, C extends BaseModel<C>> {

	public C createContextModel(T model);

	public T fetchByPrimaryKey(long primaryKey);

	public C fetchContextModel(long primaryKey, long ctCollectionId);

	public List<C> fetchContextModels(long[] primaryKeys, long ctCollectionId);

	public List<T> findByCTCollectionId(long ctCollectionId);

	public Class<T> getModelClass();

	public long getModelCTCollectionId(T model);

	public long getModelPrimaryKey(C ctContextModel);

	public long getPrimaryKey(T model);

	public String getPrimaryKeyColumnName();

	public String getTableName();

	public void populateContextModel(T model, C ctContextModel);

	public void populateModel(T model, C ctContextModel);

	public void removeContext(C ctContextModel);

	public void removeContexts(T model);

	public void setModelContextCTCollectionId(
		C ctContextModel, long ctCollectionId);

	public void setModelCTCollectionId(
		T ctContextHolderModel, long ctCollectionId);

	public void updateContextModel(C ctContextModel);

}