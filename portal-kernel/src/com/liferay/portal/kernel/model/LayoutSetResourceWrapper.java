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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetResource}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetResource
 * @generated
 */
@ProviderType
public class LayoutSetResourceWrapper extends BaseModelWrapper<LayoutSetResource>
	implements LayoutSetResource, ModelWrapper<LayoutSetResource> {
	public LayoutSetResourceWrapper(LayoutSetResource layoutSetResource) {
		super(layoutSetResource);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("layoutSetResourceId", getLayoutSetResourceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("pageCount", getPageCount());
		attributes.put("privateLayout", isPrivateLayout());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long layoutSetResourceId = (Long)attributes.get("layoutSetResourceId");

		if (layoutSetResourceId != null) {
			setLayoutSetResourceId(layoutSetResourceId);
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

		Integer pageCount = (Integer)attributes.get("pageCount");

		if (pageCount != null) {
			setPageCount(pageCount);
		}

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}
	}

	/**
	* Returns the company ID of this layout set resource.
	*
	* @return the company ID of this layout set resource
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this layout set resource.
	*
	* @return the create date of this layout set resource
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this layout set resource.
	*
	* @return the group ID of this layout set resource
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the layout set resource ID of this layout set resource.
	*
	* @return the layout set resource ID of this layout set resource
	*/
	@Override
	public long getLayoutSetResourceId() {
		return model.getLayoutSetResourceId();
	}

	/**
	* Returns the modified date of this layout set resource.
	*
	* @return the modified date of this layout set resource
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this layout set resource.
	*
	* @return the mvcc version of this layout set resource
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the page count of this layout set resource.
	*
	* @return the page count of this layout set resource
	*/
	@Override
	public int getPageCount() {
		return model.getPageCount();
	}

	/**
	* Returns the primary key of this layout set resource.
	*
	* @return the primary key of this layout set resource
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the private layout of this layout set resource.
	*
	* @return the private layout of this layout set resource
	*/
	@Override
	public boolean getPrivateLayout() {
		return model.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this layout set resource is private layout.
	*
	* @return <code>true</code> if this layout set resource is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return model.isPrivateLayout();
	}

	/**
	* Sets the company ID of this layout set resource.
	*
	* @param companyId the company ID of this layout set resource
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout set resource.
	*
	* @param createDate the create date of this layout set resource
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this layout set resource.
	*
	* @param groupId the group ID of this layout set resource
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the layout set resource ID of this layout set resource.
	*
	* @param layoutSetResourceId the layout set resource ID of this layout set resource
	*/
	@Override
	public void setLayoutSetResourceId(long layoutSetResourceId) {
		model.setLayoutSetResourceId(layoutSetResourceId);
	}

	/**
	* Sets the modified date of this layout set resource.
	*
	* @param modifiedDate the modified date of this layout set resource
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this layout set resource.
	*
	* @param mvccVersion the mvcc version of this layout set resource
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the page count of this layout set resource.
	*
	* @param pageCount the page count of this layout set resource
	*/
	@Override
	public void setPageCount(int pageCount) {
		model.setPageCount(pageCount);
	}

	/**
	* Sets the primary key of this layout set resource.
	*
	* @param primaryKey the primary key of this layout set resource
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets whether this layout set resource is private layout.
	*
	* @param privateLayout the private layout of this layout set resource
	*/
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		model.setPrivateLayout(privateLayout);
	}

	@Override
	protected LayoutSetResourceWrapper wrap(LayoutSetResource layoutSetResource) {
		return new LayoutSetResourceWrapper(layoutSetResource);
	}
}