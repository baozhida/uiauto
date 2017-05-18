package com.tn.automation.testngcase;

//import static org.testng.AssertJUnit.assertTrue;
//import static org.testng.AssertJUnit.fail;
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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tn.automation.util.CaseResult;
import com.tn.automation.util.CommonFunction;
import com.tn.automation.util.Path;
import com.alibaba.fastjson.JSONObject;

import com.tn.automation.browser.BrowserType;
import com.tn.automation.browser.Browsers;
import com.tn.automation.browser.Element;
import com.tn.automation.browser.WebBrowser;

public class RunWEB {
    
	private String browsertype;
	private String browserversion;
	private String remoteIP;
	boolean isFail;
	int initcount = 0;
	private WebBrowser driver;
	private Browsers browser = new Browsers();
	private CommonFunction cf;
	private Element el;

    
    @BeforeTest
    @Parameters({ "browsertype", "browserversion", "remoteIP" })  
    public void setUp(String browsertype, String browserversion, String remoteIP) throws Exception {
    	this.browsertype = browsertype;
    	this.browserversion = browserversion;
    	System.out.println("进入setUp方法");
        System.out.println(browserversion);
        driver = browser.initBrowser("chrome"+browserversion, browsertype, browserversion, remoteIP);
        cf = new CommonFunction(driver);
        el = new Element(driver);
    	Thread.sleep(5000);
    }
    
    @BeforeMethod
    public void beforeMethod() throws Exception {
    	System.out.println("进入beforecase方法");
    }

    @Test
    public void case_001检查北京首页css_js引用连接() throws Exception {
    	driver.open("http://www.baidu.com");
    	driver.scrollToBottom();
    	Thread.sleep(5000);
    	//检查js引用
    	CaseResult result = cf.checkJsURLConnect();
    	Assert.assertTrue(result.getKey(),result.getMsg());
    	//检查css引用
    	result = cf.checkCssURLConnect();
    	Assert.assertTrue(result.getKey(),result.getMsg());
    	//el.getSizeX("xpath:://div[@id=\"lg\"]");
    	System.out.println("end case_001()");
    }
    
    
    @Test//(enabled = false)
    public void case_002检查北京首页图片正常连接() throws Exception {
    	driver.open("http://bj.tuniu.com");
    	driver.scrollToBottom();
    	CaseResult result =cf.checkPicConnect();
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
    	driver.quit();
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
