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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.impl.ExportImportConfigurationImpl;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CalendarFactoryImpl;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Daniel Kocsis
 */
@PrepareForTest(LayoutSetLocalServiceUtil.class)
@RunWith(PowerMockRunner.class)
public class ExportImportDateUtilTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();
		calendarFactoryUtil.setCalendarFactory(new CalendarFactoryImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetCalendar() {
		ThemeDisplay mockThemeDisplay = new ThemeDisplay();

		mockThemeDisplay.setLocale(LocaleUtil.HUNGARY);
		mockThemeDisplay.setTimeZone(_budapestTimeZone);

		MockPortletRequest request = new MockPortletRequest();

		request.setAttribute(WebKeys.THEME_DISPLAY, mockThemeDisplay);

		String paramPrefix = "mock";

		request.setParameter(paramPrefix + "Month" , String.valueOf(1));
		request.setParameter(paramPrefix + "Day", String.valueOf(1));
		request.setParameter(paramPrefix + "Year", String.valueOf(1));
		request.setParameter(paramPrefix + "Hour", String.valueOf(1));
		request.setParameter(paramPrefix + "Minute", String.valueOf(1));
		request.setParameter(paramPrefix + "AmPm", String.valueOf(Calendar.AM));

		Calendar calendar = ExportImportDateUtil.getCalendar(
			request, paramPrefix, true);

		Assert.assertEquals(
			_budapestTimeZone.getID(), calendar.getTimeZone().getID());

		Assert.assertEquals(1, calendar.get(Calendar.MONTH));
		Assert.assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(1, calendar.get(Calendar.YEAR));
		Assert.assertEquals(1, calendar.get(Calendar.HOUR));
		Assert.assertEquals(1, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(Calendar.AM, calendar.get(Calendar.AM_PM));
	}

	@Test
	public void testGetDateRangeWithDefaultDates() throws Exception {
		Map<String, Serializable> settingsMap =
			new HashMap<String, Serializable>();

		Date startDate = new Date();
		Date endDate = new Date();

		settingsMap.put("startDate", startDate);
		settingsMap.put("endDate", endDate);

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			getExportImportConfiguration(settingsMap),
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);

		Assert.assertEquals(startDate, dateRange.getStartDate());
		Assert.assertEquals(endDate, dateRange.getEndDate());
	}

	@Test
	public void testGetDateRangeWithRangeFromLastPublishDate()
		throws Exception {

		mockStatic(LayoutSetLocalServiceUtil.class);

		Date lastPublishDate = new Date();

		when(
			LayoutSetLocalServiceUtil.getLayoutSet(
				Mockito.anyLong(), Mockito.anyBoolean())
		).thenReturn(
			new MockLayoutSetImpl(lastPublishDate)
		);

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			getExportImportConfiguration(null),
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);

		Assert.assertEquals(lastPublishDate, dateRange.getStartDate());

		verifyStatic();
	}

	@Test
	public void testGetDateRangeWithRangeAll() throws Exception {
		DateRange dateRange = ExportImportDateUtil.getDateRange(
			getExportImportConfiguration(null), ExportImportDateUtil.RANGE_ALL);

		Assert.assertEquals(null, dateRange.getStartDate());
		Assert.assertEquals(null, dateRange.getEndDate());
	}

	@Test
	public void testGetDateRangeWithRangeDateRange() throws Exception {
		Map<String, Serializable> settingsMap =
			new HashMap<String, Serializable>();

		settingsMap.put("locale", LocaleUtil.HUNGARY);
		settingsMap.put("timezone", _budapestTimeZone);

		HashMap<String, String[]> parameterMap =
			new HashMap<String, String[]>();

		parameterMap.put(
			"startDateAmPm", new String[] {String.valueOf(Calendar.AM)});
		parameterMap.put("startDateYear", new String[] {String.valueOf(1)});
		parameterMap.put("startDateMonth", new String[] {String.valueOf(1)});
		parameterMap.put("startDateDay", new String[] {String.valueOf(1)});
		parameterMap.put("startDateHour", new String[] {String.valueOf(1)});
		parameterMap.put("startDateMinute", new String[] {String.valueOf(1)});
		parameterMap.put(
			"endDateAmPm", new String[] {String.valueOf(Calendar.AM)});
		parameterMap.put("endDateYear", new String[] {String.valueOf(1)});
		parameterMap.put("endDateMonth", new String[] {String.valueOf(1)});
		parameterMap.put("endDateDay", new String[] {String.valueOf(1)});
		parameterMap.put("endDateHour", new String[] {String.valueOf(1)});
		parameterMap.put("endDateMinute", new String[] {String.valueOf(1)});

		settingsMap.put("parameterMap", parameterMap);

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			getExportImportConfiguration(settingsMap),
			ExportImportDateUtil.RANGE_DATE_RANGE);

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			_budapestTimeZone, LocaleUtil.HUNGARY);

		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.YEAR, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Assert.assertEquals(calendar.getTime(), dateRange.getStartDate());
		Assert.assertEquals(calendar.getTime(), dateRange.getEndDate());
	}

	@Test
	public void testGetDateRangeWithRangeLast() throws Exception {
		Map<String, Serializable> settingsMap =
			new HashMap<String, Serializable>();

		HashMap<String, String[]> parameterMap =
			new HashMap<String, String[]>();

		parameterMap.put("last", new String[]{String.valueOf(1)});

		settingsMap.put("parameterMap", parameterMap);

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			getExportImportConfiguration(settingsMap),
			ExportImportDateUtil.RANGE_LAST);

		Date endDate = dateRange.getEndDate();
		Date startDate = dateRange.getStartDate();

		Assert.assertEquals(Time.HOUR, endDate.getTime() - startDate.getTime());
	}

	protected ExportImportConfiguration getExportImportConfiguration(
		Map<String, Serializable> settingsMap) {

		ExportImportConfiguration exportImportConfiguration =
			new ExportImportConfigurationImpl();

		if (settingsMap == null) {
			settingsMap = new HashMap<String, Serializable>();

			HashMap<String, String[]> parameterMap =
				new HashMap<String, String[]>();

			settingsMap.put("parameterMap", parameterMap);
		}

		String settings = JSONFactoryUtil.serialize(settingsMap);
		exportImportConfiguration.setSettings(settings);

		return exportImportConfiguration;
	}

	private static TimeZone _budapestTimeZone = TimeZoneUtil.getTimeZone(
		"Europe/Budapest");

	private static class MockLayoutSetImpl extends LayoutSetImpl {

		public MockLayoutSetImpl(Date lastPublishDate) {
			_lastPublishDate = lastPublishDate;
		}

		@Override
		public String getSettingsProperty(String key) {
			return String.valueOf(_lastPublishDate.getTime());
		}

		private Date _lastPublishDate;

	}

}