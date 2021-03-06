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

package com.liferay.text.localizer.address.util;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
public class AddressUtil {

	public static Optional<String> getCountryNameOptional(Address address) {
		Optional<Address> addressOptional = Optional.ofNullable(address);

		return addressOptional.map(
			Address::getCountry
		).filter(
			country -> {
				if (country.getCountryId() > 0) {
					return true;
				}

				return false;
			}
		).map(
			country -> {
				Optional<ServiceContext> serviceContextOptional =
					Optional.ofNullable(
						ServiceContextThreadLocal.getServiceContext());

				return serviceContextOptional.map(
					serviceContext ->
						country.getName(serviceContext.getLocale())
				).orElseGet(
					country::getName
				);
			}
		).filter(
			Validator::isNotNull
		);
	}

	public static Optional<String> getRegionNameOptional(Address address) {
		Optional<Address> addressOptional = Optional.ofNullable(address);

		return addressOptional.map(
			Address::getRegion
		).filter(
			region -> {
				if (region.getRegionId() > 0) {
					return true;
				}

				return false;
			}
		).map(
			Region::getName
		).filter(
			Validator::isNotNull
		);
	}

}