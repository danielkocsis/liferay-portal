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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the SocialActivityLimit service. Represents a row in the &quot;SocialActivityLimit&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.social.model.impl.SocialActivityLimitImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimit
 * @see com.liferay.portlet.social.model.impl.SocialActivityLimitImpl
 * @see com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl
 * @generated
 */
public interface SocialActivityLimitModel extends AttachedModel,
	BaseModel<SocialActivityLimit>, TypedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a social activity limit model instance should use the {@link SocialActivityLimit} interface instead.
	 */

	/**
	 * Returns the primary key of this social activity limit.
	 *
	 * @return the primary key of this social activity limit
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this social activity limit.
	 *
	 * @param primaryKey the primary key of this social activity limit
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the activity limit ID of this social activity limit.
	 *
	 * @return the activity limit ID of this social activity limit
	 */
	public long getActivityLimitId();

	/**
	 * Sets the activity limit ID of this social activity limit.
	 *
	 * @param activityLimitId the activity limit ID of this social activity limit
	 */
	public void setActivityLimitId(long activityLimitId);

	/**
	 * Returns the group ID of this social activity limit.
	 *
	 * @return the group ID of this social activity limit
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this social activity limit.
	 *
	 * @param groupId the group ID of this social activity limit
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this social activity limit.
	 *
	 * @return the company ID of this social activity limit
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this social activity limit.
	 *
	 * @param companyId the company ID of this social activity limit
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this social activity limit.
	 *
	 * @return the user ID of this social activity limit
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this social activity limit.
	 *
	 * @param userId the user ID of this social activity limit
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this social activity limit.
	 *
	 * @return the user uuid of this social activity limit
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this social activity limit.
	 *
	 * @param userUuid the user uuid of this social activity limit
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the fully qualified class name of this social activity limit.
	 *
	 * @return the fully qualified class name of this social activity limit
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this social activity limit.
	 *
	 * @return the class name ID of this social activity limit
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this social activity limit.
	 *
	 * @param classNameId the class name ID of this social activity limit
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class p k of this social activity limit.
	 *
	 * @return the class p k of this social activity limit
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class p k of this social activity limit.
	 *
	 * @param classPK the class p k of this social activity limit
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the activity type of this social activity limit.
	 *
	 * @return the activity type of this social activity limit
	 */
	public int getActivityType();

	/**
	 * Sets the activity type of this social activity limit.
	 *
	 * @param activityType the activity type of this social activity limit
	 */
	public void setActivityType(int activityType);

	/**
	 * Returns the activity counter name of this social activity limit.
	 *
	 * @return the activity counter name of this social activity limit
	 */
	@AutoEscape
	public String getActivityCounterName();

	/**
	 * Sets the activity counter name of this social activity limit.
	 *
	 * @param activityCounterName the activity counter name of this social activity limit
	 */
	public void setActivityCounterName(String activityCounterName);

	/**
	 * Returns the value of this social activity limit.
	 *
	 * @return the value of this social activity limit
	 */
	@AutoEscape
	public String getValue();

	/**
	 * Sets the value of this social activity limit.
	 *
	 * @param value the value of this social activity limit
	 */
	public void setValue(String value);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(SocialActivityLimit socialActivityLimit);

	@Override
	public int hashCode();

	@Override
	public CacheModel<SocialActivityLimit> toCacheModel();

	@Override
	public SocialActivityLimit toEscapedModel();

	@Override
	public SocialActivityLimit toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}