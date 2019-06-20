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

import com.liferay.portal.kernel.service.persistence.LayoutCTPK;

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
public class LayoutCTSoap implements Serializable {

	public static LayoutCTSoap toSoapModel(LayoutCT model) {
		LayoutCTSoap soapModel = new LayoutCTSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setPlid(model.getPlid());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static LayoutCTSoap[] toSoapModels(LayoutCT[] models) {
		LayoutCTSoap[] soapModels = new LayoutCTSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutCTSoap[][] toSoapModels(LayoutCT[][] models) {
		LayoutCTSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutCTSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutCTSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutCTSoap[] toSoapModels(List<LayoutCT> models) {
		List<LayoutCTSoap> soapModels = new ArrayList<LayoutCTSoap>(
			models.size());

		for (LayoutCT model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutCTSoap[soapModels.size()]);
	}

	public LayoutCTSoap() {
	}

	public LayoutCTPK getPrimaryKey() {
		return new LayoutCTPK(_plid, _ctCollectionId);
	}

	public void setPrimaryKey(LayoutCTPK pk) {
		setPlid(pk.plid);
		setCtCollectionId(pk.ctCollectionId);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private long _mvccVersion;
	private long _plid;
	private long _ctCollectionId;
	private String _typeSettings;

}