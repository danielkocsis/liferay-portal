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

package com.liferay.portlet.sitesadmin.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.model.StagedModel;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Mate Thurzo
 */
public class StagedGroupStagedModelRepository
	implements StagedModelRepository<StagedGroup> {

	@Override
	public List<? extends StagedModel> fetchChildStagedModels(
		StagedGroup stagedGroup) {

		Registry registry = RegistryUtil.getRegistry();

		Collection<StagedModelRepository> stagedModelRepositories = null;

		try {
			stagedModelRepositories = registry.getServices(
				StagedModelRepository.class, null);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}

		List<StagedModel> childStagedModels = new ArrayList<>();

		for (StagedModelRepository stagedModelRepository :
				stagedModelRepositories) {

			childStagedModels.addAll(
				stagedModelRepository.fetchStagedModels(
					stagedGroup.getGroupId()));
		}

		return childStagedModels;
	}

	@Override
	public List<? extends StagedModel> fetchParentStagedModels(
		StagedGroup stagedGroup) {

		return Collections.emptyList();
	}

	@Override
	public StagedGroup fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public StagedGroup fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return null;
	}

	@Override
	public List<StagedGroup> fetchStagedModels(long groupId) {
		return Collections.emptyList();
	}

	@Override
	public List<StagedGroup> fetchStagedModels(
		PortletDataContext portletDataContext) {

		return null;
	}

}