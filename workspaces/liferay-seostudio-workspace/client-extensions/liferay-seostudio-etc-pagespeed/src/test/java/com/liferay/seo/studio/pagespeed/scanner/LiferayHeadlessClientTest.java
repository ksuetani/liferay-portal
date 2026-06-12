/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class LiferayHeadlessClientTest {

	@Test
	public void testGetPageURLs() throws Exception {
		HttpClient httpClient = Mockito.mock(HttpClient.class);

		HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);

		Mockito.when(
			httpResponse.statusCode()
		).thenReturn(
			200
		);

		Mockito.when(
			httpResponse.body()
		).thenReturn(
			_SITEMAP_XML
		);

		Mockito.when(
			httpClient.send(
				Mockito.any(HttpRequest.class),
				Mockito.any(HttpResponse.BodyHandler.class))
		).thenReturn(
			httpResponse
		);

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			null, httpClient, "https://portal.a.co");

		List<String> pageURLs = liferayHeadlessClient.getPageURLs("a.co", 100);

		Assertions.assertEquals(2, pageURLs.size());
		Assertions.assertEquals("https://a.co/1", pageURLs.get(0));
		Assertions.assertEquals("https://a.co/2", pageURLs.get(1));
	}

	private static final String _SITEMAP_XML =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">" +
				"<url><loc>https://a.co/1</loc></url>" +
					"<url><loc>https://a.co/2</loc></url></urlset>";

}