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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.util.Encryptor;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vilmos Papp
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class UserLocalServiceTest {

	@Test
	public void testAddDefaultAdminUser() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		String screenName = "DefaultAdmin";
		String emailAddress = "default.admin@" + DEFAULT_COMPANY_MX;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "Default";
		String middleName = StringPool.BLANK;
		String lastName = "Admin";

		User adminUser = UserLocalServiceUtil.addDefaultAdminUser(
			companyId, screenName, emailAddress, locale, firstName, middleName,
			lastName);

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

		long companyId = TestPropsValues.getCompanyId();
		long creatorUserId = TestPropsValues.getUserId();

		ServiceContext serviceContext = new ServiceContext();

		User user = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		Assert.assertNotNull(user);
	}

	@Test
	public void testAddUser2() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
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
		user.setCompanyId(companyId);
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

		Contact contact = createContact(companyId, user.getUserId());

		user.setContactId(contact.getContactId());

		user = UserLocalServiceUtil.addUser(user);

		Assert.assertNotNull(user);
	}

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

		long companyId = TestPropsValues.getCompanyId();
		long creatorUserId = TestPropsValues.getUserId();

		ServiceContext serviceContext = new ServiceContext();

		User user = UserLocalServiceUtil.addUserWithWorkflow(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
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
	public void testGetDefaultUser() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		User user = UserLocalServiceUtil.getDefaultUser(companyId);

		Assert.assertNotNull(user);
	}

	@Test
	public void testGetDefaultUserid() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Assert.assertNotNull(userId);
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

	@Test
	public void testUpdateUser() throws Exception {
		User user = addUser();

		user.setFirstName(DEFAULT_LASTNAME);
		user.setLastName(DEFAULT_FIRSTNAME);

		User updatedUser = UserLocalServiceUtil.updateUser(user);

		Assert.assertEquals(DEFAULT_FIRSTNAME, updatedUser.getLastName());
		Assert.assertEquals(DEFAULT_LASTNAME, updatedUser.getFirstName());
	}

	protected User addUser() throws Exception {
		boolean autoPassword = false;
		String password1 = DEFAULT_PASSWORD;
		String password2 = DEFAULT_PASSWORD;
		boolean autoScreenName = false;
		String screenName =
			"UserLocalServiceTest." + ServiceTestUtil.nextLong();
		String emailAddress =
			"UserLocalServiceTest." + ServiceTestUtil.nextLong() +
			"@" + DEFAULT_COMPANY_MX;
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

		long companyId = TestPropsValues.getCompanyId();
		long creatorUserId = TestPropsValues.getUserId();

		ServiceContext serviceContext = new ServiceContext();

		return UserLocalServiceUtil.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendMail,
				serviceContext);
	}

	protected Contact createContact(long companyId, long userId)
		throws Exception {

		Contact contact = ContactLocalServiceUtil.createContact(
			ServiceTestUtil.nextLong());

		contact.setCompanyId(companyId);
		contact.setUserId(userId);
		contact.setUserName(ServiceTestUtil.randomString());
		contact.setCreateDate(ServiceTestUtil.nextDate());
		contact.setModifiedDate(ServiceTestUtil.nextDate());
		contact.setClassNameId(ServiceTestUtil.nextLong());
		contact.setClassPK(ServiceTestUtil.nextLong());
		contact.setAccountId(ServiceTestUtil.nextLong());
		contact.setParentContactId(ServiceTestUtil.nextLong());
		contact.setEmailAddress(ServiceTestUtil.randomString());
		contact.setFirstName(ServiceTestUtil.randomString());
		contact.setMiddleName(ServiceTestUtil.randomString());
		contact.setLastName(ServiceTestUtil.randomString());
		contact.setPrefixId(ServiceTestUtil.nextInt());
		contact.setSuffixId(ServiceTestUtil.nextInt());
		contact.setMale(ServiceTestUtil.randomBoolean());
		contact.setBirthday(ServiceTestUtil.nextDate());
		contact.setSmsSn(ServiceTestUtil.randomString());
		contact.setAimSn(ServiceTestUtil.randomString());
		contact.setFacebookSn(ServiceTestUtil.randomString());
		contact.setIcqSn(ServiceTestUtil.randomString());
		contact.setJabberSn(ServiceTestUtil.randomString());
		contact.setMsnSn(ServiceTestUtil.randomString());
		contact.setMySpaceSn(ServiceTestUtil.randomString());
		contact.setSkypeSn(ServiceTestUtil.randomString());
		contact.setTwitterSn(ServiceTestUtil.randomString());
		contact.setYmSn(ServiceTestUtil.randomString());
		contact.setEmployeeStatusId(ServiceTestUtil.randomString());
		contact.setEmployeeNumber(ServiceTestUtil.randomString());
		contact.setJobTitle(ServiceTestUtil.randomString());
		contact.setJobClass(ServiceTestUtil.randomString());
		contact.setHoursOfOperation(ServiceTestUtil.randomString());

		ContactLocalServiceUtil.addContact(contact);

		return contact;
	}

	private static final String DEFAULT_COMPANY_MX = "liferay.com";
	private static final String DEFAULT_FIRSTNAME = "FirstName";
	private static final String DEFAULT_LASTNAME = "LastName";
	private static final String DEFAULT_PASSWORD = "Liferay123";

}