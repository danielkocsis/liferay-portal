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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link LayoutCT}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCT
 * @generated
 */
@ProviderType
public class LayoutCTWrapper
	extends BaseModelWrapper<LayoutCT>
	implements LayoutCT, ModelWrapper<LayoutCT> {

	public LayoutCTWrapper(LayoutCT layoutCT) {
		super(layoutCT);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("plid", getPlid());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("typeSettings", getTypeSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}
	}

	/**
	 * Returns the ct collection ID of this layout ct.
	 *
	 * @return the ct collection ID of this layout ct
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the mvcc version of this layout ct.
	 *
	 * @return the mvcc version of this layout ct
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the plid of this layout ct.
	 *
	 * @return the plid of this layout ct
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this layout ct.
	 *
	 * @return the primary key of this layout ct
	 */
	@Override
	public com.liferay.portal.kernel.service.persistence.LayoutCTPK
		getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the type settings of this layout ct.
	 *
	 * @return the type settings of this layout ct
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	/**
	 * Sets the ct collection ID of this layout ct.
	 *
	 * @param ctCollectionId the ct collection ID of this layout ct
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the mvcc version of this layout ct.
	 *
	 * @param mvccVersion the mvcc version of this layout ct
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the plid of this layout ct.
	 *
	 * @param plid the plid of this layout ct
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this layout ct.
	 *
	 * @param primaryKey the primary key of this layout ct
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.portal.kernel.service.persistence.LayoutCTPK primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type settings of this layout ct.
	 *
	 * @param typeSettings the type settings of this layout ct
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	protected LayoutCTWrapper wrap(LayoutCT layoutCT) {
		return new LayoutCTWrapper(layoutCT);
	}

}