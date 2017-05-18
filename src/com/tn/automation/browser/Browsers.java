package com.tn.automation.browser;

import java.util.HashMap;
import java.util.Map;

public class Browsers {

	private String activeAlias = null;
	private WebBrowser activeBrowser;
	private Map<String, WebBrowser> browsers = new HashMap<String, WebBrowser>();

	public WebBrowser getDriver() {
		return activeBrowser;
	}


    public boolean isExist(String alias) {
		return browsers.containsKey(alias);
	}

	public WebBrowser initBrowser(String alias, String type) {
		return initBrowser(alias, type, null, null);
	}

	/*
	 *初始化浏览器BrowserType
	 */
    public WebBrowser initBrowser(String alias, String type, String browserversion, String remoteIP) {
		if (isExist(alias)) {
			System.out.println("Browser with alias <" + alias + "> is already exist.");
			remove(alias);
		}

		WebBrowser browser = null;
		BrowserType browsertype;
		
		if (type.equalsIgnoreCase("firefox"))
        {
			browsertype=BrowserType.Firefox;
        }
        else if (type.equalsIgnoreCase("chrome"))
        {
        	browsertype=BrowserType.Chrome;
        }
        else if (type.equalsIgnoreCase("IE"))
        {
        	browsertype=BrowserType.IE;
        }
        else if (type.equalsIgnoreCase("Opera"))
        {
        	browsertype=BrowserType.Opera;
        }
        else if (type.equalsIgnoreCase("Safari"))
        {
        	browsertype=BrowserType.Safari;
        }
        else
        {
        	browsertype=BrowserType.Chrome;
        }

		
		switch (browsertype)
	    {
	    	case IE:
	            //browser = new InternetExplorer();
	            break;
            case Firefox:
	            //browser = new Firefox();
	        	break;
	        case Chrome:
	            browser = new Chrome(browserversion,remoteIP);
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

	public void remove(String alias) {
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

	private void activate(String alias, WebBrowser browser) {
		browsers.put(alias, browser);
		activeAlias = alias;
		activeBrowser = browser;
	}

	public WebBrowser activate(String alias) {
		if (isExist(alias)) {
			activeAlias = alias;
			activeBrowser = browsers.get(alias);
			return activeBrowser;
		} else {
			return null;
		}
	}

}
