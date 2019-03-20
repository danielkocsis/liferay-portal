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

package com.liferay.document.library.change.tracking.service.internal.service;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = CTDLFileEntryManager.class)
public class CTDLFileEntryManager {

	public Optional<DLFileVersion> getLatestFileVersion(
		long userId, long fileEntryId) {

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				PrincipalThreadLocal.getUserId(), fileEntryId);

		if (!ctEntryOptional.isPresent()) {
			Optional.empty();
		}

		Optional<DLFileVersion> fileVersionOptional = ctEntryOptional.map(
			CTEntry::getModelClassPK
		).map(
			_dlFileVersionLocalService::fetchDLFileVersion
		);

		return fileVersionOptional;
	}

	public boolean isChangeTrackingEnabled(long groupId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		if (_ctEngineManager.isChangeTrackingEnabled(group.getCompanyId()) &&
			_ctEngineManager.isChangeTrackingSupported(
				group.getCompanyId(), DLFileVersion.class)) {

			return true;
		}

		return false;
	}

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}