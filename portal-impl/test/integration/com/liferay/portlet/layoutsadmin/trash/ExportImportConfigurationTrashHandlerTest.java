/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.trash;

import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
public class ExportImportConfigurationTrashHandlerTest
	extends BaseTrashHandlerTestCase {

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		//TODO util a settingsMap-re

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				getSearchKeywords(), "Description",
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
				settingsMap, new ServiceContext());
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return ExportImportConfigurationLocalServiceUtil.
			getExportImportConfiguration(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return ExportImportConfiguration.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return ExportImportConfigurationLocalServiceUtil.
			getExportImportConfigurationCount(
				(Long)parentBaseModel.getPrimaryKeyObj());
	}

	@Override
	protected String getSearchKeywords() {
		return "Name";
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		ExportImportConfigurationLocalServiceUtil.
			moveExportImportConfigurationToTrash(
				TestPropsValues.getUserId(), primaryKey);
	}

}