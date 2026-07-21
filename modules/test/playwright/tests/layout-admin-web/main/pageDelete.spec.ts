/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';

const DELETE_IN_PROGRESS_TOAST = 'This page is currently being deleted.';

const DELETE_LAYOUT_ROUTE_DELAY_MS = 5000;

const DELETE_LAYOUT_ROUTE_GLOB = '**/delete_layout**';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest
);

test(
	'Guards a duplicate delete submit for the same page',
	{tag: '@LPD-98622'},
	async ({apiHelpers, page, pagesAdminPage, site}) => {
		const layoutTitle = getRandomString();

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle,
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await page.route(DELETE_LAYOUT_ROUTE_GLOB, async (route) => {
			await new Promise((resolve) =>
				setTimeout(resolve, DELETE_LAYOUT_ROUTE_DELAY_MS)
			);

			await route.continue();
		});

		await pagesAdminPage.openDeletePageModal(layoutTitle);

		await pagesAdminPage.confirmDeletePage();

		await pagesAdminPage.clickOnAction('Delete', layoutTitle);

		await expect(page.getByText(DELETE_IN_PROGRESS_TOAST)).toBeVisible();

		await expect(
			page.locator('.modal-title').getByText('Delete Page')
		).not.toBeVisible();
	}
);

test(
	'Does not block a delete of a different page while another delete is in flight',
	{tag: '@LPD-98622'},
	async ({apiHelpers, page, pagesAdminPage, site}) => {
		const layoutTitle1 = getRandomString();
		const layoutTitle2 = getRandomString();

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle1,
		});

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle2,
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);

		await page.route(DELETE_LAYOUT_ROUTE_GLOB, async (route) => {
			await new Promise((resolve) =>
				setTimeout(resolve, DELETE_LAYOUT_ROUTE_DELAY_MS)
			);

			await route.continue();
		});

		await pagesAdminPage.openDeletePageModal(layoutTitle1);

		await pagesAdminPage.confirmDeletePage();

		await pagesAdminPage.openDeletePageModal(layoutTitle2);

		await expect(
			page.getByText(DELETE_IN_PROGRESS_TOAST)
		).not.toBeVisible();
	}
);
