/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import com.liferay.seo.studio.pagespeed.PageSpeedConstants;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScannerTest {

	@Test
	public void testScanURLs() throws Exception {
		PageSpeedScoreProvider allFailedPageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			allFailedPageSpeedScoreProvider.getScores(Mockito.anyString())
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException("")
		);

		PageSpeedScanResult allFailedPageSpeedScanResult = _scanURLs(
			allFailedPageSpeedScoreProvider, _createURLs(3));

		Assertions.assertEquals(
			PageSpeedConstants.STRATEGY_DESKTOP,
			allFailedPageSpeedScanResult.getStrategy());
		Assertions.assertEquals(
			"All pages failed to scan",
			allFailedPageSpeedScanResult.getErrorMessage());
		Assertions.assertEquals(
			3, allFailedPageSpeedScanResult.getPagesErrored());
		Assertions.assertEquals(
			0, allFailedPageSpeedScanResult.getPagesScanned());
		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_FAILED,
			allFailedPageSpeedScanResult.getStatus());

		PageSpeedScoreProvider successPageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			successPageSpeedScoreProvider.getScores(Mockito.anyString())
		).thenReturn(
			new PageSpeedScores(80, 90, 70, 60)
		);

		PageSpeedScanResult successPageSpeedScanResult = _scanURLs(
			successPageSpeedScoreProvider, _createURLs(5));

		Assertions.assertNull(successPageSpeedScanResult.getErrorMessage());
		Assertions.assertEquals(
			0, successPageSpeedScanResult.getPagesErrored());
		Assertions.assertEquals(
			5, successPageSpeedScanResult.getPagesScanned());
		Assertions.assertEquals(5, successPageSpeedScanResult.getPagesTotal());
		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_COMPLETED,
			successPageSpeedScanResult.getStatus());

		PageSpeedScores averageScores =
			successPageSpeedScanResult.getAverageScores();

		Assertions.assertEquals(80, averageScores.getAccessibility());
		Assertions.assertEquals(90, averageScores.getBestPractices());
		Assertions.assertEquals(70, averageScores.getPerformance());
		Assertions.assertEquals(60, averageScores.getSEO());

		PageSpeedScoreProvider mixedPageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			mixedPageSpeedScoreProvider.getScores("https://example.com/page1")
		).thenReturn(
			new PageSpeedScores(80, 90, 70, 60)
		);

		Mockito.when(
			mixedPageSpeedScoreProvider.getScores("https://example.com/page2")
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException("")
		);

		Mockito.when(
			mixedPageSpeedScoreProvider.getScores("https://example.com/page3")
		).thenReturn(
			new PageSpeedScores(90, 80, 80, 80)
		);

		PageSpeedScanResult mixedPageSpeedScanResult = _scanURLs(
			mixedPageSpeedScoreProvider, _createURLs(3));

		Assertions.assertNull(mixedPageSpeedScanResult.getErrorMessage());
		Assertions.assertEquals(1, mixedPageSpeedScanResult.getPagesErrored());
		Assertions.assertEquals(2, mixedPageSpeedScanResult.getPagesScanned());
		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_COMPLETED,
			mixedPageSpeedScanResult.getStatus());

		averageScores = mixedPageSpeedScanResult.getAverageScores();

		Assertions.assertEquals(85, averageScores.getAccessibility());
		Assertions.assertEquals(85, averageScores.getBestPractices());
		Assertions.assertEquals(75, averageScores.getPerformance());
		Assertions.assertEquals(70, averageScores.getSEO());

		PageSpeedScoreProvider errorPageSpeedScoreProvider = Mockito.mock(
			PageSpeedScoreProvider.class);

		Mockito.when(
			errorPageSpeedScoreProvider.getScores("https://example.com/page1")
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException(
				"API error")
		);

		Mockito.when(
			errorPageSpeedScoreProvider.getScores("https://example.com/page2")
		).thenThrow(
			new PageSpeedScoreProvider.PageSpeedScoreProviderException(
				"API error")
		);

		PageSpeedScanResult errorPageSpeedScanResult = _scanURLs(
			errorPageSpeedScoreProvider, _createURLs(2));

		Assertions.assertEquals(
			PageSpeedScanResult.STATUS_FAILED,
			errorPageSpeedScanResult.getStatus());
	}

	private List<String> _createURLs(int count) {
		List<String> urls = new ArrayList<>();

		for (int i = 1; i <= count; i++) {
			urls.add("https://example.com/page" + i);
		}

		return urls;
	}

	private PageSpeedScanResult _scanURLs(
			PageSpeedScoreProvider pageSpeedScoreProvider, List<String> urls)
		throws Exception {

		PageSpeedScanner pageSpeedScanner = new PageSpeedScanner();

		Method method = PageSpeedScanner.class.getDeclaredMethod(
			"_scanURLs", PageSpeedScoreProvider.class, Consumer.class,
			String.class, List.class);

		method.setAccessible(true);

		return (PageSpeedScanResult)method.invoke(
			pageSpeedScanner, pageSpeedScoreProvider, null,
			PageSpeedConstants.STRATEGY_DESKTOP, urls);
	}

}