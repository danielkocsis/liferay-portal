/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class UserServiceTest {

	@Test
	public void testAddUser() throws Exception {
		addUser();
	}

	@Test
	public void testDeleteUser() throws Exception {
		User user = addUser();

		UserServiceUtil.deleteUser(user.getUserId());
	}

	@Test
	public void testGetCompanyUsers() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		List<User> users = UserServiceUtil.getCompanyUsers(
			user.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testGetCompanyUsersCount() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		long usersCount = UserServiceUtil.getCompanyUsersCount(
			user.getCompanyId());

		Assert.assertTrue(usersCount > 0);
	}

	@Test
	public void testGetUser() throws Exception {
		User user = addUser();

		UserServiceUtil.getUserByEmailAddress(
			TestPropsValues.getCompanyId(), user.getEmailAddress());
	}

	@Test
	public void testGetUserByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User loadedUser = UserServiceUtil.getUserByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());

		Assert.assertNotNull(loadedUser);
	}

	@Test
	public void testGetUserByScreenName() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User loadedUser = UserServiceUtil.getUserByScreenName(
			user.getCompanyId(), user.getScreenName());

		Assert.assertNotNull(loadedUser);
	}

	@Test
	public void testGetUserIdByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		long userId = UserServiceUtil.getUserIdByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());

		Assert.assertEquals(user.getUserId(), userId);
	}

	@Test
	public void testGetUserIdByScreenName() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		long userId = UserServiceUtil.getUserIdByScreenName(
			user.getCompanyId(), user.getScreenName());

		Assert.assertEquals(user.getUserId(), userId);
	}

	@Test
	public void testHasRoleUser() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		boolean hasRole = UserServiceUtil.hasRoleUser(
			user.getCompanyId(), "Power user", user.getUserId(), false);

		Assert.assertTrue(hasRole);
	}

	protected User addUser() throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress =
			"UserServiceTest." + ServiceTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.addUser(
			TestPropsValues.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);
	}

}