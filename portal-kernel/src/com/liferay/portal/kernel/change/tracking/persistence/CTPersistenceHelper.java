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
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface CTPersistenceHelper {

	public Object[] appendContextFinderArgs(
		Object[] finderArgs, Class<? extends BaseModel<?>> modelClass);

	public void appendContextSQL(
		StringBundler sb, Class<? extends BaseModel<?>> modelClass);

	public boolean isValidFinderResult(BaseModel<?> baseModel);

	public void setContext(
		Session session, BaseModel<?> baseModel,
		Class<? extends BaseModel<?>> modelClass);

	public void setContexts(
		Session session, List<BaseModel<?>> baseModels,
		Class<? extends BaseModel<?>> modelClass);

}