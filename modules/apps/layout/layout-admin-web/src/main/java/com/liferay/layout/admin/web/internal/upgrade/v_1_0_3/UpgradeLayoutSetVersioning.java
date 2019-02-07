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

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_3;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class UpgradeLayoutSetVersioning extends UpgradeProcess {

	public UpgradeLayoutSetVersioning(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDrafts();
	}

	private void updateDrafts() throws Exception {
		List<LayoutSet> layoutSets =
			_layoutSetLocalService.getLayoutSets(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (LayoutSet layoutSet : layoutSets) {
			if (layoutSet.isHead()) {
				continue;
			}

			_layoutSetLocalService.publishDraft(layoutSet);
		}
	}

	private final LayoutSetLocalService _layoutSetLocalService;

}