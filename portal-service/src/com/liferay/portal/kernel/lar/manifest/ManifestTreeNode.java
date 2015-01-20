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

package com.liferay.portal.kernel.lar.manifest;

import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mate Thurzo
 */
public class ManifestTreeNode {

	public ManifestTreeNode(StagedModel stagedModel) {
		_className = stagedModel.getModelClassName();
		_classPK = stagedModel.getPrimaryKeyObj();
	}

	public ManifestTreeNode(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	public void addChild(ManifestTreeNode manifestTreeNode) {
		if (manifestTreeNode == null) {
			return;
		}

		_children.add(manifestTreeNode);
	}

	public void addParent(ManifestTreeNode manifestTreeNode) {
		if (manifestTreeNode == null) {
			return;
		}

		_parents.add(manifestTreeNode);
	}

	public List<ManifestTreeNode> getParents() {
		return _parents;
	}

	public boolean hasParent() {
		return !_parents.isEmpty();
	}

	private final List<ManifestTreeNode> _children = new ArrayList<>();
	private final String _className;
	private final Serializable _classPK;
	private final List<ManifestTreeNode> _parents = new ArrayList<>();

}