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

import com.liferay.portal.kernel.exception.NoSuchPortletPreferencesCTException;
import com.liferay.portal.kernel.model.PortletPreferencesCT;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the portlet preferences ct service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesCTUtil
 * @generated
 */
@ProviderType
public interface PortletPreferencesCTPersistence
	extends BasePersistence<PortletPreferencesCT> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferencesCTUtil} to access the portlet preferences ct persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the portlet preferences cts where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the matching portlet preferences cts
	 */
	public java.util.List<PortletPreferencesCT> findByClassPK(long classPK);

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
	public java.util.List<PortletPreferencesCT> findByClassPK(
		long classPK, int start, int end);

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
	public java.util.List<PortletPreferencesCT> findByClassPK(
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

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
	public java.util.List<PortletPreferencesCT> findByClassPK(
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT findByClassPK_First(
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT fetchByClassPK_First(
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT findByClassPK_Last(
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT fetchByClassPK_Last(
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

	/**
	 * Returns the portlet preferences cts before and after the current portlet preferences ct in the ordered set where classPK = &#63;.
	 *
	 * @param portletPreferencesCTPK the primary key of the current portlet preferences ct
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public PortletPreferencesCT[] findByClassPK_PrevAndNext(
			PortletPreferencesCTPK portletPreferencesCTPK, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Removes all the portlet preferences cts where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 */
	public void removeByClassPK(long classPK);

	/**
	 * Returns the number of portlet preferences cts where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching portlet preferences cts
	 */
	public int countByClassPK(long classPK);

	/**
	 * Returns all the portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching portlet preferences cts
	 */
	public java.util.List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId);

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
	public java.util.List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId, int start, int end);

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
	public java.util.List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

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
	public java.util.List<PortletPreferencesCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT findByCTCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the first portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT fetchByCTCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

	/**
	 * Returns the last portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT findByCTCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the last portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT fetchByCTCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

	/**
	 * Returns the portlet preferences cts before and after the current portlet preferences ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param portletPreferencesCTPK the primary key of the current portlet preferences ct
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public PortletPreferencesCT[] findByCTCollectionId_PrevAndNext(
			PortletPreferencesCTPK portletPreferencesCTPK, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Removes all the portlet preferences cts where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCTCollectionId(long ctCollectionId);

	/**
	 * Returns the number of portlet preferences cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching portlet preferences cts
	 */
	public int countByCTCollectionId(long ctCollectionId);

	/**
	 * Returns all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @return the matching portlet preferences cts
	 */
	public java.util.List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId);

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end);

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT findByC_C_First(
			long classPK, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the first portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT fetchByC_C_First(
		long classPK, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT findByC_C_Last(
			long classPK, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the last portlet preferences ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences ct, or <code>null</code> if a matching portlet preferences ct could not be found
	 */
	public PortletPreferencesCT fetchByC_C_Last(
		long classPK, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

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
	public PortletPreferencesCT[] findByC_C_PrevAndNext(
			PortletPreferencesCTPK portletPreferencesCTPK, long classPK,
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<PortletPreferencesCT> orderByComparator)
		throws NoSuchPortletPreferencesCTException;

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId);

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end);

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

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
	public java.util.List<PortletPreferencesCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the portlet preferences cts where classPK = &#63; and ctCollectionId = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByC_C(long classPK, long ctCollectionId);

	/**
	 * Returns the number of portlet preferences cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching portlet preferences cts
	 */
	public int countByC_C(long classPK, long ctCollectionId);

	/**
	 * Returns the number of portlet preferences cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching portlet preferences cts
	 */
	public int countByC_C(long[] classPKs, long ctCollectionId);

	/**
	 * Caches the portlet preferences ct in the entity cache if it is enabled.
	 *
	 * @param portletPreferencesCT the portlet preferences ct
	 */
	public void cacheResult(PortletPreferencesCT portletPreferencesCT);

	/**
	 * Caches the portlet preferences cts in the entity cache if it is enabled.
	 *
	 * @param portletPreferencesCTs the portlet preferences cts
	 */
	public void cacheResult(
		java.util.List<PortletPreferencesCT> portletPreferencesCTs);

	/**
	 * Creates a new portlet preferences ct with the primary key. Does not add the portlet preferences ct to the database.
	 *
	 * @param portletPreferencesCTPK the primary key for the new portlet preferences ct
	 * @return the new portlet preferences ct
	 */
	public PortletPreferencesCT create(
		PortletPreferencesCTPK portletPreferencesCTPK);

	/**
	 * Removes the portlet preferences ct with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferencesCTPK the primary key of the portlet preferences ct
	 * @return the portlet preferences ct that was removed
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public PortletPreferencesCT remove(
			PortletPreferencesCTPK portletPreferencesCTPK)
		throws NoSuchPortletPreferencesCTException;

	public PortletPreferencesCT updateImpl(
		PortletPreferencesCT portletPreferencesCT);

	/**
	 * Returns the portlet preferences ct with the primary key or throws a <code>NoSuchPortletPreferencesCTException</code> if it could not be found.
	 *
	 * @param portletPreferencesCTPK the primary key of the portlet preferences ct
	 * @return the portlet preferences ct
	 * @throws NoSuchPortletPreferencesCTException if a portlet preferences ct with the primary key could not be found
	 */
	public PortletPreferencesCT findByPrimaryKey(
			PortletPreferencesCTPK portletPreferencesCTPK)
		throws NoSuchPortletPreferencesCTException;

	/**
	 * Returns the portlet preferences ct with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferencesCTPK the primary key of the portlet preferences ct
	 * @return the portlet preferences ct, or <code>null</code> if a portlet preferences ct with the primary key could not be found
	 */
	public PortletPreferencesCT fetchByPrimaryKey(
		PortletPreferencesCTPK portletPreferencesCTPK);

	/**
	 * Returns all the portlet preferences cts.
	 *
	 * @return the portlet preferences cts
	 */
	public java.util.List<PortletPreferencesCT> findAll();

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
	public java.util.List<PortletPreferencesCT> findAll(int start, int end);

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
	public java.util.List<PortletPreferencesCT> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator);

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
	public java.util.List<PortletPreferencesCT> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferencesCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the portlet preferences cts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of portlet preferences cts.
	 *
	 * @return the number of portlet preferences cts
	 */
	public int countAll();

	public Set<String> getCompoundPKColumnNames();

}