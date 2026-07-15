/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTResourcePendingDeletionException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class CTPersistenceHelperImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_ctPersistenceHelperImpl = new CTPersistenceHelperImpl();

		_classNameLocalService = Mockito.mock(ClassNameLocalService.class);
		_ctEntryLocalService = Mockito.mock(CTEntryLocalService.class);

		Mockito.when(
			_classNameLocalService.getClassNameId(Mockito.<Class<?>>any())
		).thenReturn(
			_MODEL_CLASS_NAME_ID
		);

		ReflectionTestUtil.setFieldValue(
			_ctPersistenceHelperImpl, "_classNameLocalService",
			_classNameLocalService);
		ReflectionTestUtil.setFieldValue(
			_ctPersistenceHelperImpl, "_ctEntryLocalService",
			_ctEntryLocalService);
	}

	@Test(expected = CTResourcePendingDeletionException.class)
	public void testIsInsertRejectsWhenDeletionAlreadyPending()
		throws Exception {

		CTEntry ctEntry = _mockCTEntry(CTConstants.CT_CHANGE_TYPE_DELETION);

		Mockito.when(
			_ctEntryLocalService.fetchCTEntry(
				_CT_COLLECTION_ID, _MODEL_CLASS_NAME_ID, _MODEL_CLASS_PK)
		).thenReturn(
			ctEntry
		);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_CT_COLLECTION_ID)) {

			_ctPersistenceHelperImpl.isInsert(_mockLayout());
		}
	}

	@Test(expected = CTResourcePendingDeletionException.class)
	public void testIsRemoveRejectsWhenDeletionAlreadyPending()
		throws Exception {

		CTEntry ctEntry = _mockCTEntry(CTConstants.CT_CHANGE_TYPE_DELETION);

		Mockito.when(
			_ctEntryLocalService.fetchCTEntry(
				_CT_COLLECTION_ID, _MODEL_CLASS_NAME_ID, _MODEL_CLASS_PK)
		).thenReturn(
			ctEntry
		);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_CT_COLLECTION_ID)) {

			_ctPersistenceHelperImpl.isRemove(_mockLayout());
		}
	}

	private CTEntry _mockCTEntry(int changeType) {
		CTEntry ctEntry = Mockito.mock(CTEntry.class);

		Mockito.when(
			ctEntry.getChangeType()
		).thenReturn(
			changeType
		);

		return ctEntry;
	}

	private Layout _mockLayout() {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			Layout.class
		).when(
			layout
		).getModelClass();

		Mockito.when(
			layout.getModelClassName()
		).thenReturn(
			Layout.class.getName()
		);

		Mockito.when(
			layout.getPrimaryKey()
		).thenReturn(
			_MODEL_CLASS_PK
		);

		return layout;
	}

	private static final long _CT_COLLECTION_ID = RandomTestUtil.randomLong();

	private static final long _MODEL_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	private static final long _MODEL_CLASS_PK = RandomTestUtil.randomLong();

	private ClassNameLocalService _classNameLocalService;
	private CTEntryLocalService _ctEntryLocalService;
	private CTPersistenceHelperImpl _ctPersistenceHelperImpl;

}