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

package com.liferay.portal.service.impl;

import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.service.base.ContactServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactServiceImpl extends ContactServiceBaseImpl {

	public List<Contact> getContacts(
			long classNameId, long classPK, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return contactPersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	public int getContactsCount(long classNameId, long classPK)
		throws SystemException {

		return contactPersistence.countByC_C(classNameId, classPK);
	}

	public Contact updateContact(
			long contactId, String emailAddress, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String facebookSn, String icqSn,
			String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn, String jobTitle)
		throws PortalException, SystemException {

		Date birthday = PortalUtil.getDate(
			birthdayMonth, birthdayDay, birthdayYear,
			ContactBirthdayException.class);

		Contact contact = contactPersistence.findByPrimaryKey(contactId);

		contact.setModifiedDate(new Date());
		contact.setEmailAddress(emailAddress);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);
		contact.setMale(male);
		contact.setBirthday(birthday);
		contact.setSmsSn(smsSn);
		contact.setAimSn(aimSn);
		contact.setFacebookSn(facebookSn);
		contact.setIcqSn(icqSn);
		contact.setJabberSn(jabberSn);
		contact.setMsnSn(msnSn);
		contact.setMySpaceSn(mySpaceSn);
		contact.setSkypeSn(skypeSn);
		contact.setTwitterSn(twitterSn);
		contact.setYmSn(ymSn);
		contact.setJobTitle(jobTitle);

		contactPersistence.update(contact, false);

		return contact;
	}

}