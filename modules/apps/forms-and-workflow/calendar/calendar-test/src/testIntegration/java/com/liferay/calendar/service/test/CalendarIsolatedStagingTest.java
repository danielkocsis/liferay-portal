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

package com.liferay.calendar.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class CalendarIsolatedStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_liveGroup.getGroupId());
	}

	@After
	public void tearDown() throws PortalException {
		StagingLocalServiceUtil.disableStaging(_liveGroup, _serviceContext);

		// This is where I clean up preferences with brute force.
		// I wonder if there is a better way to do it.
		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, 0);
	}

	@Test
	public void testInviteLiveCalendarCreatesStagingCalendarBooking()
		throws Exception {

		long groupId = _liveGroup.getGroupId();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				groupId, _serviceContext);

		Calendar liveCalendar = calendarResource.getDefaultCalendar();

		String stagedPortletCalendarKey =
			StagingConstants.STAGED_PREFIX +
				StagingUtil.getStagedPortletId(CalendarPortletKeys.CALENDAR) +
					StringPool.DOUBLE_DASH;

		_serviceContext.setAttribute(stagedPortletCalendarKey, Boolean.TRUE);

		StagingLocalServiceUtil.enableLocalStaging(
			_liveGroup.getCreatorUserId(), _liveGroup, false, false,
			_serviceContext);

		Group stagingGroup = _liveGroup.getStagingGroup();

		Calendar stagingCalendar =
			CalendarLocalServiceUtil.fetchCalendarByUuidAndGroupId(
				liveCalendar.getUuid(), stagingGroup.getGroupId());

		Assert.assertNotNull(stagingCalendar);
	}

	@DeleteAfterTestRun
	private Group _liveGroup;

	private ServiceContext _serviceContext;

}