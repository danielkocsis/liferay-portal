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

<%@ include file="/html/portlet/background_task/init.jsp" %>

<%
long backgroundTaskId = ParamUtil.getLong(request, "backgroundTaskId");

BackgroundTask backgroundTask = BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

JSONObject jsonObject = null;

try {
	jsonObject = JSONFactoryUtil.createJSONObject(backgroundTask.getStatusMessage());
}
catch (Exception e) {
}
%>

<c:choose>
	<c:when test="<%= jsonObject == null %>">
		<div class="alert <%= backgroundTask.getStatus() == BackgroundTaskConstants.STATUS_FAILED ? "alert-danger" : StringPool.BLANK %> publish-error">
			<liferay-ui:message arguments="<%= backgroundTask.getStatusMessage() %>" key="unable-to-execute-process-x" translateArguments="<%= false %>" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger publish-error">
			<h4 class="upload-error-message">

				<%
				boolean exported = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "exported");
				boolean validated = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "validated");
				%>

				<c:choose>
					<c:when test="<%= exported && !validated %>">
						<liferay-ui:message key="the-publication-process-did-not-start-due-to-validation-errors" /></h4>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="an-unexpected-error-occurred-with-the-publication-process.-please-check-your-portal-and-publishing-configuration" /></h4>
					</c:otherwise>
				</c:choose>

			<%
			List<BackgroundTaskDetailsSectionJSONObject> sections = new ArrayList<>(2);

			JSONArray messageListItemsJSONArray = jsonObject.getJSONArray("messageListItems");

			if (messageListItemsJSONArray == null) {
				messageListItemsJSONArray = JSONFactoryUtil.createJSONArray();
			}

			sections.add(new BackgroundTaskDetailsSectionJSONObject(jsonObject.getString("message"), messageListItemsJSONArray));

			JSONArray warningMessagesJSONArray = jsonObject.getJSONArray("warningMessages");

			if ((warningMessagesJSONArray != null) && (warningMessagesJSONArray.length() > 0)) {
				String warningMessage = ((messageListItemsJSONArray != null) && (messageListItemsJSONArray.length() > 0)) ? "consider-that-the-following-data-would-not-have-been-published-either" : "the-following-data-has-not-been-published";

				sections.add(new BackgroundTaskDetailsSectionJSONObject(warningMessage, warningMessagesJSONArray));
			}

			JSONArray sectionsJSONArray = BackgroundTaskJSONTransformer.toJSONArray(sections);
			%>

			<c:if test="<%= (sectionsJSONArray != null) && (sectionsJSONArray.length() > 0) %>">

				<%
				for (int i = 0; i < sectionsJSONArray.length(); i++) {
					JSONObject detailsItemJSONObject = sectionsJSONArray.getJSONObject(i);
				%>

					<span class="error-message">
						<%= HtmlUtil.escape(detailsItemJSONObject.getString("message")) %>
					</span>

					<ul class="error-list-items">

					<%
					JSONArray itemsListJSONArray = detailsItemJSONObject.getJSONArray("itemsList");
					%>

					<c:if test="<%= itemsListJSONArray != null %>">

						<%
						for (int j = 0; j < itemsListJSONArray.length(); j++) {
							JSONObject itemsListJSONObject = itemsListJSONArray.getJSONObject(j);

							String info = itemsListJSONObject.getString("info");
						%>

						<li>
							<%= itemsListJSONObject.getString("errorMessage") %>:

							<strong><%= HtmlUtil.escape(itemsListJSONObject.getString("errorStrongMessage")) %></strong>

							<c:if test="<%= Validator.isNotNull(info) %>">
								<span class="error-info">(<%= HtmlUtil.escape(info) %>)</span>
							</c:if>
						</li>

						<%
						}
						%>

					</c:if>
				</ul>

				<%
				}
				%>

			</c:if>
		</div>
	</c:otherwise>
</c:choose>