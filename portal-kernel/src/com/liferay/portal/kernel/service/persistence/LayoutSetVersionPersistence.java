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

import com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException;
import com.liferay.portal.kernel.model.LayoutSetVersion;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the layout set version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.LayoutSetVersionPersistenceImpl
 * @see LayoutSetVersionUtil
 * @generated
 */
@ProviderType
public interface LayoutSetVersionPersistence extends BasePersistence<LayoutSetVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetVersionUtil} to access the layout set version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, LayoutSetVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the layout set versions where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId);

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
	public java.util.List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end);

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
	public java.util.List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

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
	public java.util.List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetResourceId_First(
		long layoutSetResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetResourceId_First(
		long layoutSetResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetResourceId_Last(
		long layoutSetResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetResourceId_Last(
		long layoutSetResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetResourceId = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetResourceId the layout set resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByLayoutSetResourceId_PrevAndNext(
		long layoutSetVersionId, long layoutSetResourceId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where layoutSetResourceId = &#63; from the database.
	*
	* @param layoutSetResourceId the layout set resource ID
	*/
	public void removeByLayoutSetResourceId(long layoutSetResourceId);

	/**
	* Returns the number of layout set versions where layoutSetResourceId = &#63;.
	*
	* @param layoutSetResourceId the layout set resource ID
	* @return the number of matching layout set versions
	*/
	public int countByLayoutSetResourceId(long layoutSetResourceId);

	/**
	* Returns all the layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the matching layout set versions
	*/
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid);

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
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end);

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
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

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
	public java.util.List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version
	* @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	*/
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	*/
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

	/**
	* Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetVersionId the primary key of the current layout set version
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException;

	/**
	* Removes all the layout set versions where layoutSetPrototypeUuid = &#63; from the database.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	*/
	public void removeByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid);

	/**
	* Returns the number of layout set versions where layoutSetPrototypeUuid = &#63;.
	*
	* @param layoutSetPrototypeUuid the layout set prototype uuid
	* @return the number of matching layout set versions
	*/
	public int countByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid);

	/**
	* Caches the layout set version in the entity cache if it is enabled.
	*
	* @param layoutSetVersion the layout set version
	*/
	public void cacheResult(LayoutSetVersion layoutSetVersion);

	/**
	* Caches the layout set versions in the entity cache if it is enabled.
	*
	* @param layoutSetVersions the layout set versions
	*/
	public void cacheResult(java.util.List<LayoutSetVersion> layoutSetVersions);

	/**
	* Creates a new layout set version with the primary key. Does not add the layout set version to the database.
	*
	* @param layoutSetVersionId the primary key for the new layout set version
	* @return the new layout set version
	*/
	public LayoutSetVersion create(long layoutSetVersionId);

	/**
	* Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version that was removed
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion remove(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException;

	public LayoutSetVersion updateImpl(LayoutSetVersion layoutSetVersion);

	/**
	* Returns the layout set version with the primary key or throws a {@link NoSuchLayoutSetVersionException} if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version
	* @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion findByPrimaryKey(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException;

	/**
	* Returns the layout set version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetVersionId the primary key of the layout set version
	* @return the layout set version, or <code>null</code> if a layout set version with the primary key could not be found
	*/
	public LayoutSetVersion fetchByPrimaryKey(long layoutSetVersionId);

	/**
	* Returns all the layout set versions.
	*
	* @return the layout set versions
	*/
	public java.util.List<LayoutSetVersion> findAll();

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
	public java.util.List<LayoutSetVersion> findAll(int start, int end);

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
	public java.util.List<LayoutSetVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator);

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
	public java.util.List<LayoutSetVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout set versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout set versions.
	*
	* @return the number of layout set versions
	*/
	public int countAll();

	@Override
	public Set<String> getBadColumnNames();
}