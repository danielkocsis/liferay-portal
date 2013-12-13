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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class LarHandlerUtil {

	public static String getExportedContent(
		PortletDataContext portletDataContext, Portlet portlet) {

		return getLarHandler().getExportedContent(portletDataContext, portlet);
	}

	public static String getExportedContent(
		PortletDataContext portletDataContext, String portletId) {

		return getLarHandler().getExportedContent(
			portletDataContext, portletId);
	}

	public static LarHandler getLarHandler() {
		PortalRuntimePermission.checkGetBeanProperty(LarHandler.class);

		return _larHandler;
	}

	public static ClassedModel readModel(
		PortletDataContext portletDataContext,
		Class<? extends ClassedModel> clazz, long groupId, String uuid) {

		return getLarHandler().readModel(
			portletDataContext, clazz, groupId, uuid);
	}

	public static Map<String, String> readModelAttributes(
		PortletDataContext portletDataContext, ClassedModel classedModel) {

		return getLarHandler().readModelAttributes(
			portletDataContext, classedModel);
	}

	public static List<ClassedModel> readModels(
		PortletDataContext portletDataContext,
		Class<? extends ClassedModel> clazz) {

		return getLarHandler().readModels(portletDataContext, clazz);
	}

	public static Map<String, String> readReferenceAttributes(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		ClassedModel referredModel) {

		return getLarHandler().readReferenceAttributes(
			portletDataContext, referrerModel, referredModel);
	}

	public static ClassedModel readReferencedModel(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz, long classPK) {

		return getLarHandler().readReferencedModel(
			portletDataContext, referrerModel, clazz, classPK);
	}

	public static ClassedModel readReferencedModel(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz, long groupId, String uuid) {

		return getLarHandler().readReferencedModel(
			portletDataContext, referrerModel, clazz, groupId, uuid);
	}

	public static List<ClassedModel> readReferencedModels(
		PortletDataContext portletDataContext, ClassedModel referrerModel) {

		return getLarHandler().readReferencedModels(
			portletDataContext, referrerModel);
	}

	public static List<ClassedModel> readReferencedModels(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		Class<? extends ClassedModel> clazz) {

		return getLarHandler().readReferencedModels(
			portletDataContext, referrerModel, clazz);
	}

	public static void writeModel(
		PortletDataContext portletDataContext, ClassedModel model,
		Map<String, String> parameters) {

		getLarHandler().writeModel(portletDataContext, model, parameters);
	}

	public static void writeReference(
		PortletDataContext portletDataContext, ClassedModel referrerModel,
		ClassedModel referredModel, Map<String, String> parameters) {

		getLarHandler().writeReference(
			portletDataContext, referrerModel, referredModel, parameters);
	}

	public void setLarHandler(LarHandler larHandler) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_larHandler = larHandler;
	}

	private static LarHandler _larHandler;

}