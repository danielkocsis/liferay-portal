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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.sitesadmin.lar.StagedGroup;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class ExportImportController {

	public static void export(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);

		final PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				exportImportConfiguration.getCompanyId(),
				exportImportConfiguration.getGroupId(), parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate(),
				ZipWriterFactoryUtil.getZipWriter());

		// TODO prepare phase

		//portletDataContext.prepareInContext(exportImportConfiguration).map(stagedModel -> stagedModels.add(stagedModel))

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		StagedModel rootStagedModel = ModelAdapterUtil.adapt(
			group, StagedGroup.class);

		ManifestSummary manifestSummary = portletDataContext.prepareInContext(
			rootStagedModel);

		// TODO topological ordering

		// TODO export

		/*List<StagedModel> stagedModels = null;

		stagedModels.parallelStream().forEach(stagedModel -> portletDataContext.exportInContext(stagedModel));*/
	}

	public static ExportImportController getInstance() {
		return _instance;
	}

	private ExportImportController() {
	}

	private static final ExportImportController _instance =
		new ExportImportController();

}