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

package com.liferay.portal.kernel.change.tracking.persistence;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface CTPersistenceHelper<T extends BaseModel<T>> {

	public Object[] appendFinderArgs(Object[] finderArgs);

	public String appendSQL(String tableName, String sql);

	public StringBundler appendSQL(String tableName, StringBundler sb);

	public boolean isValidFinderResult(T baseModel);

	public List<T> populate(List<T> baseModels);

	public T populate(T baseModel);

	public void remove(T baseModel);

	public void update(T baseModel);

}