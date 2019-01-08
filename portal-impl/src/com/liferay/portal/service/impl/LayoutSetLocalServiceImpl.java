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

package com.liferay.portal.service.impl;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.LayoutSetVirtualHostException;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetResource;
import com.liferay.portal.kernel.model.LayoutSetStagingHandler;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.service.base.LayoutSetLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Ganesh Ram
 */
public class LayoutSetLocalServiceImpl extends LayoutSetLocalServiceBaseImpl {

	@Override
	public LayoutSet addLayoutSet(long groupId, boolean privateLayout)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Date now = new Date();

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.create(
				counterLocalService.increment());

		LayoutSetVersion layoutSetVersion = layoutSetVersionPersistence.create(
			counterLocalService.increment());

		layoutSetResource.setGroupId(groupId);
		layoutSetResource.setCompanyId(group.getCompanyId());
		layoutSetResource.setCreateDate(now);
		layoutSetResource.setModifiedDate(now);
		layoutSetResource.setPrivateLayout(privateLayout);

		initLayoutSet(layoutSetResource, layoutSetVersion);

		layoutSetResourcePersistence.update(layoutSetResource);
		layoutSetVersionPersistence.update(layoutSetVersion);

		return _createLayoutSetDTO(layoutSetResource, layoutSetVersion);
	}

	@Override
	public void deleteLayoutSet(
			long groupId, boolean privateLayout, ServiceContext serviceContext)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		// Layouts

		serviceContext.setAttribute("updatePageCount", Boolean.FALSE);

		layoutLocalService.deleteLayouts(
			groupId, privateLayout, serviceContext);

		// Logo

		if (group.isStagingGroup() || !group.isOrganization() ||
			!group.isSite()) {

			List<LayoutSetVersion> layoutSetVersions =
				layoutSetVersionPersistence.findByLayoutSetResourceId(
					layoutSetResource.getLayoutSetResourceId());

			for (LayoutSetVersion layoutSetVersion : layoutSetVersions) {
				try {
					imageLocalService.deleteImage(layoutSetVersion.getLogoId());
				}
				catch (NoSuchImageException nsie) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to delete image " +
								layoutSetVersion.getLogoId(),
							nsie);
					}
				}
			}
		}

		// Layout set

		if (!group.isStagingGroup() && group.isOrganization() &&
			group.isSite()) {

			LayoutSetVersion layoutSetVersion = _createNewLayoutSetVersion(
				layoutSetResource);

			initLayoutSet(layoutSetResource, layoutSetVersion);

			layoutSetResourcePersistence.update(layoutSetResource);
			layoutSetVersionPersistence.update(layoutSetVersion);
		}
		else {
			layoutSetVersionPersistence.removeByLayoutSetResourceId(
				layoutSetResource.getLayoutSetResourceId());

			layoutSetResourcePersistence.remove(layoutSetResource);
		}

		// Virtual host

		try {
			virtualHostPersistence.removeByC_L(
				layoutSetResource.getCompanyId(),
				layoutSetResource.getLayoutSetResourceId());
		}
		catch (NoSuchVirtualHostException nsvhe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsvhe, nsvhe);
			}
		}
	}

	@Override
	public LayoutSet fetchLayoutSet(long groupId, boolean privateLayout) {
		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.fetchByG_P(groupId, privateLayout);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet fetchLayoutSet(String virtualHostname) {
		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
			virtualHostname);

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() == 0)) {
			return null;
		}

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.fetchByPrimaryKey(
				virtualHost.getLayoutSetId());

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet fetchLayoutSetByLogoId(boolean privateLayout, long logoId)
		throws PortalException {

		// todo: finder!

		return null;

		//layoutSetResourcePersistence.fetchByP_L(privateLayout, logoId);
	}

	@Override
	public LayoutSet getLayoutSet(long layoutSetId) throws PortalException {
		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByPrimaryKey(layoutSetId);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet getLayoutSet(long groupId, boolean privateLayout)
		throws PortalException {

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet getLayoutSet(String virtualHostname)
		throws PortalException {

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		VirtualHost virtualHost = virtualHostPersistence.findByHostname(
			virtualHostname);

		if (virtualHost.getLayoutSetId() == 0) {
			throw new LayoutSetVirtualHostException(
				"Virtual host is associated with company " +
					virtualHost.getCompanyId());
		}

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByPrimaryKey(
				virtualHost.getLayoutSetId());

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public List<LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {

		List<LayoutSetVersion> layoutSetVersions =
			layoutSetVersionPersistence.findByLayoutSetPrototypeUuid(
				layoutSetPrototypeUuid);

		List<LayoutSet> layoutSetDTOs = new ArrayList<>();

		for (LayoutSetVersion layoutSetVersion : layoutSetVersions) {
			LayoutSetResource layoutSetResource =
				layoutSetResourcePersistence.fetchByPrimaryKey(
					layoutSetVersion.getLayoutSetResourceId());

			if (layoutSetResource == null) {
				continue;
			}

			LayoutSet layoutSetDTO = _createLayoutSetDTO(
				layoutSetResource, layoutSetVersion);

			layoutSetDTOs.add(layoutSetDTO);
		}

		return layoutSetDTOs;
	}

	@Override
	public LayoutSet updateLayoutSet(LayoutSet layoutSet)
		throws PortalException {

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByPrimaryKey(
				layoutSet.getPrimaryKey());

		LayoutSetVersion layoutSetVersion = _createNewLayoutSetVersion(
			layoutSetResource);

		layoutSetVersion.setLayoutSetResourceId(
			layoutSetResource.getLayoutSetResourceId());
		layoutSetVersion.setLogoId(layoutSet.getLogoId());
		layoutSetVersion.setThemeId(layoutSet.getThemeId());
		layoutSetVersion.setColorSchemeId(layoutSet.getColorSchemeId());
		layoutSetVersion.setCss(layoutSet.getCss());
		layoutSetVersion.setSettings(layoutSet.getSettings());
		layoutSetVersion.setLayoutSetPrototypeUuid(
			layoutSet.getLayoutSetPrototypeUuid());
		layoutSetVersion.setLayoutSetPrototypeLinkEnabled(
			layoutSet.isLayoutSetPrototypeLinkEnabled());

		layoutSetVersionPersistence.update(layoutSetVersion);

		return _createLayoutSetDTO(layoutSetResource, layoutSetVersion);
	}

	/**
	 * Updates the state of the layout set prototype link.
	 *
	 * @param groupId the primary key of the group
	 * @param privateLayout whether the layout set is private to the group
	 * @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	 *        link enabled
	 * @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	 *        link with
	 */
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException {

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(
			layoutSetResource);

		if (layoutSetBranch == null) {
			LayoutSetVersion latestLayoutSetVersion = _getLatestVersion(
				layoutSetResource);

			if (Validator.isNull(layoutSetPrototypeUuid)) {
				layoutSetPrototypeUuid =
					latestLayoutSetVersion.getLayoutSetPrototypeUuid();
			}

			if (Validator.isNull(layoutSetPrototypeUuid)) {
				layoutSetPrototypeLinkEnabled = false;
			}

			// new version here

			LayoutSetVersion layoutSetVersion = _createNewLayoutSetVersion(
				layoutSetResource);

			layoutSetVersion.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
			layoutSetVersion.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);

			layoutSetVersionPersistence.update(layoutSetVersion);

			return;
		}

		if (Validator.isNull(layoutSetPrototypeUuid)) {
			layoutSetPrototypeUuid =
				layoutSetBranch.getLayoutSetPrototypeUuid();
		}

		if (Validator.isNull(layoutSetPrototypeUuid) &&
			layoutSetPrototypeLinkEnabled) {

			throw new IllegalStateException(
				"Cannot set layoutSetPrototypeLinkEnabled to true when " +
					"layoutSetPrototypeUuid is null");
		}

		layoutSetBranch.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		layoutSetBranch.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);

		layoutSetBranchPersistence.update(layoutSetBranch);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, byte[] bytes)
		throws PortalException {

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(
			layoutSetResource);

		if (layoutSetBranch == null) {
			layoutSetResource.setModifiedDate(new Date());

			LayoutSetVersion layoutSetVersion = _createNewLayoutSetVersion(
				layoutSetResource);

			PortalUtil.updateImageId(
				layoutSetVersion, logo, bytes, "logoId", 0, 0, 0);

			layoutSetVersionPersistence.update(layoutSetVersion);

			layoutSetResourcePersistence.update(layoutSetResource);

			return _createLayoutSetDTO(layoutSetResource, layoutSetVersion);
		}

		layoutSetBranch.setModifiedDate(new Date());

		PortalUtil.updateImageId(
			layoutSetBranch, logo, bytes, "logoId", 0, 0, 0);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, File file)
		throws PortalException {

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateLogo(groupId, privateLayout, logo, bytes);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is)
		throws PortalException {

		return updateLogo(groupId, privateLayout, logo, is, true);
	}

	@Override
	public LayoutSet updateLogo(
			long groupId, boolean privateLayout, boolean logo, InputStream is,
			boolean cleanUpStream)
		throws PortalException {

		byte[] bytes = null;

		try {
			bytes = FileUtil.getBytes(is, -1, cleanUpStream);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return updateLogo(groupId, privateLayout, logo, bytes);
	}

	@Override
	public LayoutSet updateLookAndFeel(
			long groupId, boolean privateLayout, String themeId,
			String colorSchemeId, String css)
		throws PortalException {

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		if (Validator.isNull(themeId)) {
			themeId = ThemeFactoryUtil.getDefaultRegularThemeId(
				layoutSetResource.getCompanyId());
		}

		if (Validator.isNull(colorSchemeId)) {
			colorSchemeId =
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
		}

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(
			layoutSetResource);

		if (layoutSetBranch == null) {
			LayoutSetVersion layoutSetVersion = _createNewLayoutSetVersion(
				layoutSetResource);

			layoutSetResource.setModifiedDate(new Date());
			layoutSetVersion.setThemeId(themeId);
			layoutSetVersion.setColorSchemeId(colorSchemeId);
			layoutSetVersion.setCss(css);

			layoutSetResourcePersistence.update(layoutSetResource);
			layoutSetVersionPersistence.update(layoutSetVersion);

			if (PrefsPropsUtil.getBoolean(
					PropsKeys.THEME_SYNC_ON_GROUP,
					PropsValues.THEME_SYNC_ON_GROUP)) {

				LayoutSetResource otherLayoutSetResource =
					layoutSetResourcePersistence.findByG_P(
						layoutSetResource.getGroupId(),
						layoutSetResource.isPrivateLayout());

				LayoutSetVersion otherLayoutSetVersion =
					_createNewLayoutSetVersion(otherLayoutSetResource);

				otherLayoutSetVersion.setThemeId(themeId);
				otherLayoutSetVersion.setColorSchemeId(colorSchemeId);

				layoutSetVersionPersistence.update(otherLayoutSetVersion);
			}

			return _createLayoutSetDTO(layoutSetResource, layoutSetVersion);
		}

		layoutSetBranch.setModifiedDate(new Date());
		layoutSetBranch.setThemeId(themeId);
		layoutSetBranch.setColorSchemeId(colorSchemeId);
		layoutSetBranch.setCss(css);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public void updateLookAndFeel(
			long groupId, String themeId, String colorSchemeId, String css)
		throws PortalException {

		updateLookAndFeel(groupId, false, themeId, colorSchemeId, css);
		updateLookAndFeel(groupId, true, themeId, colorSchemeId, css);
	}

	@Override
	public LayoutSet updatePageCount(long groupId, boolean privateLayout)
		throws PortalException {

		int pageCount = layoutPersistence.countByG_P(groupId, privateLayout);

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		layoutSetResource.setModifiedDate(new Date());
		layoutSetResource.setPageCount(pageCount);

		layoutSetResourcePersistence.update(layoutSetResource);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet updateSettings(
			long groupId, boolean privateLayout, String settings)
		throws PortalException {

		UnicodeProperties settingsProperties = new UnicodeProperties();

		settingsProperties.fastLoad(settings);

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		LayoutSetBranch layoutSetBranch = _getLayoutSetBranch(
			layoutSetResource);

		if (layoutSetBranch == null) {
			layoutSetResource.setModifiedDate(new Date());

			LayoutSetVersion latestLayoutSetVersion = _getLatestVersion(
				layoutSetResource);

			validateSettings(
				latestLayoutSetVersion.getSettingsProperties(),
				settingsProperties);

			LayoutSetVersion layoutSetVersion = _createNewLayoutSetVersion(
				layoutSetResource);

			layoutSetVersion.setSettingsProperties(settingsProperties);

			layoutSetVersionPersistence.update(latestLayoutSetVersion);

			return _createLayoutSetDTO(layoutSetResource, layoutSetVersion);
		}

		layoutSetBranch.setModifiedDate(new Date());

		validateSettings(
			layoutSetBranch.getSettingsProperties(), settingsProperties);

		layoutSetBranch.setSettingsProperties(settingsProperties);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return _createLayoutSetDTO(layoutSetResource);
	}

	@Override
	public LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHostname)
		throws PortalException {

		virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(virtualHostname));

		if (Validator.isNotNull(virtualHostname) &&
			!Validator.isDomain(virtualHostname)) {

			throw new LayoutSetVirtualHostException();
		}

		LayoutSetResource layoutSetResource =
			layoutSetResourcePersistence.findByG_P(groupId, privateLayout);

		if (Validator.isNotNull(virtualHostname)) {
			VirtualHost virtualHost = virtualHostPersistence.fetchByHostname(
				virtualHostname);

			if (virtualHost == null) {
				virtualHostLocalService.updateVirtualHost(
					layoutSetResource.getCompanyId(),
					layoutSetResource.getLayoutSetResourceId(),
					virtualHostname);
			}
			else {
				if ((virtualHost.getCompanyId() !=
						layoutSetResource.getCompanyId()) ||
					(virtualHost.getLayoutSetId() !=
						layoutSetResource.getLayoutSetResourceId())) {

					throw new LayoutSetVirtualHostException();
				}
			}
		}
		else {
			try {
				virtualHostPersistence.removeByC_L(
					layoutSetResource.getCompanyId(),
					layoutSetResource.getLayoutSetResourceId());

				layoutSetResourcePersistence.clearCache(layoutSetResource);

				TransactionCommitCallbackUtil.registerCallback(
					new Callable<Void>() {

						@Override
						public Void call() {
							EntityCacheUtil.removeResult(
								LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
								LayoutSetImpl.class,
								layoutSetResource.getLayoutSetResourceId());

							return null;
						}

					});
			}
			catch (NoSuchVirtualHostException nsvhe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsvhe, nsvhe);
				}
			}
		}

		return _createLayoutSetDTO(layoutSetResource);
	}

	protected void initLayoutSet(
			LayoutSetResource layoutSetResource,
			LayoutSetVersion layoutSetVersion)
		throws PortalException {

		Group group = groupLocalService.getGroup(
			layoutSetResource.getGroupId());

		if (group.isStagingGroup()) {
			LayoutSet liveLayoutSet = null;

			Group liveGroup = group.getLiveGroup();

			if (layoutSetResource.isPrivateLayout()) {
				liveLayoutSet = liveGroup.getPrivateLayoutSet();
			}
			else {
				liveLayoutSet = liveGroup.getPublicLayoutSet();
			}

			layoutSetVersion.setLogoId(liveLayoutSet.getLogoId());

			if (liveLayoutSet.isLogo()) {
				Image logoImage = imageLocalService.getImage(
					liveLayoutSet.getLogoId());

				long logoId = counterLocalService.increment();

				imageLocalService.updateImage(
					logoId, logoImage.getTextObj(), logoImage.getType(),
					logoImage.getHeight(), logoImage.getWidth(),
					logoImage.getSize());

				layoutSetVersion.setLogoId(logoId);
			}

			layoutSetVersion.setThemeId(liveLayoutSet.getThemeId());
			layoutSetVersion.setColorSchemeId(liveLayoutSet.getColorSchemeId());
			layoutSetVersion.setCss(liveLayoutSet.getCss());
			layoutSetVersion.setSettings(liveLayoutSet.getSettings());
		}
		else {
			layoutSetVersion.setThemeId(
				ThemeFactoryUtil.getDefaultRegularThemeId(
					group.getCompanyId()));
			layoutSetVersion.setColorSchemeId(
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId());
			layoutSetVersion.setCss(StringPool.BLANK);
			layoutSetVersion.setSettings(StringPool.BLANK);
		}
	}

	protected void validateSettings(
		UnicodeProperties oldSettingsProperties,
		UnicodeProperties newSettingsProperties) {

		boolean enableJavaScript =
			PropsValues.
				FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_LAYOUTSET_JAVASCRIPT;

		if (!enableJavaScript) {
			String javaScript = oldSettingsProperties.getProperty("javascript");

			newSettingsProperties.setProperty("javascript", javaScript);
		}
	}

	private LayoutSet _createLayoutSetDTO(LayoutSetResource layoutSetResource) {
		LayoutSetVersion layoutSetVersion = _getLatestVersion(
			layoutSetResource);

		return _createLayoutSetDTO(layoutSetResource, layoutSetVersion);
	}

	private LayoutSet _createLayoutSetDTO(
		LayoutSetResource layoutSetResource,
		LayoutSetVersion layoutSetVersion) {

		LayoutSet layoutSet = new LayoutSetImpl();

		layoutSet.setGroupId(layoutSetResource.getGroupId());

		layoutSet.setCompanyId(layoutSetResource.getCompanyId());
		layoutSet.setCreateDate(layoutSetResource.getCreateDate());
		layoutSet.setModifiedDate(layoutSetResource.getModifiedDate());
		layoutSet.setPrivateLayout(layoutSetResource.isPrivateLayout());
		layoutSet.setLogoId(layoutSetVersion.getLogoId());
		layoutSet.setThemeId(layoutSetVersion.getThemeId());
		layoutSet.setColorSchemeId(layoutSetVersion.getColorSchemeId());
		layoutSet.setCss(layoutSetVersion.getCss());
		layoutSet.setPageCount(layoutSetResource.getPageCount());
		layoutSet.setSettings(layoutSetVersion.getSettings());
		layoutSet.setLayoutSetPrototypeUuid(
			layoutSetVersion.getLayoutSetPrototypeUuid());
		layoutSet.setLayoutSetPrototypeLinkEnabled(
			layoutSetVersion.isLayoutSetPrototypeLinkEnabled());

		return layoutSet;
	}

	private LayoutSetVersion _createNewLayoutSetVersion(
		LayoutSetResource layoutSetResource) {

		LayoutSetVersion latestLayoutSetVersion = _getLatestVersion(
			layoutSetResource);

		LayoutSetVersion layoutSetVersion = layoutSetVersionPersistence.create(
			counterLocalService.increment());

		layoutSetVersion.setLayoutSetResourceId(
			latestLayoutSetVersion.getLayoutSetResourceId());
		layoutSetVersion.setLogoId(latestLayoutSetVersion.getLogoId());
		layoutSetVersion.setThemeId(latestLayoutSetVersion.getThemeId());
		layoutSetVersion.setColorSchemeId(
			latestLayoutSetVersion.getColorSchemeId());
		layoutSetVersion.setCss(latestLayoutSetVersion.getCss());
		layoutSetVersion.setSettings(latestLayoutSetVersion.getSettings());
		layoutSetVersion.setLayoutSetPrototypeUuid(
			latestLayoutSetVersion.getLayoutSetPrototypeUuid());
		layoutSetVersion.setLayoutSetPrototypeLinkEnabled(
			latestLayoutSetVersion.isLayoutSetPrototypeLinkEnabled());

		return layoutSetVersionPersistence.update(layoutSetVersion);
	}

	private LayoutSetVersion _getLatestVersion(
		LayoutSetResource layoutSetResource) {

		return layoutSetVersionPersistence.fetchByLayoutSetResourceId_First(
			layoutSetResource.getLayoutSetResourceId(),
			new OrderByComparator<LayoutSetVersion>() {

				@Override
				public int compare(LayoutSetVersion o1, LayoutSetVersion o2) {
					Date createDate = o1.getCreateDate();

					return createDate.compareTo(o2.getCreateDate());
				}

			});
	}

	private LayoutSetBranch _getLayoutSetBranch(
			LayoutSetResource layoutSetResource)
		throws PortalException {

		LayoutSet layoutSet = _createLayoutSetDTO(layoutSetResource);

		LayoutSetStagingHandler layoutSetStagingHandler =
			LayoutStagingUtil.getLayoutSetStagingHandler(layoutSet);

		if (layoutSetStagingHandler != null) {
			return layoutSetStagingHandler.getLayoutSetBranch();
		}

		Group group = groupPersistence.findByPrimaryKey(
			layoutSetResource.getGroupId());

		if (LayoutStagingUtil.isBranchingLayoutSet(
				group, layoutSetResource.isPrivateLayout())) {

			layoutSetStagingHandler = new LayoutSetStagingHandler(layoutSet);

			return layoutSetStagingHandler.getLayoutSetBranch();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetLocalServiceImpl.class);

}