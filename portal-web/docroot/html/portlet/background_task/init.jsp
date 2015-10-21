<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.NoSuchBackgroundTaskException" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTask" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskDetailsSectionJSONObject" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskJSONTransformer" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus" %><%@
page import="com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil" %>

<%@ include file="/html/portlet/background_task/init-ext.jsp" %>