/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.samples.portlet.toolbar.contributor.test;

import com.liferay.arquillian.portal.annotation.PortalURL;
import com.liferay.blade.sample.test.functional.utils.BladeSampleFunctionalActionUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Lawrence Lee
 */
@RunAsClient
@RunWith(Arquillian.class)
public class BladePortletToolbarContributorTest {

	@Deployment
	public static JavaArchive create() throws Exception {
		final File jarFile = new File(
			System.getProperty("portletToolbarContributorJarFile"));

		return ShrinkWrap.createFromZipFile(JavaArchive.class, jarFile);
	}

	@Test
	public void testBladePortletToolbarContributor()
		throws InterruptedException, PortalException {

		_webDriver.get(_portletURL.toExternalForm());

		Assert.assertTrue(
			"Portlet was not deployed",
			BladeSampleFunctionalActionUtil.isVisible(_webDriver, _helloWorldPortlet));

		Assert.assertTrue(
			"Portlet Topper Toolbar is not clickable",
			BladeSampleFunctionalActionUtil.isClickable(_webDriver, _portletTopperToolbar));

		BladeSampleFunctionalActionUtil.customClick(_webDriver, _portletTopperToolbar);

		BladeSampleFunctionalActionUtil.customClick(_webDriver, _lfrMenuLiferay);

		Thread.sleep(2000);

		Assert.assertTrue(
			"Expected: https://www.liferay.com/, but saw " +
				_webDriver.getCurrentUrl(),
			BladeSampleFunctionalActionUtil.isPageLoaded(_webDriver, "https://www.liferay.com/"));
	}

	@FindBy(xpath = "//section[contains(@id,'portlet_com_liferay_hello_world_web_portlet_HelloWorldPortlet')]")
	private WebElement _helloWorldPortlet;

	@FindBy(xpath = "//header[@id='banner']")
	private WebElement _lfrBanner;

	@FindBy(xpath = "//ul[contains(@class,'dropdown-menu')]/li[1]/a[contains(.,'Liferay')]")
	private WebElement _lfrMenuLiferay;

	@FindBy(xpath = "//section[contains(@id,'portlet_com_liferay_hello_world_web_portlet_HelloWorldPortlet')]//..//*[name()='svg'][contains(@class,'lexicon-icon')]")
	private WebElement _portletTopperToolbar;

	@PortalURL("com_liferay_hello_world_web_portlet_HelloWorldPortlet")
	private URL _portletURL;

	@Drone
	private WebDriver _webDriver;

}