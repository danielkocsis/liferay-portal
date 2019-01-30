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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.UserNotificationEventModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the UserNotificationEvent service. Represents a row in the &quot;UserNotificationEvent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link UserNotificationEventModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link UserNotificationEventImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventImpl
 * @see UserNotificationEvent
 * @see UserNotificationEventModel
 * @generated
 */
@ProviderType
public class UserNotificationEventModelImpl extends BaseModelImpl<UserNotificationEvent>
	implements UserNotificationEventModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a user notification event model instance should use the {@link UserNotificationEvent} interface instead.
	 */
	public static final String TABLE_NAME = "UserNotificationEvent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "uuid_", Types.VARCHAR },
			{ "userNotificationEventId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "type_", Types.VARCHAR },
			{ "timestamp", Types.BIGINT },
			{ "deliveryType", Types.INTEGER },
			{ "deliverBy", Types.BIGINT },
			{ "delivered", Types.BOOLEAN },
			{ "payload", Types.CLOB },
			{ "actionRequired", Types.BOOLEAN },
			{ "archived", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("userNotificationEventId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("timestamp", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("deliveryType", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("deliverBy", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("delivered", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("payload", Types.CLOB);
		TABLE_COLUMNS_MAP.put("actionRequired", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("archived", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table UserNotificationEvent (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,userNotificationEventId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(200) null,timestamp LONG,deliveryType INTEGER,deliverBy LONG,delivered BOOLEAN,payload TEXT null,actionRequired BOOLEAN,archived BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table UserNotificationEvent";
	public static final String ORDER_BY_JPQL = " ORDER BY userNotificationEvent.timestamp DESC";
	public static final String ORDER_BY_SQL = " ORDER BY UserNotificationEvent.timestamp DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.UserNotificationEvent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.UserNotificationEvent"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.UserNotificationEvent"),
			true);
	public static final long ACTIONREQUIRED_COLUMN_BITMASK = 1L;
	public static final long ARCHIVED_COLUMN_BITMASK = 2L;
	public static final long COMPANYID_COLUMN_BITMASK = 4L;
	public static final long DELIVERED_COLUMN_BITMASK = 8L;
	public static final long DELIVERYTYPE_COLUMN_BITMASK = 16L;
	public static final long TYPE_COLUMN_BITMASK = 32L;
	public static final long USERID_COLUMN_BITMASK = 64L;
	public static final long UUID_COLUMN_BITMASK = 128L;
	public static final long TIMESTAMP_COLUMN_BITMASK = 256L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.kernel.model.UserNotificationEvent"));

	public UserNotificationEventModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _userNotificationEventId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setUserNotificationEventId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userNotificationEventId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return UserNotificationEvent.class;
	}

	@Override
	public String getModelClassName() {
		return UserNotificationEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("userNotificationEventId", getUserNotificationEventId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("timestamp", getTimestamp());
		attributes.put("deliveryType", getDeliveryType());
		attributes.put("deliverBy", getDeliverBy());
		attributes.put("delivered", isDelivered());
		attributes.put("payload", getPayload());
		attributes.put("actionRequired", isActionRequired());
		attributes.put("archived", isArchived());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long userNotificationEventId = (Long)attributes.get(
				"userNotificationEventId");

		if (userNotificationEventId != null) {
			setUserNotificationEventId(userNotificationEventId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long timestamp = (Long)attributes.get("timestamp");

		if (timestamp != null) {
			setTimestamp(timestamp);
		}

		Integer deliveryType = (Integer)attributes.get("deliveryType");

		if (deliveryType != null) {
			setDeliveryType(deliveryType);
		}

		Long deliverBy = (Long)attributes.get("deliverBy");

		if (deliverBy != null) {
			setDeliverBy(deliverBy);
		}

		Boolean delivered = (Boolean)attributes.get("delivered");

		if (delivered != null) {
			setDelivered(delivered);
		}

		String payload = (String)attributes.get("payload");

		if (payload != null) {
			setPayload(payload);
		}

		Boolean actionRequired = (Boolean)attributes.get("actionRequired");

		if (actionRequired != null) {
			setActionRequired(actionRequired);
		}

		Boolean archived = (Boolean)attributes.get("archived");

		if (archived != null) {
			setArchived(archived);
		}
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getUserNotificationEventId() {
		return _userNotificationEventId;
	}

	@Override
	public void setUserNotificationEventId(long userNotificationEventId) {
		_userNotificationEventId = userNotificationEventId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public String getType() {
		if (_type == null) {
			return "";
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (_originalType == null) {
			_originalType = _type;
		}

		_type = type;
	}

	public String getOriginalType() {
		return GetterUtil.getString(_originalType);
	}

	@Override
	public long getTimestamp() {
		return _timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		_columnBitmask = -1L;

		_timestamp = timestamp;
	}

	@Override
	public int getDeliveryType() {
		return _deliveryType;
	}

	@Override
	public void setDeliveryType(int deliveryType) {
		_columnBitmask |= DELIVERYTYPE_COLUMN_BITMASK;

		if (!_setOriginalDeliveryType) {
			_setOriginalDeliveryType = true;

			_originalDeliveryType = _deliveryType;
		}

		_deliveryType = deliveryType;
	}

	public int getOriginalDeliveryType() {
		return _originalDeliveryType;
	}

	@Override
	public long getDeliverBy() {
		return _deliverBy;
	}

	@Override
	public void setDeliverBy(long deliverBy) {
		_deliverBy = deliverBy;
	}

	@Override
	public boolean getDelivered() {
		return _delivered;
	}

	@Override
	public boolean isDelivered() {
		return _delivered;
	}

	@Override
	public void setDelivered(boolean delivered) {
		_columnBitmask |= DELIVERED_COLUMN_BITMASK;

		if (!_setOriginalDelivered) {
			_setOriginalDelivered = true;

			_originalDelivered = _delivered;
		}

		_delivered = delivered;
	}

	public boolean getOriginalDelivered() {
		return _originalDelivered;
	}

	@Override
	public String getPayload() {
		if (_payload == null) {
			return "";
		}
		else {
			return _payload;
		}
	}

	@Override
	public void setPayload(String payload) {
		_payload = payload;
	}

	@Override
	public boolean getActionRequired() {
		return _actionRequired;
	}

	@Override
	public boolean isActionRequired() {
		return _actionRequired;
	}

	@Override
	public void setActionRequired(boolean actionRequired) {
		_columnBitmask |= ACTIONREQUIRED_COLUMN_BITMASK;

		if (!_setOriginalActionRequired) {
			_setOriginalActionRequired = true;

			_originalActionRequired = _actionRequired;
		}

		_actionRequired = actionRequired;
	}

	public boolean getOriginalActionRequired() {
		return _originalActionRequired;
	}

	@Override
	public boolean getArchived() {
		return _archived;
	}

	@Override
	public boolean isArchived() {
		return _archived;
	}

	@Override
	public void setArchived(boolean archived) {
		_columnBitmask |= ARCHIVED_COLUMN_BITMASK;

		if (!_setOriginalArchived) {
			_setOriginalArchived = true;

			_originalArchived = _archived;
		}

		_archived = archived;
	}

	public boolean getOriginalArchived() {
		return _originalArchived;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			UserNotificationEvent.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public UserNotificationEvent toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (UserNotificationEvent)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		UserNotificationEventImpl userNotificationEventImpl = new UserNotificationEventImpl();

		userNotificationEventImpl.setMvccVersion(getMvccVersion());
		userNotificationEventImpl.setUuid(getUuid());
		userNotificationEventImpl.setUserNotificationEventId(getUserNotificationEventId());
		userNotificationEventImpl.setCompanyId(getCompanyId());
		userNotificationEventImpl.setUserId(getUserId());
		userNotificationEventImpl.setType(getType());
		userNotificationEventImpl.setTimestamp(getTimestamp());
		userNotificationEventImpl.setDeliveryType(getDeliveryType());
		userNotificationEventImpl.setDeliverBy(getDeliverBy());
		userNotificationEventImpl.setDelivered(isDelivered());
		userNotificationEventImpl.setPayload(getPayload());
		userNotificationEventImpl.setActionRequired(isActionRequired());
		userNotificationEventImpl.setArchived(isArchived());

		userNotificationEventImpl.resetOriginalValues();

		return userNotificationEventImpl;
	}

	@Override
	public int compareTo(UserNotificationEvent userNotificationEvent) {
		int value = 0;

		if (getTimestamp() < userNotificationEvent.getTimestamp()) {
			value = -1;
		}
		else if (getTimestamp() > userNotificationEvent.getTimestamp()) {
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

		if (!(obj instanceof UserNotificationEvent)) {
			return false;
		}

		UserNotificationEvent userNotificationEvent = (UserNotificationEvent)obj;

		long primaryKey = userNotificationEvent.getPrimaryKey();

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
		UserNotificationEventModelImpl userNotificationEventModelImpl = this;

		userNotificationEventModelImpl._originalUuid = userNotificationEventModelImpl._uuid;

		userNotificationEventModelImpl._originalCompanyId = userNotificationEventModelImpl._companyId;

		userNotificationEventModelImpl._setOriginalCompanyId = false;

		userNotificationEventModelImpl._originalUserId = userNotificationEventModelImpl._userId;

		userNotificationEventModelImpl._setOriginalUserId = false;

		userNotificationEventModelImpl._originalType = userNotificationEventModelImpl._type;

		userNotificationEventModelImpl._originalDeliveryType = userNotificationEventModelImpl._deliveryType;

		userNotificationEventModelImpl._setOriginalDeliveryType = false;

		userNotificationEventModelImpl._originalDelivered = userNotificationEventModelImpl._delivered;

		userNotificationEventModelImpl._setOriginalDelivered = false;

		userNotificationEventModelImpl._originalActionRequired = userNotificationEventModelImpl._actionRequired;

		userNotificationEventModelImpl._setOriginalActionRequired = false;

		userNotificationEventModelImpl._originalArchived = userNotificationEventModelImpl._archived;

		userNotificationEventModelImpl._setOriginalArchived = false;

		userNotificationEventModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<UserNotificationEvent> toCacheModel() {
		UserNotificationEventCacheModel userNotificationEventCacheModel = new UserNotificationEventCacheModel();

		userNotificationEventCacheModel.mvccVersion = getMvccVersion();

		userNotificationEventCacheModel.uuid = getUuid();

		String uuid = userNotificationEventCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			userNotificationEventCacheModel.uuid = null;
		}

		userNotificationEventCacheModel.userNotificationEventId = getUserNotificationEventId();

		userNotificationEventCacheModel.companyId = getCompanyId();

		userNotificationEventCacheModel.userId = getUserId();

		userNotificationEventCacheModel.type = getType();

		String type = userNotificationEventCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			userNotificationEventCacheModel.type = null;
		}

		userNotificationEventCacheModel.timestamp = getTimestamp();

		userNotificationEventCacheModel.deliveryType = getDeliveryType();

		userNotificationEventCacheModel.deliverBy = getDeliverBy();

		userNotificationEventCacheModel.delivered = isDelivered();

		userNotificationEventCacheModel.payload = getPayload();

		String payload = userNotificationEventCacheModel.payload;

		if ((payload != null) && (payload.length() == 0)) {
			userNotificationEventCacheModel.payload = null;
		}

		userNotificationEventCacheModel.actionRequired = isActionRequired();

		userNotificationEventCacheModel.archived = isArchived();

		return userNotificationEventCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(getMvccVersion());
		sb.append(", uuid=");
		sb.append(getUuid());
		sb.append(", userNotificationEventId=");
		sb.append(getUserNotificationEventId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", timestamp=");
		sb.append(getTimestamp());
		sb.append(", deliveryType=");
		sb.append(getDeliveryType());
		sb.append(", deliverBy=");
		sb.append(getDeliverBy());
		sb.append(", delivered=");
		sb.append(isDelivered());
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append(", actionRequired=");
		sb.append(isActionRequired());
		sb.append(", archived=");
		sb.append(isArchived());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(43);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.kernel.model.UserNotificationEvent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mvccVersion</column-name><column-value><![CDATA[");
		sb.append(getMvccVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userNotificationEventId</column-name><column-value><![CDATA[");
		sb.append(getUserNotificationEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>timestamp</column-name><column-value><![CDATA[");
		sb.append(getTimestamp());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>deliveryType</column-name><column-value><![CDATA[");
		sb.append(getDeliveryType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>deliverBy</column-name><column-value><![CDATA[");
		sb.append(getDeliverBy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>delivered</column-name><column-value><![CDATA[");
		sb.append(isDelivered());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>payload</column-name><column-value><![CDATA[");
		sb.append(getPayload());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actionRequired</column-name><column-value><![CDATA[");
		sb.append(isActionRequired());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>archived</column-name><column-value><![CDATA[");
		sb.append(isArchived());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = UserNotificationEvent.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			UserNotificationEvent.class, ModelWrapper.class
		};
	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _userNotificationEventId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _type;
	private String _originalType;
	private long _timestamp;
	private int _deliveryType;
	private int _originalDeliveryType;
	private boolean _setOriginalDeliveryType;
	private long _deliverBy;
	private boolean _delivered;
	private boolean _originalDelivered;
	private boolean _setOriginalDelivered;
	private String _payload;
	private boolean _actionRequired;
	private boolean _originalActionRequired;
	private boolean _setOriginalActionRequired;
	private boolean _archived;
	private boolean _originalArchived;
	private boolean _setOriginalArchived;
	private long _columnBitmask;
	private UserNotificationEvent _escapedModel;
}