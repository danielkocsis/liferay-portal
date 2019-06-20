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
public class LayoutCTPK implements Comparable<LayoutCTPK>, Serializable {

	public long plid;
	public long ctCollectionId;

	public LayoutCTPK() {
	}

	public LayoutCTPK(long plid, long ctCollectionId) {
		this.plid = plid;
		this.ctCollectionId = ctCollectionId;
	}

	public long getPlid() {
		return plid;
	}

	public void setPlid(long plid) {
		this.plid = plid;
	}

	public long getCtCollectionId() {
		return ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		this.ctCollectionId = ctCollectionId;
	}

	@Override
	public int compareTo(LayoutCTPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (plid < pk.plid) {
			value = -1;
		}
		else if (plid > pk.plid) {
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

		if (!(obj instanceof LayoutCTPK)) {
			return false;
		}

		LayoutCTPK pk = (LayoutCTPK)obj;

		if ((plid == pk.plid) && (ctCollectionId == pk.ctCollectionId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, plid);
		hashCode = HashUtil.hash(hashCode, ctCollectionId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6);

		sb.append("{");

		sb.append("plid=");

		sb.append(plid);
		sb.append(", ctCollectionId=");

		sb.append(ctCollectionId);

		sb.append("}");

		return sb.toString();
	}

}