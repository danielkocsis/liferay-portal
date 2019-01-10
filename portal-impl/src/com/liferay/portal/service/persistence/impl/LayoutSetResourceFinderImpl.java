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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutSetResource;
import com.liferay.portal.kernel.service.persistence.LayoutSetResourceFinder;
import com.liferay.portal.model.impl.LayoutSetResourceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class LayoutSetResourceFinderImpl
	extends LayoutSetResourceFinderBaseImpl implements LayoutSetResourceFinder {

	public static final String COUNT_BY_LAYOUT_SET_PROTOTYPE_UUID =
		LayoutSetResourceFinder.class.getName() + ".countByLayoutSetPrototypeUuid";

	public static final String FIND_BY_P_L =
		LayoutSetResourceFinder.class.getName() + ".findByP_L";

	@Override
	public int countByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_LAYOUT_SET_PROTOTYPE_UUID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(layoutSetPrototypeUuid);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<LayoutSetResource> findByP_L(
		boolean privateLayout, long logoId) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_P_L);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("LayoutSetResource", LayoutSetResourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(privateLayout);
			qPos.add(logoId);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}