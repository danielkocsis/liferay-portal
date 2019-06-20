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

import com.liferay.portal.kernel.exception.NoSuchLayoutCTException;
import com.liferay.portal.kernel.model.LayoutCT;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout ct service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCTUtil
 * @generated
 */
@ProviderType
public interface LayoutCTPersistence extends BasePersistence<LayoutCT> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutCTUtil} to access the layout ct persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout cts where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout cts
	 */
	public java.util.List<LayoutCT> findByPlid(long plid);

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
	public java.util.List<LayoutCT> findByPlid(long plid, int start, int end);

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
	public java.util.List<LayoutCT> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

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
	public java.util.List<LayoutCT> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public LayoutCT findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public LayoutCT fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public LayoutCT findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public LayoutCT fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where plid = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public LayoutCT[] findByPlid_PrevAndNext(
			LayoutCTPK layoutCTPK, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Removes all the layout cts where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of layout cts where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout cts
	 */
	public int countByPlid(long plid);

	/**
	 * Returns all the layout cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	public java.util.List<LayoutCT> findByCTCollectionId(long ctCollectionId);

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
	public java.util.List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end);

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
	public java.util.List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

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
	public java.util.List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public LayoutCT findByCTCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the first layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public LayoutCT fetchByCTCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

	/**
	 * Returns the last layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public LayoutCT findByCTCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the last layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public LayoutCT fetchByCTCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public LayoutCT[] findByCTCollectionId_PrevAndNext(
			LayoutCTPK layoutCTPK, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Removes all the layout cts where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCTCollectionId(long ctCollectionId);

	/**
	 * Returns the number of layout cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	public int countByCTCollectionId(long ctCollectionId);

	/**
	 * Returns all the layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	public java.util.List<LayoutCT> findByP_CT(long plid, long ctCollectionId);

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
	public java.util.List<LayoutCT> findByP_CT(
		long plid, long ctCollectionId, int start, int end);

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
	public java.util.List<LayoutCT> findByP_CT(
		long plid, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

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
	public java.util.List<LayoutCT> findByP_CT(
		long plid, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public LayoutCT findByP_CT_First(
			long plid, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the first layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public LayoutCT fetchByP_CT_First(
		long plid, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	public LayoutCT findByP_CT_Last(
			long plid, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the last layout ct in the ordered set where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	public LayoutCT fetchByP_CT_Last(
		long plid, long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

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
	public LayoutCT[] findByP_CT_PrevAndNext(
			LayoutCTPK layoutCTPK, long plid, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
				orderByComparator)
		throws NoSuchLayoutCTException;

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
	public java.util.List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId);

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
	public java.util.List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId, int start, int end);

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
	public java.util.List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

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
	public java.util.List<LayoutCT> findByP_CT(
		long[] plids, long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the layout cts where plid = &#63; and ctCollectionId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByP_CT(long plid, long ctCollectionId);

	/**
	 * Returns the number of layout cts where plid = &#63; and ctCollectionId = &#63;.
	 *
	 * @param plid the plid
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	public int countByP_CT(long plid, long ctCollectionId);

	/**
	 * Returns the number of layout cts where plid = any &#63; and ctCollectionId = &#63;.
	 *
	 * @param plids the plids
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	public int countByP_CT(long[] plids, long ctCollectionId);

	/**
	 * Caches the layout ct in the entity cache if it is enabled.
	 *
	 * @param layoutCT the layout ct
	 */
	public void cacheResult(LayoutCT layoutCT);

	/**
	 * Caches the layout cts in the entity cache if it is enabled.
	 *
	 * @param layoutCTs the layout cts
	 */
	public void cacheResult(java.util.List<LayoutCT> layoutCTs);

	/**
	 * Creates a new layout ct with the primary key. Does not add the layout ct to the database.
	 *
	 * @param layoutCTPK the primary key for the new layout ct
	 * @return the new layout ct
	 */
	public LayoutCT create(LayoutCTPK layoutCTPK);

	/**
	 * Removes the layout ct with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct that was removed
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public LayoutCT remove(LayoutCTPK layoutCTPK)
		throws NoSuchLayoutCTException;

	public LayoutCT updateImpl(LayoutCT layoutCT);

	/**
	 * Returns the layout ct with the primary key or throws a <code>NoSuchLayoutCTException</code> if it could not be found.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	public LayoutCT findByPrimaryKey(LayoutCTPK layoutCTPK)
		throws NoSuchLayoutCTException;

	/**
	 * Returns the layout ct with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct, or <code>null</code> if a layout ct with the primary key could not be found
	 */
	public LayoutCT fetchByPrimaryKey(LayoutCTPK layoutCTPK);

	/**
	 * Returns all the layout cts.
	 *
	 * @return the layout cts
	 */
	public java.util.List<LayoutCT> findAll();

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
	public java.util.List<LayoutCT> findAll(int start, int end);

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
	public java.util.List<LayoutCT> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator);

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
	public java.util.List<LayoutCT> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutCT>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the layout cts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout cts.
	 *
	 * @return the number of layout cts
	 */
	public int countAll();

	public Set<String> getCompoundPKColumnNames();

}