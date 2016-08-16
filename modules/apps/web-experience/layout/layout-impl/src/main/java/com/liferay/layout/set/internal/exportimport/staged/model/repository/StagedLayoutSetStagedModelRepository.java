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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

	public void deleteStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets

	}

	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		// Not supported for layout sets

	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		// Not supported for layout sets

	}

	public List<Layout> fetchChildrenStagedModels(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			stagedLayoutSet.getGroupId(), stagedLayoutSet.isPrivateLayout());

		if (ListUtil.isEmpty(layouts)) {
			return Collections.emptyList();
		}

		return layouts;
	}

	public List<StagedModel> fetchDependencyStagedModels(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		return null;
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

		// Layout set prototype settings

		boolean layoutSetPrototypeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS);

		if (!layoutSetPrototypeSettings ||
			Validator.isNull(stagedLayoutSet.getLayoutSetPrototypeUuid())) {

			stagedLayoutSet.setLayoutSetPrototypeUuid(null);
			stagedLayoutSet.setLayoutSetPrototypeLinkEnabled(false);

			_layoutSetLocalService.updateLayoutSet(stagedLayoutSet);
		}

		// Layout Set settings

		boolean layoutSetSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (layoutSetSettings) {
			_layoutSetLocalService.updateSettings(
				portletDataContext.getGroupId(),
				portletDataContext.isPrivateLayout(),
				stagedLayoutSet.getSettings());
		}

		return stagedLayoutSet;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}