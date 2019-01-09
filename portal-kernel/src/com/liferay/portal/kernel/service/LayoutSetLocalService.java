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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * Provides the local service interface for LayoutSet. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetLocalServiceUtil
 * @see com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutSetLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetLocalServiceUtil} to access the layout set local service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public LayoutSet addLayoutSet(long groupId, boolean privateLayout)
		throws PortalException;

	public void deleteLayoutSet(long groupId, boolean privateLayout,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSet(long layoutSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSet(long groupId, boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSet(String virtualHostname);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSetByLogoId(boolean privateLayout, long logoId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSet> fetchLayoutSets(long companyId, boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getLayoutSet(long layoutSetId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getLayoutSet(long groupId, boolean privateLayout)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getLayoutSet(String virtualHostname)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public LayoutSet updateLayoutSet(LayoutSet layoutSet);

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
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		String layoutSetPrototypeUuid) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, byte[] bytes) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, File file) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, InputStream is) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, InputStream is, boolean cleanUpStream)
		throws PortalException;

	public LayoutSet updateLookAndFeel(long groupId, boolean privateLayout,
		String themeId, String colorSchemeId, String css)
		throws PortalException;

	public void updateLookAndFeel(long groupId, String themeId,
		String colorSchemeId, String css) throws PortalException;

	public LayoutSet updatePageCount(long groupId, boolean privateLayout)
		throws PortalException;

	public LayoutSet updateSettings(long groupId, boolean privateLayout,
		String settings) throws PortalException;

	public LayoutSet updateVirtualHost(long groupId, boolean privateLayout,
		String virtualHostname) throws PortalException;
}