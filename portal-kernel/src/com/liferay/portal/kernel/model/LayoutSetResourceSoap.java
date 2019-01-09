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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutSetResourceSoap implements Serializable {
	public static LayoutSetResourceSoap toSoapModel(LayoutSetResource model) {
		LayoutSetResourceSoap soapModel = new LayoutSetResourceSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setLayoutSetResourceId(model.getLayoutSetResourceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPageCount(model.getPageCount());
		soapModel.setPrivateLayout(model.isPrivateLayout());

		return soapModel;
	}

	public static LayoutSetResourceSoap[] toSoapModels(
		LayoutSetResource[] models) {
		LayoutSetResourceSoap[] soapModels = new LayoutSetResourceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutSetResourceSoap[][] toSoapModels(
		LayoutSetResource[][] models) {
		LayoutSetResourceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutSetResourceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutSetResourceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutSetResourceSoap[] toSoapModels(
		List<LayoutSetResource> models) {
		List<LayoutSetResourceSoap> soapModels = new ArrayList<LayoutSetResourceSoap>(models.size());

		for (LayoutSetResource model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutSetResourceSoap[soapModels.size()]);
	}

	public LayoutSetResourceSoap() {
	}

	public long getPrimaryKey() {
		return _layoutSetResourceId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutSetResourceId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getLayoutSetResourceId() {
		return _layoutSetResourceId;
	}

	public void setLayoutSetResourceId(long layoutSetResourceId) {
		_layoutSetResourceId = layoutSetResourceId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public int getPageCount() {
		return _pageCount;
	}

	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
	}

	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	private long _mvccVersion;
	private long _layoutSetResourceId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private int _pageCount;
	private boolean _privateLayout;
}