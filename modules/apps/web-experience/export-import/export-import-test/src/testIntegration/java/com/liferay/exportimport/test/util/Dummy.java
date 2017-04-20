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

package com.liferay.exportimport.test.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Akos Thurzo
 */
public class Dummy implements StagedGroupedModel {

	private long _groupId;
	private Date _lastPublishDate;
	private String _userUuid;
	private String _userName;
	private long _userId;

	public Dummy() {
	}

	public Dummy(long companyId, long groupId, User user) {
		_companyId = companyId;
		_groupId = groupId;
		_userId = user.getUserId();
		_userName = user.getScreenName();
		_userUuid = user.getUserUuid();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			Dummy.class.getName(), getPrimaryKey());
	}

	@Override
	public Class<?> getModelClass() {
		return Dummy.class;
	}

	@Override
	public String getModelClassName() {
		return Dummy.class.getName();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _id;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_id = (long)primaryKeyObj;
	}

	@Override
	public Object clone() {
		Dummy dummy = new Dummy();

		dummy.setCompanyId(_companyId);
		dummy.setCreateDate(_createDate);
		dummy.setGroupId(_groupId);
		dummy.setLastPublishDate(_lastPublishDate);
		dummy.setModifiedDate(_modifiedDate);
		dummy.setPrimaryKeyObj(_id);
		dummy.setUserId(_userId);
		dummy.setUserName(_userName);
		dummy.setUserUuid(_userUuid);
		dummy.setUuid(_uuid);

		return dummy;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public String getUserUuid() {
		return _userUuid;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(Dummy.class);
	}

	@Override
	public String getUuid() {
		return _uuid;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private String _uuid = PortalUUIDUtil.generate();
	private Date _createDate = new Date();
	private Date _modifiedDate = new Date();
	private long _companyId;
	private long _id = System.currentTimeMillis();

	public long getPrimaryKey() {
		return _id;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	public void setPrimaryKey(long primaryKey) {
		_id = primaryKey;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}
}