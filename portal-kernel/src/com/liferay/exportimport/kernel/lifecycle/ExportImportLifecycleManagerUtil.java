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

import java.util.List;

/**
 * @author Michael C. Han
 */
@ProviderType
public class ExportImportLifecycleManagerUtil {

	public static void fireExportImportLifecycleEvent(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		_exportImportLifecycleManager.fireExportImportLifecycleEvent(
			exportImportLifecycleEvent.getCode(),
			exportImportLifecycleEvent.getProcessFlag(),
			attributes.toArray(new Serializable[attributes.size()]));
	}

	public static void fireExportImportLifecycleEvent(
		int code, int processFlag, Serializable... arguments) {

		_exportImportLifecycleManager.fireExportImportLifecycleEvent(
			code, processFlag, arguments);
	}

	private static volatile ExportImportLifecycleManager
		_exportImportLifecycleManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportLifecycleManager.class,
				ExportImportLifecycleManagerUtil.class,
				"_exportImportLifecycleManager", false);

}