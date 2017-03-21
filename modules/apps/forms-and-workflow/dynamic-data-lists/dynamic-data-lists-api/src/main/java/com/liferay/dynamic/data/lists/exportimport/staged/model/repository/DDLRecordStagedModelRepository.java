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

package com.liferay.dynamic.data.lists.exportimport.staged.model.repository;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Mate Thurzo
 */
public interface DDLRecordStagedModelRepository
	extends StagedModelRepository<DDLRecord> {

	public DDLRecord addStagedModel(
			PortletDataContext portletDataContext, DDLRecord ddlRecord,
			DDMFormValues ddmFormValues)
		throws PortalException;

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext, final int scope);

	public DDLRecord updateStagedModel(
			PortletDataContext portletDataContext, DDLRecord ddlRecord,
			DDMFormValues ddmFormValues)
		throws PortalException;

}