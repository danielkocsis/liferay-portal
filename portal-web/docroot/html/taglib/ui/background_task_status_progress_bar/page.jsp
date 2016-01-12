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

<%@ include file="/html/taglib/ui/background_task_status/init.jsp" %>

<div class="active progress progress-striped">
	<c:choose>
		<c:when test="<%= backgroundTaskDisplay.hasPercentage() %>">

			<%
			int percentage = backgroundTaskDisplay.getPercentage();
			%>

			<div class="progress-bar" style="width: <%= percentage %>%;">
				<%= percentage + StringPool.PERCENT %>
			</div>
		</c:when>
		<c:otherwise>
			<div class="progress-bar" style="width: 100%;">
		</c:otherwise>
	</c:choose>
</div>