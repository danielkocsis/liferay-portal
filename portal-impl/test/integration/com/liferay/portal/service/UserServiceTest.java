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

import com.liferay.portal.UserLockoutException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.util.Encryptor;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 * @author Mate Thurzo
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
	public void testAddDefaultAdminUser() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		String screenName = "DefaultAdmin";
		String emailAddress = "default.admin@" + _DEFAULT_COMPANY_MX;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "Default";
		String middleName = StringPool.BLANK;
		String lastName = "Admin";

		User adminUser = UserLocalServiceUtil.addDefaultAdminUser(
			companyId, screenName, emailAddress, locale, firstName, middleName,
			lastName);
	}

	@Test
	public void testAddUser() throws Exception {
		addUser();
	}

	@Test
	public void testAuthenticateByEmailAddress() throws Exception {
		User user = addUser();

		int status = UserLocalServiceUtil.authenticateByEmailAddress(
			user.getCompanyId(), user.getEmailAddress(), _DEFAULT_PASSWORD,
			null, null, null);

		Assert.assertEquals(Authenticator.SUCCESS, status);
	}

	@Test
	public void testAuthenticateByScreenname() throws Exception {
		User user = addUser();

		int status = UserLocalServiceUtil.authenticateByScreenName(
			user.getCompanyId(), user.getScreenName(), _DEFAULT_PASSWORD, null,
			null, null);

		Assert.assertEquals(Authenticator.SUCCESS, status);
	}

	@Test
	public void testAuthenticateByUserId() throws Exception {
		User user = addUser();

		int status = UserLocalServiceUtil.authenticateByUserId(
			user.getCompanyId(), user.getUserId(), _DEFAULT_PASSWORD, null,
			null, null);

		Assert.assertEquals(Authenticator.SUCCESS, status);
	}

	@Test
	public void testCheckLoginFailure() throws Exception {
		User user = addUser();

		UserLocalServiceUtil.checkLoginFailure(user);
	}

	@Test
	public void testCheckLoginFailureByEmailAddress() throws Exception {
		User user = addUser();

		UserLocalServiceUtil.checkLoginFailureByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());
	}

	@Test
	public void testCheckLoginFailureByScreenName() throws Exception {
		User user = addUser();

		UserLocalServiceUtil.checkLoginFailureByScreenName(
			user.getCompanyId(), user.getScreenName());
	}

	@Test
	public void testCheckPasswordExpired() throws Exception {
		User user = addUser();

		UserLocalServiceUtil.checkPasswordExpired(user);
	}

	@Test
	public void testCompleteUserRegistration() throws Exception {
		boolean autoPassword = true;
		boolean sendEmail = false;

		User user = addUser();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAttribute("autoPassword", autoPassword);
		serviceContext.setAttribute("sendEmail", sendEmail);

		UserLocalServiceUtil.completeUserRegistration(user, serviceContext);
	}

	@Test
	public void testDecryptUserId() throws Exception {
		User user = addUser();

		Company company = CompanyLocalServiceUtil.getCompany(
			user.getCompanyId());

		Assert.assertNotNull(company);

		Assert.assertEquals(company.getPrimaryKey(), user.getCompanyId());

		String encryptedUserId = Encryptor.encrypt(
			company.getKeyObj(), String.valueOf(user.getUserId()));

		String encryptedPassword = Encryptor.encrypt(
			company.getKeyObj(), _DEFAULT_PASSWORD);

		KeyValuePair kvp = UserLocalServiceUtil.decryptUserId(
			user.getCompanyId(), encryptedUserId, encryptedPassword);

		Assert.assertEquals(Long.parseLong(kvp.getKey()), user.getUserId());
		Assert.assertEquals(kvp.getValue(), _DEFAULT_PASSWORD);
	}

	@Test
	public void testDeleteUser() throws Exception {
		User user = addUser();

		UserServiceUtil.deleteUser(user.getUserId());
	}

	@Test
	public void testGetCompanyUsers() throws Exception {
		User user = addUser();

		List<User> users = UserServiceUtil.getCompanyUsers(
			user.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(users.contains(user));
	}

	@Test
	public void testGetCompanyUsersCount() throws Exception {
		long originalCount = UserServiceUtil.getCompanyUsersCount(
			TestPropsValues.getCompanyId());

		User user = addUser();

		long userCount = UserServiceUtil.getCompanyUsersCount(
			TestPropsValues.getCompanyId());

		Assert.assertEquals(originalCount + 1, userCount);
	}

	@Test
	public void testGetDefaultUser() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		User user = UserLocalServiceUtil.getDefaultUser(companyId);
	}

	@Test
	public void testGetUserByEmailAddress() throws Exception {
		User user = addUser();

		UserServiceUtil.getUserByEmailAddress(
			TestPropsValues.getCompanyId(), user.getEmailAddress());
	}

	@Test
	public void testGetUserByScreenName() throws Exception {
		User user = addUser();

		UserServiceUtil.getUserByScreenName(
			user.getCompanyId(), user.getScreenName());
	}

	@Test
	public void testHasRoleUser() throws Exception {
		User user = addUser();

		boolean hasRole = UserServiceUtil.hasRoleUser(
			user.getCompanyId(), "Power user", user.getUserId(), false);

		Assert.assertTrue(hasRole);
	}

	@Test
	public void testIsPasswordExpired() throws Exception {
		User user = addUser();

		boolean passwordExpired = UserLocalServiceUtil.isPasswordExpired(user);

		Assert.assertFalse(passwordExpired);
	}

	@Test
	public void testIsPasswordExpiringSoon() throws Exception {
		User user = addUser();

		boolean passwordExpiringSoon =
			UserLocalServiceUtil.isPasswordExpiringSoon(user);

		Assert.assertFalse(passwordExpiringSoon);
	}

	@Test(expected = UserLockoutException.class)
	public void testLockout() throws Exception {
		User user = addUser();

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		boolean isLockout = passwordPolicy.getLockout();

		passwordPolicy.setLockout(true);

		PasswordPolicyLocalServiceUtil.updatePasswordPolicy(passwordPolicy);

		try {
			UserLocalServiceUtil.checkLockout(user);
		}
		catch (UserLockoutException ule) {
			Assert.fail("User should not be locked right after creating it.");
		}

		UserLocalServiceUtil.updateLockout(user, true);

		try {
			UserLocalServiceUtil.checkLockout(user);
		}
		finally {
			passwordPolicy.setLockout(isLockout);

			PasswordPolicyLocalServiceUtil.updatePasswordPolicy(passwordPolicy);
		}
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

	private static final String _DEFAULT_COMPANY_MX = "liferay.com";
	private static final String _DEFAULT_FIRSTNAME = "FirstName";
	private static final String _DEFAULT_LASTNAME = "LastName";
	private static final String _DEFAULT_PASSWORD = "Liferay123";

}