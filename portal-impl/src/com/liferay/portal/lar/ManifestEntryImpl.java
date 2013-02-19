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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ManifestEntry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 */
public class ManifestEntryImpl implements ManifestEntry {

	public void addModelAttribute(String key, String value) {
		_modelAttributes.put(key, value);
	}

	public void addReferencedModel(ClassedModel classedModel) {
		ManifestEntry referencedEntry = new ManifestEntryImpl();

		referencedEntry.setModel(classedModel);

		addReferencedEntry(referencedEntry);
	}

	public void addReferencedEntry(ManifestEntry entry) {
		if (entry == null) {
			return;
		}

		String entryKey = entry.getEntryKey();

		if (_referencesEntries.containsKey(entryKey)) {
			return;
		}

		_referencesEntries.put(entryKey, entry);
	}

	public void setModel(ClassedModel classedModel) {
		_model = classedModel;
		_modelClassName = classedModel.getModelClassName();

		addModelAttribute("class-name", _modelClassName);
		addModelAttribute("classpk", String.valueOf(_model.getPrimaryKeyObj()));
	}

	public String getModelAttribute(String key) {
		return _modelAttributes.get(key);
	}

	public Map<String, String> getModelAttributes() {
		return _modelAttributes;
	}

	public ClassedModel getModel() {
		return _model;
	}

	public List<ManifestEntry> getReferencedEntries() {
		return ListUtil.fromCollection(_referencesEntries.values());
	}

	public String getEntryKey() {
		if ((_model == null) || Validator.isNull(_modelClassName)) {
			return StringPool.BLANK;
		}

		return _modelClassName + StringPool.POUND +
			String.valueOf(_model.getPrimaryKeyObj());
	}

	public String getModelClassName() {
		return _modelClassName;
	}

	public void setModelClassName(String modelClassName) {
		_modelClassName = modelClassName;
	}

	private Map<String, String> _modelAttributes =
		new HashMap<String, String>();
	private ClassedModel _model;
	private String _modelClassName;

	private Map<String, ManifestEntry> _referencesEntries =
		new HashMap<String, ManifestEntry>();

}
