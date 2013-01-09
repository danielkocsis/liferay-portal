<%--
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
--%>

<%@ include file="/html/taglib/ui/breadcrumb/init.jsp" %>

<%
StringBundler sb = new StringBundler();

if (showGuestGroup) {
	_buildGuestGroupBreadcrumb(themeDisplay, sb);
}

if (showParentGroups) {
	_buildParentGroupsBreadcrumb(selLayout.getLayoutSet(), portletURL, themeDisplay, sb);
}

if (showLayout) {
	_buildLayoutBreadcrumb(selLayout, selLayoutParam, true, portletURL, themeDisplay, sb);
}

if (showPortletBreadcrumb) {
	_buildPortletBreadcrumb(request, showCurrentGroup, showCurrentPortlet, themeDisplay, sb);
}

String breadcrumbString = sb.toString();

if (Validator.isNotNull(breadcrumbString)) {
	int pos_first = breadcrumbString.indexOf("<li");

	breadcrumbString = StringUtil.insert(breadcrumbString, " class=\"first", pos_first + 3);

	int pos_last = breadcrumbString.lastIndexOf("<li");

	if (pos_first == pos_last) {
		breadcrumbString = StringUtil.insert(breadcrumbString, " last\"", pos_first + 16);
	}
	else {
		breadcrumbString = StringUtil.insert(breadcrumbString, "\"", pos_first + 16);

		breadcrumbString = StringUtil.insert(breadcrumbString, " class=\"last\"", pos_last + 4);
	}
}
%>

<ul class="breadcrumbs breadcrumbs-horizontal lfr-component">
	<%= breadcrumbString %>
</ul>