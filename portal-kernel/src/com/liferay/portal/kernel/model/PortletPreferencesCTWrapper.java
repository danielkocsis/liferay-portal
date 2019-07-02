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
 * This class is a wrapper for {@link PortletPreferencesCT}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesCT
 * @generated
 */
@ProviderType
public class PortletPreferencesCTWrapper
	extends BaseModelWrapper<PortletPreferencesCT>
	implements PortletPreferencesCT, ModelWrapper<PortletPreferencesCT> {

	public PortletPreferencesCTWrapper(
		PortletPreferencesCT portletPreferencesCT) {

		super(portletPreferencesCT);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("classPK", getClassPK());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("preferences", getPreferences());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String preferences = (String)attributes.get("preferences");

		if (preferences != null) {
			setPreferences(preferences);
		}
	}

	/**
	 * Returns the class pk of this portlet preferences ct.
	 *
	 * @return the class pk of this portlet preferences ct
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the ct collection ID of this portlet preferences ct.
	 *
	 * @return the ct collection ID of this portlet preferences ct
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the mvcc version of this portlet preferences ct.
	 *
	 * @return the mvcc version of this portlet preferences ct
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the preferences of this portlet preferences ct.
	 *
	 * @return the preferences of this portlet preferences ct
	 */
	@Override
	public String getPreferences() {
		return model.getPreferences();
	}

	/**
	 * Returns the primary key of this portlet preferences ct.
	 *
	 * @return the primary key of this portlet preferences ct
	 */
	@Override
	public com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPK
		getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Sets the class pk of this portlet preferences ct.
	 *
	 * @param classPK the class pk of this portlet preferences ct
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the ct collection ID of this portlet preferences ct.
	 *
	 * @param ctCollectionId the ct collection ID of this portlet preferences ct
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the mvcc version of this portlet preferences ct.
	 *
	 * @param mvccVersion the mvcc version of this portlet preferences ct
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the preferences of this portlet preferences ct.
	 *
	 * @param preferences the preferences of this portlet preferences ct
	 */
	@Override
	public void setPreferences(String preferences) {
		model.setPreferences(preferences);
	}

	/**
	 * Sets the primary key of this portlet preferences ct.
	 *
	 * @param primaryKey the primary key of this portlet preferences ct
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPK
			primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected PortletPreferencesCTWrapper wrap(
		PortletPreferencesCT portletPreferencesCT) {

		return new PortletPreferencesCTWrapper(portletPreferencesCT);
	}

}