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

<%@ include file="/html/taglib/ui/background_task_status_progress_bar/init.jsp" %>

<%
BackgroundTaskDisplay backgroundTaskDisplay = (BackgroundTaskDisplay)request.getAttribute("liferay-ui:backgroundTaskStatus:backgroundTaskDisplay");

int percentage = 100;

if (backgroundTaskDisplay.hasPercentage()) {
	percentage = backgroundTaskDisplay.getPercentage();
}
%>

<div class="progress">
	<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="<%= percentage %>" class="active progress-bar progress-bar-striped" role="progressbar" style="width: <%= percentage %>%;">
		<c:if test="<%= backgroundTaskDisplay.hasPercentage() %>">
			<%= percentage + StringPool.PERCENT %>
		</c:if>
	</div>
</div>