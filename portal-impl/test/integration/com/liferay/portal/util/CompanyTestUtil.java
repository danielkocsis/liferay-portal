/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

import java.util.Locale;

import javax.portlet.PortletPreferences;

/**
 * @author Manuel de la Peña
 */
public class CompanyTestUtil {

	public static Company addCompany() throws Exception {
		return addCompany(ServiceTestUtil.randomString());
	}

	public static Company addCompany(String name) throws Exception {
		String virtualHostname = name + "." +  ServiceTestUtil.randomString(3);

		return CompanyLocalServiceUtil.addCompany(
			name, virtualHostname, virtualHostname,
			PropsValues.SHARD_DEFAULT_NAME, false, 0, true);
	}

	public static void resetCompanyLocales(
			long companyId, Locale defaultLocale, Locale[] locales)
		throws Exception {

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String languageIds = ArrayUtil.toString(
			locales, _LOCALE_LANGUAGE_ID_ACCESSOR);

		resetCompanyLocales(companyId, defaultLanguageId, languageIds);
	}

	public static void resetCompanyLocales(
			long companyId, String defaultLanguageId, String languageIds)
		throws Exception {

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		preferences.setValue(PropsKeys.LOCALES, languageIds);

		preferences.store();

		LanguageUtil.resetAvailableLocales(companyId);

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		CompanyLocalServiceUtil.updateDisplay(
			companyId, defaultLanguageId, company.getTimeZone().getID());

		LocaleThreadLocal.setDefaultLocale(
			LocaleUtil.fromLanguageId(defaultLanguageId));
	}

	private static final Accessor<Locale, String> _LOCALE_LANGUAGE_ID_ACCESSOR =

		new Accessor<Locale, String>() {

			@Override
			public String get(Locale locale) {
				return LocaleUtil.toLanguageId(locale);
			}

		};

}