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

import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public interface LarHandler {

	public String getExportedContent(
		PortletDataContext portletDataContext, Portlet portlet);

	public String getExportedContent(
		PortletDataContext portletDataContext, String portletId);

	public ClassedModel readModel(
		PortletDataContext portletDataContext,
		Class<? extends ClassedModel> clazz, long groupId, String uuid);

	public Map<String, String> readModelAttributes(
		PortletDataContext portletDataContext, ClassedModel classedModel);

	public List<ClassedModel> readModels(
		PortletDataContext portletDataContext,
		Class<? extends ClassedModel> clazz);

	public Map<String, String> readReferenceAttributes(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		ClassedModel referredModel);

	public ClassedModel readReferencedModel(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz, long classPK);

	public ClassedModel readReferencedModel(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz, long groupId, String uuid);

	public List<ClassedModel> readReferencedModels(
		PortletDataContext portletDataContext, ClassedModel referrerModel);

	public List<ClassedModel> readReferencedModels(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz);

	public void writeModel(
		PortletDataContext portletDataContext, ClassedModel model,
		Map<String, String> parameters);

	public void writeReference(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		ClassedModel referredModel, Map<String, String> parameters);

}