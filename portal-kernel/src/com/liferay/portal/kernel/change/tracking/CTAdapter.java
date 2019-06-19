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

	public T fetchByPrimaryKey(Serializable primaryKey);

	public List<T> findByContextId(long contextId);

	public C getContext(T ctContextHolderModel);

	public long getContextId();

	public Class<T> getModelClass();

	public Serializable getPrimaryKey(T ctContextHolderModel);

	public String getPrimaryKeyColumnName();

	public String getTableName();

	public void removeContext(Serializable primaryKey, long contextId);

	public void setContext(T ctContextHolderModel, C ctContextModel);

	public void setContextId(long contextId);

}