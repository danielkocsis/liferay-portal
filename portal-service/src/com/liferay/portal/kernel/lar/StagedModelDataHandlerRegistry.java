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

package com.liferay.portal.kernel.lar;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public interface StagedModelDataHandlerRegistry {

	/**
	 * Returns the staged model data handler for a given class name from the
	 * registry.
	 *
	 * @param className the name of the staged model class
	 * @return the staged model data handler for the staged model class if it
	 *         has been registered, null otherwise
	 */
	public StagedModelDataHandler getStagedModelDataHandler(String className);

	/**
	 * Return every staged model data handler that have been registered in the
	 * system as a list.
	 *
	 * @return a list of staged model data handlers registered in the system
	 */
	public List<StagedModelDataHandler> getStagedModelDataHandlerList();

	/**
	 * Return every staged model data handler that have been registered in the
	 * system as a map.
	 *
	 * @return a map of staged model data handlers registered in the system
	 */
	public Map<String, StagedModelDataHandler> getStagedModelDataHandlers();

	/**
	 * Registers a staged model data handler.
	 *
	 * @param stagedModelDataHandler the staged model data handler to register,
	 *                               a passed null value won't be registered
	 */
	public void register(StagedModelDataHandler stagedModelDataHandler);

	/**
	 * Unregister a staged model data handler.
	 *
	 * @param stagedModelDataHandler the staged model data handler to
	 *                               unregister, a passed null value doesn't
	 *                               affect the registry
	 */
	public void unregister(StagedModelDataHandler stagedModelDataHandler);

}