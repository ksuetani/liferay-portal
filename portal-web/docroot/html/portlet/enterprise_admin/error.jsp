<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<liferay-ui:tabs names="error" backURL="javascript:history.go(-1);" />

<liferay-ui:error exception="<%= NoSuchOrganizationException.class %>" message="the-organization-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchRoleException.class %>" message="the-role-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="the-user-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= RoleAssignmentException.class %>" message="you-cannot-assign-groups-or-users-to-this-role" />
<liferay-ui:error exception="<%= RolePermissionsException.class %>" message="you-cannot-edit-the-permissions-of-this-role" />