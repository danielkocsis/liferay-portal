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

package com.liferay.portlet.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portlet.internal.exportimport.staged.model.repository.StagedPortletStagedModelRepository;
import com.liferay.portlet.model.adapter.StagedPortlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedPortletStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedPortlet> {

	public static final String[] CLASS_NAMES = {StagedPortlet.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedPortlet stagedPortlet) {
		return stagedPortlet.getPortletName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, StagedPortlet stagedPortlet)
		throws Exception {
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, StagedPortlet stagedPortlet)
		throws Exception {
	}

	@Override
	protected StagedModelRepository<StagedPortlet> getStagedModelRepository() {
		return _stagedPortletStagedModelRepository;
	}

	@Reference
	private StagedPortletStagedModelRepository
		_stagedPortletStagedModelRepository;

}