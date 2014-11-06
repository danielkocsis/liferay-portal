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

package com.liferay.portal.dao.orm;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.CacheSizeAwareSession;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CacheSizeAwareSessionTest {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule(Propagation.REQUIRED);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	// This test doesn't fail in case of a regular Session without any
	// Session.clear() calls during the transaction what makes all attached
	// objects to detached

	@Test(expected = SystemException.class)
	public void testStaleObjectException() throws Exception {
		Session session = _sessionFactory.getCurrentSession();

		Assert.assertTrue(session instanceof CacheSizeAwareSession);

		Layout layout = LayoutTestUtil.addLayout(_group);

		Assert.assertFalse(session.contains(layout));

		layout.setName(RandomTestUtil.randomString());

		LayoutLocalServiceUtil.updateLayout(layout);

		Assert.assertFalse(session.contains(layout));

		layout.setName(RandomTestUtil.randomString());

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	@DeleteAfterTestRun
	private Group _group;

	private final SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}