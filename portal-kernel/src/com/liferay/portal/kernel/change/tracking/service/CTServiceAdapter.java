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

package com.liferay.portal.kernel.change.tracking.service;

import com.liferay.portal.kernel.change.tracking.model.CTModelAdapter;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

/**
 * @author Preston Crary
 */
public interface CTServiceAdapter
	<T extends BaseModel<T>, C extends BaseModel<C>> {

	@Transactional(enabled = false)
	public C createModelCT(long classPK, long ctCollectionId);

	public T fetchByPrimaryKey(long classPK);

	public C fetchModelCT(long classPK, long ctCollectionId);

	public List<C> fetchModelCTs(long[] classPKs, long ctCollectionId);

	public List<T> findByCTCollectionId(long ctCollectionId);

	@Transactional(enabled = false)
	public CTModelAdapter<T, C> getCTModelAdapter();

	public void removeModelCT(C modelCT);

	public void removeModelCTs(long classPK);

	public void updateModel(T model);

	public void updateModelCT(C modelCT);

}