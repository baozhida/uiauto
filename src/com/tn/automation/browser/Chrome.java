package com.tn.automation.browser;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.tn.automation.util.Path;

public class Chrome extends WebBrowser {
	
	public Chrome(String chrome_version, String remoteip) {
		super();

		if (chrome_version == null ||chrome_version == "" || remoteip == null || remoteip == "") {
			System.setProperty("webdriver.chrome.driver", Path.getChromeDriverPath());
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");
			//options.setExperimentalOption("forceDevToolsScreenshot", true);
			driver = new ChromeDriver(options);
		} else {
			DesiredCapabilities desiredCaps = new DesiredCapabilities("chrome", chrome_version, Platform.LINUX);

			try {
				driver = new RemoteWebDriver(new URL("http://" + remoteip + ":4444/wd/hub/"), desiredCaps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
	}

}