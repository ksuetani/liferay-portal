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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Permission permission = (Permission)objArray[0];
Role role = (Role)objArray[1];

String languageKey = "";

if (ResourceBlockLocalServiceUtil.isSupported(permission.getName()))
{
	languageKey = "removing-this-permission-will-remove-all-permissions-of-this-type-from-the-entity.-are-your-sure-you-want-to-remove-this-permission";
}
else {
	languageKey = "are-you-sure-you-want-to-remove-this-permission";
}
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<portlet:actionURL name="deletePermission" var="deletePermissionURL">
		<portlet:param name="mvcPath" value="/edit_role_permissions.jsp" />
		<portlet:param name="tabs1" value="define-permissions" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		<portlet:param name="name" value="<%= permission.getName() %>" />
		<portlet:param name="scope" value="<%= String.valueOf(permission.getScope()) %>" />
		<portlet:param name="primKey" value="<%= permission.getPrimKey() %>" />
		<portlet:param name="actionId" value="<%= String.valueOf(permission.getActionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		confirmation="<%= languageKey %>"
		message="remove"
		url="<%= deletePermissionURL %>"
	/>
</liferay-ui:icon-menu>