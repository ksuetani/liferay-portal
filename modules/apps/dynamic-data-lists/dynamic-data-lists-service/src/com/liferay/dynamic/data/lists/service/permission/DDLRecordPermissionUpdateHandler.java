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

package com.liferay.dynamic.data.lists.service.permission;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord"},
	service = PermissionUpdateHandler.class
)
public class DDLRecordPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		DDLRecord record = _ddlRecordLocalService.fetchDDLRecord(
			GetterUtil.getLong(primKey));

		if (record == null) {
			return;
		}

		record.setModifiedDate(new Date());

		_ddlRecordLocalService.updateDDLRecord(record);
	}

	@Reference
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	private DDLRecordLocalService _ddlRecordLocalService;

}