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
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PortletPreferencesCT;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPK;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing PortletPreferencesCT in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class PortletPreferencesCTCacheModel
	implements CacheModel<PortletPreferencesCT>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletPreferencesCTCacheModel)) {
			return false;
		}

		PortletPreferencesCTCacheModel portletPreferencesCTCacheModel =
			(PortletPreferencesCTCacheModel)obj;

		if (portletPreferencesCTPK.equals(
				portletPreferencesCTCacheModel.portletPreferencesCTPK) &&
			(mvccVersion == portletPreferencesCTCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, portletPreferencesCTPK);

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
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", preferences=");
		sb.append(preferences);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortletPreferencesCT toEntityModel() {
		PortletPreferencesCTImpl portletPreferencesCTImpl =
			new PortletPreferencesCTImpl();

		portletPreferencesCTImpl.setMvccVersion(mvccVersion);
		portletPreferencesCTImpl.setClassPK(classPK);
		portletPreferencesCTImpl.setCtCollectionId(ctCollectionId);

		if (preferences == null) {
			portletPreferencesCTImpl.setPreferences("");
		}
		else {
			portletPreferencesCTImpl.setPreferences(preferences);
		}

		portletPreferencesCTImpl.resetOriginalValues();

		return portletPreferencesCTImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		classPK = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		preferences = objectInput.readUTF();

		portletPreferencesCTPK = new PortletPreferencesCTPK(
			classPK, ctCollectionId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(ctCollectionId);

		if (preferences == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(preferences);
		}
	}

	public long mvccVersion;
	public long classPK;
	public long ctCollectionId;
	public String preferences;
	public transient PortletPreferencesCTPK portletPreferencesCTPK;

}