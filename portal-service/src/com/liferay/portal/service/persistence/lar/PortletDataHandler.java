/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence.lar;

import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.persistence.BaseDataHandler;

/**
 * @author Mate Thurzo
 */
public abstract interface PortletDataHandler extends BaseDataHandler<Portlet> {

	public LarDigestItem digest(Portlet portlet) throws Exception;

	public abstract LarDigestItem doDigest(Portlet portlet) throws Exception;

	public Portlet getEntity(String classPK);

	public abstract PortletDataHandlerControl[] getExportControls();

	public abstract PortletDataHandlerControl[] getExportMetadataControls();

	public abstract PortletDataHandlerControl[] getImportControls();

	public abstract PortletDataHandlerControl[] getImportMetadataControls();

	public abstract boolean isAlwaysExportable();

	public abstract boolean isAlwaysStaged();

	public abstract boolean isDataLocalized();

	public abstract boolean isPublishToLiveByDefault();

}
