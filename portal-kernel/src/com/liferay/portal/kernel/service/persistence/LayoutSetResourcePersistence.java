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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException;
import com.liferay.portal.kernel.model.LayoutSetResource;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the layout set resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.LayoutSetResourcePersistenceImpl
 * @see LayoutSetResourceUtil
 * @generated
 */
@ProviderType
public interface LayoutSetResourcePersistence extends BasePersistence<LayoutSetResource> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetResourceUtil} to access the layout set resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, LayoutSetResource> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the layout set resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByGroupId(long groupId);

	/**
	* Returns a range of all the layout set resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @return the range of matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the layout set resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns an ordered range of all the layout set resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public LayoutSetResource findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the first layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public LayoutSetResource fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns the last layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public LayoutSetResource findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the last layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public LayoutSetResource fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns the layout set resources before and after the current layout set resource in the ordered set where groupId = &#63;.
	*
	* @param layoutSetResourceId the primary key of the current layout set resource
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set resource
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public LayoutSetResource[] findByGroupId_PrevAndNext(
		long layoutSetResourceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException;

	/**
	* Removes all the layout set resources where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of layout set resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout set resources
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or throws a {@link NoSuchLayoutSetResourceException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public LayoutSetResource findByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public LayoutSetResource fetchByG_P(long groupId, boolean privateLayout);

	/**
	* Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public LayoutSetResource fetchByG_P(long groupId, boolean privateLayout,
		boolean retrieveFromCache);

	/**
	* Removes the layout set resource where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the layout set resource that was removed
	*/
	public LayoutSetResource removeByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the number of layout set resources where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layout set resources
	*/
	public int countByG_P(long groupId, boolean privateLayout);

	/**
	* Returns all the layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @return the matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout);

	/**
	* Returns a range of all the layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @return the range of matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end);

	/**
	* Returns an ordered range of all the layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns an ordered range of all the layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set resources
	*/
	public java.util.List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public LayoutSetResource findByC_P_First(long companyId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the first layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public LayoutSetResource fetchByC_P_First(long companyId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns the last layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public LayoutSetResource findByC_P_Last(long companyId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the last layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public LayoutSetResource fetchByC_P_Last(long companyId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns the layout set resources before and after the current layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param layoutSetResourceId the primary key of the current layout set resource
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set resource
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public LayoutSetResource[] findByC_P_PrevAndNext(long layoutSetResourceId,
		long companyId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException;

	/**
	* Removes all the layout set resources where companyId = &#63; and privateLayout = &#63; from the database.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	*/
	public void removeByC_P(long companyId, boolean privateLayout);

	/**
	* Returns the number of layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @return the number of matching layout set resources
	*/
	public int countByC_P(long companyId, boolean privateLayout);

	/**
	* Caches the layout set resource in the entity cache if it is enabled.
	*
	* @param layoutSetResource the layout set resource
	*/
	public void cacheResult(LayoutSetResource layoutSetResource);

	/**
	* Caches the layout set resources in the entity cache if it is enabled.
	*
	* @param layoutSetResources the layout set resources
	*/
	public void cacheResult(
		java.util.List<LayoutSetResource> layoutSetResources);

	/**
	* Creates a new layout set resource with the primary key. Does not add the layout set resource to the database.
	*
	* @param layoutSetResourceId the primary key for the new layout set resource
	* @return the new layout set resource
	*/
	public LayoutSetResource create(long layoutSetResourceId);

	/**
	* Removes the layout set resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetResourceId the primary key of the layout set resource
	* @return the layout set resource that was removed
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public LayoutSetResource remove(long layoutSetResourceId)
		throws NoSuchLayoutSetResourceException;

	public LayoutSetResource updateImpl(LayoutSetResource layoutSetResource);

	/**
	* Returns the layout set resource with the primary key or throws a {@link NoSuchLayoutSetResourceException} if it could not be found.
	*
	* @param layoutSetResourceId the primary key of the layout set resource
	* @return the layout set resource
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public LayoutSetResource findByPrimaryKey(long layoutSetResourceId)
		throws NoSuchLayoutSetResourceException;

	/**
	* Returns the layout set resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetResourceId the primary key of the layout set resource
	* @return the layout set resource, or <code>null</code> if a layout set resource with the primary key could not be found
	*/
	public LayoutSetResource fetchByPrimaryKey(long layoutSetResourceId);

	/**
	* Returns all the layout set resources.
	*
	* @return the layout set resources
	*/
	public java.util.List<LayoutSetResource> findAll();

	/**
	* Returns a range of all the layout set resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @return the range of layout set resources
	*/
	public java.util.List<LayoutSetResource> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout set resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout set resources
	*/
	public java.util.List<LayoutSetResource> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator);

	/**
	* Returns an ordered range of all the layout set resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set resources
	* @param end the upper bound of the range of layout set resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout set resources
	*/
	public java.util.List<LayoutSetResource> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout set resources from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout set resources.
	*
	* @return the number of layout set resources
	*/
	public int countAll();
}