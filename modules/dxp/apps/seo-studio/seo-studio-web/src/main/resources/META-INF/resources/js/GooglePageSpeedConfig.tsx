/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import React, {useEffect, useState} from 'react';

declare const Liferay: {
	Icons: {spritemap: string};
	Util: {
		openToast: (config: {
			message: string;
			title?: string;
			type?: string;
		}) => void;
	};
	authToken: string;
};

const BACK_URL = '/web/seo-studio/configurations';

const INSTANCES_API = '/o/seo-studio/instances';

const PAGESPEED_VALIDATION_URL =
	'https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=invalid_url&key=';

export default function GooglePageSpeedConfig() {
	const [apiKey, setApiKey] = useState('');
	const [instanceId, setInstanceId] = useState<number | null>(null);
	const [loading, setLoading] = useState(true);
	const [saving, setSaving] = useState(false);
	const [validationError, setValidationError] = useState('');
	const [visible, setVisible] = useState(false);

	const spritemap = Liferay.Icons.spritemap;

	useEffect(() => {
		fetch(`${INSTANCES_API}?pageSize=1`, {
			headers: {
				'Accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		})
			.then((response) => response.json())
			.then((data) => {
				const instance = data.items?.[0];

				if (instance) {
					setInstanceId(instance.id);

					if (instance.googlePageSpeedAPIKey) {
						setApiKey(instance.googlePageSpeedAPIKey);
					}
				}
			})
			.catch(() => {
				Liferay.Util.openToast({
					message: 'Failed to load configuration.',
					type: 'danger',
				});
			})
			.finally(() => setLoading(false));
	}, []);

	const validateApiKey = (key: string): Promise<void> =>
		fetch(`${PAGESPEED_VALIDATION_URL}${encodeURIComponent(key)}`)
			.then((response) => response.json())
			.then((data) => {
				const errorDetails = data.error?.details || [];

				const errorInfo = errorDetails.find(
					(detail: {reason?: string}) => detail.reason
				);

				const reason = errorInfo?.reason || '';
				const status = data.error?.status || '';

				if (reason || status === 'PERMISSION_DENIED') {
					throw new Error(
						'Google PageSpeed connection failed. Please verify your configuration and try again.'
					);
				}

				});

	const saveApiKey = (): Promise<void> =>
		fetch(`${INSTANCES_API}/${instanceId}`, {
			body: JSON.stringify({
				googlePageSpeedAPIKey: apiKey,
			}),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'PATCH',
		}).then((response) => {
			if (!response.ok) {
				throw new Error('Failed to save API Key.');
			}
		});

	const handleSave = () => {
		if (!instanceId) {
			Liferay.Util.openToast({
				message: 'No SEO Studio instance found. Add a domain first.',
				type: 'danger',
			});

			return;
		}

		setSaving(true);
		setValidationError('');

		validateApiKey(apiKey)
			.then(() => saveApiKey())
			.then(() => {
				sessionStorage.setItem(
					'seoStudioToast',
					'Google PageSpeed API key added.'
				);

				window.location.href = BACK_URL;
			})
			.catch((error) => {
				setSaving(false);

				setValidationError(
					error.message ||
						'Google PageSpeed connection failed. Please verify your configuration and try again.'
				);
			});
	};

	return (
		<div>
			<ClayToolbar className="bg-white border-bottom px-3">
				<ClayToolbar.Nav>
					<ClayToolbar.Item>
						<a className="component-action" href={BACK_URL}>
							<ClayIcon
								spritemap={spritemap}
								symbol="angle-left"
							/>
						</a>
					</ClayToolbar.Item>

					<ClayToolbar.Item className="text-left" expand>
						<ClayToolbar.Section>
							<span className="font-weight-semi-bold text-dark">
								Configuration
							</span>
						</ClayToolbar.Section>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<a
							className="btn btn-secondary border-0"
							href={BACK_URL}
						>
							Cancel
						</a>
					</ClayToolbar.Item>

					<ClayToolbar.Item>
						<ClayButton
						disabled={!apiKey.trim() || saving || loading}
						onClick={handleSave}
					>
						{saving ? 'Validating...' : 'Save'}
					</ClayButton>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayToolbar>

			<div className="mt-4 mx-auto px-4" style={{maxWidth: 960}}>
				<h2 className="font-weight-bold" style={{fontSize: 28}}>
					Google PageSpeed
				</h2>

				<p className="mb-4 text-secondary">
					To connect Google PageSpeed Insights, create an API Key
					in your Google Cloud project after enabling the PageSpeed
					Insights API, then paste the API Key below to complete
					setup. If your API Key has application restrictions
					enabled, make sure to add the following domain to your
					allowlist so SEO can access the PageSpeed Insights API
					successfully.
				</p>

				{validationError && (
					<div
						className="alert alert-warning mb-4"
						role="alert"
					>
						<ClayIcon
							className="mr-2"
							spritemap={spritemap}
							symbol="warning-full"
						/>

						{validationError}
					</div>
				)}

				<div className="form-group">
					<label htmlFor="googleApiKey">
						API Key{' '}

						<span className="reference-mark text-warning">*</span>
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem prepend>
							<ClayInput
								id="googleApiKey"
								insetAfter
								onChange={(event) => {
									setApiKey(event.target.value);
									setValidationError('');
								}}
								placeholder="Enter Key"
								type={visible ? 'text' : 'password'}
								value={apiKey}
							/>

							<ClayInput.GroupInsetItem after>
								<ClayButtonWithIcon
									aria-label="Toggle API Key visibility"
									displayType="unstyled"
									onClick={() => setVisible(!visible)}
									spritemap={spritemap}
									symbol={visible ? 'hidden' : 'view'}
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</div>
			</div>
		</div>
	);
}
