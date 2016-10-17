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

package com.liferay.exportimport.staged.model.reference.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.StagedModel;

import java.util.Optional;

/**
 * @author Daniel Kocsis
 */
public interface StageModelReferenceProcessor
	<S extends StagedModel, T extends StagedModel> {

	public String getReferenceType();

	public Optional<StagedModel> process(
		final PortletDataContext portletDataContext, final S stagedModel);

}