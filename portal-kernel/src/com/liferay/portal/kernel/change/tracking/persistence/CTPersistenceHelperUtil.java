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
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public class CTPersistenceHelperUtil {

	public static Object[] appendContextFinderArgs(
		Object[] finderArgs, Class<? extends BaseModel<?>> modelClass) {

		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return finderArgs;
		}

		return ctPersistenceHelper.appendContextFinderArgs(
			finderArgs, modelClass);
	}

	public static void appendContextSQL(
		StringBundler sb, Class<? extends BaseModel<?>> modelClass) {

		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return;
		}

		ctPersistenceHelper.appendContextSQL(sb, modelClass);
	}

	public static boolean isValidFinderResult(BaseModel<?> baseModel) {
		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return true;
		}

		return ctPersistenceHelper.isValidFinderResult(baseModel);
	}

	public static void setContext(
		Session session, BaseModel<?> baseModel,
		Class<? extends BaseModel<?>> modelClass) {

		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return;
		}

		ctPersistenceHelper.setContext(session, baseModel, modelClass);
	}

	public static void setContexts(
		Session session, List<BaseModel<?>> baseModels,
		Class<? extends BaseModel<?>> modelClass) {

		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return;
		}

		ctPersistenceHelper.setContexts(session, baseModels, modelClass);
	}

	private CTPersistenceHelperUtil() {
	}

	private static volatile CTPersistenceHelper _ctPersistenceHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			CTPersistenceHelper.class, CTPersistenceHelperUtil.class,
			"_ctPersistenceHelper", false, true);

}