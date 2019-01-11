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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.model.LayoutSetVersionModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.model.version.VersionedModelInvocationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the LayoutSetVersion service. Represents a row in the &quot;LayoutSetVersion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link LayoutSetVersionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutSetVersionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersionImpl
 * @see LayoutSetVersion
 * @see LayoutSetVersionModel
 * @generated
 */
@ProviderType
public class LayoutSetVersionModelImpl extends BaseModelImpl<LayoutSetVersion>
	implements LayoutSetVersionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout set version model instance should use the {@link LayoutSetVersion} interface instead.
	 */
	public static final String TABLE_NAME = "LayoutSetVersion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "layoutSetVersionId", Types.BIGINT },
			{ "version", Types.INTEGER },
			{ "layoutSetId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "privateLayout", Types.BOOLEAN },
			{ "logoId", Types.BIGINT },
			{ "themeId", Types.VARCHAR },
			{ "colorSchemeId", Types.VARCHAR },
			{ "css", Types.CLOB },
			{ "pageCount", Types.INTEGER },
			{ "settings_", Types.CLOB },
			{ "layoutSetPrototypeUuid", Types.VARCHAR },
			{ "layoutSetPrototypeLinkEnabled", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("layoutSetVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("version", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("layoutSetId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("themeId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("colorSchemeId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("css", Types.CLOB);
		TABLE_COLUMNS_MAP.put("pageCount", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("settings_", Types.CLOB);
		TABLE_COLUMNS_MAP.put("layoutSetPrototypeUuid", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("layoutSetPrototypeLinkEnabled", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table LayoutSetVersion (layoutSetVersionId LONG not null primary key,version INTEGER,layoutSetId LONG,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,css TEXT null,pageCount INTEGER,settings_ TEXT null,layoutSetPrototypeUuid VARCHAR(75) null,layoutSetPrototypeLinkEnabled BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table LayoutSetVersion";
	public static final String ORDER_BY_JPQL = " ORDER BY layoutSetVersion.version DESC";
	public static final String ORDER_BY_SQL = " ORDER BY LayoutSetVersion.version DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.LayoutSetVersion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.LayoutSetVersion"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.LayoutSetVersion"),
			true);
	public static final long GROUPID_COLUMN_BITMASK = 1L;
	public static final long LAYOUTSETID_COLUMN_BITMASK = 2L;
	public static final long LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK = 4L;
	public static final long LOGOID_COLUMN_BITMASK = 8L;
	public static final long PRIVATELAYOUT_COLUMN_BITMASK = 16L;
	public static final long VERSION_COLUMN_BITMASK = 32L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.kernel.model.LayoutSetVersion"));

	public LayoutSetVersionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _layoutSetVersionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLayoutSetVersionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutSetVersionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutSetVersion.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutSetVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutSetVersionId", getLayoutSetVersionId());
		attributes.put("version", getVersion());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("privateLayout", isPrivateLayout());
		attributes.put("logoId", getLogoId());
		attributes.put("themeId", getThemeId());
		attributes.put("colorSchemeId", getColorSchemeId());
		attributes.put("css", getCss());
		attributes.put("pageCount", getPageCount());
		attributes.put("settings", getSettings());
		attributes.put("layoutSetPrototypeUuid", getLayoutSetPrototypeUuid());
		attributes.put("layoutSetPrototypeLinkEnabled",
			isLayoutSetPrototypeLinkEnabled());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutSetVersionId = (Long)attributes.get("layoutSetVersionId");

		if (layoutSetVersionId != null) {
			setLayoutSetVersionId(layoutSetVersionId);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Long layoutSetId = (Long)attributes.get("layoutSetId");

		if (layoutSetId != null) {
			setLayoutSetId(layoutSetId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		String themeId = (String)attributes.get("themeId");

		if (themeId != null) {
			setThemeId(themeId);
		}

		String colorSchemeId = (String)attributes.get("colorSchemeId");

		if (colorSchemeId != null) {
			setColorSchemeId(colorSchemeId);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		Integer pageCount = (Integer)attributes.get("pageCount");

		if (pageCount != null) {
			setPageCount(pageCount);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		String layoutSetPrototypeUuid = (String)attributes.get(
				"layoutSetPrototypeUuid");

		if (layoutSetPrototypeUuid != null) {
			setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		Boolean layoutSetPrototypeLinkEnabled = (Boolean)attributes.get(
				"layoutSetPrototypeLinkEnabled");

		if (layoutSetPrototypeLinkEnabled != null) {
			setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
		}
	}

	@Override
	public long getVersionedModelId() {
		return getLayoutSetId();
	}

	@Override
	public void populateVersionedModel(LayoutSet layoutSet) {
		layoutSet.setGroupId(getGroupId());
		layoutSet.setCompanyId(getCompanyId());
		layoutSet.setCreateDate(getCreateDate());
		layoutSet.setModifiedDate(getModifiedDate());
		layoutSet.setPrivateLayout(getPrivateLayout());
		layoutSet.setLogoId(getLogoId());
		layoutSet.setThemeId(getThemeId());
		layoutSet.setColorSchemeId(getColorSchemeId());
		layoutSet.setCss(getCss());
		layoutSet.setPageCount(getPageCount());
		layoutSet.setSettings(getSettings());
		layoutSet.setLayoutSetPrototypeUuid(getLayoutSetPrototypeUuid());
		layoutSet.setLayoutSetPrototypeLinkEnabled(getLayoutSetPrototypeLinkEnabled());
	}

	@Override
	public void setVersionedModelId(long layoutSetId) {
		setLayoutSetId(layoutSetId);
	}

	@Override
	public LayoutSet toVersionedModel() {
		if (_layoutSet == null) {
			_layoutSet = (LayoutSet)ProxyUtil.newProxyInstance(_classLoader,
					_versionedModelInterfaces,
					new VersionedModelInvocationHandler(this,
						_versionedModelMethodsMap));
		}

		return _layoutSet;
	}

	@Override
	public long getLayoutSetVersionId() {
		return _layoutSetVersionId;
	}

	@Override
	public void setLayoutSetVersionId(long layoutSetVersionId) {
		_layoutSetVersionId = layoutSetVersionId;
	}

	@Override
	public int getVersion() {
		return _version;
	}

	@Override
	public void setVersion(int version) {
		_columnBitmask = -1L;

		if (!_setOriginalVersion) {
			_setOriginalVersion = true;

			_originalVersion = _version;
		}

		_version = version;
	}

	public int getOriginalVersion() {
		return _originalVersion;
	}

	@Override
	public long getLayoutSetId() {
		return _layoutSetId;
	}

	@Override
	public void setLayoutSetId(long layoutSetId) {
		_columnBitmask |= LAYOUTSETID_COLUMN_BITMASK;

		if (!_setOriginalLayoutSetId) {
			_setOriginalLayoutSetId = true;

			_originalLayoutSetId = _layoutSetId;
		}

		_layoutSetId = layoutSetId;
	}

	public long getOriginalLayoutSetId() {
		return _originalLayoutSetId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	@Override
	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_columnBitmask |= PRIVATELAYOUT_COLUMN_BITMASK;

		if (!_setOriginalPrivateLayout) {
			_setOriginalPrivateLayout = true;

			_originalPrivateLayout = _privateLayout;
		}

		_privateLayout = privateLayout;
	}

	public boolean getOriginalPrivateLayout() {
		return _originalPrivateLayout;
	}

	@Override
	public long getLogoId() {
		return _logoId;
	}

	@Override
	public void setLogoId(long logoId) {
		_columnBitmask |= LOGOID_COLUMN_BITMASK;

		if (!_setOriginalLogoId) {
			_setOriginalLogoId = true;

			_originalLogoId = _logoId;
		}

		_logoId = logoId;
	}

	public long getOriginalLogoId() {
		return _originalLogoId;
	}

	@Override
	public String getThemeId() {
		if (_themeId == null) {
			return "";
		}
		else {
			return _themeId;
		}
	}

	@Override
	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	@Override
	public String getColorSchemeId() {
		if (_colorSchemeId == null) {
			return "";
		}
		else {
			return _colorSchemeId;
		}
	}

	@Override
	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	@Override
	public String getCss() {
		if (_css == null) {
			return "";
		}
		else {
			return _css;
		}
	}

	@Override
	public void setCss(String css) {
		_css = css;
	}

	@Override
	public int getPageCount() {
		return _pageCount;
	}

	@Override
	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
	}

	@Override
	public String getSettings() {
		if (_settings == null) {
			return "";
		}
		else {
			return _settings;
		}
	}

	@Override
	public void setSettings(String settings) {
		_settings = settings;
	}

	@Override
	public String getLayoutSetPrototypeUuid() {
		if (_layoutSetPrototypeUuid == null) {
			return "";
		}
		else {
			return _layoutSetPrototypeUuid;
		}
	}

	@Override
	public void setLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		_columnBitmask |= LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK;

		if (_originalLayoutSetPrototypeUuid == null) {
			_originalLayoutSetPrototypeUuid = _layoutSetPrototypeUuid;
		}

		_layoutSetPrototypeUuid = layoutSetPrototypeUuid;
	}

	public String getOriginalLayoutSetPrototypeUuid() {
		return GetterUtil.getString(_originalLayoutSetPrototypeUuid);
	}

	@Override
	public boolean getLayoutSetPrototypeLinkEnabled() {
		return _layoutSetPrototypeLinkEnabled;
	}

	@Override
	public boolean isLayoutSetPrototypeLinkEnabled() {
		return _layoutSetPrototypeLinkEnabled;
	}

	@Override
	public void setLayoutSetPrototypeLinkEnabled(
		boolean layoutSetPrototypeLinkEnabled) {
		_layoutSetPrototypeLinkEnabled = layoutSetPrototypeLinkEnabled;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			LayoutSetVersion.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LayoutSetVersion toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (LayoutSetVersion)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		LayoutSetVersionImpl layoutSetVersionImpl = new LayoutSetVersionImpl();

		layoutSetVersionImpl.setLayoutSetVersionId(getLayoutSetVersionId());
		layoutSetVersionImpl.setVersion(getVersion());
		layoutSetVersionImpl.setLayoutSetId(getLayoutSetId());
		layoutSetVersionImpl.setGroupId(getGroupId());
		layoutSetVersionImpl.setCompanyId(getCompanyId());
		layoutSetVersionImpl.setCreateDate(getCreateDate());
		layoutSetVersionImpl.setModifiedDate(getModifiedDate());
		layoutSetVersionImpl.setPrivateLayout(isPrivateLayout());
		layoutSetVersionImpl.setLogoId(getLogoId());
		layoutSetVersionImpl.setThemeId(getThemeId());
		layoutSetVersionImpl.setColorSchemeId(getColorSchemeId());
		layoutSetVersionImpl.setCss(getCss());
		layoutSetVersionImpl.setPageCount(getPageCount());
		layoutSetVersionImpl.setSettings(getSettings());
		layoutSetVersionImpl.setLayoutSetPrototypeUuid(getLayoutSetPrototypeUuid());
		layoutSetVersionImpl.setLayoutSetPrototypeLinkEnabled(isLayoutSetPrototypeLinkEnabled());

		layoutSetVersionImpl.resetOriginalValues();

		return layoutSetVersionImpl;
	}

	@Override
	public int compareTo(LayoutSetVersion layoutSetVersion) {
		int value = 0;

		if (getVersion() < layoutSetVersion.getVersion()) {
			value = -1;
		}
		else if (getVersion() > layoutSetVersion.getVersion()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetVersion)) {
			return false;
		}

		LayoutSetVersion layoutSetVersion = (LayoutSetVersion)obj;

		long primaryKey = layoutSetVersion.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		LayoutSetVersionModelImpl layoutSetVersionModelImpl = this;

		layoutSetVersionModelImpl._originalVersion = layoutSetVersionModelImpl._version;

		layoutSetVersionModelImpl._setOriginalVersion = false;

		layoutSetVersionModelImpl._originalLayoutSetId = layoutSetVersionModelImpl._layoutSetId;

		layoutSetVersionModelImpl._setOriginalLayoutSetId = false;

		layoutSetVersionModelImpl._originalGroupId = layoutSetVersionModelImpl._groupId;

		layoutSetVersionModelImpl._setOriginalGroupId = false;

		layoutSetVersionModelImpl._setModifiedDate = false;

		layoutSetVersionModelImpl._originalPrivateLayout = layoutSetVersionModelImpl._privateLayout;

		layoutSetVersionModelImpl._setOriginalPrivateLayout = false;

		layoutSetVersionModelImpl._originalLogoId = layoutSetVersionModelImpl._logoId;

		layoutSetVersionModelImpl._setOriginalLogoId = false;

		layoutSetVersionModelImpl._originalLayoutSetPrototypeUuid = layoutSetVersionModelImpl._layoutSetPrototypeUuid;

		layoutSetVersionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<LayoutSetVersion> toCacheModel() {
		LayoutSetVersionCacheModel layoutSetVersionCacheModel = new LayoutSetVersionCacheModel();

		layoutSetVersionCacheModel.layoutSetVersionId = getLayoutSetVersionId();

		layoutSetVersionCacheModel.version = getVersion();

		layoutSetVersionCacheModel.layoutSetId = getLayoutSetId();

		layoutSetVersionCacheModel.groupId = getGroupId();

		layoutSetVersionCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			layoutSetVersionCacheModel.createDate = createDate.getTime();
		}
		else {
			layoutSetVersionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			layoutSetVersionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			layoutSetVersionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		layoutSetVersionCacheModel.privateLayout = isPrivateLayout();

		layoutSetVersionCacheModel.logoId = getLogoId();

		layoutSetVersionCacheModel.themeId = getThemeId();

		String themeId = layoutSetVersionCacheModel.themeId;

		if ((themeId != null) && (themeId.length() == 0)) {
			layoutSetVersionCacheModel.themeId = null;
		}

		layoutSetVersionCacheModel.colorSchemeId = getColorSchemeId();

		String colorSchemeId = layoutSetVersionCacheModel.colorSchemeId;

		if ((colorSchemeId != null) && (colorSchemeId.length() == 0)) {
			layoutSetVersionCacheModel.colorSchemeId = null;
		}

		layoutSetVersionCacheModel.css = getCss();

		String css = layoutSetVersionCacheModel.css;

		if ((css != null) && (css.length() == 0)) {
			layoutSetVersionCacheModel.css = null;
		}

		layoutSetVersionCacheModel.pageCount = getPageCount();

		layoutSetVersionCacheModel.settings = getSettings();

		String settings = layoutSetVersionCacheModel.settings;

		if ((settings != null) && (settings.length() == 0)) {
			layoutSetVersionCacheModel.settings = null;
		}

		layoutSetVersionCacheModel.layoutSetPrototypeUuid = getLayoutSetPrototypeUuid();

		String layoutSetPrototypeUuid = layoutSetVersionCacheModel.layoutSetPrototypeUuid;

		if ((layoutSetPrototypeUuid != null) &&
				(layoutSetPrototypeUuid.length() == 0)) {
			layoutSetVersionCacheModel.layoutSetPrototypeUuid = null;
		}

		layoutSetVersionCacheModel.layoutSetPrototypeLinkEnabled = isLayoutSetPrototypeLinkEnabled();

		return layoutSetVersionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{layoutSetVersionId=");
		sb.append(getLayoutSetVersionId());
		sb.append(", version=");
		sb.append(getVersion());
		sb.append(", layoutSetId=");
		sb.append(getLayoutSetId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", privateLayout=");
		sb.append(isPrivateLayout());
		sb.append(", logoId=");
		sb.append(getLogoId());
		sb.append(", themeId=");
		sb.append(getThemeId());
		sb.append(", colorSchemeId=");
		sb.append(getColorSchemeId());
		sb.append(", css=");
		sb.append(getCss());
		sb.append(", pageCount=");
		sb.append(getPageCount());
		sb.append(", settings=");
		sb.append(getSettings());
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(getLayoutSetPrototypeUuid());
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(isLayoutSetPrototypeLinkEnabled());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.kernel.model.LayoutSetVersion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>layoutSetVersionId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetVersionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>version</column-name><column-value><![CDATA[");
		sb.append(getVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(isPrivateLayout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>logoId</column-name><column-value><![CDATA[");
		sb.append(getLogoId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>themeId</column-name><column-value><![CDATA[");
		sb.append(getThemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>colorSchemeId</column-name><column-value><![CDATA[");
		sb.append(getColorSchemeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>css</column-name><column-value><![CDATA[");
		sb.append(getCss());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>pageCount</column-name><column-value><![CDATA[");
		sb.append(getPageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>settings</column-name><column-value><![CDATA[");
		sb.append(getSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetPrototypeUuid</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetPrototypeUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutSetPrototypeLinkEnabled</column-name><column-value><![CDATA[");
		sb.append(isLayoutSetPrototypeLinkEnabled());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = LayoutSetVersion.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			LayoutSetVersion.class, ModelWrapper.class
		};
	private static final Map<Method, Method> _versionedModelMethodsMap = new HashMap<Method, Method>();
	private static final Class<?>[] _versionedModelInterfaces = new Class<?>[] {
			LayoutSet.class
		};

	static {
		try {
			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getPrimaryKey"),
				LayoutSetVersion.class.getMethod("getVersionedModelId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getLayoutSetId"),
				LayoutSetVersion.class.getMethod("getLayoutSetId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getGroupId"),
				LayoutSetVersion.class.getMethod("getGroupId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getCompanyId"),
				LayoutSetVersion.class.getMethod("getCompanyId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getCreateDate"),
				LayoutSetVersion.class.getMethod("getCreateDate"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getModifiedDate"),
				LayoutSetVersion.class.getMethod("getModifiedDate"));
			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"isPrivateLayout"),
				LayoutSetVersion.class.getMethod("isPrivateLayout"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getPrivateLayout"),
				LayoutSetVersion.class.getMethod("getPrivateLayout"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod("getLogoId"),
				LayoutSetVersion.class.getMethod("getLogoId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getThemeId"),
				LayoutSetVersion.class.getMethod("getThemeId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getColorSchemeId"),
				LayoutSetVersion.class.getMethod("getColorSchemeId"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod("getCss"),
				LayoutSetVersion.class.getMethod("getCss"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getPageCount"),
				LayoutSetVersion.class.getMethod("getPageCount"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getSettings"),
				LayoutSetVersion.class.getMethod("getSettings"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getLayoutSetPrototypeUuid"),
				LayoutSetVersion.class.getMethod("getLayoutSetPrototypeUuid"));
			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"isLayoutSetPrototypeLinkEnabled"),
				LayoutSetVersion.class.getMethod(
					"isLayoutSetPrototypeLinkEnabled"));

			_versionedModelMethodsMap.put(LayoutSet.class.getMethod(
					"getLayoutSetPrototypeLinkEnabled"),
				LayoutSetVersion.class.getMethod(
					"getLayoutSetPrototypeLinkEnabled"));
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

	private volatile LayoutSet _layoutSet;
	private long _layoutSetVersionId;
	private int _version;
	private int _originalVersion;
	private boolean _setOriginalVersion;
	private long _layoutSetId;
	private long _originalLayoutSetId;
	private boolean _setOriginalLayoutSetId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private long _logoId;
	private long _originalLogoId;
	private boolean _setOriginalLogoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _css;
	private int _pageCount;
	private String _settings;
	private String _layoutSetPrototypeUuid;
	private String _originalLayoutSetPrototypeUuid;
	private boolean _layoutSetPrototypeLinkEnabled;
	private long _columnBitmask;
	private LayoutSetVersion _escapedModel;
}