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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutSetResource;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutSetResource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetResource
 * @generated
 */
@ProviderType
public class LayoutSetResourceCacheModel implements CacheModel<LayoutSetResource>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetResourceCacheModel)) {
			return false;
		}

		LayoutSetResourceCacheModel layoutSetResourceCacheModel = (LayoutSetResourceCacheModel)obj;

		if ((layoutSetResourceId == layoutSetResourceCacheModel.layoutSetResourceId) &&
				(mvccVersion == layoutSetResourceCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSetResourceId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", layoutSetResourceId=");
		sb.append(layoutSetResourceId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", pageCount=");
		sb.append(pageCount);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSetResource toEntityModel() {
		LayoutSetResourceImpl layoutSetResourceImpl = new LayoutSetResourceImpl();

		layoutSetResourceImpl.setMvccVersion(mvccVersion);
		layoutSetResourceImpl.setLayoutSetResourceId(layoutSetResourceId);
		layoutSetResourceImpl.setGroupId(groupId);
		layoutSetResourceImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			layoutSetResourceImpl.setCreateDate(null);
		}
		else {
			layoutSetResourceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSetResourceImpl.setModifiedDate(null);
		}
		else {
			layoutSetResourceImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSetResourceImpl.setPageCount(pageCount);
		layoutSetResourceImpl.setPrivateLayout(privateLayout);

		layoutSetResourceImpl.resetOriginalValues();

		return layoutSetResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		layoutSetResourceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		pageCount = objectInput.readInt();

		privateLayout = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(layoutSetResourceId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeInt(pageCount);

		objectOutput.writeBoolean(privateLayout);
	}

	public long mvccVersion;
	public long layoutSetResourceId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public int pageCount;
	public boolean privateLayout;
}