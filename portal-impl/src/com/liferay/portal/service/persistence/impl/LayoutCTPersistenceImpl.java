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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchLayoutCTException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutCT;
import com.liferay.portal.kernel.service.persistence.LayoutCTPK;
import com.liferay.portal.kernel.service.persistence.LayoutCTPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.LayoutCTImpl;
import com.liferay.portal.model.impl.LayoutCTModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the layout ct service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutCTPersistenceImpl
	extends BasePersistenceImpl<LayoutCT> implements LayoutCTPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutCTUtil</code> to access the layout ct persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutCTImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByClassPK;
	private FinderPath _finderPathWithoutPaginationFindByClassPK;
	private FinderPath _finderPathCountByClassPK;

	/**
	 * Returns all the layout cts where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the matching layout cts
	 */
	@Override
	public List<LayoutCT> findByClassPK(long classPK) {
		return findByClassPK(
			classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout cts where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByClassPK(long classPK, int start, int end) {
		return findByClassPK(classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout cts where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByClassPK(
		long classPK, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return findByClassPK(classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout cts where classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByClassPK(
		long classPK, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByClassPK;
			finderArgs = new Object[] {classPK};
		}
		else {
			finderPath = _finderPathWithPaginationFindByClassPK;
			finderArgs = new Object[] {classPK, start, end, orderByComparator};
		}

		List<LayoutCT> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutCT>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutCT layoutCT : list) {
					if ((classPK != layoutCT.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTCT_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT findByClassPK_First(
			long classPK, OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByClassPK_First(classPK, orderByComparator);

		if (layoutCT != null) {
			return layoutCT;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchLayoutCTException(msg.toString());
	}

	/**
	 * Returns the first layout ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT fetchByClassPK_First(
		long classPK, OrderByComparator<LayoutCT> orderByComparator) {

		List<LayoutCT> list = findByClassPK(classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT findByClassPK_Last(
			long classPK, OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByClassPK_Last(classPK, orderByComparator);

		if (layoutCT != null) {
			return layoutCT;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchLayoutCTException(msg.toString());
	}

	/**
	 * Returns the last layout ct in the ordered set where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT fetchByClassPK_Last(
		long classPK, OrderByComparator<LayoutCT> orderByComparator) {

		int count = countByClassPK(classPK);

		if (count == 0) {
			return null;
		}

		List<LayoutCT> list = findByClassPK(
			classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where classPK = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT[] findByClassPK_PrevAndNext(
			LayoutCTPK layoutCTPK, long classPK,
			OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = findByPrimaryKey(layoutCTPK);

		Session session = null;

		try {
			session = openSession();

			LayoutCT[] array = new LayoutCTImpl[3];

			array[0] = getByClassPK_PrevAndNext(
				session, layoutCT, classPK, orderByComparator, true);

			array[1] = layoutCT;

			array[2] = getByClassPK_PrevAndNext(
				session, layoutCT, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutCT getByClassPK_PrevAndNext(
		Session session, LayoutCT layoutCT, long classPK,
		OrderByComparator<LayoutCT> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTCT_WHERE);

		query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(layoutCT)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutCT> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout cts where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 */
	@Override
	public void removeByClassPK(long classPK) {
		for (LayoutCT layoutCT :
				findByClassPK(
					classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutCT);
		}
	}

	/**
	 * Returns the number of layout cts where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching layout cts
	 */
	@Override
	public int countByClassPK(long classPK) {
		FinderPath finderPath = _finderPathCountByClassPK;

		Object[] finderArgs = new Object[] {classPK};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTCT_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 =
		"layoutCT.id.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByCTCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCTCollectionId;
	private FinderPath _finderPathCountByCTCollectionId;

	/**
	 * Returns all the layout cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	@Override
	public List<LayoutCT> findByCTCollectionId(long ctCollectionId) {
		return findByCTCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCTCollectionId(ctCollectionId, start, end, null);
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
	@Override
	public List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
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
	@Override
	public List<LayoutCT> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCTCollectionId;
			finderArgs = new Object[] {ctCollectionId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByCTCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<LayoutCT> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutCT>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutCT layoutCT : list) {
					if ((ctCollectionId != layoutCT.getCtCollectionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTCT_WHERE);

			query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				if (!pagination) {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT findByCTCollectionId_First(
			long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);

		if (layoutCT != null) {
			return layoutCT;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchLayoutCTException(msg.toString());
	}

	/**
	 * Returns the first layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT fetchByCTCollectionId_First(
		long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator) {

		List<LayoutCT> list = findByCTCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT findByCTCollectionId_Last(
			long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (layoutCT != null) {
			return layoutCT;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchLayoutCTException(msg.toString());
	}

	/**
	 * Returns the last layout ct in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT fetchByCTCollectionId_Last(
		long ctCollectionId, OrderByComparator<LayoutCT> orderByComparator) {

		int count = countByCTCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<LayoutCT> list = findByCTCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public LayoutCT[] findByCTCollectionId_PrevAndNext(
			LayoutCTPK layoutCTPK, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = findByPrimaryKey(layoutCTPK);

		Session session = null;

		try {
			session = openSession();

			LayoutCT[] array = new LayoutCTImpl[3];

			array[0] = getByCTCollectionId_PrevAndNext(
				session, layoutCT, ctCollectionId, orderByComparator, true);

			array[1] = layoutCT;

			array[2] = getByCTCollectionId_PrevAndNext(
				session, layoutCT, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutCT getByCTCollectionId_PrevAndNext(
		Session session, LayoutCT layoutCT, long ctCollectionId,
		OrderByComparator<LayoutCT> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTCT_WHERE);

		query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(layoutCT)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutCT> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout cts where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCTCollectionId(long ctCollectionId) {
		for (LayoutCT layoutCT :
				findByCTCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutCT);
		}
	}

	/**
	 * Returns the number of layout cts where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	@Override
	public int countByCTCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCTCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTCT_WHERE);

			query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"layoutCT.id.ctCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;
	private FinderPath _finderPathWithPaginationCountByC_C;

	/**
	 * Returns all the layout cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(long classPK, long ctCollectionId) {
		return findByC_C(
			classPK, ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end) {

		return findByC_C(classPK, ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return findByC_C(
			classPK, ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(
		long classPK, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_C;
			finderArgs = new Object[] {classPK, ctCollectionId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classPK, ctCollectionId, start, end, orderByComparator
			};
		}

		List<LayoutCT> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutCT>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutCT layoutCT : list) {
					if ((classPK != layoutCT.getClassPK()) ||
						(ctCollectionId != layoutCT.getCtCollectionId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTCT_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				qPos.add(ctCollectionId);

				if (!pagination) {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT findByC_C_First(
			long classPK, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByC_C_First(
			classPK, ctCollectionId, orderByComparator);

		if (layoutCT != null) {
			return layoutCT;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append(", ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchLayoutCTException(msg.toString());
	}

	/**
	 * Returns the first layout ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT fetchByC_C_First(
		long classPK, long ctCollectionId,
		OrderByComparator<LayoutCT> orderByComparator) {

		List<LayoutCT> list = findByC_C(
			classPK, ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct
	 * @throws NoSuchLayoutCTException if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT findByC_C_Last(
			long classPK, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByC_C_Last(
			classPK, ctCollectionId, orderByComparator);

		if (layoutCT != null) {
			return layoutCT;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classPK=");
		msg.append(classPK);

		msg.append(", ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchLayoutCTException(msg.toString());
	}

	/**
	 * Returns the last layout ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout ct, or <code>null</code> if a matching layout ct could not be found
	 */
	@Override
	public LayoutCT fetchByC_C_Last(
		long classPK, long ctCollectionId,
		OrderByComparator<LayoutCT> orderByComparator) {

		int count = countByC_C(classPK, ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<LayoutCT> list = findByC_C(
			classPK, ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout cts before and after the current layout ct in the ordered set where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param layoutCTPK the primary key of the current layout ct
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT[] findByC_C_PrevAndNext(
			LayoutCTPK layoutCTPK, long classPK, long ctCollectionId,
			OrderByComparator<LayoutCT> orderByComparator)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = findByPrimaryKey(layoutCTPK);

		Session session = null;

		try {
			session = openSession();

			LayoutCT[] array = new LayoutCTImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, layoutCT, classPK, ctCollectionId, orderByComparator,
				true);

			array[1] = layoutCT;

			array[2] = getByC_C_PrevAndNext(
				session, layoutCT, classPK, ctCollectionId, orderByComparator,
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

	protected LayoutCT getByC_C_PrevAndNext(
		Session session, LayoutCT layoutCT, long classPK, long ctCollectionId,
		OrderByComparator<LayoutCT> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTCT_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_CTCOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classPK);

		qPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(layoutCT)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutCT> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @return the matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(long[] classPKs, long ctCollectionId) {
		return findByC_C(
			classPKs, ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @return the range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end) {

		return findByC_C(classPKs, ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator) {

		return findByC_C(
			classPKs, ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout cts where classPK = &#63; and ctCollectionId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutCTModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of layout cts
	 * @param end the upper bound of the range of layout cts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout cts
	 */
	@Override
	public List<LayoutCT> findByC_C(
		long[] classPKs, long ctCollectionId, int start, int end,
		OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		if (classPKs == null) {
			classPKs = new long[0];
		}
		else if (classPKs.length > 1) {
			classPKs = ArrayUtil.sortedUnique(classPKs);
		}

		if (classPKs.length == 1) {
			return findByC_C(
				classPKs[0], ctCollectionId, start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderArgs = new Object[] {
				StringUtil.merge(classPKs), ctCollectionId
			};
		}
		else {
			finderArgs = new Object[] {
				StringUtil.merge(classPKs), ctCollectionId, start, end,
				orderByComparator
			};
		}

		List<LayoutCT> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutCT>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByC_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutCT layoutCT : list) {
					if (!ArrayUtil.contains(classPKs, layoutCT.getClassPK()) ||
						(ctCollectionId != layoutCT.getCtCollectionId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_LAYOUTCT_WHERE);

			if (classPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_C_CLASSPK_7);

				query.append(StringUtil.merge(classPKs));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_C_C_CTCOLLECTIONID_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutCTModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				if (!pagination) {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(
					_finderPathWithPaginationFindByC_C, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathWithPaginationFindByC_C, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout cts where classPK = &#63; and ctCollectionId = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByC_C(long classPK, long ctCollectionId) {
		for (LayoutCT layoutCT :
				findByC_C(
					classPK, ctCollectionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutCT);
		}
	}

	/**
	 * Returns the number of layout cts where classPK = &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPK the class pk
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	@Override
	public int countByC_C(long classPK, long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classPK, ctCollectionId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTCT_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_CTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				qPos.add(ctCollectionId);

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

	/**
	 * Returns the number of layout cts where classPK = any &#63; and ctCollectionId = &#63;.
	 *
	 * @param classPKs the class pks
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching layout cts
	 */
	@Override
	public int countByC_C(long[] classPKs, long ctCollectionId) {
		if (classPKs == null) {
			classPKs = new long[0];
		}
		else if (classPKs.length > 1) {
			classPKs = ArrayUtil.sortedUnique(classPKs);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(classPKs), ctCollectionId
		};

		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathWithPaginationCountByC_C, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_LAYOUTCT_WHERE);

			if (classPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_C_CLASSPK_7);

				query.append(StringUtil.merge(classPKs));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_C_C_CTCOLLECTIONID_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathWithPaginationCountByC_C, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathWithPaginationCountByC_C, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"layoutCT.id.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_7 =
		"layoutCT.id.classPK IN (";

	private static final String _FINDER_COLUMN_C_C_CTCOLLECTIONID_2 =
		"layoutCT.id.ctCollectionId = ?";

	public LayoutCTPersistenceImpl() {
		setModelClass(LayoutCT.class);

		setModelImplClass(LayoutCTImpl.class);
		setModelPKClass(LayoutCTPK.class);
		setEntityCacheEnabled(LayoutCTModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout ct in the entity cache if it is enabled.
	 *
	 * @param layoutCT the layout ct
	 */
	@Override
	public void cacheResult(LayoutCT layoutCT) {
		EntityCacheUtil.putResult(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED, LayoutCTImpl.class,
			layoutCT.getPrimaryKey(), layoutCT);

		layoutCT.resetOriginalValues();
	}

	/**
	 * Caches the layout cts in the entity cache if it is enabled.
	 *
	 * @param layoutCTs the layout cts
	 */
	@Override
	public void cacheResult(List<LayoutCT> layoutCTs) {
		for (LayoutCT layoutCT : layoutCTs) {
			if (EntityCacheUtil.getResult(
					LayoutCTModelImpl.ENTITY_CACHE_ENABLED, LayoutCTImpl.class,
					layoutCT.getPrimaryKey()) == null) {

				cacheResult(layoutCT);
			}
			else {
				layoutCT.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout cts.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutCTImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout ct.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutCT layoutCT) {
		EntityCacheUtil.removeResult(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED, LayoutCTImpl.class,
			layoutCT.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LayoutCT> layoutCTs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutCT layoutCT : layoutCTs) {
			EntityCacheUtil.removeResult(
				LayoutCTModelImpl.ENTITY_CACHE_ENABLED, LayoutCTImpl.class,
				layoutCT.getPrimaryKey());
		}
	}

	/**
	 * Creates a new layout ct with the primary key. Does not add the layout ct to the database.
	 *
	 * @param layoutCTPK the primary key for the new layout ct
	 * @return the new layout ct
	 */
	@Override
	public LayoutCT create(LayoutCTPK layoutCTPK) {
		LayoutCT layoutCT = new LayoutCTImpl();

		layoutCT.setNew(true);
		layoutCT.setPrimaryKey(layoutCTPK);

		return layoutCT;
	}

	/**
	 * Removes the layout ct with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct that was removed
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT remove(LayoutCTPK layoutCTPK)
		throws NoSuchLayoutCTException {

		return remove((Serializable)layoutCTPK);
	}

	/**
	 * Removes the layout ct with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout ct
	 * @return the layout ct that was removed
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT remove(Serializable primaryKey)
		throws NoSuchLayoutCTException {

		Session session = null;

		try {
			session = openSession();

			LayoutCT layoutCT = (LayoutCT)session.get(
				LayoutCTImpl.class, primaryKey);

			if (layoutCT == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutCTException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutCT);
		}
		catch (NoSuchLayoutCTException nsee) {
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
	protected LayoutCT removeImpl(LayoutCT layoutCT) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutCT)) {
				layoutCT = (LayoutCT)session.get(
					LayoutCTImpl.class, layoutCT.getPrimaryKeyObj());
			}

			if (layoutCT != null) {
				session.delete(layoutCT);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutCT != null) {
			clearCache(layoutCT);
		}

		return layoutCT;
	}

	@Override
	public LayoutCT updateImpl(LayoutCT layoutCT) {
		boolean isNew = layoutCT.isNew();

		if (!(layoutCT instanceof LayoutCTModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutCT.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layoutCT);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutCT proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutCT implementation " +
					layoutCT.getClass());
		}

		LayoutCTModelImpl layoutCTModelImpl = (LayoutCTModelImpl)layoutCT;

		Session session = null;

		try {
			session = openSession();

			if (layoutCT.isNew()) {
				session.save(layoutCT);

				layoutCT.setNew(false);
			}
			else {
				layoutCT = (LayoutCT)session.merge(layoutCT);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutCTModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {layoutCTModelImpl.getClassPK()};

			FinderCacheUtil.removeResult(_finderPathCountByClassPK, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByClassPK, args);

			args = new Object[] {layoutCTModelImpl.getCtCollectionId()};

			FinderCacheUtil.removeResult(
				_finderPathCountByCTCollectionId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCTCollectionId, args);

			args = new Object[] {
				layoutCTModelImpl.getClassPK(),
				layoutCTModelImpl.getCtCollectionId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutCTModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByClassPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutCTModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByClassPK, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByClassPK, args);

				args = new Object[] {layoutCTModelImpl.getClassPK()};

				FinderCacheUtil.removeResult(_finderPathCountByClassPK, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByClassPK, args);
			}

			if ((layoutCTModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCTCollectionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutCTModelImpl.getOriginalCtCollectionId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByCTCollectionId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCTCollectionId, args);

				args = new Object[] {layoutCTModelImpl.getCtCollectionId()};

				FinderCacheUtil.removeResult(
					_finderPathCountByCTCollectionId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCTCollectionId, args);
			}

			if ((layoutCTModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutCTModelImpl.getOriginalClassPK(),
					layoutCTModelImpl.getOriginalCtCollectionId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					layoutCTModelImpl.getClassPK(),
					layoutCTModelImpl.getCtCollectionId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}
		}

		EntityCacheUtil.putResult(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED, LayoutCTImpl.class,
			layoutCT.getPrimaryKey(), layoutCT, false);

		layoutCT.resetOriginalValues();

		return layoutCT;
	}

	/**
	 * Returns the layout ct with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout ct
	 * @return the layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutCTException {

		LayoutCT layoutCT = fetchByPrimaryKey(primaryKey);

		if (layoutCT == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutCTException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutCT;
	}

	/**
	 * Returns the layout ct with the primary key or throws a <code>NoSuchLayoutCTException</code> if it could not be found.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct
	 * @throws NoSuchLayoutCTException if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT findByPrimaryKey(LayoutCTPK layoutCTPK)
		throws NoSuchLayoutCTException {

		return findByPrimaryKey((Serializable)layoutCTPK);
	}

	/**
	 * Returns the layout ct with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutCTPK the primary key of the layout ct
	 * @return the layout ct, or <code>null</code> if a layout ct with the primary key could not be found
	 */
	@Override
	public LayoutCT fetchByPrimaryKey(LayoutCTPK layoutCTPK) {
		return fetchByPrimaryKey((Serializable)layoutCTPK);
	}

	/**
	 * Returns all the layout cts.
	 *
	 * @return the layout cts
	 */
	@Override
	public List<LayoutCT> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<LayoutCT> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<LayoutCT> findAll(
		int start, int end, OrderByComparator<LayoutCT> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<LayoutCT> findAll(
		int start, int end, OrderByComparator<LayoutCT> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutCT> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutCT>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTCT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTCT;

				if (pagination) {
					sql = sql.concat(LayoutCTModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutCT>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the layout cts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutCT layoutCT : findAll()) {
			remove(layoutCT);
		}
	}

	/**
	 * Returns the number of layout cts.
	 *
	 * @return the number of layout cts
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTCT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getCompoundPKColumnNames() {
		return _compoundPKColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutCTPK";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTCT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutCTModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout ct persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByClassPK = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassPK",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByClassPK = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassPK",
			new String[] {Long.class.getName()},
			LayoutCTModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByClassPK = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassPK",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCTCollectionId = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCTCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCTCollectionId = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCTCollectionId",
			new String[] {Long.class.getName()},
			LayoutCTModelImpl.CTCOLLECTIONID_COLUMN_BITMASK);

		_finderPathCountByCTCollectionId = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCTCollectionId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, LayoutCTImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			LayoutCTModelImpl.CLASSPK_COLUMN_BITMASK |
			LayoutCTModelImpl.CTCOLLECTIONID_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationCountByC_C = new FinderPath(
			LayoutCTModelImpl.ENTITY_CACHE_ENABLED,
			LayoutCTModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutCTImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LAYOUTCT =
		"SELECT layoutCT FROM LayoutCT layoutCT";

	private static final String _SQL_SELECT_LAYOUTCT_WHERE =
		"SELECT layoutCT FROM LayoutCT layoutCT WHERE ";

	private static final String _SQL_COUNT_LAYOUTCT =
		"SELECT COUNT(layoutCT) FROM LayoutCT layoutCT";

	private static final String _SQL_COUNT_LAYOUTCT_WHERE =
		"SELECT COUNT(layoutCT) FROM LayoutCT layoutCT WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutCT.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutCT exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutCT exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCTPersistenceImpl.class);

	private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
		new String[] {"classPK", "ctCollectionId"});

}