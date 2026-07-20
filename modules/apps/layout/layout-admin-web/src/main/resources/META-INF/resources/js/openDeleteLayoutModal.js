/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';
import {
	clearDeleteLayoutInFlight,
	isDeleteLayoutInFlight,
	markDeleteLayoutInFlight,
	sub,
} from 'frontend-js-web';

export default function openDeleteLayoutModal({
	deleteURL,
	message,
	multiple = false,
	onDelete,
}) {
	if (deleteURL && isDeleteLayoutInFlight(deleteURL)) {
		openToast({
			message: Liferay.Language.get(
				'this-page-is-currently-being-deleted'
			),
			type: 'danger',
		});

		return;
	}

	openModal({
		bodyHTML: message,
		buttons: [
			{
				autoFocus: true,
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'danger',
				id: 'deleteLayoutModalDeleteButton',
				label: Liferay.Language.get('delete'),
				onClick: ({processClose}) => {
					if (deleteURL) {
						markDeleteLayoutInFlight(deleteURL);
					}

					processClose();

					const promise = onDelete();

					if (deleteURL) {
						Promise.resolve(promise).finally(() => {
							clearDeleteLayoutInFlight(deleteURL);
						});
					}
				},
			},
		],
		status: 'danger',
		title: sub(
			Liferay.Language.get('delete-x'),
			multiple
				? Liferay.Language.get('pages')
				: Liferay.Language.get('page')
		),
	});
}
