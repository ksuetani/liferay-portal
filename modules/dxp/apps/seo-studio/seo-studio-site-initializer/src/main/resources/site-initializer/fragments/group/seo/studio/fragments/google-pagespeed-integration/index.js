/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

(function () {
	const container = fragmentElement.querySelector(
		'.google-pagespeed-config-root'
	);

	if (!container || layoutMode === 'edit') {
		return;
	}

	import('/o/seo-studio-web/__liferay__/index.js').then((module) => {
		const root = module.renderGooglePageSpeedConfig(container);

		if (Liferay && Liferay.on) {
			Liferay.on('beforeNavigate', () => {
				root.unmount();
			});
		}
	});
})();
