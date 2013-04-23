/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ClassedModel;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public interface ManifestEntry {

	// Export

	public void addModelAttribute(String key, String value);

	public void addReferencedModel(ClassedModel classedModel);

	public void addReferencedEntry(ManifestEntry entry);

	public void setModel(ClassedModel classedModel);

	// Import

	public String getModelAttribute(String key);

	public Map<String, String> getModelAttributes();

	public ClassedModel getModel();

	public List<ManifestEntry> getReferencedEntries();

	// Other

	public String getEntryKey();

	public String getModelClassName();

}
