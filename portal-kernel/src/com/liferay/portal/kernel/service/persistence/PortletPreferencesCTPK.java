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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class PortletPreferencesCTPK
	implements Comparable<PortletPreferencesCTPK>, Serializable {

	public long classPK;
	public long ctCollectionId;

	public PortletPreferencesCTPK() {
	}

	public PortletPreferencesCTPK(long classPK, long ctCollectionId) {
		this.classPK = classPK;
		this.ctCollectionId = ctCollectionId;
	}

	public long getClassPK() {
		return classPK;
	}

	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}

	public long getCtCollectionId() {
		return ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		this.ctCollectionId = ctCollectionId;
	}

	@Override
	public int compareTo(PortletPreferencesCTPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (classPK < pk.classPK) {
			value = -1;
		}
		else if (classPK > pk.classPK) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (ctCollectionId < pk.ctCollectionId) {
			value = -1;
		}
		else if (ctCollectionId > pk.ctCollectionId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletPreferencesCTPK)) {
			return false;
		}

		PortletPreferencesCTPK pk = (PortletPreferencesCTPK)obj;

		if ((classPK == pk.classPK) && (ctCollectionId == pk.ctCollectionId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, classPK);
		hashCode = HashUtil.hash(hashCode, ctCollectionId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6);

		sb.append("{");

		sb.append("classPK=");

		sb.append(classPK);
		sb.append(", ctCollectionId=");

		sb.append(ctCollectionId);

		sb.append("}");

		return sb.toString();
	}

}