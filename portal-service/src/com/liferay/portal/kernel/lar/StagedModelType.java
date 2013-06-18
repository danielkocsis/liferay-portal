/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Zsolt Berentey
 */
public class StagedModelType {

	public StagedModelType(Class<?> clazz) {
		setClassName(clazz.getName());
	}

	public StagedModelType(Class<?> clazz, Class<?> referrerClass) {
		setClassName(clazz.getName());
		setReferrerClassName(referrerClass.getName());
	}

	public StagedModelType(long classNameId) {
		setClassNameId(classNameId);
	}

	public StagedModelType(long classNameId, long referrerClassNameId) {
		setClassNameId(classNameId);
		setReferrerClassNameId(referrerClassNameId);
	}

	public StagedModelType(String className) {
		setClassName(className);
	}

	public StagedModelType(String className, String referrerClassName) {
		setClassName(className);
		setReferrerClassName(referrerClassName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof StagedModelType)) {
			return false;
		}

		StagedModelType stagedModelType = (StagedModelType)obj;

		if ((stagedModelType._classNameId != _classNameId) ||
			(stagedModelType._referrerClassNameId != _referrerClassNameId)) {

			return false;
		}

		return true;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public String getReferrerClassName() {
		return _referrerClassName;
	}

	public long getReferrerClassNameId() {
		return _referrerClassNameId;
	}

	@Override
	public int hashCode() {
		int hashCode = (int) _classNameId;

		hashCode = 31 * hashCode + (int)(_classNameId >>> 32);
		hashCode = 31 * hashCode + (int)_referrerClassNameId;

		return 31 * hashCode + (int)(_referrerClassNameId >>> 32);
	}

	public void setClassName(String className) {
		_className = className;
		_classNameId = PortalUtil.getClassNameId(className);
	}

	public void setClassNameId(long classNameId) {
		_className = PortalUtil.getClassName(classNameId);
		_classNameId = classNameId;
	}

	public void setReferrerClassName(String referrerClassName) {
		_referrerClassName = referrerClassName;
		_referrerClassNameId = PortalUtil.getClassNameId(referrerClassName);
	}

	public void setReferrerClassNameId(long referrerClassNameId) {
		_referrerClassName = PortalUtil.getClassName(referrerClassNameId);
		_referrerClassNameId = referrerClassNameId;
	}

	@Override
	public String toString() {
		if (_classNameId == 0) {
			return _className;
		}

		return _className + StringPool.POUND + _referrerClassName;
	}

	private String _className;
	private long _classNameId;
	private String _referrerClassName;
	private long _referrerClassNameId;

}