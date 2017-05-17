package com.tn.automation.testngcase;

//import static org.testng.AssertJUnit.assertTrue;
//import static org.testng.AssertJUnit.fail;
import static com.tn.automation.browser.Browsers.initBrowser;
import static com.tn.automation.browser.Browsers.getDriver;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tn.automation.util.CaseResult;
import com.tn.automation.util.CommonFunction;
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
    	initDriver();
    	getDriver().open("http://www.tuniu.com");
    	Thread.sleep(5000);
    }
    
    @BeforeMethod
    public void beforeMethod() throws Exception {
    	System.out.println("进入beforecase方法");
    }

    @Test
    public void case_001检查首页css和js引用正常连接() throws Exception {
    	System.out.println("start case_001");
    	//getDriver().scrollToBottom();
    	//检查js引用
    	CaseResult result = CommonFunction.checkJsURLConnect();
    	Assert.assertTrue(result.getKey(),result.getMsg());
    	//检查css引用
    	result = CommonFunction.checkCssURLConnect();
    	Assert.assertTrue(result.getKey(),result.getMsg());
    	
    	//getDriver().takeScreenshot(browsertype, BrowserVersion);
    	System.out.println("end case_001()");
    }
    
    
    @Test//(enabled = false)
    public void case_002检查首页图片能正常连接() throws Exception {
    	System.out.println("start case_002()");
    	//getDriver().scrollToBottom();
    	CaseResult result =CommonFunction.checkPicConnect();
    	Assert.assertTrue(result.getKey(),result.getMsg());
    	System.out.println("end case_002()");
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
