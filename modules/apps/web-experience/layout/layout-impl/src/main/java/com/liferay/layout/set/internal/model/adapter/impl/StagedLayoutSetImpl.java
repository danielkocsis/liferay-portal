/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.set.internal.model.adapter.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mate Thurzo
 */
public class StagedLayoutSetImpl implements StagedLayoutSet {

	public StagedLayoutSetImpl() {
	}

	public StagedLayoutSetImpl(LayoutSet layoutSet) {
		Objects.requireNonNull(
			layoutSet,
			"Cannot create a new StagedLayoutSet for a null LayoutSet");

		_layoutSet = layoutSet;
	}

	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedLayoutSet.class);
	}

	public String getUuid() {
		StringBundler sb = new StringBundler(5);

		sb.append(_layoutSet.getLayoutSetId());
		sb.append(StringPool.POUND);
		sb.append(_layoutSet.getGroupId());
		sb.append(StringPool.POUND);
		sb.append(_layoutSet.isPrivateLayout());

		return sb.toString();
	}

	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	public ColorScheme getColorScheme() {
		return _layoutSet.getColorScheme();
	}

	public String getCompanyFallbackVirtualHostname() {
		return _layoutSet.getCompanyFallbackVirtualHostname();
	}

	public Group getGroup() throws PortalException {
		return _layoutSet.getGroup();
	}

	public long getLayoutSetPrototypeId() throws PortalException {
		return _layoutSet.getLayoutSetPrototypeId();
	}

	public long getLiveLogoId() {
		return _layoutSet.getLiveLogoId();
	}

	public boolean getLogo() {
		return _layoutSet.getLogo();
	}

	public UnicodeProperties getSettingsProperties() {
		return _layoutSet.getSettingsProperties();
	}

	public String getSettingsProperty(String key) {
		return _layoutSet.getSettingsProperty(key);
	}

	public Theme getTheme() {
		return _layoutSet.getTheme();
	}

	public String getThemeSetting(String key, String device) {
		return _layoutSet.getThemeSetting(key, device);
	}

	public String getVirtualHostname() {
		return _layoutSet.getVirtualHostname();
	}

	public boolean hasSetModifiedDate() {
		return _layoutSet.hasSetModifiedDate();
	}

	public boolean isLayoutSetPrototypeLinkActive() {
		return _layoutSet.isLayoutSetPrototypeLinkActive();
	}

	public boolean isLogo() {
		return _layoutSet.isLogo();
	}

	public void setCompanyFallbackVirtualHostname(
		String companyFallbackVirtualHostname) {

		_layoutSet.setCompanyFallbackVirtualHostname(
			companyFallbackVirtualHostname);
	}

	public void setSettingsProperties(
		UnicodeProperties settingsProperties) {

		_layoutSet.setSettingsProperties(settingsProperties);
	}

	public void setVirtualHostname(String virtualHostname) {
		_layoutSet.setVirtualHostname(virtualHostname);
	}

	public long getPrimaryKey() {
		return _layoutSet.getPrimaryKey();
	}

	public void setPrimaryKey(long primaryKey) {
		_layoutSet.setPrimaryKey(primaryKey);
	}

	public long getMvccVersion() {
		return _layoutSet.getMvccVersion();
	}

	public void setMvccVersion(long mvccVersion) {
		_layoutSet.setMvccVersion(mvccVersion);
	}

	public long getLayoutSetId() {
		return _layoutSet.getLayoutSetId();
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSet.setLayoutSetId(layoutSetId);
	}

	public long getGroupId() {
		return _layoutSet.getGroupId();
	}

	public void setGroupId(long groupId) {
		_layoutSet.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _layoutSet.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_layoutSet.setCompanyId(companyId);
	}

	public Date getCreateDate() {
		return _layoutSet.getCreateDate();
	}

	public void setCreateDate(Date createDate) {
		_layoutSet.setCreateDate(createDate);
	}

	public Date getModifiedDate() {
		return _layoutSet.getModifiedDate();
	}

	public void setModifiedDate(Date modifiedDate) {
		_layoutSet.setModifiedDate(modifiedDate);
	}

	public boolean getPrivateLayout() {
		return _layoutSet.getPrivateLayout();
	}

	public boolean isPrivateLayout() {
		return _layoutSet.isPrivateLayout();
	}

	public void setPrivateLayout(boolean privateLayout) {
		_layoutSet.setPrivateLayout(privateLayout);
	}

	public long getLogoId() {
		return _layoutSet.getLogoId();
	}

	public void setLogoId(long logoId) {
		_layoutSet.setLogoId(logoId);
	}

	public String getThemeId() {
		return _layoutSet.getThemeId();
	}

	public void setThemeId(String themeId) {
		_layoutSet.setThemeId(themeId);
	}

	public String getColorSchemeId() {
		return _layoutSet.getColorSchemeId();
	}

	public void setColorSchemeId(String colorSchemeId) {
		_layoutSet.setColorSchemeId(colorSchemeId);
	}

	public String getCss() {
		return _layoutSet.getCss();
	}

	public void setCss(String css) {
		_layoutSet.setCss(css);
	}

	public int getPageCount() {
		return _layoutSet.getPageCount();
	}

	public void setPageCount(int pageCount) {
		_layoutSet.setPageCount(pageCount);
	}

	public String getSettings() {
		return _layoutSet.getSettings();
	}

	public void setSettings(String settings) {
		_layoutSet.setSettings(settings);
	}

	public String getLayoutSetPrototypeUuid() {
		return _layoutSet.getLayoutSetPrototypeUuid();
	}

	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSet.getLayoutSetPrototypeLinkEnabled();
	}

	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSet.isLayoutSetPrototypeLinkEnabled();
	}

	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {

		_layoutSet.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);
	}

	public boolean isNew() {
		return _layoutSet.isNew();
	}

	public void resetOriginalValues() {
		_layoutSet.resetOriginalValues();
	}

	public void setNew(boolean n) {
		_layoutSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSet.isCachedModel();
	}

	public boolean isEntityCacheEnabled() {
		return _layoutSet.isEntityCacheEnabled();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSet.isEscapedModel();
	}

	public boolean isFinderCacheEnabled() {
		return _layoutSet.isFinderCacheEnabled();
	}

	public Serializable getPrimaryKeyObj() {
		return _layoutSet.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutSet.setPrimaryKeyObj(primaryKeyObj);
	}

	public ExpandoBridge getExpandoBridge() {
		return _layoutSet.getExpandoBridge();
	}

	public Class<?> getModelClass() {
		return _layoutSet.getModelClass();
	}

	public String getModelClassName() {
		return _layoutSet.getModelClassName();
	}

	public Map<String, Object> getModelAttributes() {
		return _layoutSet.getModelAttributes();
	}

	public void setExpandoBridgeAttributes(
		BaseModel<?> baseModel) {

		_layoutSet.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		ExpandoBridge expandoBridge) {

		_layoutSet.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		ServiceContext serviceContext) {

		_layoutSet.setExpandoBridgeAttributes(serviceContext);
	}

	public Object clone() {
		return _layoutSet.clone();
	}

	public void setModelAttributes(
		Map<String, Object> attributes) {

		_layoutSet.setModelAttributes(attributes);
	}

	public int compareTo(LayoutSet layoutSet) {
		return _layoutSet.compareTo(layoutSet);
	}

	public CacheModel<LayoutSet> toCacheModel() {
		return _layoutSet.toCacheModel();
	}

	public LayoutSet toEscapedModel() {
		return _layoutSet.toEscapedModel();
	}

	public LayoutSet toUnescapedModel() {
		return _layoutSet.toUnescapedModel();
	}

	public String toXmlString() {
		return _layoutSet.toXmlString();
	}

	public void persist() {
		_layoutSet.persist();
	}

	private LayoutSet _layoutSet;

}