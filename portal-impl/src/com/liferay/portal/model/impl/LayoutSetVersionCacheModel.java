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
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutSetVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersion
 * @generated
 */
@ProviderType
public class LayoutSetVersionCacheModel implements CacheModel<LayoutSetVersion>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetVersionCacheModel)) {
			return false;
		}

		LayoutSetVersionCacheModel layoutSetVersionCacheModel = (LayoutSetVersionCacheModel)obj;

		if ((layoutSetVersionId == layoutSetVersionCacheModel.layoutSetVersionId) &&
				(mvccVersion == layoutSetVersionCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSetVersionId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", layoutSetVersionId=");
		sb.append(layoutSetVersionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", layoutSetResourceId=");
		sb.append(layoutSetResourceId);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", themeId=");
		sb.append(themeId);
		sb.append(", colorSchemeId=");
		sb.append(colorSchemeId);
		sb.append(", css=");
		sb.append(css);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(layoutSetPrototypeUuid);
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(layoutSetPrototypeLinkEnabled);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSetVersion toEntityModel() {
		LayoutSetVersionImpl layoutSetVersionImpl = new LayoutSetVersionImpl();

		layoutSetVersionImpl.setMvccVersion(mvccVersion);
		layoutSetVersionImpl.setLayoutSetVersionId(layoutSetVersionId);
		layoutSetVersionImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			layoutSetVersionImpl.setCreateDate(null);
		}
		else {
			layoutSetVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSetVersionImpl.setModifiedDate(null);
		}
		else {
			layoutSetVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSetVersionImpl.setLayoutSetResourceId(layoutSetResourceId);
		layoutSetVersionImpl.setLogoId(logoId);

		if (themeId == null) {
			layoutSetVersionImpl.setThemeId("");
		}
		else {
			layoutSetVersionImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutSetVersionImpl.setColorSchemeId("");
		}
		else {
			layoutSetVersionImpl.setColorSchemeId(colorSchemeId);
		}

		if (css == null) {
			layoutSetVersionImpl.setCss("");
		}
		else {
			layoutSetVersionImpl.setCss(css);
		}

		if (settings == null) {
			layoutSetVersionImpl.setSettings("");
		}
		else {
			layoutSetVersionImpl.setSettings(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			layoutSetVersionImpl.setLayoutSetPrototypeUuid("");
		}
		else {
			layoutSetVersionImpl.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		layoutSetVersionImpl.setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);

		layoutSetVersionImpl.resetOriginalValues();

		return layoutSetVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		layoutSetVersionId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		layoutSetResourceId = objectInput.readLong();

		logoId = objectInput.readLong();
		themeId = objectInput.readUTF();
		colorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();
		settings = objectInput.readUTF();
		layoutSetPrototypeUuid = objectInput.readUTF();

		layoutSetPrototypeLinkEnabled = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(layoutSetVersionId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(layoutSetResourceId);

		objectOutput.writeLong(logoId);

		if (themeId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(themeId);
		}

		if (colorSchemeId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(colorSchemeId);
		}

		if (css == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(css);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutSetPrototypeUuid);
		}

		objectOutput.writeBoolean(layoutSetPrototypeLinkEnabled);
	}

	public long mvccVersion;
	public long layoutSetVersionId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long layoutSetResourceId;
	public long logoId;
	public String themeId;
	public String colorSchemeId;
	public String css;
	public String settings;
	public String layoutSetPrototypeUuid;
	public boolean layoutSetPrototypeLinkEnabled;
}