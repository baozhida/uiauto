package com.tn.automation.browser;

import java.util.HashMap;
import java.util.Map;

public class Browsers {

	private static String activeAlias = null;
	private static WebBrowser activeBrowser;
	private static Map<String, WebBrowser> browsers = new HashMap<String, WebBrowser>();

	public static WebBrowser getDriver() {
		return activeBrowser;
	}


    public static boolean isExist(String alias) {
		return browsers.containsKey(alias);
	}

	public static WebBrowser initBrowser(String alias, BrowserType type) {
		return initBrowser(alias, type, null, null);
	}

	/*
	 *初始化浏览器
	 */
    public static WebBrowser initBrowser(String alias, BrowserType type, String browserVersion, String remoteIP) {
		if (isExist(alias)) {
			System.out.println("Browser with alias <" + alias + "> is already exist.");
			remove(alias);
		}

		WebBrowser browser = null;

		if (type == null) {
			type = BrowserType.Chrome;
		}

		
		switch (type)
	    {
	    	case IE:
	            //browser = new InternetExplorer();
	            break;
            case Firefox:
	            //browser = new Firefox();
	        	break;
	        case Chrome:
	            browser = new Chrome(browserVersion,remoteIP);
	            break;
	        case Opera:
	            //browser = new Opera();
	            break;
	        case Safari:
	            //browser = new Safari();
	            break;
	    }
		activate(alias, browser);

		return browser;
	}

	public static void remove(String alias) {
		if (isExist(alias)) {
			try {
				browsers.remove(alias).quit();
			} catch (Exception e) {
			}
		}

		if (!isExist(activeAlias)) {
			activeAlias = null;
			activeBrowser = null;
		}
	}

	private static void activate(String alias, WebBrowser browser) {
		browsers.put(alias, browser);
		activeAlias = alias;
		activeBrowser = browser;
	}

	public static WebBrowser activate(String alias) {
		if (isExist(alias)) {
			activeAlias = alias;
			activeBrowser = browsers.get(alias);
			return activeBrowser;
		} else {
			return null;
		}
	}

}
