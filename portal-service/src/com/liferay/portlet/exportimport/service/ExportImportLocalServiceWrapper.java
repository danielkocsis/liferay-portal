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

package com.liferay.portlet.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExportImportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalService
 * @generated
 */
@ProviderType
public class ExportImportLocalServiceWrapper implements ExportImportLocalService,
	ServiceWrapper<ExportImportLocalService> {
	public ExportImportLocalServiceWrapper(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _exportImportLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_exportImportLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExportImportLocalService getWrappedExportImportLocalService() {
		return _exportImportLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExportImportLocalService(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	@Override
	public ExportImportLocalService getWrappedService() {
		return _exportImportLocalService;
	}

	@Override
	public void setWrappedService(
		ExportImportLocalService exportImportLocalService) {
		_exportImportLocalService = exportImportLocalService;
	}

	private ExportImportLocalService _exportImportLocalService;
}