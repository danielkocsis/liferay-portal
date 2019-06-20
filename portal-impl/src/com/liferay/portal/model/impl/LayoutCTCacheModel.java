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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutCT;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.service.persistence.LayoutCTPK;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing LayoutCT in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutCTCacheModel
	implements CacheModel<LayoutCT>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutCTCacheModel)) {
			return false;
		}

		LayoutCTCacheModel layoutCTCacheModel = (LayoutCTCacheModel)obj;

		if (layoutCTPK.equals(layoutCTCacheModel.layoutCTPK) &&
			(mvccVersion == layoutCTCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutCTPK);

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
		StringBundler sb = new StringBundler(9);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutCT toEntityModel() {
		LayoutCTImpl layoutCTImpl = new LayoutCTImpl();

		layoutCTImpl.setMvccVersion(mvccVersion);
		layoutCTImpl.setPlid(plid);
		layoutCTImpl.setCtCollectionId(ctCollectionId);

		if (typeSettings == null) {
			layoutCTImpl.setTypeSettings("");
		}
		else {
			layoutCTImpl.setTypeSettings(typeSettings);
		}

		layoutCTImpl.resetOriginalValues();

		return layoutCTImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		plid = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		typeSettings = objectInput.readUTF();

		layoutCTPK = new LayoutCTPK(plid, ctCollectionId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(plid);

		objectOutput.writeLong(ctCollectionId);

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}
	}

	public long mvccVersion;
	public long plid;
	public long ctCollectionId;
	public String typeSettings;
	public transient LayoutCTPK layoutCTPK;

}