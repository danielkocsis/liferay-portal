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

package com.liferay.portlet.internal.model.adapter.builder;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portlet.internal.model.adapter.impl.StagedPortletImpl;
import com.liferay.portlet.model.adapter.StagedPortlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ModelAdapterBuilder.class)
public class StagedPortletModelAdapterBuilder
	implements ModelAdapterBuilder<Portlet, StagedPortlet> {

	@Override
	public StagedPortlet build(Portlet portlet) {
		return new StagedPortletImpl(portlet);
	}

}