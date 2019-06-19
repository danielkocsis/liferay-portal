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

import java.io.Serializable;

import java.util.List;

/**
 * @author Preston Crary
 */
public interface CTAdapter<T extends BaseModel<T>, C extends BaseModel<C>> {

	public C createContextModel(T ctContextHolderModel);

	public T fetchByPrimaryKey(Serializable primaryKey);

	public C fetchContextModel(Serializable primaryKey, long contextId);

	public List<T> findByContextId(long contextId);

	public Class<T> getModelClass();

	public Class<C> getContextModelClass();

	public long getModelContextId(T ctContextHolderModel);

	public Serializable getPrimaryKey(T ctContextHolderModel);

	public String getPrimaryKeyColumnName();

	public String getTableName();

	public void populateContextHolderModel(
		T ctContextHolderModel, C ctContextModel);

	public void populateContextModel(T ctContextHolderModel, C ctContextModel);

	public void removeContext(C ctContextModel);

	public void removeContexts(T ctContextHolderModel);

	public void setContextContextId(C ctContextModel, long contextId);

	public void setModelContextId(T ctContextHolderModel, long contextId);

	public void updateContextModel(C ctContextModel);

}