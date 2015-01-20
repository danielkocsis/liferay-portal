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

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Mate Thurzo
 */
public abstract class BaseModelStager<T extends StagedModel>
	implements ModelStager<T> {

	@Override
	public void deleteStagedModel(T stagedModel) throws PortletDataException {
		try {
			doDeleteStagedModel(stagedModel);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public void exportStagedModel(T stagedModel, OutputStream outputStream)
		throws PortletDataException {

		try {
			doExportStagedModel(stagedModel, outputStream);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public String[] getClassNames() {
		return new String[0];
	}

	@Override
	public String getDisplayName(T StagedModel) {
		return null;
	}

	@Override
	public int[] getExportableStatuses() {
		return new int[0];
	}

	@Override
	public T importStagedModel(InputStream inputStream)
		throws PortletDataException {

		return null;
	}

	@Override
	public void restoreStagedModel(T stagedModel) throws PortletDataException {
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		return false;
	}

	protected void doDeleteStagedModel(T stagedModel) throws Exception {
	}

	protected void doExportStagedModel(T stagedModel, OutputStream outputStream)
		throws Exception {
	}

}