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
import com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.LayoutSetVersionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.LayoutSetVersionImpl;
import com.liferay.portal.model.impl.LayoutSetVersionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout set version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetVersionPersistence
 * @see com.liferay.portal.kernel.service.persistence.LayoutSetVersionUtil
 * @generated
 */
@ProviderType
public class LayoutSetVersionPersistenceImpl extends BasePersistenceImpl<LayoutSetVersion>
	implements LayoutSetVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutSetVersionUtil} to access the layout set version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID =
		new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutSetResourceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID =
		new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetResourceId", new String[] { Long.class.getName() },
			LayoutSetVersionModelImpl.LAYOUTSETRESOURCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTSETRESOURCEID = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetResourceId", new String[] { Long.class.getName() });

	/**
	 * Returns all the layout set versions where layoutSetResourceId = &#63;.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId) {
		return findByLayoutSetResourceId(layoutSetResourceId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end) {
		return findByLayoutSetResourceId(layoutSetResourceId, start, end, null);
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
	@Override
	public List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByLayoutSetResourceId(layoutSetResourceId, start, end,
			orderByComparator, true);
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
	@Override
	public List<LayoutSetVersion> findByLayoutSetResourceId(
		long layoutSetResourceId, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID;
			finderArgs = new Object[] { layoutSetResourceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID;
			finderArgs = new Object[] {
					layoutSetResourceId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((layoutSetResourceId != layoutSetVersion.getLayoutSetResourceId())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETRESOURCEID_LAYOUTSETRESOURCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetResourceId);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where layoutSetResourceId = &#63;.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetResourceId_First(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetResourceId_First(layoutSetResourceId,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetResourceId=");
		msg.append(layoutSetResourceId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where layoutSetResourceId = &#63;.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetResourceId_First(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByLayoutSetResourceId(layoutSetResourceId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetResourceId = &#63;.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetResourceId_Last(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetResourceId_Last(layoutSetResourceId,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetResourceId=");
		msg.append(layoutSetResourceId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetResourceId = &#63;.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetResourceId_Last(
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByLayoutSetResourceId(layoutSetResourceId);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByLayoutSetResourceId(layoutSetResourceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public LayoutSetVersion[] findByLayoutSetResourceId_PrevAndNext(
		long layoutSetVersionId, long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByLayoutSetResourceId_PrevAndNext(session,
					layoutSetVersion, layoutSetResourceId, orderByComparator,
					true);

			array[1] = layoutSetVersion;

			array[2] = getByLayoutSetResourceId_PrevAndNext(session,
					layoutSetVersion, layoutSetResourceId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByLayoutSetResourceId_PrevAndNext(
		Session session, LayoutSetVersion layoutSetVersion,
		long layoutSetResourceId,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETRESOURCEID_LAYOUTSETRESOURCEID_2);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetResourceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where layoutSetResourceId = &#63; from the database.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 */
	@Override
	public void removeByLayoutSetResourceId(long layoutSetResourceId) {
		for (LayoutSetVersion layoutSetVersion : findByLayoutSetResourceId(
				layoutSetResourceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where layoutSetResourceId = &#63;.
	 *
	 * @param layoutSetResourceId the layout set resource ID
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByLayoutSetResourceId(long layoutSetResourceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTSETRESOURCEID;

		Object[] finderArgs = new Object[] { layoutSetResourceId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETRESOURCEID_LAYOUTSETRESOURCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetResourceId);

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

	private static final String _FINDER_COLUMN_LAYOUTSETRESOURCEID_LAYOUTSETRESOURCEID_2 =
		"layoutSetVersion.layoutSetResourceId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID =
		new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutSetPrototypeUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID =
		new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetPrototypeUuid",
			new String[] { String.class.getName() },
			LayoutSetVersionModelImpl.LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTSETPROTOTYPEUUID = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetPrototypeUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end) {
		return findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start, end,
			null);
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
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start, end,
			orderByComparator, true);
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
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID;
			finderArgs = new Object[] { layoutSetPrototypeUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID;
			finderArgs = new Object[] {
					layoutSetPrototypeUuid,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if (!layoutSetPrototypeUuid.equals(
								layoutSetVersion.getLayoutSetPrototypeUuid())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			boolean bindLayoutSetPrototypeUuid = false;

			if (layoutSetPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3);
			}
			else {
				bindLayoutSetPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutSetPrototypeUuid) {
					qPos.add(layoutSetPrototypeUuid);
				}

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetPrototypeUuid_First(layoutSetPrototypeUuid,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetPrototypeUuid=");
		msg.append(layoutSetPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetPrototypeUuid_Last(layoutSetPrototypeUuid,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetPrototypeUuid=");
		msg.append(layoutSetPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public LayoutSetVersion[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByLayoutSetPrototypeUuid_PrevAndNext(session,
					layoutSetVersion, layoutSetPrototypeUuid,
					orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByLayoutSetPrototypeUuid_PrevAndNext(session,
					layoutSetVersion, layoutSetPrototypeUuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByLayoutSetPrototypeUuid_PrevAndNext(
		Session session, LayoutSetVersion layoutSetVersion,
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		boolean bindLayoutSetPrototypeUuid = false;

		if (layoutSetPrototypeUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3);
		}
		else {
			bindLayoutSetPrototypeUuid = true;

			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2);
		}

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutSetPrototypeUuid) {
			qPos.add(layoutSetPrototypeUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where layoutSetPrototypeUuid = &#63; from the database.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 */
	@Override
	public void removeByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		for (LayoutSetVersion layoutSetVersion : findByLayoutSetPrototypeUuid(
				layoutSetPrototypeUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTSETPROTOTYPEUUID;

		Object[] finderArgs = new Object[] { layoutSetPrototypeUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			boolean bindLayoutSetPrototypeUuid = false;

			if (layoutSetPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3);
			}
			else {
				bindLayoutSetPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutSetPrototypeUuid) {
					qPos.add(layoutSetPrototypeUuid);
				}

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

	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2 =
		"layoutSetVersion.layoutSetPrototypeUuid = ?";
	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3 =
		"(layoutSetVersion.layoutSetPrototypeUuid IS NULL OR layoutSetVersion.layoutSetPrototypeUuid = '')";

	public LayoutSetVersionPersistenceImpl() {
		setModelClass(LayoutSetVersion.class);

		setModelImplClass(LayoutSetVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout set version in the entity cache if it is enabled.
	 *
	 * @param layoutSetVersion the layout set version
	 */
	@Override
	public void cacheResult(LayoutSetVersion layoutSetVersion) {
		EntityCacheUtil.putResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey(),
			layoutSetVersion);

		layoutSetVersion.resetOriginalValues();
	}

	/**
	 * Caches the layout set versions in the entity cache if it is enabled.
	 *
	 * @param layoutSetVersions the layout set versions
	 */
	@Override
	public void cacheResult(List<LayoutSetVersion> layoutSetVersions) {
		for (LayoutSetVersion layoutSetVersion : layoutSetVersions) {
			if (EntityCacheUtil.getResult(
						LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetVersionImpl.class,
						layoutSetVersion.getPrimaryKey()) == null) {
				cacheResult(layoutSetVersion);
			}
			else {
				layoutSetVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout set versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutSetVersionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout set version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSetVersion layoutSetVersion) {
		EntityCacheUtil.removeResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LayoutSetVersion> layoutSetVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSetVersion layoutSetVersion : layoutSetVersions) {
			EntityCacheUtil.removeResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new layout set version with the primary key. Does not add the layout set version to the database.
	 *
	 * @param layoutSetVersionId the primary key for the new layout set version
	 * @return the new layout set version
	 */
	@Override
	public LayoutSetVersion create(long layoutSetVersionId) {
		LayoutSetVersion layoutSetVersion = new LayoutSetVersionImpl();

		layoutSetVersion.setNew(true);
		layoutSetVersion.setPrimaryKey(layoutSetVersionId);

		layoutSetVersion.setCompanyId(companyProvider.getCompanyId());

		return layoutSetVersion;
	}

	/**
	 * Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetVersionId the primary key of the layout set version
	 * @return the layout set version that was removed
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion remove(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException {
		return remove((Serializable)layoutSetVersionId);
	}

	/**
	 * Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout set version
	 * @return the layout set version that was removed
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion remove(Serializable primaryKey)
		throws NoSuchLayoutSetVersionException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion layoutSetVersion = (LayoutSetVersion)session.get(LayoutSetVersionImpl.class,
					primaryKey);

			if (layoutSetVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutSetVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutSetVersion);
		}
		catch (NoSuchLayoutSetVersionException nsee) {
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
	protected LayoutSetVersion removeImpl(LayoutSetVersion layoutSetVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSetVersion)) {
				layoutSetVersion = (LayoutSetVersion)session.get(LayoutSetVersionImpl.class,
						layoutSetVersion.getPrimaryKeyObj());
			}

			if (layoutSetVersion != null) {
				session.delete(layoutSetVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSetVersion != null) {
			clearCache(layoutSetVersion);
		}

		return layoutSetVersion;
	}

	@Override
	public LayoutSetVersion updateImpl(LayoutSetVersion layoutSetVersion) {
		boolean isNew = layoutSetVersion.isNew();

		if (!(layoutSetVersion instanceof LayoutSetVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSetVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layoutSetVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSetVersion proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSetVersion implementation " +
				layoutSetVersion.getClass());
		}

		LayoutSetVersionModelImpl layoutSetVersionModelImpl = (LayoutSetVersionModelImpl)layoutSetVersion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutSetVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSetVersion.setCreateDate(now);
			}
			else {
				layoutSetVersion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutSetVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSetVersion.setModifiedDate(now);
			}
			else {
				layoutSetVersion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSetVersion.isNew()) {
				session.save(layoutSetVersion);

				layoutSetVersion.setNew(false);
			}
			else {
				layoutSetVersion = (LayoutSetVersion)session.merge(layoutSetVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutSetVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutSetVersionModelImpl.getLayoutSetResourceId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETRESOURCEID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.getLayoutSetPrototypeUuid()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETPROTOTYPEUUID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID,
				args);

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalLayoutSetResourceId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETRESOURCEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.getLayoutSetResourceId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETRESOURCEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETRESOURCEID,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalLayoutSetPrototypeUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETPROTOTYPEUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.getLayoutSetPrototypeUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETPROTOTYPEUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETPROTOTYPEUUID,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey(),
			layoutSetVersion, false);

		layoutSetVersion.resetOriginalValues();

		return layoutSetVersion;
	}

	/**
	 * Returns the layout set version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set version
	 * @return the layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByPrimaryKey(primaryKey);

		if (layoutSetVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutSetVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutSetVersion;
	}

	/**
	 * Returns the layout set version with the primary key or throws a {@link NoSuchLayoutSetVersionException} if it could not be found.
	 *
	 * @param layoutSetVersionId the primary key of the layout set version
	 * @return the layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion findByPrimaryKey(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException {
		return findByPrimaryKey((Serializable)layoutSetVersionId);
	}

	/**
	 * Returns the layout set version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSetVersionId the primary key of the layout set version
	 * @return the layout set version, or <code>null</code> if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion fetchByPrimaryKey(long layoutSetVersionId) {
		return fetchByPrimaryKey((Serializable)layoutSetVersionId);
	}

	/**
	 * Returns all the layout set versions.
	 *
	 * @return the layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutSetVersion> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
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

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTSETVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSETVERSION;

				if (pagination) {
					sql = sql.concat(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Removes all the layout set versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSetVersion layoutSetVersion : findAll()) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions.
	 *
	 * @return the number of layout set versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSETVERSION);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutSetVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSETVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutSetVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout set version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutSetVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_LAYOUTSETVERSION = "SELECT layoutSetVersion FROM LayoutSetVersion layoutSetVersion";
	private static final String _SQL_SELECT_LAYOUTSETVERSION_WHERE = "SELECT layoutSetVersion FROM LayoutSetVersion layoutSetVersion WHERE ";
	private static final String _SQL_COUNT_LAYOUTSETVERSION = "SELECT COUNT(layoutSetVersion) FROM LayoutSetVersion layoutSetVersion";
	private static final String _SQL_COUNT_LAYOUTSETVERSION_WHERE = "SELECT COUNT(layoutSetVersion) FROM LayoutSetVersion layoutSetVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSetVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSetVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSetVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutSetVersionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"settings"
			});
}