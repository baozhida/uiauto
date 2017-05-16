package com.tn.automation.browser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.tn.automation.util.Path;
import com.tn.automation.browser.BrowserType;

public class Firefox extends WebBrowser {
	@SuppressWarnings("deprecation")
	public Firefox(String firefox_version, String remoteip) {
		super();
		if(firefox_version == null ||firefox_version == "" || remoteip == null || remoteip == ""){
			System.setProperty("webdriver.gecko.driver", Path.getFirefoxDriverPath());
			// 连续两次信任证书出现时，selenium自身机制无法处理。
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			// 使用已有的profile 会直接内存溢出，所以暂时都是新建一个profile
			driver = new FirefoxDriver(capabilities);
			mType = BrowserType.Firefox;
		}else{
	        DesiredCapabilities desiredCaps= new DesiredCapabilities("firefox", firefox_version, Platform.LINUX); 
	    	try {
				driver = new RemoteWebDriver(new URL("http://"+remoteip+":4444/wd/hub/"), desiredCaps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	
	    }
	}

	public Firefox(String userAgent) {
		super();
		System.setProperty("webdriver.chrome.driver", Path.getFirefoxDriverPath());
		// 修改火狐浏览器的user-agent,伪装浏览器版本
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("general.useragent.override", userAgent);
		driver = new FirefoxDriver(profile);

		mType = BrowserType.Firefox;

	}

}