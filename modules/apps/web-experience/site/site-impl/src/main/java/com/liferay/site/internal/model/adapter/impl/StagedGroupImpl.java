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

package com.liferay.site.internal.model.adapter.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.model.adapter.StagedGroup;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class StagedGroupImpl implements StagedGroup {

	public StagedGroupImpl() {
	}

	public StagedGroupImpl(Group group) {
		_group = group;
	}

	public String buildTreePath() throws PortalException {
		return _group.buildTreePath();
	}

	public void clearStagingGroup() {
		_group.clearStagingGroup();
	}

	@Override
	public Object clone() {
		return new StagedGroupImpl(_group);
	}

	public int compareTo(Group group) {
		return _group.compareTo(group);
	}

	public boolean getActive() {
		return _group.getActive();
	}

	public List<Group> getAncestors() throws PortalException {
		return _group.getAncestors();
	}

	public String[] getAvailableLanguageIds() {
		return _group.getAvailableLanguageIds();
	}

	public List<Group> getChildren(boolean site) {
		return _group.getChildren(site);
	}

	@Deprecated
	public List<Group> getChildrenWithLayouts(
		boolean site, int start, int end) {

		return _group.getChildrenWithLayouts(site, start, end);
	}

	public List<Group> getChildrenWithLayouts(
		boolean site, int start, int end, OrderByComparator<Group> obc) {

		return _group.getChildrenWithLayouts(site, start, end, obc);
	}

	public int getChildrenWithLayoutsCount(boolean site) {
		return _group.getChildrenWithLayoutsCount(site);
	}

	public String getClassName() {
		return _group.getClassName();
	}

	public long getClassNameId() {
		return _group.getClassNameId();
	}

	public long getClassPK() {
		return _group.getClassPK();
	}

	public long getCompanyId() {
		return _group.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	public long getCreatorUserId() {
		return _group.getCreatorUserId();
	}

	public String getCreatorUserUuid() {
		return _group.getCreatorUserUuid();
	}

	public String getDefaultLanguageId() {
		return _group.getDefaultLanguageId();
	}

	public long getDefaultPrivatePlid() {
		return _group.getDefaultPrivatePlid();
	}

	public long getDefaultPublicPlid() {
		return _group.getDefaultPublicPlid();
	}

	public List<Group> getDescendants(boolean site) {
		return _group.getDescendants(site);
	}

	public String getDescription() {
		return _group.getDescription();
	}

	public String getDescription(Locale locale) {
		return _group.getDescription(locale);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		return _group.getDescription(locale, useDefault);
	}

	public String getDescription(String languageId) {
		return _group.getDescription(languageId);
	}

	public String getDescription(String languageId, boolean useDefault) {
		return _group.getDescription(languageId, useDefault);
	}

	public String getDescriptionCurrentLanguageId() {
		return _group.getDescriptionCurrentLanguageId();
	}

	public String getDescriptionCurrentValue() {
		return _group.getDescriptionCurrentValue();
	}

	public Map<Locale, String> getDescriptionMap() {
		return _group.getDescriptionMap();
	}

	public String getDescriptiveName() throws PortalException {
		return _group.getDescriptiveName();
	}

	public String getDescriptiveName(Locale locale) throws PortalException {
		return _group.getDescriptiveName(locale);
	}

	public String getDisplayURL(ThemeDisplay themeDisplay) {
		return _group.getDisplayURL(themeDisplay);
	}

	public String getDisplayURL(
		ThemeDisplay themeDisplay, boolean privateLayout) {

		return _group.getDisplayURL(themeDisplay, privateLayout);
	}

	public ExpandoBridge getExpandoBridge() {
		return _group.getExpandoBridge();
	}

	public String getFriendlyURL() {
		return _group.getFriendlyURL();
	}

	public long getGroupId() {
		return _group.getGroupId();
	}

	public String getGroupKey() {
		return _group.getGroupKey();
	}

	public String getIconCssClass() {
		return _group.getIconCssClass();
	}

	public String getIconURL(ThemeDisplay themeDisplay) {
		return _group.getIconURL(themeDisplay);
	}

	public boolean getInheritContent() {
		return _group.getInheritContent();
	}

	public String getLayoutRootNodeName(boolean privateLayout, Locale locale) {
		return _group.getLayoutRootNodeName(privateLayout, locale);
	}

	public Group getLiveGroup() {
		return _group.getLiveGroup();
	}

	public long getLiveGroupId() {
		return _group.getLiveGroupId();
	}

	public String getLiveParentTypeSettingsProperty(String key) {
		return _group.getLiveParentTypeSettingsProperty(key);
	}

	public String getLogoURL(ThemeDisplay themeDisplay, boolean useDefault) {
		return _group.getLogoURL(themeDisplay, useDefault);
	}

	public boolean getManualMembership() {
		return _group.getManualMembership();
	}

	public int getMembershipRestriction() {
		return _group.getMembershipRestriction();
	}

	public Map<String, Object> getModelAttributes() {
		return _group.getModelAttributes();
	}

	@Override
	public Class<?> getModelClass() {
		return StagedGroup.class;
	}

	@Override
	public String getModelClassName() {
		return StagedGroup.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	public long getMvccVersion() {
		return _group.getMvccVersion();
	}

	public String getName() {
		return _group.getName();
	}

	public String getName(Locale locale) {
		return _group.getName(locale);
	}

	public String getName(Locale locale, boolean useDefault) {
		return _group.getName(locale, useDefault);
	}

	public String getName(String languageId) {
		return _group.getName(languageId);
	}

	public String getName(String languageId, boolean useDefault) {
		return _group.getName(languageId, useDefault);
	}

	public String getNameCurrentLanguageId() {
		return _group.getNameCurrentLanguageId();
	}

	public String getNameCurrentValue() {
		return _group.getNameCurrentValue();
	}

	public Map<Locale, String> getNameMap() {
		return _group.getNameMap();
	}

	public long getOrganizationId() {
		return _group.getOrganizationId();
	}

	public Group getParentGroup() throws PortalException {
		return _group.getParentGroup();
	}

	public long getParentGroupId() {
		return _group.getParentGroupId();
	}

	public UnicodeProperties getParentLiveGroupTypeSettingsProperties() {
		return _group.getParentLiveGroupTypeSettingsProperties();
	}

	public String getPathFriendlyURL(
		boolean privateLayout, ThemeDisplay themeDisplay) {

		return _group.getPathFriendlyURL(privateLayout, themeDisplay);
	}

	public long getPrimaryKey() {
		return _group.getPrimaryKey();
	}

	public Serializable getPrimaryKeyObj() {
		return _group.getPrimaryKeyObj();
	}

	public LayoutSet getPrivateLayoutSet() {
		return _group.getPrivateLayoutSet();
	}

	public int getPrivateLayoutsPageCount() {
		return _group.getPrivateLayoutsPageCount();
	}

	public LayoutSet getPublicLayoutSet() {
		return _group.getPublicLayoutSet();
	}

	public int getPublicLayoutsPageCount() {
		return _group.getPublicLayoutsPageCount();
	}

	public long getRemoteLiveGroupId() {
		return _group.getRemoteLiveGroupId();
	}

	public int getRemoteStagingGroupCount() {
		return _group.getRemoteStagingGroupCount();
	}

	public String getScopeDescriptiveName(ThemeDisplay themeDisplay)
		throws PortalException {

		return _group.getScopeDescriptiveName(themeDisplay);
	}

	public String getScopeLabel(ThemeDisplay themeDisplay) {
		return _group.getScopeLabel(themeDisplay);
	}

	public boolean getSite() {
		return _group.getSite();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedGroup.class);
	}

	public Group getStagingGroup() {
		return _group.getStagingGroup();
	}

	public String getTreePath() {
		return _group.getTreePath();
	}

	public int getType() {
		return _group.getType();
	}

	public String getTypeLabel() {
		return _group.getTypeLabel();
	}

	public String getTypeSettings() {
		return _group.getTypeSettings();
	}

	public UnicodeProperties getTypeSettingsProperties() {
		return _group.getTypeSettingsProperties();
	}

	public String getTypeSettingsProperty(String key) {
		return _group.getTypeSettingsProperty(key);
	}

	public String getUnambiguousName(String name, Locale locale) {
		return _group.getUnambiguousName(name, locale);
	}

	public String getUuid() {
		return _group.getUuid();
	}

	public boolean hasAncestor(long groupId) {
		return _group.hasAncestor(groupId);
	}

	public boolean hasLocalOrRemoteStagingGroup() {
		return _group.hasLocalOrRemoteStagingGroup();
	}

	public boolean hasPrivateLayouts() {
		return _group.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts() {
		return _group.hasPublicLayouts();
	}

	public boolean hasRemoteStagingGroup() {
		return _group.hasRemoteStagingGroup();
	}

	public boolean hasStagingGroup() {
		return _group.hasStagingGroup();
	}

	public boolean isActive() {
		return _group.isActive();
	}

	public boolean isCachedModel() {
		return _group.isCachedModel();
	}

	@Deprecated
	public boolean isChild(long groupId) {
		return _group.isChild(groupId);
	}

	public boolean isCompany() {
		return _group.isCompany();
	}

	public boolean isCompanyStagingGroup() {
		return _group.isCompanyStagingGroup();
	}

	public boolean isControlPanel() {
		return _group.isControlPanel();
	}

	public boolean isEntityCacheEnabled() {
		return _group.isEntityCacheEnabled();
	}

	public boolean isEscapedModel() {
		return _group.isEscapedModel();
	}

	public boolean isFinderCacheEnabled() {
		return _group.isFinderCacheEnabled();
	}

	public boolean isGuest() {
		return _group.isGuest();
	}

	public boolean isInheritContent() {
		return _group.isInheritContent();
	}

	public boolean isInStagingPortlet(String portletId) {
		return _group.isInStagingPortlet(portletId);
	}

	public boolean isLayout() {
		return _group.isLayout();
	}

	public boolean isLayoutPrototype() {
		return _group.isLayoutPrototype();
	}

	public boolean isLayoutSetPrototype() {
		return _group.isLayoutSetPrototype();
	}

	public boolean isLimitedToParentSiteMembers() {
		return _group.isLimitedToParentSiteMembers();
	}

	public boolean isManualMembership() {
		return _group.isManualMembership();
	}

	public boolean isNew() {
		return _group.isNew();
	}

	public boolean isOrganization() {
		return _group.isOrganization();
	}

	public boolean isRegularSite() {
		return _group.isRegularSite();
	}

	public boolean isRoot() {
		return _group.isRoot();
	}

	public boolean isShowSite(
			PermissionChecker permissionChecker, boolean privateSite)
		throws PortalException {

		return _group.isShowSite(permissionChecker, privateSite);
	}

	public boolean isSite() {
		return _group.isSite();
	}

	public boolean isStaged() {
		return _group.isStaged();
	}

	public boolean isStagedPortlet(String portletId) {
		return _group.isStagedPortlet(portletId);
	}

	public boolean isStagedRemotely() {
		return _group.isStagedRemotely();
	}

	public boolean isStagingGroup() {
		return _group.isStagingGroup();
	}

	public boolean isUser() {
		return _group.isUser();
	}

	public boolean isUserGroup() {
		return _group.isUserGroup();
	}

	public boolean isUserPersonalSite() {
		return _group.isUserPersonalSite();
	}

	public void persist() {
		_group.persist();
	}

	public void prepareLocalizedFieldsForImport() throws LocaleException {
		_group.prepareLocalizedFieldsForImport();
	}

	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		_group.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public void resetOriginalValues() {
		_group.resetOriginalValues();
	}

	public void setActive(boolean active) {
		_group.setActive(active);
	}

	public void setCachedModel(boolean cachedModel) {
		_group.setCachedModel(cachedModel);
	}

	public void setClassName(String className) {
		_group.setClassName(className);
	}

	public void setClassNameId(long classNameId) {
		_group.setClassNameId(classNameId);
	}

	public void setClassPK(long classPK) {
		_group.setClassPK(classPK);
	}

	@Override
	public void setCompanyId(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCreateDate(Date createDate) {
		throw new UnsupportedOperationException();
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setCreatorUserId(long creatorUserId) {
		_group.setCreatorUserId(creatorUserId);
	}

	public void setCreatorUserUuid(String creatorUserUuid) {
		_group.setCreatorUserUuid(creatorUserUuid);
	}

	public void setDescription(String description) {
		_group.setDescription(description);
	}

	public void setDescription(String description, Locale locale) {
		_group.setDescription(description, locale);
	}

	public void setDescription(
		String description, Locale locale, Locale defaultLocale) {

		_group.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(String languageId) {
		_group.setDescriptionCurrentLanguageId(languageId);
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		_group.setDescriptionMap(descriptionMap);
	}

	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale) {

		_group.setDescriptionMap(descriptionMap, defaultLocale);
	}

	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_group.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_group.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_group.setExpandoBridgeAttributes(serviceContext);
	}

	public void setFriendlyURL(String friendlyURL) {
		_group.setFriendlyURL(friendlyURL);
	}

	public void setGroupId(long groupId) {
		_group.setGroupId(groupId);
	}

	public void setGroupKey(String groupKey) {
		_group.setGroupKey(groupKey);
	}

	public void setInheritContent(boolean inheritContent) {
		_group.setInheritContent(inheritContent);
	}

	public void setLiveGroupId(long liveGroupId) {
		_group.setLiveGroupId(liveGroupId);
	}

	public void setManualMembership(boolean manualMembership) {
		_group.setManualMembership(manualMembership);
	}

	public void setMembershipRestriction(int membershipRestriction) {
		_group.setMembershipRestriction(membershipRestriction);
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		_group.setModelAttributes(attributes);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		throw new UnsupportedOperationException();
	}

	public void setMvccVersion(long mvccVersion) {
		_group.setMvccVersion(mvccVersion);
	}

	public void setName(String name) {
		_group.setName(name);
	}

	public void setName(String name, Locale locale) {
		_group.setName(name, locale);
	}

	public void setName(String name, Locale locale, Locale defaultLocale) {
		_group.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(String languageId) {
		_group.setNameCurrentLanguageId(languageId);
	}

	public void setNameMap(Map<Locale, String> nameMap) {
		_group.setNameMap(nameMap);
	}

	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		_group.setNameMap(nameMap, defaultLocale);
	}

	public void setNew(boolean n) {
		_group.setNew(n);
	}

	public void setParentGroupId(long parentGroupId) {
		_group.setParentGroupId(parentGroupId);
	}

	public void setPrimaryKey(long primaryKey) {
		_group.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		throw new UnsupportedOperationException();
	}

	public void setRemoteStagingGroupCount(int remoteStagingGroupCount) {
		_group.setRemoteStagingGroupCount(remoteStagingGroupCount);
	}

	public void setSite(boolean site) {
		_group.setSite(site);
	}

	public void setTreePath(String treePath) {
		_group.setTreePath(treePath);
	}

	public void setType(int type) {
		_group.setType(type);
	}

	public void setTypeSettings(String typeSettings) {
		_group.setTypeSettings(typeSettings);
	}

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_group.setTypeSettingsProperties(typeSettingsProperties);
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	public CacheModel<Group> toCacheModel() {
		return _group.toCacheModel();
	}

	public Group toEscapedModel() {
		return _group.toEscapedModel();
	}

	public Group toUnescapedModel() {
		return _group.toUnescapedModel();
	}

	public String toXmlString() {
		return _group.toXmlString();
	}

	public void updateTreePath(String treePath) {
		_group.updateTreePath(treePath);
	}

	private final Group _group;

}