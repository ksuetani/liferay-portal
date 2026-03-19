/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author Kiana Suetani
 */
@RunWith(Arquillian.class)
public class GetLayoutContentChangesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_layout = LayoutTestUtil.addTypeContentLayout(
			_groupLocalService.fetchGroup(TestPropsValues.getGroupId()));

		_updateFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				"{}", _layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()));

		_deleteFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				"{}", _layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()));
	}

	@Test
	public void testGetLayoutContentChanges() throws Exception {
		FragmentEntryLink addFragmentEntryLink;

		try (SafeCloseable safeCloseable =
				 CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					 _ctCollection.getCtCollectionId())) {

			_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				_deleteFragmentEntryLink);

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				_updateFragmentEntryLink);

			addFragmentEntryLink =
				ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
					"{}", _layout,
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(
							_layout.getPlid()));
		}

		CTEntry layoutCTEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(),
			_classNameLocalService.getClassNameId(Layout.class),
			_layout.getPlid());

		long fragmentClassNameId = _classNameLocalService.getClassNameId(
			FragmentEntryLink.class);

		CTEntry deleteCTEntry =
			_ctEntryLocalService.fetchCTEntry(
				_ctCollection.getCtCollectionId(),
				fragmentClassNameId,
				_deleteFragmentEntryLink.getFragmentEntryLinkId());

		CTEntry updateCTEntry =
			_ctEntryLocalService.fetchCTEntry(
				_ctCollection.getCtCollectionId(),
				fragmentClassNameId,
				_updateFragmentEntryLink.getFragmentEntryLinkId());

		CTEntry addCTEntry =
			_ctEntryLocalService.fetchCTEntry(
				_ctCollection.getCtCollectionId(),
				fragmentClassNameId,
				addFragmentEntryLink.getFragmentEntryLinkId());

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(layoutCTEntry.getCtEntryId()),
			mockLiferayResourceResponse);

		JSONArray jsonArray = _getContentChangesJSONArray(
			mockLiferayResourceResponse);

		long[] ctEntryIds = new long[] {
			deleteCTEntry.getCtEntryId(), updateCTEntry.getCtEntryId(),
			addCTEntry.getCtEntryId()};

		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject)object;

			Assert.assertTrue(ArrayUtil.contains(ctEntryIds,
				jsonObject.getLong("ctEntryId"))
			);
		}

		Assert.assertEquals(3, jsonArray.length());
	}

	private JSONArray _getContentChangesJSONArray(
		MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONArray(
			new String(byteArrayOutputStream.toByteArray()));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		long ctEntryId)
		throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_ctCollection.getCompanyId()));

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG, null);
		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
		mockLiferayResourceRequest.setParameter(
			"ctEntryId", String.valueOf(ctEntryId));
		mockLiferayResourceRequest.setParameter(
			"cur", String.valueOf(0));

		return mockLiferayResourceRequest;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	private FragmentEntryLink _deleteFragmentEntryLink;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject(filter = "mvc.command.name=/change_tracking/get_layout_content_changes")
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private FragmentEntryLink _updateFragmentEntryLink;

}