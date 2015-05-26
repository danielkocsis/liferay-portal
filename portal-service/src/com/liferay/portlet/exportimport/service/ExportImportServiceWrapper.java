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
 * Provides a wrapper for {@link ExportImportService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportService
 * @generated
 */
@ProviderType
public class ExportImportServiceWrapper implements ExportImportService,
	ServiceWrapper<ExportImportService> {
	public ExportImportServiceWrapper(ExportImportService exportImportService) {
		_exportImportService = exportImportService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _exportImportService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_exportImportService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ExportImportService getWrappedExportImportService() {
		return _exportImportService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedExportImportService(
		ExportImportService exportImportService) {
		_exportImportService = exportImportService;
	}

	@Override
	public ExportImportService getWrappedService() {
		return _exportImportService;
	}

	@Override
	public void setWrappedService(ExportImportService exportImportService) {
		_exportImportService = exportImportService;
	}

	private ExportImportService _exportImportService;
}