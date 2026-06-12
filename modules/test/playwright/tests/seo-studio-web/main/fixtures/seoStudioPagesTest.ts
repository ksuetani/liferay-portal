/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {GooglePageSpeedConfigPage} from '../pages/GooglePageSpeedConfigPage';
import {OnPagePage} from '../pages/OnPagePage';
import {SEOStudioSitePage} from '../pages/SEOStudioSitePage';

const seoStudioPagesTest = test.extend<{
	googlePageSpeedConfigPage: GooglePageSpeedConfigPage;
	onPagePage: OnPagePage;
	seoStudioSitePage: SEOStudioSitePage;
}>({
	googlePageSpeedConfigPage: async ({page}, use) => {
		await use(new GooglePageSpeedConfigPage(page));
	},
	onPagePage: async ({page}, use) => {
		await use(new OnPagePage(page));
	},
	seoStudioSitePage: async ({page}, use) => {
		await use(new SEOStudioSitePage(page));
	},
});

export {seoStudioPagesTest};
