/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.exception;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Kiana Suetani
 */
public class CTResourcePendingDeletionException extends SystemException {

	public CTResourcePendingDeletionException() {
	}

	public CTResourcePendingDeletionException(String msg) {
		super(msg);
	}

	public CTResourcePendingDeletionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CTResourcePendingDeletionException(Throwable throwable) {
		super(throwable);
	}

}