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

import com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class PortletPreferencesCTSoap implements Serializable {

	public static PortletPreferencesCTSoap toSoapModel(
		PortletPreferencesCT model) {

		PortletPreferencesCTSoap soapModel = new PortletPreferencesCTSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setPreferences(model.getPreferences());

		return soapModel;
	}

	public static PortletPreferencesCTSoap[] toSoapModels(
		PortletPreferencesCT[] models) {

		PortletPreferencesCTSoap[] soapModels =
			new PortletPreferencesCTSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortletPreferencesCTSoap[][] toSoapModels(
		PortletPreferencesCT[][] models) {

		PortletPreferencesCTSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new PortletPreferencesCTSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortletPreferencesCTSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortletPreferencesCTSoap[] toSoapModels(
		List<PortletPreferencesCT> models) {

		List<PortletPreferencesCTSoap> soapModels =
			new ArrayList<PortletPreferencesCTSoap>(models.size());

		for (PortletPreferencesCT model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new PortletPreferencesCTSoap[soapModels.size()]);
	}

	public PortletPreferencesCTSoap() {
	}

	public PortletPreferencesCTPK getPrimaryKey() {
		return new PortletPreferencesCTPK(_classPK, _ctCollectionId);
	}

	public void setPrimaryKey(PortletPreferencesCTPK pk) {
		setClassPK(pk.classPK);
		setCtCollectionId(pk.ctCollectionId);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getPreferences() {
		return _preferences;
	}

	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	private long _mvccVersion;
	private long _classPK;
	private long _ctCollectionId;
	private String _preferences;

}