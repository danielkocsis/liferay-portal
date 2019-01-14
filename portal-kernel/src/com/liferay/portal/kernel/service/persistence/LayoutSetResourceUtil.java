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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.LayoutSetResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout set resource service. This utility wraps {@link com.liferay.portal.service.persistence.impl.LayoutSetResourcePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetResourcePersistence
 * @see com.liferay.portal.service.persistence.impl.LayoutSetResourcePersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutSetResourceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(LayoutSetResource layoutSetResource) {
		getPersistence().clearCache(layoutSetResource);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, LayoutSetResource> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSetResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSetResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSetResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSetResource update(LayoutSetResource layoutSetResource) {
		return getPersistence().update(layoutSetResource);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSetResource update(
		LayoutSetResource layoutSetResource, ServiceContext serviceContext) {
		return getPersistence().update(layoutSetResource, serviceContext);
	}

	/**
	* Returns all the layout set resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout set resources
	*/
	public static List<LayoutSetResource> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<LayoutSetResource> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<LayoutSetResource> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

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
	public static List<LayoutSetResource> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public static LayoutSetResource findByGroupId_First(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public static LayoutSetResource fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public static LayoutSetResource findByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last layout set resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public static LayoutSetResource fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the layout set resources before and after the current layout set resource in the ordered set where groupId = &#63;.
	*
	* @param layoutSetResourceId the primary key of the current layout set resource
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set resource
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public static LayoutSetResource[] findByGroupId_PrevAndNext(
		long layoutSetResourceId, long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutSetResourceId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the layout set resources where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of layout set resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout set resources
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or throws a {@link NoSuchLayoutSetResourceException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public static LayoutSetResource findByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	* Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public static LayoutSetResource fetchByG_P(long groupId,
		boolean privateLayout) {
		return getPersistence().fetchByG_P(groupId, privateLayout);
	}

	/**
	* Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public static LayoutSetResource fetchByG_P(long groupId,
		boolean privateLayout, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P(groupId, privateLayout, retrieveFromCache);
	}

	/**
	* Removes the layout set resource where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the layout set resource that was removed
	*/
	public static LayoutSetResource removeByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	* Returns the number of layout set resources where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching layout set resources
	*/
	public static int countByG_P(long groupId, boolean privateLayout) {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	* Returns all the layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @return the matching layout set resources
	*/
	public static List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout) {
		return getPersistence().findByC_P(companyId, privateLayout);
	}

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
	public static List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end) {
		return getPersistence().findByC_P(companyId, privateLayout, start, end);
	}

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
	public static List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence()
				   .findByC_P(companyId, privateLayout, start, end,
			orderByComparator);
	}

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
	public static List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_P(companyId, privateLayout, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public static LayoutSetResource findByC_P_First(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence()
				   .findByC_P_First(companyId, privateLayout, orderByComparator);
	}

	/**
	* Returns the first layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public static LayoutSetResource fetchByC_P_First(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_First(companyId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource
	* @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	*/
	public static LayoutSetResource findByC_P_Last(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence()
				   .findByC_P_Last(companyId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	*/
	public static LayoutSetResource fetchByC_P_Last(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_Last(companyId, privateLayout, orderByComparator);
	}

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
	public static LayoutSetResource[] findByC_P_PrevAndNext(
		long layoutSetResourceId, long companyId, boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence()
				   .findByC_P_PrevAndNext(layoutSetResourceId, companyId,
			privateLayout, orderByComparator);
	}

	/**
	* Removes all the layout set resources where companyId = &#63; and privateLayout = &#63; from the database.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	*/
	public static void removeByC_P(long companyId, boolean privateLayout) {
		getPersistence().removeByC_P(companyId, privateLayout);
	}

	/**
	* Returns the number of layout set resources where companyId = &#63; and privateLayout = &#63;.
	*
	* @param companyId the company ID
	* @param privateLayout the private layout
	* @return the number of matching layout set resources
	*/
	public static int countByC_P(long companyId, boolean privateLayout) {
		return getPersistence().countByC_P(companyId, privateLayout);
	}

	/**
	* Caches the layout set resource in the entity cache if it is enabled.
	*
	* @param layoutSetResource the layout set resource
	*/
	public static void cacheResult(LayoutSetResource layoutSetResource) {
		getPersistence().cacheResult(layoutSetResource);
	}

	/**
	* Caches the layout set resources in the entity cache if it is enabled.
	*
	* @param layoutSetResources the layout set resources
	*/
	public static void cacheResult(List<LayoutSetResource> layoutSetResources) {
		getPersistence().cacheResult(layoutSetResources);
	}

	/**
	* Creates a new layout set resource with the primary key. Does not add the layout set resource to the database.
	*
	* @param layoutSetResourceId the primary key for the new layout set resource
	* @return the new layout set resource
	*/
	public static LayoutSetResource create(long layoutSetResourceId) {
		return getPersistence().create(layoutSetResourceId);
	}

	/**
	* Removes the layout set resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetResourceId the primary key of the layout set resource
	* @return the layout set resource that was removed
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public static LayoutSetResource remove(long layoutSetResourceId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence().remove(layoutSetResourceId);
	}

	public static LayoutSetResource updateImpl(
		LayoutSetResource layoutSetResource) {
		return getPersistence().updateImpl(layoutSetResource);
	}

	/**
	* Returns the layout set resource with the primary key or throws a {@link NoSuchLayoutSetResourceException} if it could not be found.
	*
	* @param layoutSetResourceId the primary key of the layout set resource
	* @return the layout set resource
	* @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	*/
	public static LayoutSetResource findByPrimaryKey(long layoutSetResourceId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException {
		return getPersistence().findByPrimaryKey(layoutSetResourceId);
	}

	/**
	* Returns the layout set resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetResourceId the primary key of the layout set resource
	* @return the layout set resource, or <code>null</code> if a layout set resource with the primary key could not be found
	*/
	public static LayoutSetResource fetchByPrimaryKey(long layoutSetResourceId) {
		return getPersistence().fetchByPrimaryKey(layoutSetResourceId);
	}

	/**
	* Returns all the layout set resources.
	*
	* @return the layout set resources
	*/
	public static List<LayoutSetResource> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<LayoutSetResource> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<LayoutSetResource> findAll(int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<LayoutSetResource> findAll(int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout set resources from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout set resources.
	*
	* @return the number of layout set resources
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutSetResourcePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutSetResourcePersistence)PortalBeanLocatorUtil.locate(LayoutSetResourcePersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutSetResourceUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static LayoutSetResourcePersistence _persistence;
}