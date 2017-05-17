package com.tn.automation.browser;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static com.tn.automation.browser.Browsers.activate;

import com.tn.automation.util.ElementLocator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Function;
import com.tn.automation.util.Path;
import com.tn.automation.util.MatchMode;

public abstract class WebBrowser {

	// private static final long STABLE_TIME = 2000;
	private static final long MAX_WAIT_TIME = 20000;
	private static final long MAX_EXECUTE_TIME = 3 * 60 * 1000;
	private static final long INTERVAL = 200;

	private SimpleDateFormat dateFormat;

	protected WebDriver driver;
	protected Actions builder;
	protected BrowserType mType = null;

	public WebBrowser() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
	}

	public BrowserType getCurrentBrowserType() {
		return this.mType;
	}

	public WebDriver currentDriver() {

		return driver;
	}
	
	/**
	 *
	 * 获取WebDriver <br/>
	 * 
	 *  @param 
	 *  @return WebDriver 
	 *  
	 */
	public WebDriver getWebDriver() {
		return this.driver;
	}

	/**
	 * 浏览器输入url，并且最大化
	 * 
	 * @param url
	 *            待输入的网址
	 * @return
	 */
	public WebDriver open(String url) {
		long start = System.currentTimeMillis();
		driver.get(url);
		driver.manage().window().maximize();
		Path.waitForStable(start);
		return driver;
	}

	/**
	 * 模拟浏览器最大化
	 */
	public void maximize() {
		long start = System.currentTimeMillis();
		try {
			driver.manage().window().maximize();
		} catch (UnsupportedCommandException e) {
			System.out.println("Remote Driver does not support maximise");
		} catch (UnsupportedOperationException e) {
			System.out.println("暂时不支持最大化Opera浏览器");
		}
		Path.waitForStable(start);
	}

	/**
	 * 模拟浏览器重新输入URL
	 */
	public void locate(String url) {
		long start = System.currentTimeMillis();
		driver.get(url);
		Path.waitForStable(start);
	}

	/**
	 * 模拟浏览器回退
	 */
	public void back() {
		long start = System.currentTimeMillis();
		driver.navigate().back();
		Path.waitForStable(start);
	}

	/**
	 * 模拟浏览器前进
	 */
	public void forward() {
		long start = System.currentTimeMillis();
		driver.navigate().forward();
		Path.waitForStable(start);
	}

	/**
	 * 模拟浏览器刷新
	 */
	public void refresh() {
		long start = System.currentTimeMillis();
		driver.navigate().refresh();
		Path.waitForStable(start);
	}

	/**
	 * 模拟浏览器退出
	 */
	public void quit() {
		long start = System.currentTimeMillis();
		builder = null;
		driver.quit();
		Path.waitForStable(start);
	}

	/**
	 * 模拟浏览器关闭
	 */
	public void close() {
		long start = System.currentTimeMillis();
		builder = null;
		driver.close();
		Path.waitForStable(start);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public void takeScreenshot(String browsertype, String browserversion) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date curretntime = new Date();
		String screenPath = Path.getReportSnapshotPath()
				+ new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(curretntime) + "_" + browsertype + "_V"
				+ browserversion + ".jpg";
		try {
			FileUtils.copyFile(scrFile, new File(screenPath));
			System.out.println("浏览器截屏成功，路径是: " + screenPath);
		} catch (IOException e) {
			System.out.println("浏览器截屏失败" + e.toString());
		}
	}

	/**
	 * Keyword名称：浏览器激活 功能描述：激活浏览器（多浏览器场景下） 多浏览器场景下激活指定浏览器
	 * ，若未指定浏览器别名，默认激活别名为空的浏览器，否则报错
	 *
	 * @param alias
	 *            待激活浏览器别名[alias]
	 * @return 返回值为空
	 * @throws Exception
	 */
	public static void browserActivate(String alias) {
		WebBrowser browser = activate(alias);
		if (browser == null) {
			throw new RuntimeException("Browser with alias <" + alias + "> is not exist.");
		}
	}

	/**
	 * 执行JavaScript脚本
	 * 
	 * @param script
	 *            待执行JS脚本内容
	 * @return 执行结果
	 */
	public Object executeJScript(String script, Object... args) {
		return ((JavascriptExecutor) driver).executeScript(script, args);
	}

	/**
	 * 切换至最新打开的窗口
	 * 
	 * @param windowHandlesBefore
	 *            新打开窗口之前的windowHandle集合
	 * @param windowHandlesNow
	 *            打开新窗口之后的句柄
	 */
	public void switchWindow(Set<String> windowHandlesBefore) {
		Set<String> windowHandlesNow = driver.getWindowHandles();
		System.out.println("Current windowhandles: " + windowHandlesNow);

		for (String windowHandleNow : windowHandlesNow) {
			if (!windowHandlesBefore.contains(windowHandleNow)) {
				driver.switchTo().window(windowHandleNow);
			}
		}
	}

	/**
	 * 关闭当前窗口，切换至上一个窗口，适用于点击跳转之后，返回的情况
	 * 
	 * @param windowHandlesBefore
	 *            新打开窗口之前的windowHandle集合
	 */
	public void closeAndSwitch() {
		Set<String> windowHandlesNow = driver.getWindowHandles();
		System.out.println("Current windowhandles: " + windowHandlesNow);

		ArrayList<String> stack = new ArrayList<String>();

		for (String current : windowHandlesNow) {
			stack.add(current);
		}
		for (int i = stack.size() - 1; i > 0; i--) {
			driver.switchTo().window(stack.get(i));
			driver.close();
		}
		driver.switchTo().window(stack.get(0));
	}

	public Actions getActionBuilder() {
		if (builder == null) {
			builder = new Actions(driver);
		}

		return builder;
	}

	/**
	 * 切换Frame句柄到新框架
	 * 
	 * @param locator
	 *            Frame元素
	 * @throws Exception
	 */
	public void switchFrame(String locator) throws Exception {
		WebElement element = findElement(locator);
		driver.switchTo().frame(element);
	}

	/**
	 * 切换Frame句柄到主框架
	 */
	public void switchFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * 根据控件定位符查找控件
	 * 
	 * @param locator
	 *            待查找控件定位符
	 * @return 控件对象
	 * @throws Exception
	 */
	public WebElement findElement(String locator) {
		final ElementLocator el = ElementLocator.create(locator);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).withTimeout(MAX_WAIT_TIME, MILLISECONDS)
				.pollingEvery(INTERVAL, MILLISECONDS).ignoring(RuntimeException.class);

		try {
			WebElement element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return el.locate(driver);
				}
			});

			return element;
		} catch (Exception e) {
			throw new RuntimeException("控件无法找到，请检查输入的控件定位符是否正确。", e.getCause());
		}
	}

	public WebElement findElement(String locator, int timeout) {
		final ElementLocator el = ElementLocator.create(locator);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).withTimeout(timeout, MILLISECONDS)
				.pollingEvery(800, MILLISECONDS).ignoring(RuntimeException.class);

		try {
			WebElement element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return el.locate(driver);
				}
			});

			return element;
		} catch (Exception e) {
			throw new RuntimeException("控件无法找到，请检查输入的控件定位符是否正确。", e.getCause());
		}
	}

	/**
	 * 根据控件定位符查找控件
	 * 
	 * @param locator
	 *            待查找控件定位符
	 * @return 控件对象
	 */
	public List<WebElement> findElements(String locator) {
		final ElementLocator el = ElementLocator.create(locator);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).withTimeout(MAX_WAIT_TIME, MILLISECONDS)
				.pollingEvery(INTERVAL, MILLISECONDS).ignoring(RuntimeException.class);

		List<WebElement> elements = wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return el.findElements(driver);
			}
		});

		List<WebElement> list = new ArrayList<WebElement>();
		for (WebElement element : elements) {
			list.add(element);
		}
		return list;
	}

	public List<WebElement> findElements(String locator, long maxWaitTime) {
		final ElementLocator el = ElementLocator.create(locator);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).withTimeout(maxWaitTime, MILLISECONDS)
				.pollingEvery(INTERVAL, MILLISECONDS).ignoring(RuntimeException.class);

		List<WebElement> elements = wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return el.findElements(driver);
			}
		});

		List<WebElement> list = new ArrayList<WebElement>();
		for (WebElement element : elements) {
			list.add(element);
		}
		return list;
	}

	// 等待页面元素可见
	public void waitElementVisibleBy(final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// 等待页面元素不可见
	public void waitElementInvisibleBy(final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIME);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	// 等待页面元素数量等于固定值
//	public void waitElementNumBy(final By locator, Integer num) {
//		WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIME);
//		wait.until(ExpectedConditions.numberOfElementsToBe(locator, num));
//	}


	public boolean isAlertExist() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public String getAlertText() {
		return driver.switchTo().alert().getText();
	}

	public void clickAlert(boolean isAccept) {
		if (isAccept) {
			driver.switchTo().alert().accept();
		} else {
			driver.switchTo().alert().dismiss();
		}
	}

	public Set<String> getWindowHandles() {
		Set<String> windowHandles = driver.getWindowHandles();
		return windowHandles;
	}

	/**
	 * 滚动条纵向移动,指定移动的距离 功能描述：滚动条纵向移动 <br>
	 * 
	 * @param 滚动条滚动距离[height]。-1移动到底部
	 * @return 返回值为空
	 */
	public void scrollVerticalBar(Integer height) {
		if (height == -1) {
			// 移动滚动条至页尾
			executeJScript("window.scrollTo(0,document.body.scrollHeight);");
		} else {
			executeJScript("window.scrollTo(0," + height + ");");
		}
	}

	/**
	 * 浏览器滚动到页面底部，每次移动500像素 功能描述：滚动条纵向移动, 每次移动500像素
	 * 
	 * @param
	 * @return 返回值为空
	 */
	public void scrollToBottom() throws Exception {
		int iHeight = 0;
		int iBody = 1;
		boolean istrue = true;
		String oLocator = "xpath:://body";
		WebElement element = findElement(oLocator);
		iBody = element.getSize().getHeight();
		System.out.println("页面高度是" + iBody);
		while (istrue) {
			iHeight = iHeight + 500;
			executeJScript("window.scrollTo(0," + iHeight + ");");
			sleep(2000);
			if (iHeight > iBody) {
				istrue = false;
			}
		}

	}

	/**
	 * Keyword名称：获取cookie的值 功能描述：获取cookie的value属性值 <br>
	 *
	 * @param poParam
	 *            哈希Map类型，其中包括cookie名称[cookieName],cookie的value属性值存放变量[
	 *            cookieValue]
	 * @return 返回值为空
	 */
	public String getCookieValueByName(String cookieName) {
		Cookie cookie = driver.manage().getCookieNamed(cookieName);
		return cookie.getValue();
	}

	/**
	 * Keyword名称：获取cookie的值 功能描述：获取cookie的value属性值 <br>
	 *
	 * @param cookieName
	 *            cookie的key
	 * @return 返回值为空
	 */
	public void deleteCookieValueByName(String cookieName) {
		driver.manage().deleteCookieNamed(cookieName);
	}

	/**
	 * Keyword名称：删除所有cookies 功能描述：删除所有的cookies
	 * 
	 * @param
	 * @return 返回值为空
	 */
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	/**
	 * 增加cookie<br/>
	 * 功能描述：增加cookie，根据其名称和属性值
	 * 
	 * @param key
	 *            cookie的键[key]
	 * @param value
	 *            cookie的value值[value]
	 * @return 返回值为空
	 */
	public void addCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		driver.manage().addCookie(cookie);
	}

	/**
	 * 增加多个cookie,批量增加cookie<br/>
	 * 功能描述：增加cookie，根据其名称和属性值
	 * 
	 * @param cookies
	 *            字符串 key=value格式，对组分号英文隔开（;）
	 * @return 返回值为空
	 */
	public void addAllCookie(String cookies) {
		String sKey, sValue;
		String[] cookieStrArr = cookies.split(";");
		for (int i = 0; i < cookieStrArr.length; i++) {
			String[] KeyandValue = cookieStrArr[i].split("=");
			sKey = KeyandValue[0];
			sValue = KeyandValue[1];
			addCookie(sKey, sValue);
		}
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 校验控件可见性
	 * 
	 * @param locator
	 *            待校验控件定位符
	 * @param isVisible
	 *            预期可见性
	 */
	public void verifyElementVisible(String locator, boolean isVisible) {
		WebElement element = findElement(locator);

		if (element.isDisplayed() != isVisible) {
			throw new RuntimeException("校验控件的可见性失败, 实际值是[" + !isVisible + "],期望值是[" + isVisible + "].");
		}
	}

	/**
	 * 校验控件是否可用
	 * 
	 * @param locator
	 *            待校验控件定位符
	 * @param isEnabled
	 *            预期可用性
	 */
	public void verifyElementEnabled(String locator, boolean isEnabled) {
		WebElement element = findElement(locator);

		if (element.isEnabled() != isEnabled) {
			throw new RuntimeException("校验控件的可用性失败, 实际值是[" + !isEnabled + "],期望值是[" + isEnabled + "].");
		}
	}

	/**
	 * 校验控件是否选中,如checkbox、radio
	 * 
	 * @param locator
	 *            待校验控件定位符
	 * @param isSelected
	 *            预期选中状态
	 */
	public void verifyElementSelected(String locator, boolean isSelected) {
		WebElement element = findElement(locator);

		if (element.isSelected() != isSelected) {
			throw new RuntimeException("校验控件是否选中失败, 实际值是[" + !isSelected + "],期望值是[" + isSelected + "].");
		}
	}

	/**
	 * 校验控件是否存在
	 * 
	 * @param locator
	 *            控件定位符
	 * @param isExisted
	 *            存在性
	 */
	public void verifyElementExisted(String locator, boolean isExisted) {
		List<WebElement> elements = findElements(locator);

		boolean result_act = true;
		if (elements.size() == 0) {
			result_act = false;
		}

		System.out.println("获取控件列表个数为" + elements.size());
		if (result_act != isExisted) {
			throw new RuntimeException("校验控件是否存在失败, 实际值是[" + result_act + "],期望值是[" + isExisted + "].");
		}
	}
	 /**
	 *
	 * 有返回值类型的方法，判断元素是否存在作
	 * @param locator 定位器
	 * @param isExisted 是否存在标识
	 * @return boolean 返回类型
	 * @throws baozhida
	 * @date 2016-4-15
	 */
	
	public boolean isElementExisted(String locator, boolean isExisted) {
		List<WebElement> elements = findElements(locator);
		boolean flag = false;
		if (elements.size() != 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 校验元素文本内容
	 * 
	 * @param locator
	 *            待校验控件定位符
	 * @param text
	 *            预期文本
	 * @param mode
	 *            匹配模式: ExactMatch=精确匹配 PartialMatch=部分匹配 RegexMatch=正则表达式匹配
	 */
	public void verifyElementText(String locator, String text, MatchMode mode, boolean matched) {
		WebElement element = findElement(locator);

		boolean bIsMatched = false;
		switch (mode) {
		case ExactMatch:
			bIsMatched = element.getText().equals(text);
			break;
		case PartialMatch:
			bIsMatched = element.getText().contains(text);
			break;
		case RegexMatch:
			bIsMatched = element.getText().matches(text);
			break;
		}

		if (bIsMatched != matched) {
			throw new RuntimeException(
				"校验元素文本内容失败, 实际值是[" + element.getText() + "], 期望值是[" + text + "],预期匹配状态是[" + matched + "].");
		}
	}

	/**
	 * 校验元素属性值
	 *
	 * @param locator
	 *            待校验控件定位符
	 * @param attribute
	 *            待校验控件属性
	 * @param value
	 *            预期属性值
	 * @param matched
	 *            期望匹配结果
	 * @param mode
	 *            匹配模式: ExactMatch=精确匹配 PartialMatch=部分匹配 RegexMatch=正则表达式匹配
	 */
	public void verifyElementAttribute(String locator, String Attribute, String value, MatchMode mode,
			boolean matched) {
		WebElement element = findElement(locator);

		boolean bIsMatched = false;
		switch (mode) {
		case ExactMatch:
			bIsMatched = element.getAttribute(Attribute).equals(value);
			break;
		case PartialMatch:
			bIsMatched = element.getAttribute(Attribute).contains(value);
			break;
		case RegexMatch:
			bIsMatched = element.getAttribute(Attribute).matches(value);
			break;
		}

		if (bIsMatched != matched) {
			throw new RuntimeException("校验元素属性值失败, 实际值是[" + element.getAttribute(Attribute) + "], 期望值是[" + value
					+ "],预期匹配状态是[" + matched + "].");
		}
	}

	/**
	 * 校验当前页面URL
	 *
	 * @param text
	 *            预期URL串
	 * @param mode
	 *            匹配模式: ExactMatch=精确匹配 PartialMatch=部分匹配 RegexMatch=正则表达式匹配
	 */
	public void verifyCurrentUrl(String text, MatchMode mode, boolean matched) {
		String sUrl = getCurrentUrl();

		boolean bIsMatched = false;
		switch (mode) {
		case ExactMatch:
			bIsMatched = sUrl.equals(text);
			break;
		case PartialMatch:
			bIsMatched = sUrl.contains(text);
			break;
		case RegexMatch:
			bIsMatched = sUrl.matches(text);
			break;
		}

		if (bIsMatched != matched) {
			throw new RuntimeException("校验当前网页地址失败, 实际值是[" + sUrl + "], 期望值是[" + text + "], 期望结果是[" + matched + "].");
		}
	}

	/**
	 * 校验弹出框
	 *
	 * @param text
	 *            预期文本
	 * @param mode
	 *            匹配模式: ExactMatch=精确匹配 PartialMatch=部分匹配 RegexMatch=正则表达式匹配
	 */
	public void verifyAlertText(String text, MatchMode mode, boolean matched) {

		boolean isExist = isAlertExist();

		if (isExist) {
			String actualText = getAlertText();

			boolean bIsMatched = false;
			switch (mode) {
			case ExactMatch:
				bIsMatched = actualText.equals(text);
				break;
			case PartialMatch:
				bIsMatched = actualText.contains(text);
				break;
			case RegexMatch:
				bIsMatched = actualText.matches(text);
				break;
			}

			if (bIsMatched != matched) {
				throw new RuntimeException(
						"校验弹出框文本失败, 实际值是[" + actualText + "], 期望值是[" + text + "], 期望匹配结果是[" + matched + "].");
			}
		} else {
			throw new RuntimeException("校验弹出框文本失败, 弹出框不存在.");
		}

	}

	/**
	 * 
	* verifyComboSelectOption
	* (校验下拉选项是否存在某个值)
	* @param locator 控件定位器
	* @param text 期望值
	* @param matched boolean值，期望匹配还是不匹配
	* @return void 返回类型
	* 
	 */
	public void verifyComboBoxSelectOption(String locator, String text, boolean matched) {
		WebElement comboSelect = findElement(locator);

		ComboBox combo = new ComboBox(comboSelect);
		List<WebElement> options = combo.getOptions();
		boolean bIsMatched = false;
		for (WebElement e : options) {
			if (e.getText().equalsIgnoreCase(text)) {
				bIsMatched = true;
			}
		}
		if (bIsMatched != matched) {
			throw new RuntimeException("校验下拉列表框选项失败, 期望值是[" + text + "], 期望匹配结果是[" + matched + "].");
		}
	}
	
	/**
	 * 
	* verifyComboSelectOption
	* (校验下拉选项是否存在某些值)
	* @param locator 控件定位器
	* @param texts 期望值字符串数组
	* @param matched boolean值，期望匹配还是不匹配
	* @return void 返回类型
	* 
	 */
	public void verifyComboBoxSelectOption(String locator, String[] texts, boolean matched) {
		WebElement comboSelect = findElement(locator);

		ComboBox combo = new ComboBox(comboSelect);
		List<WebElement> options = combo.getOptions();
		boolean bIsMatched = false;
		for (int i = 0; i < texts.length; i++) {
			if (options.get(i).getText().equalsIgnoreCase(texts[i])) {
				bIsMatched = true;
				System.out.println("校验成功" + texts[i]);
			}
		}
		if (bIsMatched != matched) {
			throw new RuntimeException("校验下拉列表框选项失败, 期望值是[" + texts + "], 期望匹配结果是[" + matched + "].");
		}
	}
	

	/**
	 * 手机浏览器通过每次向下移动300像素查找元素，查到元素之后，移动到该元素位置，最多移动20次,
	 *
	 * @param oLocator
	 * @author baozhida
	 * @date 2016-04-12
	 */

	public WebElement scrollFindElement(String oLocator) {
		int i = 0;
		int iHeight = 0;
		WebElement ele = null;
		while (i < 20) {
			List<WebElement> elements = findElements(oLocator);
			if (elements.size() > 0) {
				System.out.println("手机浏览器滚动" + i + "次,找到元素");
				ele = elements.get(0);
				// 滚动告诉设定为元素的纵坐标位置向上偏移50像素
				iHeight = ele.getLocation().getY() - 50;
				System.out.println("元素的纵向位置向上偏移50像素的位置是" + iHeight);
				executeJScript("window.scrollTo(0," + iHeight + ");");
				break;
			} else {
				// 没有找到元素就向下移动300像素
				iHeight = iHeight + 300;
				executeJScript("window.scrollTo(0," + iHeight + ");");
				sleep(500);
			}
			i++;
		}
		return ele;

	}

	/**
	 * 手机浏览器通过每次向下移动300像素查找元素，查到元素之后，移动到该元素位置，最多移动20次,
	 *
	 * @param oLocator
	 * @author baozhida
	 * @date 2016-04-12
	 */

	public WebElement scrollFindElement(String oLocator, int offset) {
		int i = 0;
		int iHeight = 0;
		WebElement ele = null;
		while (i < 20) {
			List<WebElement> elements = findElements(oLocator);
			if (elements.size() > 0) {
				System.out.println("手机浏览器滚动" + i + "次,找到元素");
				ele = elements.get(0);
				// 滚动告诉设定为元素的纵坐标位置向上偏移50像素
				iHeight = ele.getLocation().getY() - offset;
				System.out.println("元素的纵向位置向上偏移" + offset + "像素的位置是" + iHeight);
				executeJScript("window.scrollTo(0," + iHeight + ");");
				break;
			} else {
				// 没有找到元素就向下移动300像素
				iHeight = iHeight + 300;
				executeJScript("window.scrollTo(0," + iHeight + ");");
				sleep(500);
			}
			i++;
		}
		return ele;

	}
}