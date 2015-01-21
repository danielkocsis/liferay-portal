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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;

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

	public List<ManifestTreeNode> getChildren() {
		return _children;
	}

	public int getLabel() {
		return _label;
	}

	public ManifestTreeNode getParent() {
		return _parent;
	}

	public StagedModel getStagedModel() {
		if (Validator.isNull(_className) || Validator.isNull(_classPK)) {
			return null;
		}

		PersistedModelLocalService persistedModelLocalService =
			PersistedModelLocalServiceRegistryUtil.
				getPersistedModelLocalService(_className);

		PersistedModel persistedModel = null;

		try {
			persistedModelLocalService.getPersistedModel(_classPK);
		}
		catch (Exception e) {
			return null;
		}

		if (persistedModel instanceof StagedModel) {
			return (StagedModel)persistedModel;
		}

		return null;
	}

	public boolean hasParent() {
		return _parent == null;
	}

	public void setLabel(int label) {
		_label = label;
	}

	public void setParent(ManifestTreeNode manifestTreeNode) {
		if (manifestTreeNode == null) {
			return;
		}

		_parent = manifestTreeNode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("ManifestTreeNode {");
		sb.append(_className);
		sb.append(StringPool.COLON);
		sb.append(_classPK);
		sb.append(StringPool.SEMICOLON);
		sb.append(" label:");
		sb.append(_label);
		sb.append("}");

		return sb.toString();
	}

	private final List<ManifestTreeNode> _children = new ArrayList<>();
	private final String _className;
	private final Serializable _classPK;
	private int _label;
	private ManifestTreeNode _parent;

}