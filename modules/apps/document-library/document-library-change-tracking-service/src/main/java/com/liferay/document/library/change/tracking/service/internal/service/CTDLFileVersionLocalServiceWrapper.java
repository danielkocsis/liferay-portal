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

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTDLFileVersionLocalServiceWrapper
	extends DLFileVersionLocalServiceWrapper {

	public CTDLFileVersionLocalServiceWrapper() {
		super(null);
	}

	public CTDLFileVersionLocalServiceWrapper(
		DLFileVersionLocalService dlFileVersionLocalService) {

		super(dlFileVersionLocalService);
	}

	@Override
	public DLFileVersion getLatestFileVersion(long userId, long fileEntryId)
		throws PortalException {

		DLFileVersion latestFileVersion = super.getLatestFileVersion(
			userId, fileEntryId);

		if (!_ctDLFileEntryHelper.isChangeTrackingEnabled(
				latestFileVersion.getGroupId())) {

			return latestFileVersion;
		}

		Optional<DLFileVersion> fileVersionOptional =
			_ctDLFileEntryHelper.getLatestFileVersion(fileEntryId);

		return fileVersionOptional.orElse(latestFileVersion);
	}

	@Reference
	private CTDLFileEntryHelper _ctDLFileEntryHelper;

}