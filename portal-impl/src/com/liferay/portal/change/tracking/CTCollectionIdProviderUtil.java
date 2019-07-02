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

package com.liferay.portal.change.tracking;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Daniel Kocsis
 */
public class CTCollectionIdProviderUtil {

	public static long getCTCollectionId() {
		CTCollectionIdProvider ctCollectionIdProvider = _ctCollectionIdProvider;

		if (ctCollectionIdProvider == null) {
			return 0;
		}

		return ctCollectionIdProvider.getCTCollectionId();
	}

	private CTCollectionIdProviderUtil() {
	}

	private static volatile CTCollectionIdProvider _ctCollectionIdProvider =
		ServiceProxyFactory.newServiceTrackedInstance(
			CTCollectionIdProvider.class, CTCollectionIdProviderUtil.class,
			"_ctCollectionIdProvider", false, true);

}