package com.tn.automation.testngcase;

//import static org.testng.AssertJUnit.assertTrue;
//import static org.testng.AssertJUnit.fail;
import static com.tn.automation.browser.Browsers.initBrowser;
import static com.tn.automation.browser.Browsers.getDriver;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tn.automation.util.Path;
import com.alibaba.fastjson.JSONObject;

import com.tn.automation.browser.BrowserType;

public class RunWEB {
    
	private String browsertype;
	private String browserversion;
	private String remoteIP;
	boolean isFail;
	int initcount = 0;
	static WebDriver driver;
	
	
	static BrowserType BrowserType;
	static String BrowserVersion;
	static String RemoteIP;
	
	public BrowserType getBrowser() {
		return BrowserType;
	}

	public void setBrowser(BrowserType browserType) {
		BrowserType = browserType;
	}

	public String getBrowserVersion() {
		return BrowserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		BrowserVersion = browserVersion;
	}
	
	public String getRemoteIP() {
		return RemoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		RemoteIP = remoteIP;
	}
	
    
    public void initDriver() throws Exception {
    	
    	if (browsertype.equalsIgnoreCase("firefox"))
        {
    		setBrowser(BrowserType.Firefox);
        }
        else if (browsertype.equalsIgnoreCase("chrome"))
        {
        	setBrowser(BrowserType.Chrome);
        }
        else if (browsertype.equalsIgnoreCase("IE"))
        {
        	setBrowser(BrowserType.IE);
        }
        else if (browsertype.equalsIgnoreCase("Opera"))
        {
        	setBrowser(BrowserType.Opera);
        }
        else if (browsertype.equalsIgnoreCase("Safari"))
        {
        	setBrowser(BrowserType.Safari);
        }
        else
        {
        	setBrowser(BrowserType.Chrome);
        }
    	setBrowserVersion(browserversion);
    	setRemoteIP(remoteIP);
    	initBrowser("chrome"+browserversion, BrowserType, BrowserVersion, RemoteIP);
    	
    }

    @BeforeTest
    @Parameters({ "browsertype", "browserversion", "remoteIP" })  
    public void initParameters(String browsertype, String browserversion, String remoteIP) {
          this.browsertype = browsertype;
          this.browserversion = browserversion;
          this.remoteIP = remoteIP;
      }
    
    @BeforeClass
    public void setUp() throws Exception {
    	System.out.println("进入setUp方法");
    }
    
    @BeforeMethod
    public void beforeMethod() throws Exception {
    	System.out.println("进入beforecase方法");
    	initDriver();
    }

    @Test
    public void case_001xxx() throws InterruptedException {
    	System.out.println("进入case_001xxx方法");
    	getDriver().open("https://baidu.com");
    	System.out.println(getDriver().getCurrentUrl());
    	Thread.sleep(5000);
    	getDriver().takeScreenshot(browsertype, BrowserVersion);
    	System.out.println("end case_001xxx()方法");
    }
    
    
    @AfterMethod
    public void aftercase() throws Exception {
    	System.out.println("进入aftercase方法");
    }
    
    @AfterClass
    public void tearDown() throws Exception {
    	System.out.println("进入tearDown方法");
    	getDriver().quit();
    }
    
    public void failReWrite(String message) {
		String imgurl = "";
		if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message+" Check:"+" ltltlta target=_blank href=file://"+imgurl+"gtgtgtScreenShotltltlt/agtgtgt"+" ");
	}
	
	
	public void assertTrueReWrite(String message, boolean condition) {
        if (!condition) {
        	failReWrite(message);
        }
    }
	
    
    public String getDateTime(){  
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
        return df.format(new Date());  
    }

}
