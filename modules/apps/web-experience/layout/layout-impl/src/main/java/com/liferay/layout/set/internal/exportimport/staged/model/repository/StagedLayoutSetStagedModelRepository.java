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

package com.liferay.layout.set.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.layout.set.model.adapter.StagedLayoutSet"
	},
	service = {
		StagedLayoutSetStagedModelRepository.class, StagedModelRepository.class
	}
)
public class StagedLayoutSetStagedModelRepository
	extends BaseStagedModelRepository<StagedLayoutSet> {

	public StagedLayoutSet addStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets

		return null;
	}

	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		// Not supported for layout sets
	}

	public void deleteStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		// Not supported for layout sets
	}

	public List<StagedLayoutSet> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return null;
	}

	public StagedLayoutSet saveStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		return null;
	}

	public StagedLayoutSet updateStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		boolean layoutSetPrototypeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS);

		if (layoutSetPrototypeSettings &&
			Validator.isNotNull(stagedLayoutSet.getLayoutSetPrototypeUuid())) {

			layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
			layoutSet.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);

			_layoutSetLocalService.updateLayoutSet(layoutSet);
		}

		// Layout Set settings

		boolean layoutSetSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (layoutSetSettings) {
			layoutSetLocalService.updateSettings(
				portletDataContext.getGroupId(),
				portletDataContext.isPrivateLayout(),
				stagedLayoutSet.getSettings());
		}

		return null;
	}

	protected LayoutSetLocalService layoutSetLocalService;

}