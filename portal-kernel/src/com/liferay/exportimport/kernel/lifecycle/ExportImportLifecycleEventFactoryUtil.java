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

package com.liferay.exportimport.kernel.lifecycle;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class ExportImportLifecycleEventFactoryUtil {

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static ExportImportLifecycleEvent create(
		int code, int processFlag, Serializable... attributes) {

		return _exportImportLifecycleEventFactory.create(
			code, processFlag, attributes);
	}

	public static ExportImportLifecycleEvent create(
		int code, int processFlag, String processId,
		Serializable... attributes) {

		return _exportImportLifecycleEventFactory.create(
			code, processFlag, processId, attributes);
	}

	private static volatile ExportImportLifecycleEventFactory
		_exportImportLifecycleEventFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportLifecycleEventFactory.class,
				ExportImportLifecycleEventFactoryUtil.class,
				"_exportImportLifecycleEventFactory", false);

}