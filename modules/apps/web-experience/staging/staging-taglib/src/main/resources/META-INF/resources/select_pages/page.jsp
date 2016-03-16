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

<%@ include file="/select_pages/init.jsp" %>

<aui:input name="layoutIds" type="hidden" value="<%= ExportImportHelperUtil.getSelectedLayoutsJSON(groupId, privateLayout, selectedLayoutIds) %>" />

<ul class="flex-container layout-selector" id="<portlet:namespace />pages">
	<li class="layout-selector-options">
		<aui:fieldset label="pages-options">
			<c:choose>
				<c:when test="<%= privateLayout %>">
					<aui:button id="changeToPublicLayoutsButton" value="change-to-public-pages" />
				</c:when>
				<c:otherwise>
					<aui:button id="changeToPrivateLayoutsButton" value="change-to-private-pages" />
				</c:otherwise>
			</c:choose>

			<c:if test="<%= LayoutStagingUtil.isBranchingLayoutSet(group, privateLayout) %>">

				<%
				List<LayoutSetBranch> layoutSetBranches = null;

				long layoutSetBranchId = MapUtil.getLong(parameterMap, "layoutSetBranchId");

				if (disableInputs && (layoutSetBranchId > 0)) {
					layoutSetBranches = new ArrayList<>(1);

					layoutSetBranches.add(LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId));
				}
				else {
					layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(group.getGroupId(), privateLayout);
				}
				%>

				<aui:select disabled="<%= disableInputs %>" label="site-pages-variation" name="layoutSetBranchId">

					<%
					for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
						boolean selected = false;

						if ((layoutSetBranchId == layoutSetBranch.getLayoutSetBranchId()) || ((layoutSetBranchId == 0) && layoutSetBranch.isMaster())) {
							selected = true;
						}
					%>

					<aui:option label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" selected="<%= selected %>" value="<%= layoutSetBranch.getLayoutSetBranchId() %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</aui:fieldset>
	</li>

	<li class="layout-selector-options">
		<aui:fieldset label="pages-to-export">

			<%
			long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
			%>

			<div class="pages-selector">
				<liferay-layout:layouts-tree
					defaultStateChecked="<%= true %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					incomplete="<%= false %>"
					portletURL="<%= renderResponse.createRenderURL() %>"
					privateLayout="<%= privateLayout %>"
					rootNodeName="<%= group.getLayoutRootNodeName(privateLayout, locale) %>"
					selectableTree="<%= true %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					selPlid="<%= selPlid %>"
					treeId="<%= treeId %>"
				/>
			</div>
		</aui:fieldset>
	</li>

	<li class="layout-selector-options">
		<aui:fieldset label="look-and-feel">
			<aui:input disabled="<%= disableInputs %>" helpMessage="export-import-theme-settings-help" label="theme-settings" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.THEME_REFERENCE, true) %>" />

			<aui:input disabled="<%= disableInputs %>" label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LOGO, true) %>" />

			<aui:input disabled="<%= disableInputs %>" label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS, true) %>" />

			<c:if test="<%= action.equals(Constants.PUBLISH) %>">
				<aui:input disabled="<%= disableInputs %>" helpMessage="delete-missing-layouts-staging-help" label="delete-missing-layouts" name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS, false) %>" />
			</c:if>
		</aui:fieldset>
	</li>
</ul>