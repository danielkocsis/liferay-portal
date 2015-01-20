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
public interface ModelStager<T extends StagedModel> {

	public void deleteStagedModel(T stagedModel) throws PortletDataException;

	public void exportStagedModel(T stagedModel, OutputStream outputStream)
		throws PortletDataException;

	public String[] getClassNames();

	public String getDisplayName(T StagedModel);

	public int[] getExportableStatuses();

	public T importStagedModel(InputStream inputStream)
		throws PortletDataException;

	public void restoreStagedModel(T stagedModel) throws PortletDataException;

	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement);

}