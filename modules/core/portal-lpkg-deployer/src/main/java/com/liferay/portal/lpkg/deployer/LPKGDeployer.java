/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lpkg.deployer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public interface LPKGDeployer {

	public List<Bundle> deploy(BundleContext bundleContext, File lpkgFile)
		throws IOException;

	public Map<Bundle, List<Bundle>> getDeployedLPKGBundles();

	public InputStream toBundle(File lpkgFile) throws IOException;

}