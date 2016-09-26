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

package com.liferay.portlet.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.model.adapter.StagedPortlet;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portlet.model.adapter.StagedPortlet"},
	service = {
		StagedPortletStagedModelRepository.class, StagedModelRepository.class
	}
)
public class StagedPortletStagedModelRepository
	extends BaseStagedModelRepository<StagedPortlet> {

	@Override
	public StagedPortlet addStagedModel(
			PortletDataContext portletDataContext, StagedPortlet stagedPortlet)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteStagedModel(StagedPortlet stagedPortlet)
		throws PortalException {
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public List<StagedPortlet> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return null;
	}

	@Override
	public StagedPortlet saveStagedModel(StagedPortlet stagedPortlet)
		throws PortalException {

		return null;
	}

	@Override
	public StagedPortlet updateStagedModel(
			PortletDataContext portletDataContext, StagedPortlet stagedPortlet)
		throws PortalException {

		return null;
	}

}