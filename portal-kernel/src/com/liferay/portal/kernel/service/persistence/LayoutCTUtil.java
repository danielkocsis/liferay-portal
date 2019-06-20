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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.LayoutCT;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence utility for the layout ct service. This utility wraps <code>com.liferay.portal.service.persistence.impl.LayoutCTPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCTPersistence
 * @generated
 */
@ProviderType
public class LayoutCTUtil {

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
	public static void clearCache(LayoutCT layoutCT) {
		getPersistence().clearCache(layoutCT);
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
	public static Map<Serializable, LayoutCT> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutCT> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutCT> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutCT> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutCT update(LayoutCT layoutCT) {
		return getPersistence().update(layoutCT);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutCT update(
		LayoutCT layoutCT, ServiceContext serviceContext) {

		return getPersistence().update(layoutCT, serviceContext);
	}

	/**
	 * Returns all the layout cts where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout cts
	 */
	public static List<LayoutCT> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the layout cts where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	public static List<LayoutCT> findByPlid(long plid, int start, int end) {
		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout cts where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout cts where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public static LayoutCT findByPlid_First(
			long plid, OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public static LayoutCT fetchByPlid_First(
		long plid, OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public static LayoutCT findByPlid_Last(
			long plid, OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public static LayoutCT fetchByPlid_Last(
		long plid, OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where plid = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public static LayoutCT[] findByPlid_PrevAndNext(
			LayoutCTPK layoutCTPK, long plid,
			OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByPlid_PrevAndNext(
			layoutCTPK, plid, orderByComparator);
	}

	/**
	 * Removes all the layout cts where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of layout cts where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout cts
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns all the layout cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	public static List<LayoutCT> findByCTCollectionId(long ctCollectionId) {
		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the layout cts where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	public static List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout cts where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout cts where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public static LayoutCT findByCTCollectionId_First(
			long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public static LayoutCT fetchByCTCollectionId_First(
		long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public static LayoutCT findByCTCollectionId_Last(
			long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public static LayoutCT fetchByCTCollectionId_Last(
		long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public static LayoutCT[] findByCTCollectionId_PrevAndNext(
			LayoutCTPK layoutCTPK, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			layoutCTPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the layout cts where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of layout cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(long plid, long ctCollectionId) {
		return getPersistence().findByP_CT(plid, ctCollectionId);
	}

	/**
	 * Returns a range of all the layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(
		long plid, long ctCollectionId, int start, int end) {

		return getPersistence().findByP_CT(plid, ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(
		long plid, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().findByP_CT(
			plid, ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(
		long plid, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByP_CT(
			plid, ctCollectionId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public static LayoutCT findByP_CT_First(
			long plid, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByP_CT_First(
			plid, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public static LayoutCT fetchByP_CT_First(
		long plid, long ctCollectionId,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().fetchByP_CT_First(
			plid, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public static LayoutCT findByP_CT_Last(
			long plid, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByP_CT_Last(
			plid, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public static LayoutCT fetchByP_CT_Last(
		long plid, long ctCollectionId,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().fetchByP_CT_Last(
			plid, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public static LayoutCT[] findByP_CT_PrevAndNext(
			LayoutCTPK layoutCTPK, long plid, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByP_CT_PrevAndNext(
			layoutCTPK, plid, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns all the layout cts where plid = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plids the plids
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(long[] plids, long ctCollectionId) {
		return getPersistence().findByP_CT(plids, ctCollectionId);
	}

	/**
	 * Returns a range of all the layout cts where plid = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plids the plids
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId, int start, int end) {

		return getPersistence().findByP_CT(plids, ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout cts where plid = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plids the plids
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().findByP_CT(
			plids, ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout cts where plid = &#63; and ctCollectionId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	public static List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByP_CT(
			plids, ctCollectionId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Removes all the layout cts where plid = &#63; and ctCollectionId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByP_CT(long plid, long ctCollectionId) {
		getPersistence().removeByP_CT(plid, ctCollectionId);
	}

	/**
	 * Returns the number of layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	public static int countByP_CT(long plid, long ctCollectionId) {
		return getPersistence().countByP_CT(plid, ctCollectionId);
	}

	/**
	 * Returns the number of layout cts where plid = any &#63; and ctCollectionId = &#63;.
	 *
	 * @param plids the plids
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	public static int countByP_CT(long[] plids, long ctCollectionId) {
		return getPersistence().countByP_CT(plids, ctCollectionId);
	}

	/**
	 * Caches the layout ct in the entity cache if it is enabled.
	 *
	 * @param layoutCT the layout ct
	 */
	public static void cacheResult(LayoutCT layoutCT) {
		getPersistence().cacheResult(layoutCT);
	}

	/**
	 * Caches the layout cts in the entity cache if it is enabled.
	 *
	 * @param layoutCTs the layout cts
	 */
	public static void cacheResult(List<LayoutCT> layoutCTs) {
		getPersistence().cacheResult(layoutCTs);
	}

	/**
	 * Creates a new layout ct with the primary key. Does not add the layout ct to the database.
	 *
	 * @param layoutCTPK the primary key for the new layout ct
	 * @return the new layout ct
	 */
	public static LayoutCT create(LayoutCTPK layoutCTPK) {
		return getPersistence().create(layoutCTPK);
	}

	/**
	 * Removes the layout ct with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct that was removed
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public static LayoutCT remove(LayoutCTPK layoutCTPK)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().remove(layoutCTPK);
	}

	public static LayoutCT updateImpl(LayoutCT layoutCT) {
		return getPersistence().updateImpl(layoutCT);
	}

	/**
	 * Returns the layout ct with the primary key or throws a <code>NoSuchLayoutCTException</code> if it could not be found.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public static LayoutCT findByPrimaryKey(LayoutCTPK layoutCTPK)
		throws com.liferay.portal.kernel.exception.NoSuchLayoutCTException {

		return getPersistence().findByPrimaryKey(layoutCTPK);
	}

	/**
	 * Returns the layout ct with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct, or <code>null</code> if a layout ct with the primary key could not be found
	 */
	public static LayoutCT fetchByPrimaryKey(LayoutCTPK layoutCTPK) {
		return getPersistence().fetchByPrimaryKey(layoutCTPK);
	}

	/**
	 * Returns all the layout cts.
	 *
	 * @return the layout cts
	 */
	public static List<LayoutCT> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout cts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of layout cts
	 */
	public static List<LayoutCT> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout cts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout cts
	 */
	public static List<LayoutCT> findAll(
		int start, int end, OrderByComparator<LayoutCT> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout cts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout cts
	 */
	public static List<LayoutCT> findAll(
		int start, int end, OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the layout cts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout cts.
	 *
	 * @return the number of layout cts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getCompoundPKColumnNames() {
		return getPersistence().getCompoundPKColumnNames();
	}

	public static LayoutCTPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutCTPersistence)PortalBeanLocatorUtil.locate(
				LayoutCTPersistence.class.getName());
		}

		return _persistence;
	}

	private static LayoutCTPersistence _persistence;

}