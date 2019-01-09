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

/**
 * Provides a wrapper for {@link LayoutSetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetLocalService
 * @generated
 */
@ProviderType
public class LayoutSetLocalServiceWrapper implements LayoutSetLocalService,
	ServiceWrapper<LayoutSetLocalService> {
	public LayoutSetLocalServiceWrapper(
		LayoutSetLocalService layoutSetLocalService) {
		_layoutSetLocalService = layoutSetLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet addLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.addLayoutSet(groupId, privateLayout);
	}

	@Override
	public void deleteLayoutSet(long groupId, boolean privateLayout,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.deleteLayoutSet(groupId, privateLayout,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		long layoutSetId) {
		return _layoutSetLocalService.fetchLayoutSet(layoutSetId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		long groupId, boolean privateLayout) {
		return _layoutSetLocalService.fetchLayoutSet(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSet(
		String virtualHostname) {
		return _layoutSetLocalService.fetchLayoutSet(virtualHostname);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet fetchLayoutSetByLogoId(
		boolean privateLayout, long logoId) {
		return _layoutSetLocalService.fetchLayoutSetByLogoId(privateLayout,
			logoId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutSet> fetchLayoutSets(
		long companyId, boolean privateLayout) {
		return _layoutSetLocalService.fetchLayoutSets(companyId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(layoutSetId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet getLayoutSet(
		String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.getLayoutSet(virtualHostname);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return _layoutSetLocalService.getLayoutSetsByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutSetLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLayoutSet(
		com.liferay.portal.kernel.model.LayoutSet layoutSet) {
		return _layoutSetLocalService.updateLayoutSet(layoutSet);
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
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(groupId,
			privateLayout, layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			bytes);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			file);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			is);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLogo(long groupId,
		boolean privateLayout, boolean logo, java.io.InputStream is,
		boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLogo(groupId, privateLayout, logo,
			is, cleanUpStream);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateLookAndFeel(
		long groupId, boolean privateLayout, String themeId,
		String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateLookAndFeel(groupId, privateLayout,
			themeId, colorSchemeId, css);
	}

	@Override
	public void updateLookAndFeel(long groupId, String themeId,
		String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetLocalService.updateLookAndFeel(groupId, themeId,
			colorSchemeId, css);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updatePageCount(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateSettings(
		long groupId, boolean privateLayout, String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateSettings(groupId, privateLayout,
			settings);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSet updateVirtualHost(
		long groupId, boolean privateLayout, String virtualHostname)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetLocalService.updateVirtualHost(groupId, privateLayout,
			virtualHostname);
	}

	@Override
	public LayoutSetLocalService getWrappedService() {
		return _layoutSetLocalService;
	}

	@Override
	public void setWrappedService(LayoutSetLocalService layoutSetLocalService) {
		_layoutSetLocalService = layoutSetLocalService;
	}

	private LayoutSetLocalService _layoutSetLocalService;
}