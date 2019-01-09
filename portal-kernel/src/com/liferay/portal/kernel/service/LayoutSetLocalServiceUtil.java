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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for LayoutSet. This utility wraps
 * {@link com.liferay.portal.service.impl.LayoutSetLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetLocalService
 * @see com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetLocalServiceImpl
 * @generated
 */
@ProviderType
public class LayoutSetLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.LayoutSet addLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addLayoutSet(groupId, privateLayout);
	}

	public static void deleteLayoutSet(long groupId, boolean privateLayout,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLayoutSet(groupId, privateLayout, serviceContext);
	}

	public static com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		long layoutSetId) {
		return getService().fetchLayoutSet(layoutSetId);
	}

	public static com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		long groupId, boolean privateLayout) {
		return getService().fetchLayoutSet(groupId, privateLayout);
	}

	public static com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		String virtualHostname) {
		return getService().fetchLayoutSet(virtualHostname);
	}

	public static com.liferay.portal.kernel.model.LayoutSet fetchLayoutSetByLogoId(
		boolean privateLayout, long logoId) {
		return getService().fetchLayoutSetByLogoId(privateLayout, logoId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutSet> fetchLayoutSets(
		long companyId, boolean privateLayout) {
		return getService().fetchLayoutSets(companyId, privateLayout);
	}

	public static com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutSet(layoutSetId);
	}

	public static com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutSet(groupId, privateLayout);
	}

	public static com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutSet(virtualHostname);
	}

	public static java.util.List<com.liferay.portal.kernel.model.LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return getService()
				   .getLayoutSetsByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLayoutSet(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return getService().updateLayoutSet(layoutSet);
	}

	/**
	* Updates the state of the layout set prototype link.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout set is private to the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	link enabled
	* @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	link with
	*/
	public static void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateLayoutSetPrototypeLinkEnabled(groupId, privateLayout,
			layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLogo(
		long groupId, boolean privateLayout, boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateLogo(groupId, privateLayout, logo, bytes);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLogo(
		long groupId, boolean privateLayout, boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateLogo(groupId, privateLayout, logo, file);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLogo(
		long groupId, boolean privateLayout, boolean logo,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateLogo(groupId, privateLayout, logo, is);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLogo(
		long groupId, boolean privateLayout, boolean logo,
		java.io.InputStream is, boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLogo(groupId, privateLayout, logo, is, cleanUpStream);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateLookAndFeel(
		long groupId, boolean privateLayout, String themeId,
		String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, themeId,
			colorSchemeId, css);
	}

	public static void updateLookAndFeel(long groupId, String themeId,
		String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateLookAndFeel(groupId, themeId, colorSchemeId, css);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updatePageCount(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updatePageCount(groupId, privateLayout);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateSettings(
		long groupId, boolean privateLayout, String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateSettings(groupId, privateLayout, settings);
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateVirtualHost(
		long groupId, boolean privateLayout, String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateVirtualHost(groupId, privateLayout, virtualHostname);
	}

	public static LayoutSetLocalService getService() {
		if (_service == null) {
			_service = (LayoutSetLocalService)PortalBeanLocatorUtil.locate(LayoutSetLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutSetLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static LayoutSetLocalService _service;
}