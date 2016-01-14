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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BackgroundTask backgroundTask = (BackgroundTask)row.getObject();
%>

<liferay-ui:background-task-status backgroundTaskId="<%= backgroundTask.getBackgroundTaskId() %>">
	<liferay-ui:background-task-status-label/>

	<c:if test="<%= backgroundTask.isInProgress() %>">
		<liferay-ui:background-task-status-progress-bar/>
	</c:if>

	<c:choose>
		<c:when test="<%= Validator.isNotNull(backgroundTask.getStatusMessage()) %>">

			<%
			long[] expandedBackgroundTaskIds = StringUtil.split(GetterUtil.getString(SessionClicks.get(request, "com.liferay.exportimport.web_backgroundTaskIds", null)), 0L);
			%>

			<a class="details-link toggler-header-<%= ArrayUtil.contains(expandedBackgroundTaskIds, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>" data-persist-id="<%= backgroundTask.getBackgroundTaskId() %>" href="#"><liferay-ui:message key="details" /></a>

			<div class="background-task-status-message toggler-content-<%= ArrayUtil.contains(expandedBackgroundTaskIds, backgroundTask.getBackgroundTaskId()) ? "expanded" : "collapsed" %>">
				<liferay-ui:background-task-status-message/>
			</div>
		</c:when>
		<c:otherwise>
			<liferay-ui:background-task-status-message/>
		</c:otherwise>
	</c:choose>
</liferay-ui:background-task-status>