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

/**
 * <p>
 * This class is a wrapper for {@link ContactService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ContactService
 * @generated
 */
public class ContactServiceWrapper implements ContactService,
	ServiceWrapper<ContactService> {
	public ContactServiceWrapper(ContactService contactService) {
		_contactService = contactService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _contactService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_contactService.setBeanIdentifier(beanIdentifier);
	}

	public java.util.List<com.liferay.portal.model.Contact> getContacts(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactService.getContacts(classNameId, classPK, start, end,
			orderByComparator);
	}

	public int getContactsCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contactService.getContactsCount(classNameId, classPK);
	}

	public com.liferay.portal.model.Contact updateContact(long contactId,
		java.lang.String emailAddress, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _contactService.updateContact(contactId, emailAddress,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ContactService getWrappedContactService() {
		return _contactService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedContactService(ContactService contactService) {
		_contactService = contactService;
	}

	public ContactService getWrappedService() {
		return _contactService;
	}

	public void setWrappedService(ContactService contactService) {
		_contactService = contactService;
	}

	private ContactService _contactService;
}