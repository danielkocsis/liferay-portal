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

package com.liferay.portal.lar.lifecycle;

import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleEventImpl
	implements ExportImportLifecycleEvent {

	@Override
	public void addEventAttribute(Serializable serializable) {
		_attributes.add(serializable);
	}

	@Override
	public List<Serializable> getEventAttributes() {
		return _attributes;
	}

	@Override
	public int getEventCode() {
		return _eventCode;
	}

	@Override
	public void setEventCode(int eventCode) {
		_eventCode = eventCode;
	}

	private final List<Serializable> _attributes =
		new ArrayList<Serializable>();
	private int _eventCode;

}