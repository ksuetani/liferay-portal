/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Root, createRoot} from 'react-dom/client';

import GooglePageSpeedConfig from './GooglePageSpeedConfig';

export {default as GooglePageSpeedConfig} from './GooglePageSpeedConfig';
export {default as SectionHeader} from './components/SectionHeader';
export {default as OnPage} from './on_page/OnPage';

export function renderGooglePageSpeedConfig(container: HTMLElement): Root {
	const root = createRoot(container);

	root.render(React.createElement(GooglePageSpeedConfig));

	return root;
}
