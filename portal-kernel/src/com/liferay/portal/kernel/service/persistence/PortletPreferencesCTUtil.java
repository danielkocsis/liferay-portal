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
import com.liferay.portal.kernel.model.PortletPreferencesCT;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence utility for the portlet preferences ct service. This utility wraps <code>com.liferay.portal.service.persistence.impl.PortletPreferencesCTPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesCTPersistence
 * @generated
 */
@ProviderType
public class PortletPreferencesCTUtil {

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
	public static void clearCache(PortletPreferencesCT portletPreferencesCT) {
		getPersistence().clearCache(portletPreferencesCT);
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
	public static Map<Serializable, PortletPreferencesCT> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PortletPreferencesCT> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortletPreferencesCT> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PortletPreferencesCT> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PortletPreferencesCT update(
		PortletPreferencesCT portletPreferencesCT) {

		return getPersistence().update(portletPreferencesCT);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PortletPreferencesCT update(
		PortletPreferencesCT portletPreferencesCT,
		ServiceContext serviceContext) {

		return getPersistence().update(portletPreferencesCT, serviceContext);
	}

	/**
	 * Returns all the portlet preferences cts where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByClassPK(long classPK) {
		return getPersistence().findByClassPK(classPK);
	}

	/**
	 * Returns a range of all the portlet preferences cts where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @return the range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByClassPK(
		long classPK, int start, int end) {

		return getPersistence().findByClassPK(classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByClassPK(
		long classPK, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().findByClassPK(
			classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByClassPK(
		long classPK, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByClassPK(
			classPK, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT findByClassPK_First(
			long classPK,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByClassPK_First(classPK, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT fetchByClassPK_First(
		long classPK,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().fetchByClassPK_First(
			classPK, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT findByClassPK_Last(
			long classPK,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByClassPK_Last(classPK, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT fetchByClassPK_Last(
		long classPK,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().fetchByClassPK_Last(classPK, orderByComparator);
	}

	/**
	 * Returns the portlet preferences cts before and after the current portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param portletPreferencesCTPK the primary key of the current portlet preferences ct
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public static PortletPreferencesCT[] findByClassPK_PrevAndNext(
			PortletPreferencesCTPK portletPreferencesCTPK, long classPK,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByClassPK_PrevAndNext(
			portletPreferencesCTPK, classPK, orderByComparator);
	}

	/**
	 * Removes all the portlet preferences cts where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 */
	public static void removeByClassPK(long classPK) {
		getPersistence().removeByClassPK(classPK);
	}

	/**
	 * Returns the number of portlet preferences cts where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching portlet preferences cts
	 */
	public static int countByClassPK(long classPK) {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	 * Returns all the portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId) {

		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @return the range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT findByCTCollectionId_First(
			long ctCollectionId,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT fetchByCTCollectionId_First(
		long ctCollectionId,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT findByCTCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT fetchByCTCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the portlet preferences cts before and after the current portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param portletPreferencesCTPK the primary key of the current portlet preferences ct
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public static PortletPreferencesCT[] findByCTCollectionId_PrevAndNext(
			PortletPreferencesCTPK portletPreferencesCTPK, long ctCollectionId,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			portletPreferencesCTPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the portlet preferences cts where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching portlet preferences cts
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @return the matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId) {

		return getPersistence().findByC_C(classPK, ctCollectionId);
	}

	/**
	 * Returns a range of all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @return the range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end) {

		return getPersistence().findByC_C(classPK, ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().findByC_C(
			classPK, ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_C(
			classPK, ctCollectionId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT findByC_C_First(
			long classPK, long ctCollectionId,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByC_C_First(
			classPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT fetchByC_C_First(
		long classPK, long ctCollectionId,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT findByC_C_Last(
			long classPK, long ctCollectionId,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByC_C_Last(
			classPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public static PortletPreferencesCT fetchByC_C_Last(
		long classPK, long ctCollectionId,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the portlet preferences cts before and after the current portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param portletPreferencesCTPK the primary key of the current portlet preferences ct
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public static PortletPreferencesCT[] findByC_C_PrevAndNext(
			PortletPreferencesCTPK portletPreferencesCTPK, long classPK,
			long ctCollectionId,
			OrderByComparator<PortletPreferencesCT> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByC_C_PrevAndNext(
			portletPreferencesCTPK, classPK, ctCollectionId, orderByComparator);
	}

	/**
	 * Returns all the portlet preferences cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @return the matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId) {

		return getPersistence().findByC_C(classPKs, ctCollectionId);
	}

	/**
	 * Returns a range of all the portlet preferences cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @return the range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end) {

		return getPersistence().findByC_C(classPKs, ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().findByC_C(
			classPKs, ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_C(
			classPKs, ctCollectionId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Removes all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByC_C(long classPK, long ctCollectionId) {
		getPersistence().removeByC_C(classPK, ctCollectionId);
	}

	/**
	 * Returns the number of portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching portlet preferences cts
	 */
	public static int countByC_C(long classPK, long ctCollectionId) {
		return getPersistence().countByC_C(classPK, ctCollectionId);
	}

	/**
	 * Returns the number of portlet preferences cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching portlet preferences cts
	 */
	public static int countByC_C(long[] classPKs, long ctCollectionId) {
		return getPersistence().countByC_C(classPKs, ctCollectionId);
	}

	/**
	 * Caches the portlet preferences ct in the entity cache if it is enabled.
	 *
	 * @param portletPreferencesCT the portlet preferences ct
	 */
	public static void cacheResult(PortletPreferencesCT portletPreferencesCT) {
		getPersistence().cacheResult(portletPreferencesCT);
	}

	/**
	 * Caches the portlet preferences cts in the entity cache if it is enabled.
	 *
	 * @param portletPreferencesCTs the portlet preferences cts
	 */
	public static void cacheResult(
		List<PortletPreferencesCT> portletPreferencesCTs) {

		getPersistence().cacheResult(portletPreferencesCTs);
	}

	/**
	 * Creates a new portlet preferences ct with the primary key. Does not add the portlet preferences ct to the database.
	 *
	 * @param portletPreferencesCTPK the primary key for the new portlet preferences ct
	 * @return the new portlet preferences ct
	 */
	public static PortletPreferencesCT create(
		PortletPreferencesCTPK portletPreferencesCTPK) {

		return getPersistence().create(portletPreferencesCTPK);
	}

	/**
	 * Removes the portlet preferences ct with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferencesCTPK the primary key of the portlet preferences ct
	 * @return the portlet preferences ct that was removed
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public static PortletPreferencesCT remove(
			PortletPreferencesCTPK portletPreferencesCTPK)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().remove(portletPreferencesCTPK);
	}

	public static PortletPreferencesCT updateImpl(
		PortletPreferencesCT portletPreferencesCT) {

		return getPersistence().updateImpl(portletPreferencesCT);
	}

	/**
	 * Returns the portlet preferences ct with the primary key or throws a <code>NoSuchPortletPreferencesCTException</code> if it could not be found.
	 *
	 * @param portletPreferencesCTPK the primary key of the portlet preferences ct
	 * @return the portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public static PortletPreferencesCT findByPrimaryKey(
			PortletPreferencesCTPK portletPreferencesCTPK)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesCTException {

		return getPersistence().findByPrimaryKey(portletPreferencesCTPK);
	}

	/**
	 * Returns the portlet preferences ct with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferencesCTPK the primary key of the portlet preferences ct
	 * @return the portlet preferences ct, or <code>null</code> if a portlet preferences ct with the primary key could not be found
	 */
	public static PortletPreferencesCT fetchByPrimaryKey(
		PortletPreferencesCTPK portletPreferencesCTPK) {

		return getPersistence().fetchByPrimaryKey(portletPreferencesCTPK);
	}

	/**
	 * Returns all the portlet preferences cts.
	 *
	 * @return the portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the portlet preferences cts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @return the range of portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findAll(
		int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferences cts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferences cts
	 * @param end the upper bound of the range of portlet preferences cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of portlet preferences cts
	 */
	public static List<PortletPreferencesCT> findAll(
		int start, int end,
		OrderByComparator<PortletPreferencesCT> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the portlet preferences cts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of portlet preferences cts.
	 *
	 * @return the number of portlet preferences cts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static Set<String> getCompoundPKColumnNames() {
		return getPersistence().getCompoundPKColumnNames();
	}

	public static PortletPreferencesCTPersistence getPersistence() {
		if (_persistence == null) {
			_persistence =
				(PortletPreferencesCTPersistence)PortalBeanLocatorUtil.locate(
					PortletPreferencesCTPersistence.class.getName());
		}

		return _persistence;
	}

	private static PortletPreferencesCTPersistence _persistence;

}