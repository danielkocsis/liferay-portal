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

package com.liferay.portal.change.tracking.persistence;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public class CTPersistenceHelperFactoryUtil {

	public static <T extends BaseModel<T>> CTPersistenceHelper<T> create(
		Class<T> modelClass) {

		CTPersistenceHelperFactory ctPersistenceHelperFactory =
			_ctPersistenceHelperFactory;

		if (ctPersistenceHelperFactory == null) {
			return new CTPersistenceHelper<T>() {

				@Override
				public Object[] appendContextFinderArgs(Object[] finderArgs) {
					return finderArgs;
				}

				@Override
				public void appendContextSQL(
					String tableName, StringBundler sb) {

					sb.append(" AND ");
					sb.append(tableName);
					sb.append(".ctCollectionId = 0 ");
				}

				@Override
				public boolean isValidFinderResult(T baseModel) {
					return true;
				}

				@Override
				public void populateContext(T baseModel) {
				}

				@Override
				public void populateContexts(List<T> baseModels) {
				}

			};
		}

		return ctPersistenceHelperFactory.create(modelClass);
	}

	private CTPersistenceHelperFactoryUtil() {
	}

	private static volatile CTPersistenceHelperFactory
		_ctPersistenceHelperFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				CTPersistenceHelperFactory.class,
				CTPersistenceHelperFactoryUtil.class,
				"_ctPersistenceHelperFactory", false, true);

}