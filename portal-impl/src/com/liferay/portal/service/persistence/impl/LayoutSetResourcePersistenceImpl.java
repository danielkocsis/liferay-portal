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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSetResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.LayoutSetResourcePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.LayoutSetResourceImpl;
import com.liferay.portal.model.impl.LayoutSetResourceModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The persistence implementation for the layout set resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetResourcePersistence
 * @see com.liferay.portal.kernel.service.persistence.LayoutSetResourceUtil
 * @generated
 */
@ProviderType
public class LayoutSetResourcePersistenceImpl extends BasePersistenceImpl<LayoutSetResource>
	implements LayoutSetResourcePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutSetResourceUtil} to access the layout set resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutSetResourceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout set resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout set resources
	 */
	@Override
	public List<LayoutSetResource> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutSetResource> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
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
	@Override
	public List<LayoutSetResource> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetResource> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
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
	@Override
	public List<LayoutSetResource> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<LayoutSetResource> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetResource>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetResource layoutSetResource : list) {
					if ((groupId != layoutSetResource.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTSETRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutSetResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetResource>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout set resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set resource
	 * @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource findByGroupId_First(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutSetResource != null) {
			return layoutSetResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutSetResourceException(msg.toString());
	}

	/**
	 * Returns the first layout set resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		List<LayoutSetResource> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set resource
	 * @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource findByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutSetResource != null) {
			return layoutSetResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutSetResourceException(msg.toString());
	}

	/**
	 * Returns the last layout set resource in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutSetResource> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public LayoutSetResource[] findByGroupId_PrevAndNext(
		long layoutSetResourceId, long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = findByPrimaryKey(layoutSetResourceId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetResource[] array = new LayoutSetResourceImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layoutSetResource,
					groupId, orderByComparator, true);

			array[1] = layoutSetResource;

			array[2] = getByGroupId_PrevAndNext(session, layoutSetResource,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetResource getByGroupId_PrevAndNext(Session session,
		LayoutSetResource layoutSetResource, long groupId,
		OrderByComparator<LayoutSetResource> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LayoutSetResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetResource)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set resources where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutSetResource layoutSetResource : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetResource);
		}
	}

	/**
	 * Returns the number of layout set resources where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout set resources
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutSetResource.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LayoutSetResourceModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutSetResourceModelImpl.PRIVATELAYOUT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or throws a {@link NoSuchLayoutSetResourceException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout set resource
	 * @throws NoSuchLayoutSetResourceException if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource findByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = fetchByG_P(groupId, privateLayout);

		if (layoutSetResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutSetResourceException(msg.toString());
		}

		return layoutSetResource;
	}

	/**
	 * Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource fetchByG_P(long groupId, boolean privateLayout) {
		return fetchByG_P(groupId, privateLayout, true);
	}

	/**
	 * Returns the layout set resource where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource fetchByG_P(long groupId, boolean privateLayout,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P,
					finderArgs, this);
		}

		if (result instanceof LayoutSetResource) {
			LayoutSetResource layoutSetResource = (LayoutSetResource)result;

			if ((groupId != layoutSetResource.getGroupId()) ||
					(privateLayout != layoutSetResource.isPrivateLayout())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTSETRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				List<LayoutSetResource> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
						finderArgs, list);
				}
				else {
					LayoutSetResource layoutSetResource = list.get(0);

					result = layoutSetResource;

					cacheResult(layoutSetResource);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutSetResource)result;
		}
	}

	/**
	 * Removes the layout set resource where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the layout set resource that was removed
	 */
	@Override
	public LayoutSetResource removeByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = findByG_P(groupId, privateLayout);

		return remove(layoutSetResource);
	}

	/**
	 * Returns the number of layout set resources where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout set resources
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P;

		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layoutSetResource.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layoutSetResource.privateLayout = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LayoutSetResourceModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutSetResourceModelImpl.PRIVATELAYOUT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the layout set resources where companyId = &#63; and privateLayout = &#63;.
	 *
	 * @param companyId the company ID
	 * @param privateLayout the private layout
	 * @return the matching layout set resources
	 */
	@Override
	public List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout) {
		return findByC_P(companyId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end) {
		return findByC_P(companyId, privateLayout, start, end, null);
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
	@Override
	public List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return findByC_P(companyId, privateLayout, start, end,
			orderByComparator, true);
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
	@Override
	public List<LayoutSetResource> findByC_P(long companyId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] { companyId, privateLayout };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] {
					companyId, privateLayout,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetResource> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetResource>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetResource layoutSetResource : list) {
					if ((companyId != layoutSetResource.getCompanyId()) ||
							(privateLayout != layoutSetResource.isPrivateLayout())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTSETRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetResourceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(privateLayout);

				if (!pagination) {
					list = (List<LayoutSetResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetResource>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public LayoutSetResource findByC_P_First(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = fetchByC_P_First(companyId,
				privateLayout, orderByComparator);

		if (layoutSetResource != null) {
			return layoutSetResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutSetResourceException(msg.toString());
	}

	/**
	 * Returns the first layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	 *
	 * @param companyId the company ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource fetchByC_P_First(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		List<LayoutSetResource> list = findByC_P(companyId, privateLayout, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public LayoutSetResource findByC_P_Last(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = fetchByC_P_Last(companyId,
				privateLayout, orderByComparator);

		if (layoutSetResource != null) {
			return layoutSetResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutSetResourceException(msg.toString());
	}

	/**
	 * Returns the last layout set resource in the ordered set where companyId = &#63; and privateLayout = &#63;.
	 *
	 * @param companyId the company ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set resource, or <code>null</code> if a matching layout set resource could not be found
	 */
	@Override
	public LayoutSetResource fetchByC_P_Last(long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		int count = countByC_P(companyId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<LayoutSetResource> list = findByC_P(companyId, privateLayout,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public LayoutSetResource[] findByC_P_PrevAndNext(long layoutSetResourceId,
		long companyId, boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = findByPrimaryKey(layoutSetResourceId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetResource[] array = new LayoutSetResourceImpl[3];

			array[0] = getByC_P_PrevAndNext(session, layoutSetResource,
					companyId, privateLayout, orderByComparator, true);

			array[1] = layoutSetResource;

			array[2] = getByC_P_PrevAndNext(session, layoutSetResource,
					companyId, privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetResource getByC_P_PrevAndNext(Session session,
		LayoutSetResource layoutSetResource, long companyId,
		boolean privateLayout,
		OrderByComparator<LayoutSetResource> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTSETRESOURCE_WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PRIVATELAYOUT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LayoutSetResourceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetResource)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set resources where companyId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByC_P(long companyId, boolean privateLayout) {
		for (LayoutSetResource layoutSetResource : findByC_P(companyId,
				privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetResource);
		}
	}

	/**
	 * Returns the number of layout set resources where companyId = &#63; and privateLayout = &#63;.
	 *
	 * @param companyId the company ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout set resources
	 */
	@Override
	public int countByC_P(long companyId, boolean privateLayout) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_P;

		Object[] finderArgs = new Object[] { companyId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETRESOURCE_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(privateLayout);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "layoutSetResource.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PRIVATELAYOUT_2 = "layoutSetResource.privateLayout = ?";

	public LayoutSetResourcePersistenceImpl() {
		setModelClass(LayoutSetResource.class);

		setModelImplClass(LayoutSetResourceImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout set resource in the entity cache if it is enabled.
	 *
	 * @param layoutSetResource the layout set resource
	 */
	@Override
	public void cacheResult(LayoutSetResource layoutSetResource) {
		EntityCacheUtil.putResult(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceImpl.class, layoutSetResource.getPrimaryKey(),
			layoutSetResource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
			new Object[] {
				layoutSetResource.getGroupId(),
				layoutSetResource.isPrivateLayout()
			}, layoutSetResource);

		layoutSetResource.resetOriginalValues();
	}

	/**
	 * Caches the layout set resources in the entity cache if it is enabled.
	 *
	 * @param layoutSetResources the layout set resources
	 */
	@Override
	public void cacheResult(List<LayoutSetResource> layoutSetResources) {
		for (LayoutSetResource layoutSetResource : layoutSetResources) {
			if (EntityCacheUtil.getResult(
						LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetResourceImpl.class,
						layoutSetResource.getPrimaryKey()) == null) {
				cacheResult(layoutSetResource);
			}
			else {
				layoutSetResource.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout set resources.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutSetResourceImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout set resource.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSetResource layoutSetResource) {
		EntityCacheUtil.removeResult(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceImpl.class, layoutSetResource.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutSetResourceModelImpl)layoutSetResource,
			true);
	}

	@Override
	public void clearCache(List<LayoutSetResource> layoutSetResources) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSetResource layoutSetResource : layoutSetResources) {
			EntityCacheUtil.removeResult(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetResourceImpl.class, layoutSetResource.getPrimaryKey());

			clearUniqueFindersCache((LayoutSetResourceModelImpl)layoutSetResource,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSetResourceModelImpl layoutSetResourceModelImpl) {
		Object[] args = new Object[] {
				layoutSetResourceModelImpl.getGroupId(),
				layoutSetResourceModelImpl.isPrivateLayout()
			};

		FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P, args,
			layoutSetResourceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutSetResourceModelImpl layoutSetResourceModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutSetResourceModelImpl.getGroupId(),
					layoutSetResourceModelImpl.isPrivateLayout()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P, args);
		}

		if ((layoutSetResourceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_P.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutSetResourceModelImpl.getOriginalGroupId(),
					layoutSetResourceModelImpl.getOriginalPrivateLayout()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P, args);
		}
	}

	/**
	 * Creates a new layout set resource with the primary key. Does not add the layout set resource to the database.
	 *
	 * @param layoutSetResourceId the primary key for the new layout set resource
	 * @return the new layout set resource
	 */
	@Override
	public LayoutSetResource create(long layoutSetResourceId) {
		LayoutSetResource layoutSetResource = new LayoutSetResourceImpl();

		layoutSetResource.setNew(true);
		layoutSetResource.setPrimaryKey(layoutSetResourceId);

		layoutSetResource.setCompanyId(companyProvider.getCompanyId());

		return layoutSetResource;
	}

	/**
	 * Removes the layout set resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetResourceId the primary key of the layout set resource
	 * @return the layout set resource that was removed
	 * @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	 */
	@Override
	public LayoutSetResource remove(long layoutSetResourceId)
		throws NoSuchLayoutSetResourceException {
		return remove((Serializable)layoutSetResourceId);
	}

	/**
	 * Removes the layout set resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout set resource
	 * @return the layout set resource that was removed
	 * @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	 */
	@Override
	public LayoutSetResource remove(Serializable primaryKey)
		throws NoSuchLayoutSetResourceException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetResource layoutSetResource = (LayoutSetResource)session.get(LayoutSetResourceImpl.class,
					primaryKey);

			if (layoutSetResource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutSetResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutSetResource);
		}
		catch (NoSuchLayoutSetResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LayoutSetResource removeImpl(LayoutSetResource layoutSetResource) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSetResource)) {
				layoutSetResource = (LayoutSetResource)session.get(LayoutSetResourceImpl.class,
						layoutSetResource.getPrimaryKeyObj());
			}

			if (layoutSetResource != null) {
				session.delete(layoutSetResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSetResource != null) {
			clearCache(layoutSetResource);
		}

		return layoutSetResource;
	}

	@Override
	public LayoutSetResource updateImpl(LayoutSetResource layoutSetResource) {
		boolean isNew = layoutSetResource.isNew();

		if (!(layoutSetResource instanceof LayoutSetResourceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSetResource.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layoutSetResource);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSetResource proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSetResource implementation " +
				layoutSetResource.getClass());
		}

		LayoutSetResourceModelImpl layoutSetResourceModelImpl = (LayoutSetResourceModelImpl)layoutSetResource;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutSetResource.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSetResource.setCreateDate(now);
			}
			else {
				layoutSetResource.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutSetResourceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSetResource.setModifiedDate(now);
			}
			else {
				layoutSetResource.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSetResource.isNew()) {
				session.save(layoutSetResource);

				layoutSetResource.setNew(false);
			}
			else {
				layoutSetResource = (LayoutSetResource)session.merge(layoutSetResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutSetResourceModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { layoutSetResourceModelImpl.getGroupId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					layoutSetResourceModelImpl.getCompanyId(),
					layoutSetResourceModelImpl.isPrivateLayout()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
			FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
				args);

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutSetResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetResourceModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { layoutSetResourceModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((layoutSetResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetResourceModelImpl.getOriginalCompanyId(),
						layoutSetResourceModelImpl.getOriginalPrivateLayout()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);

				args = new Object[] {
						layoutSetResourceModelImpl.getCompanyId(),
						layoutSetResourceModelImpl.isPrivateLayout()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutSetResourceModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetResourceImpl.class, layoutSetResource.getPrimaryKey(),
			layoutSetResource, false);

		clearUniqueFindersCache(layoutSetResourceModelImpl, false);
		cacheUniqueFindersCache(layoutSetResourceModelImpl);

		layoutSetResource.resetOriginalValues();

		return layoutSetResource;
	}

	/**
	 * Returns the layout set resource with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set resource
	 * @return the layout set resource
	 * @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	 */
	@Override
	public LayoutSetResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutSetResourceException {
		LayoutSetResource layoutSetResource = fetchByPrimaryKey(primaryKey);

		if (layoutSetResource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutSetResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutSetResource;
	}

	/**
	 * Returns the layout set resource with the primary key or throws a {@link NoSuchLayoutSetResourceException} if it could not be found.
	 *
	 * @param layoutSetResourceId the primary key of the layout set resource
	 * @return the layout set resource
	 * @throws NoSuchLayoutSetResourceException if a layout set resource with the primary key could not be found
	 */
	@Override
	public LayoutSetResource findByPrimaryKey(long layoutSetResourceId)
		throws NoSuchLayoutSetResourceException {
		return findByPrimaryKey((Serializable)layoutSetResourceId);
	}

	/**
	 * Returns the layout set resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSetResourceId the primary key of the layout set resource
	 * @return the layout set resource, or <code>null</code> if a layout set resource with the primary key could not be found
	 */
	@Override
	public LayoutSetResource fetchByPrimaryKey(long layoutSetResourceId) {
		return fetchByPrimaryKey((Serializable)layoutSetResourceId);
	}

	/**
	 * Returns all the layout set resources.
	 *
	 * @return the layout set resources
	 */
	@Override
	public List<LayoutSetResource> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutSetResource> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<LayoutSetResource> findAll(int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<LayoutSetResource> findAll(int start, int end,
		OrderByComparator<LayoutSetResource> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LayoutSetResource> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetResource>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTSETRESOURCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSETRESOURCE;

				if (pagination) {
					sql = sql.concat(LayoutSetResourceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSetResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetResource>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout set resources from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSetResource layoutSetResource : findAll()) {
			remove(layoutSetResource);
		}
	}

	/**
	 * Returns the number of layout set resources.
	 *
	 * @return the number of layout set resources
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSETRESOURCE);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutSetResourceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSETRESOURCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutSetResourceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout set resource persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutSetResourceImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_LAYOUTSETRESOURCE = "SELECT layoutSetResource FROM LayoutSetResource layoutSetResource";
	private static final String _SQL_SELECT_LAYOUTSETRESOURCE_WHERE = "SELECT layoutSetResource FROM LayoutSetResource layoutSetResource WHERE ";
	private static final String _SQL_COUNT_LAYOUTSETRESOURCE = "SELECT COUNT(layoutSetResource) FROM LayoutSetResource layoutSetResource";
	private static final String _SQL_COUNT_LAYOUTSETRESOURCE_WHERE = "SELECT COUNT(layoutSetResource) FROM LayoutSetResource layoutSetResource WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSetResource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSetResource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSetResource exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutSetResourcePersistenceImpl.class);
}