/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class GooglePageSpeedConfigPage {
	readonly apiKeyInput: Locator;
	readonly cancelButton: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly validationAlert: Locator;

	constructor(page: Page) {
		this.page = page;

		this.apiKeyInput = this.page.locator('#googleApiKey');
		this.cancelButton = this.page.getByRole('link', {name: 'Cancel'});
		this.saveButton = this.page.getByRole('button', {name: 'Save'});
		this.validationAlert = this.page.locator('.alert-warning');
	}

	async goto(friendlyUrlPath: string): Promise<void> {
		await this.page.goto(
			`/web${friendlyUrlPath}/configurations/google-pagespeed`
		);
	}
}
