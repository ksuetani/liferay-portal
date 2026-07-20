/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const deleteLayoutURLs = new Set();

export function clearDeleteLayoutInFlight(url) {
	deleteLayoutURLs.delete(url);
}

export function isDeleteLayoutInFlight(url) {
	return deleteLayoutURLs.has(url);
}

export function markDeleteLayoutInFlight(url) {
	deleteLayoutURLs.add(url);
}
