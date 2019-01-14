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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import java.util.Date;

/**
 * The base model interface for the LayoutSetResource service. Represents a row in the &quot;LayoutSetResource&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.LayoutSetResourceModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.LayoutSetResourceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetResource
 * @see com.liferay.portal.model.impl.LayoutSetResourceImpl
 * @see com.liferay.portal.model.impl.LayoutSetResourceModelImpl
 * @generated
 */
@ProviderType
public interface LayoutSetResourceModel extends BaseModel<LayoutSetResource>,
	MVCCModel, ShardedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a layout set resource model instance should use the {@link LayoutSetResource} interface instead.
	 */

	/**
	 * Returns the primary key of this layout set resource.
	 *
	 * @return the primary key of this layout set resource
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this layout set resource.
	 *
	 * @param primaryKey the primary key of this layout set resource
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this layout set resource.
	 *
	 * @return the mvcc version of this layout set resource
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this layout set resource.
	 *
	 * @param mvccVersion the mvcc version of this layout set resource
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the layout set resource ID of this layout set resource.
	 *
	 * @return the layout set resource ID of this layout set resource
	 */
	public long getLayoutSetResourceId();

	/**
	 * Sets the layout set resource ID of this layout set resource.
	 *
	 * @param layoutSetResourceId the layout set resource ID of this layout set resource
	 */
	public void setLayoutSetResourceId(long layoutSetResourceId);

	/**
	 * Returns the group ID of this layout set resource.
	 *
	 * @return the group ID of this layout set resource
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this layout set resource.
	 *
	 * @param groupId the group ID of this layout set resource
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this layout set resource.
	 *
	 * @return the company ID of this layout set resource
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this layout set resource.
	 *
	 * @param companyId the company ID of this layout set resource
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this layout set resource.
	 *
	 * @return the create date of this layout set resource
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this layout set resource.
	 *
	 * @param createDate the create date of this layout set resource
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this layout set resource.
	 *
	 * @return the modified date of this layout set resource
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this layout set resource.
	 *
	 * @param modifiedDate the modified date of this layout set resource
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the page count of this layout set resource.
	 *
	 * @return the page count of this layout set resource
	 */
	public int getPageCount();

	/**
	 * Sets the page count of this layout set resource.
	 *
	 * @param pageCount the page count of this layout set resource
	 */
	public void setPageCount(int pageCount);

	/**
	 * Returns the private layout of this layout set resource.
	 *
	 * @return the private layout of this layout set resource
	 */
	public boolean getPrivateLayout();

	/**
	 * Returns <code>true</code> if this layout set resource is private layout.
	 *
	 * @return <code>true</code> if this layout set resource is private layout; <code>false</code> otherwise
	 */
	public boolean isPrivateLayout();

	/**
	 * Sets whether this layout set resource is private layout.
	 *
	 * @param privateLayout the private layout of this layout set resource
	 */
	public void setPrivateLayout(boolean privateLayout);
}