/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {seoStudioPagesTest} from './fixtures/seoStudioPagesTest';
import {seoStudioSiteTest} from './fixtures/seoStudioSiteTest';

const test = mergeTests(
	loginTest(),
	seoStudioPagesTest,
	seoStudioSiteTest
);

test(
	'Shows validation error when saving an invalid API key',
	{tag: '@LPD-94529'},
	async ({googlePageSpeedConfigPage, seoStudioSite}) => {
		await googlePageSpeedConfigPage.goto(seoStudioSite.friendlyUrlPath);

		await googlePageSpeedConfigPage.apiKeyInput.fill(getRandomString());

		await googlePageSpeedConfigPage.saveButton.click();

		await expect(
			googlePageSpeedConfigPage.validationAlert
		).toBeVisible();

		await expect(
			googlePageSpeedConfigPage.validationAlert
		).toContainText('Google PageSpeed connection failed');

		await expect(googlePageSpeedConfigPage.page).toHaveURL(
			/configurations\/google-pagespeed/
		);
	}
);

test(
	'Disables save button when API key input is empty',
	{tag: '@LPD-94529'},
	async ({googlePageSpeedConfigPage, seoStudioSite}) => {
		await googlePageSpeedConfigPage.goto(seoStudioSite.friendlyUrlPath);

		await expect(
			googlePageSpeedConfigPage.saveButton
		).toBeDisabled();

		await googlePageSpeedConfigPage.apiKeyInput.fill(getRandomString());

		await expect(
			googlePageSpeedConfigPage.saveButton
		).toBeEnabled();

		await googlePageSpeedConfigPage.apiKeyInput.clear();

		await expect(
			googlePageSpeedConfigPage.saveButton
		).toBeDisabled();
	}
);
