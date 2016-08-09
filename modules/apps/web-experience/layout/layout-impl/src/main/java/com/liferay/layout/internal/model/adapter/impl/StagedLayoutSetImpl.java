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

package com.liferay.layout.internal.model.adapter.impl;

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
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class StagedLayoutSetImpl implements StagedLayoutSet {

	public StagedModelType getStagedModelType() {
		return null;
	}

	public String getUuid() {
		return null;
	}

	public void setUuid(String uuid) {

	}

	public ColorScheme getColorScheme() {
		return null;
	}

	public String getCompanyFallbackVirtualHostname() {
		return null;
	}

	public Group getGroup() throws PortalException {
		return null;
	}

	public long getLayoutSetPrototypeId() throws PortalException {
		return 0;
	}

	public long getLiveLogoId() {
		return 0;
	}

	public boolean getLogo() {
		return false;
	}

	public UnicodeProperties getSettingsProperties() {
		return null;
	}

	public String getSettingsProperty(String key) {
		return null;
	}

	public Theme getTheme() {
		return null;
	}

	public String getThemeSetting(String key, String device) {
		return null;
	}

	public String getVirtualHostname() {
		return null;
	}

	public boolean hasSetModifiedDate() {
		return false;
	}

	public boolean isLayoutSetPrototypeLinkActive() {
		return false;
	}

	public boolean isLogo() {
		return false;
	}

	public void setCompanyFallbackVirtualHostname(
		String companyFallbackVirtualHostname) {

	}

	public void setSettingsProperties(
		UnicodeProperties settingsProperties) {

	}

	public void setVirtualHostname(String virtualHostname) {

	}

	public long getPrimaryKey() {
		return 0;
	}

	public void setPrimaryKey(long primaryKey) {

	}

	public long getMvccVersion() {
		return 0;
	}

	public void setMvccVersion(long mvccVersion) {

	}

	public long getLayoutSetId() {
		return 0;
	}

	public void setLayoutSetId(long layoutSetId) {

	}

	public long getGroupId() {
		return 0;
	}

	public void setGroupId(long groupId) {

	}

	public long getCompanyId() {
		return 0;
	}

	public void setCompanyId(long companyId) {

	}

	public Date getCreateDate() {
		return null;
	}

	public void setCreateDate(Date createDate) {

	}

	public Date getModifiedDate() {
		return null;
	}

	public void setModifiedDate(Date modifiedDate) {

	}

	public boolean getPrivateLayout() {
		return false;
	}

	public boolean isPrivateLayout() {
		return false;
	}

	public void setPrivateLayout(boolean privateLayout) {

	}

	public long getLogoId() {
		return 0;
	}

	public void setLogoId(long logoId) {

	}

	public String getThemeId() {
		return null;
	}

	public void setThemeId(String themeId) {

	}

	public String getColorSchemeId() {
		return null;
	}

	public void setColorSchemeId(String colorSchemeId) {

	}

	public String getCss() {
		return null;
	}

	public void setCss(String css) {

	}

	public int getPageCount() {
		return 0;
	}

	public void setPageCount(int pageCount) {

	}

	public String getSettings() {
		return null;
	}

	public void setSettings(String settings) {

	}

	public String getLayoutSetPrototypeUuid() {
		return null;
	}

	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {

	}

	public boolean getLayoutSetPrototypeLinkEnabled() {
		return false;
	}

	public boolean isLayoutSetPrototypeLinkEnabled() {
		return false;
	}

	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {

	}

	public boolean isNew() {
		return false;
	}

	public void resetOriginalValues() {

	}

	public void setNew(boolean n) {

	}

	public boolean isCachedModel() {
		return false;
	}

	public boolean isEntityCacheEnabled() {
		return false;
	}

	public void setCachedModel(boolean cachedModel) {

	}

	public boolean isEscapedModel() {
		return false;
	}

	public boolean isFinderCacheEnabled() {
		return false;
	}

	public Serializable getPrimaryKeyObj() {
		return null;
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {

	}

	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	public Class<?> getModelClass() {
		return null;
	}

	public String getModelClassName() {
		return null;
	}

	public Map<String, Object> getModelAttributes() {
		return null;
	}

	public void setExpandoBridgeAttributes(
		BaseModel<?> baseModel) {

	}

	public void setExpandoBridgeAttributes(
		ExpandoBridge expandoBridge) {

	}

	public void setExpandoBridgeAttributes(
		ServiceContext serviceContext) {

	}

	public Object clone() {
		return null;
	}

	public void setModelAttributes(
		Map<String, Object> attributes) {

	}

	public int compareTo(LayoutSet layoutSet) {
		return 0;
	}

	public CacheModel<LayoutSet> toCacheModel() {
		return null;
	}

	public LayoutSet toEscapedModel() {
		return null;
	}

	public LayoutSet toUnescapedModel() {
		return null;
	}

	public String toXmlString() {
		return null;
	}

	public void persist() {

	}

}