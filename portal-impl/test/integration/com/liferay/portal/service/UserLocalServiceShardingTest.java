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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.util.Encryptor;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Vilmos Papp
 *
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@Transactional
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserLocalServiceShardingTest {

	@Before
	public void setUp() throws Exception {
		addCompany();
	}

	@Test
	public void testAddDefaultAdminUser() throws Exception {
		String screenName = "ShardAdmin";
		String emailAddress = "shard.admin@" + DEFAULT_COMPANY_MX;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "Shard";
		String middleName = StringPool.BLANK;
		String lastName = "Admin";

		User adminUser = UserLocalServiceUtil.addDefaultAdminUser(
			shardCompanyId, screenName, emailAddress, locale, firstName,
			middleName, lastName);

		Assert.assertNotNull(adminUser);
	}

	@Test
	public void testAddUser1() throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = "test." + ServiceTestUtil.nextLong();
		String emailAddress =
			"test." + ServiceTestUtil.nextLong() + "@" + DEFAULT_COMPANY_MX;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = DEFAULT_FIRSTNAME;
		String middleName = StringPool.BLANK;
		String lastName = DEFAULT_LASTNAME;
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

		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		User user = UserLocalServiceUtil.addUser(
			defaultCreatorUserId, shardCompanyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		Assert.assertNotNull(user);
	}

	/**
	 * Commented out as an Exception is thrown because of LPS-30580
	 */
	/*
	@Test
	public void testAddUser2() throws Exception {
		String password = "password";
		String screenName = "screenName." + ServiceTestUtil.nextLong();
		String emailAddress =
			"test." + ServiceTestUtil.nextLong() + "@" + DEFAULT_COMPANY_MX;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = DEFAULT_FIRSTNAME;
		String middleName = StringPool.BLANK;
		String lastName = DEFAULT_LASTNAME;
		String jobTitle = StringPool.BLANK;

		User user = UserLocalServiceUtil.createUser(ServiceTestUtil.nextLong());

		user.setAgreedToTermsOfUse(true);
		user.setCompanyId(shardCompanyId);
		user.setCreateDate(new Date());
		user.setDefaultUser(false);
		user.setEmailAddress(emailAddress);
		user.setFacebookId(facebookId);
		user.setFirstName(firstName);
		user.setJobTitle(jobTitle);
		user.setLastName(lastName);
		user.setLanguageId(locale.getLanguage());
		user.setMiddleName(middleName);
		user.setOpenId(openId);
		user.setPassword(password);
		user.setPasswordReset(false);
		user.setScreenName(screenName);

		user = UserLocalServiceUtil.addUser(user);

		Assert.assertNotNull(user);
	}

	*/

	@Test
	public void testAddUserWithWorkflow() throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = false;
		String screenName = "test." + ServiceTestUtil.nextLong();
		String emailAddress =
			"test." + ServiceTestUtil.nextLong() + "@" + DEFAULT_COMPANY_MX;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = DEFAULT_FIRSTNAME;
		String middleName = StringPool.BLANK;
		String lastName = DEFAULT_LASTNAME;
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

		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		User user = UserLocalServiceUtil.addUserWithWorkflow(
			defaultCreatorUserId, shardCompanyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		Assert.assertNotNull(user);
	}

	@Test
	public void testAuthenticateByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		int status = UserLocalServiceUtil.authenticateByEmailAddress(
			user.getCompanyId(), user.getEmailAddress(), DEFAULT_PASSWORD, null,
			null, null);

		Assert.assertEquals(Authenticator.SUCCESS, status);
	}

	@Test
	public void testAuthenticateByScreenname() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		int status = UserLocalServiceUtil.authenticateByScreenName(
			user.getCompanyId(), user.getScreenName(), DEFAULT_PASSWORD, null,
			null, null);

		Assert.assertEquals(Authenticator.SUCCESS, status);
	}

	@Test
	public void testAuthenticateByUserId() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		int status = UserLocalServiceUtil.authenticateByUserId(
			user.getCompanyId(), user.getUserId(), DEFAULT_PASSWORD, null, null,
			null);

		Assert.assertEquals(Authenticator.SUCCESS, status);
	}

	@Test
	public void testCheckLockout() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		UserLocalServiceUtil.checkLockout(user);
	}

	@Test
	public void testCheckLoginFailure() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		UserLocalServiceUtil.checkLoginFailure(user);
	}

	@Test
	public void testCheckLoginFailureByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		UserLocalServiceUtil.checkLoginFailureByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());
	}

	@Test
	public void testCheckLoginFailureByScreenName() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		UserLocalServiceUtil.checkLoginFailureByScreenName(
			user.getCompanyId(), user.getScreenName());
	}

	@Test
	public void testCheckPasswordExpired() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		UserLocalServiceUtil.checkPasswordExpired(user);
	}

	@Test
	public void testCompleteUserRegistration() throws Exception {
		boolean autoPassword = true;
		boolean sendEmail = false;

		User user = addUser();

		Assert.assertNotNull(user);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAttribute("autoPassword", autoPassword);
		serviceContext.setAttribute("sendEmail", sendEmail);

		UserLocalServiceUtil.completeUserRegistration(user, serviceContext);
	}

	@Test
	public void testDecryptUserId() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		Company company = CompanyLocalServiceUtil.getCompany(
			user.getCompanyId());

		Assert.assertNotNull(company);

		Assert.assertEquals(company.getPrimaryKey(), user.getCompanyId());

		String encryptedUserId = Encryptor.encrypt(
			company.getKeyObj(), String.valueOf(user.getUserId()));

		String encryptedPassword = Encryptor.encrypt(
			company.getKeyObj(), DEFAULT_PASSWORD);

		KeyValuePair kvp = UserLocalServiceUtil.decryptUserId(
			user.getCompanyId(), encryptedUserId, encryptedPassword);

		Assert.assertEquals(Long.parseLong(kvp.getKey()), user.getUserId());
		Assert.assertEquals(kvp.getValue(), DEFAULT_PASSWORD);
	}

	@Test
	public void testDeleteUser() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User deletedUser = UserLocalServiceUtil.deleteUser(user);

		Assert.assertEquals(user.getUserId(), deletedUser.getUserId());
	}

	@Test
	public void testFetchUserByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User fetchedUser = UserLocalServiceUtil.fetchUserByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());

		Assert.assertNotNull(fetchedUser);
	}

	@Test
	public void testFetchUserByScreenName() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User fetchedUser = UserLocalServiceUtil.fetchUserByScreenName(
			user.getCompanyId(), user.getScreenName());

		Assert.assertNotNull(fetchedUser);
	}

	@Test
	public void testGetCompanyUsers() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		List<User> users = UserLocalServiceUtil.getCompanyUsers(
			user.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testGetCompanyUsersCount() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		long usersCount = UserLocalServiceUtil.getCompanyUsersCount(
			user.getCompanyId());

		Assert.assertTrue(usersCount > 0);
	}

	@Test
	public void testGetDefaultUser() throws Exception {
		User user = UserLocalServiceUtil.getDefaultUser(shardCompanyId);

		Assert.assertNotNull(user);
	}

	@Test
	public void testGetDefaultUserid() throws Exception {
		long userId = UserLocalServiceUtil.getDefaultUserId(shardCompanyId);

		Assert.assertNotNull(userId);
	}

	@Test
	public void testGetUserByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User loadedUser = UserLocalServiceUtil.getUserByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());

		Assert.assertNotNull(loadedUser);
	}

	@Test
	public void testGetUserByScreenName() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		User loadedUser = UserLocalServiceUtil.getUserByScreenName(
			user.getCompanyId(), user.getScreenName());

		Assert.assertNotNull(loadedUser);
	}

	@Test
	public void testGetUserIdByEmailAddress() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		long userId = UserLocalServiceUtil.getUserIdByEmailAddress(
			user.getCompanyId(), user.getEmailAddress());

		Assert.assertEquals(user.getUserId(), userId);
	}

	@Test
	public void testGetUserIdByScreenName() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		long userId = UserLocalServiceUtil.getUserIdByScreenName(
			user.getCompanyId(), user.getScreenName());

		Assert.assertEquals(user.getUserId(), userId);
	}

	@Test
	public void testHasRoleUser() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		boolean hasRole = UserLocalServiceUtil.hasRoleUser(
			user.getCompanyId(), "Power user", user.getUserId(), false);

		Assert.assertTrue(hasRole);
	}

	@Test
	public void testIsPasswordExpired() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		boolean passwordExpired = UserLocalServiceUtil.isPasswordExpired(user);

		Assert.assertFalse(passwordExpired);
	}

	@Test
	public void testIsPasswordExpiringSoon() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		boolean passwordExpiringSoon =
			UserLocalServiceUtil.isPasswordExpiringSoon(user);

		Assert.assertFalse(passwordExpiringSoon);
	}

	@Test
	public void testLoadGetDefaultUser() throws Exception {
		User user = addUser();

		Assert.assertNotNull(user);

		user.setDefaultUser(true);
		UserLocalServiceUtil.updateUser(user);

		User defaultUser = UserLocalServiceUtil.loadGetDefaultUser(
			user.getCompanyId());

		Assert.assertEquals(user.getUserId(), defaultUser.getUserId());
	}

	/**
	 * Commented out as an Exception is thrown because of LPS-30580
	 */
	/*
	@Test
	public void testUpdateUser() throws Exception {
		User user = addUser();

		user.setFirstName(DEFAULT_LASTNAME);
		user.setLastName(DEFAULT_FIRSTNAME);

		User updatedUser = UserLocalServiceUtil.updateUser(user);

		Assert.assertEquals(DEFAULT_FIRSTNAME, updatedUser.getLastName());
		Assert.assertEquals(DEFAULT_LASTNAME, updatedUser.getFirstName());
	}

	*/

	protected Company addCompany() throws Exception {
		Company company = null;

		try {
			company = CompanyLocalServiceUtil.getCompanyByMx(
				DEFAULT_COMPANY_MX);
		}
		catch (PortalException e) {
			company = CompanyLocalServiceUtil.addCompany(
				DEFAULT_COMPANY_WEBID, DEFAULT_COMPANY_HOSTNAME,
				DEFAULT_COMPANY_MX, DEFAULT_SHARDNAME, false, 0, true);
		}

		Assert.assertNotNull(company);

		defaultCreatorUserId = company.getDefaultUser().getUserId();
		shardCompanyId = company.getCompanyId();

		return company;
	}

	protected User addUser() throws Exception {

		boolean autoPassword = false;
		String password1 = DEFAULT_PASSWORD;
		String password2 = DEFAULT_PASSWORD;
		boolean autoScreenName = false;
		String screenName = "test." + ServiceTestUtil.nextLong();
		String emailAddress =
			"test." + ServiceTestUtil.nextLong() + "@" + DEFAULT_COMPANY_MX;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = DEFAULT_FIRSTNAME;
		String middleName = StringPool.BLANK;
		String lastName = DEFAULT_LASTNAME;
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

		return UserLocalServiceUtil.addUser(
				defaultCreatorUserId, shardCompanyId, autoPassword, password1,
				password2, autoScreenName, screenName, emailAddress, facebookId,
				openId, locale, firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
				sendMail, serviceContext);
	}

	private static final String DEFAULT_COMPANY_HOSTNAME = "liferay.org";
	private static final String DEFAULT_COMPANY_MX = "liferay.org";
	private static final String DEFAULT_COMPANY_WEBID = "liferay.org";
	private static final String DEFAULT_FIRSTNAME = "FirstName";
	private static final String DEFAULT_LASTNAME = "LastName";
	private static final String DEFAULT_PASSWORD = "Liferay123";
	private static final String DEFAULT_SHARDNAME = "one";
	private static long defaultCreatorUserId = 0;
	private static long shardCompanyId = 0;

}