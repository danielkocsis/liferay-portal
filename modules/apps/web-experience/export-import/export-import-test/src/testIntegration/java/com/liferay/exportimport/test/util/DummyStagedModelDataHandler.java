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

package com.liferay.exportimport.test.util;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.xml.Element;
import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {
		DummyStagedModelDataHandler.class, StagedModelDataHandler.class
	}
)
public class DummyStagedModelDataHandler
	extends BaseStagedModelDataHandler<Dummy> {

	public static final String[] CLASS_NAMES = {Dummy.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Dummy dummy)
		throws Exception {

		Element dummyElement = portletDataContext.getExportDataElement(dummy);

		portletDataContext.addClassedModel(
			dummyElement, ExportImportPathUtil.getModelPath(dummy), dummy);
	}

	@Override
	protected void doImportStagedModel(
		PortletDataContext portletDataContext, Dummy dummy) throws Exception {


	}
}