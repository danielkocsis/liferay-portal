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
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout set version service. This utility wraps {@link com.liferay.portal.service.persistence.impl.LayoutSetVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersionPersistence
 * @see com.liferay.portal.service.persistence.impl.LayoutSetVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutSetVersionUtil {
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
	public static void clearCache(LayoutSetVersion layoutSetVersion) {
		getPersistence().clearCache(layoutSetVersion);
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
	public static Map<Serializable, LayoutSetVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSetVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutSetVersion update(LayoutSetVersion layoutSetVersion) {
		return getPersistence().update(layoutSetVersion);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutSetVersion update(LayoutSetVersion layoutSetVersion,
		ServiceContext serviceContext) {
		return getPersistence().update(layoutSetVersion, serviceContext);
	}

	/**
	* Returns all the layout set versions where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId) {
		return getPersistence().findByLayoutSetResourceId(layoutSetResourceId);
	}

	/**
	* Returns a range of all the layout set versions where layoutSetResourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end) {
		return getPersistence()
				   .findByLayoutSetResourceId(layoutSetResourceId, start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetResourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetResourceId(layoutSetResourceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetResourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetResourceId(layoutSetResourceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetResourceId_First(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetResourceId_First(layoutSetResourceId,
			orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetResourceId_First(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetResourceId_First(layoutSetResourceId,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetResourceId_Last(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetResourceId_Last(layoutSetResourceId,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetResourceId_Last(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetResourceId_Last(layoutSetResourceId,
			orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByLayoutSetResourceId_PrevAndNext(
		long layoutSetVersionId, long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetResourceId_PrevAndNext(layoutSetVersionId,
			layoutSetResourceId, orderByComparator);
	}

	/**
	* Removes all the layout set versions where layoutSetResourceId = &#63; from the database.
	*
	* @param layoutSetResourceId the layout set resource ID
	*/
	public static void removeByLayoutSetResourceId(long layoutSetResourceId) {
		getPersistence().removeByLayoutSetResourceId(layoutSetResourceId);
	}

	/**
	* Returns the number of layout set versions where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @return the number of matching layout set versions
	*/
	public static int countByLayoutSetResourceId(long layoutSetResourceId) {
		return getPersistence().countByLayoutSetResourceId(layoutSetResourceId);
	}

	/**
	* Returns all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start,
			end);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout set versions
	*/
	public static List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_First(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetPrototypeUuid_First(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public static LayoutSetVersion findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_Last(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public static LayoutSetVersion fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutSetPrototypeUuid_Last(layoutSetPrototypeUuid,
			orderByComparator);
	}

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence()
				   .findByLayoutSetPrototypeUuid_PrevAndNext(layoutSetVersionId,
			layoutSetPrototypeUuid, orderByComparator);
	}

	/**
	* Removes all the layout set versions where layoutSetPrototypeUuid = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	*/
	public static void removeByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		getPersistence().removeByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Returns the number of layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the number of matching layout set versions
	*/
	public static int countByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return getPersistence()
				   .countByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
	}

	/**
	* Caches the layout set version in the entity cache if it is enabled.
	*
	* @param layoutSetVersion the layout set version
	*/
	public static void cacheResult(LayoutSetVersion layoutSetVersion) {
		getPersistence().cacheResult(layoutSetVersion);
	}

	/**
	* Caches the layout set versions in the entity cache if it is enabled.
	*
	* @param layoutSetVersions the layout set versions
	*/
	public static void cacheResult(List<LayoutSetVersion> layoutSetVersions) {
		getPersistence().cacheResult(layoutSetVersions);
	}

	/**
	* Creates a new layout set version with the primary key. Does not add the layout set version to the database.
	*
	* @param layoutSetVersionId the primary key for the new layout set version
	* @return the new layout set version
	*/
	public static LayoutSetVersion create(long layoutSetVersionId) {
		return getPersistence().create(layoutSetVersionId);
	}

	/**
	* Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version that was removed
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion remove(long layoutSetVersionId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().remove(layoutSetVersionId);
	}

	public static LayoutSetVersion updateImpl(LayoutSetVersion layoutSetVersion) {
		return getPersistence().updateImpl(layoutSetVersion);
	}

	/**
	* Returns the layout set version with the primary key or throws a {@link NoSuchLayoutSetVersionException} if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion findByPrimaryKey(long layoutSetVersionId)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException {
		return getPersistence().findByPrimaryKey(layoutSetVersionId);
	}

	/**
	* Returns the layout set version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version, or <code>null</code> if a layout set version with the primary key could not be found
	*/
	public static LayoutSetVersion fetchByPrimaryKey(long layoutSetVersionId) {
		return getPersistence().fetchByPrimaryKey(layoutSetVersionId);
	}

	/**
	* Returns all the layout set versions.
	*
	* @return the layout set versions
	*/
	public static List<LayoutSetVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @return the range of layout set versions
	*/
	public static List<LayoutSetVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout set versions
	*/
	public static List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout set versions
	* @param end the upper bound of the range of layout set versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout set versions
	*/
	public static List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout set versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout set versions.
	*
	* @return the number of layout set versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static LayoutSetVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutSetVersionPersistence)PortalBeanLocatorUtil.locate(LayoutSetVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutSetVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static LayoutSetVersionPersistence _persistence;
}