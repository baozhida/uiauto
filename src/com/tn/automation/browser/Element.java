package com.tn.automation.browser;

import static com.tn.automation.browser.Browsers.getDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import com.tn.automation.browser.Browsers;
import com.tn.automation.browser.ComboBox;
import com.tn.automation.util.Path;
import com.tn.automation.browser.BrowserType;
import com.tn.automation.util.ElementLocator;
import com.tn.automation.browser.WebBrowser;

public class Element
{
	
	//获取元素的宽度
    public int getSizeX(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
	     
		return element.getSize().getWidth();
		
	}
    //获取元素的高度
    public int getSizeY(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
    	WebElement element = getDriver().findElement(locatorexpression);
    	return element.getSize().getHeight();
		
	}
    //获取元素的横坐标X
    public int getLocationX(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
	    return element.getLocation().getX();
		
	}
    
  //获取元素的纵坐标Y
    public int getLocationY(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
	    return element.getLocation().getY();	
	}
    
    
    /**
     * 
     * 判断元素是否存在，存在即点击该元素，或者点击别的元素，不存在就不做操作<br/>
     * 
     * locatorexpression1等于 locatorexpression2 就是操作同一个元素，如果不相等，是点击第二个元素
     * @param     设定文件
     * @return void    返回类型
     * @throws  baozhida
     * @date 2016-4-15
      */
    
    public void verifyelementExistedToClick(String locatorexpression1,String locatorexpression2,boolean match) {
    	//ElementLocator locator1 = ElementLocator.create(locatorexpression1);
    	//ElementLocator locator2 = ElementLocator.create(locatorexpression2);
    	//WebElement element4click = getDriver().findElement(locator2);
    	boolean flag = getDriver().isElementExisted(locatorexpression1, match);;
    	//如果元素存在，点击元素，可以是当前元素，也可以是另一个元素
    	if(flag == match)
    	{	
    		getDriver().findElement(locatorexpression2).click();
    	}else{
    		System.out.println("元素 " + locatorexpression1 + "不存在，不做任何操作。");
    	}
    }
    
	
    /**
	 * 获取控件属性值（单个控件）
	 * 
	 * @param locatorexpression
	 *            待点击的控件定位符
	 * @param name
	 *            属性名
	 * @return String
	 *			属性值
	 */
	public String getElementAttribute(String locatorexpression,String name) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		return element.getAttribute(name);
	}
	
	/**
	 * 获取控件属性值（多个控件）
	 * 
	 * @param locatorexpression
	 *            待点击的控件定位符
	 * @param name
	 *            属性名
	 * @return String
	 *			属性值
	 */
	public String getElementAttributes(String locatorexpression,String name) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
    	List<WebElement> elements = getDriver().findElements(locatorexpression);
    	ArrayList<String> list = new ArrayList<String>();
    	for(int i=0;i<elements.size();i++) {
			String s = elements.get(i).getAttribute(name);
			list.add(s);
		}
		return list.toString();
	}
	
	/**
	 * 
	* 设置元素属性值
	* 
	* @param locatorexpression  待点击的控件定位符
	* @param name 属性名
	* @param value  属性值
	* @return void
	 */
	public void setElementAttribute(String locatorexpression,String name,String value) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		
		if(!value.equalsIgnoreCase("empty")){
		
			getDriver().executeJScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element , name , value);
		}else{
			getDriver().executeJScript("arguments[0].removeAttribute(arguments[1]);", element ,name);			
		}
	}

	/**
	 * 获取当前控件文本信息
	 * 
	 * @param locatorexpression
	 *            待点击的控件定位符
	 * @return string类型
	 *            控件文本
	 */
	public String getElementText(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		return element.getText();
	}

	/**
	 * 获取表格中的控件元素
	 * 
	 * @param locator
	 *            待点击的控件定位符
	 * @param value
	 *            文本信息[OUT]
	 */
	public String getTableElement(String locatorexpression, String xpath,int col_num,int row_num) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement tableElement = getDriver().findElement(locatorexpression);
		Table table = new Table(tableElement);
		WebElement element = table.findElementAtCell(xpath, col_num, row_num);
		return element.getText();
	}

	/**
	 * 模拟鼠标点击
	 * 
	 * @param locatorexpression
	 *            待点击的控件定位符
	 */
	public void elementClick(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		
		element.click();
	}

	/**
	 * 模拟鼠标点击并切换至新打开窗口 (适用于鼠标点击会打开新窗口且需在新窗口操作的场景)
	 * 
	 * @param locatorexpression
	 *            待点击的控件定位符
	 * @throws InterruptedException 
	 */
	public void elementClickAndSwitch(String locatorexpression) throws InterruptedException {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		Set<String> windowHandlesBefore = getDriver().getWindowHandles();
		System.out.println("windowHandlesBefore是： " + windowHandlesBefore);
		element.click();
		Thread.sleep(2000);
		getDriver().switchWindow(windowHandlesBefore);
	}

	/**
	 * 模拟鼠标右键点击
	 * 
	 * @param locatorexpression
	 *            待右击的控件定位符
	 */
	public void elementContextClick(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		Actions builder = getDriver().getActionBuilder();
		Action action = builder.contextClick(element).build();
		action.perform();
	}

	/**
	 * 模拟鼠标双击
	 * 
	 * @param locatorexpression
	 *            待双击的控件定位符
	 */
	public void elementDoubleClick(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		Actions builder = getDriver().getActionBuilder();
		Action action = builder.doubleClick(element).build();
		action.perform();
	}

	/**
	 * 模拟鼠标移动
	 * 
	 * @param locatorexpression
	 *            待移动至的控件定位符
	 */
	public void elementMove(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		Actions builder = getDriver().getActionBuilder();
		Action action = builder.moveToElement(element).build();
		action.perform();
    }

	/**
	 * 模拟鼠标拖拽。把元素拖拽到另一个元素上
	 * 
	 * @param source 需要拖拽的控件定位信息
	 * @param target 拖拽目标位置的控件定位信息
	 */
	public void elementDrag(String source, String target) {
		//ElementLocator locatorsource = ElementLocator.create(source);
		WebElement elementfrom = getDriver().findElement(source);
		//ElementLocator locatortarget = ElementLocator.create(target);
		WebElement elemento = getDriver().findElement(target);

		Actions builder = getDriver().getActionBuilder();
		Action action = builder.dragAndDrop(elementfrom, elemento).build();
		action.perform();
	}

	/**
	 * 模拟文本框的文字清空
	 * 
	 * @param locator
	 *            文本框控件定位符
	 * @param text
	 *            待输入的文字
	 */
	public void textClear(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		element.clear();
	}
	
	
	/**
	 * 模拟文本框的文字输入，若之前有内容，接在后面输入
	 * 
	 * @param locator
	 *            文本框控件定位符
	 * @param text
	 *            待输入的文字
	 */
	public void textInput(String locatorexpression, String text) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		System.out.println("待输入的文本是 " + text);
		if (text.length() != 0)
		{
			element.sendKeys(text);
		}
	}
	
	/**
	 * 模拟文本框的文字输入，先清空再输入
	 * 
	 * @param locator
	 *            文本框控件定位符
	 * @param text
	 *            待输入的文字
	 */
	public static void textClearAndInput(String locatorexpression, String text) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().findElement(locatorexpression);
		System.out.println("待输入的文本是 " + text);
		element.clear();
		if (text.length() != 0)
		{
			element.sendKeys(text);
		}
	}

	/**
	 * 模拟Checkbox的选择,全部选中，全部不选中
	 * 
	 * @param locatorexpression
	 *            Checkbox控件定位符
	 * @param flag
	 *            选择状态
	 */
	public static void checkBoxClick(String locatorexpression, boolean flag) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
        
		List<WebElement> elements = getDriver().findElements(locatorexpression);
		
		if (elements.size() < 0) {
			throw new RuntimeException("控件不存在！");
		}
		for (WebElement element : elements) {
			Select checkBox = new Select(element);

			checkBox.setSelected(flag);
		}
    }
	
	
	/**
	 * 模拟Checkbox的选择,指定第几个Checkbox，选中或者不选中
	 * 
	 * @param locatorexpression
	 *            Checkbox控件定位符
	 * @param flag
	 *            选择状态
	 * @param num
	 *            具体需要操作的复选框序号，1，2，3
	 */
	public static void checkBoxClick(String locatorexpression, int num, boolean flag) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
        
		List<WebElement> elements = getDriver().findElements(locatorexpression);
		
		if (elements.size() < 0) {
			throw new RuntimeException("控件不存在！");
		}else if (elements.size() < num) {
			throw new RuntimeException("第"+num+"个checkbox控件不存在！");
		}else {
			Select checkBox = new Select(elements.get(num-1));
			checkBox.setSelected(flag);
		}
    }

	/**
	 * 模拟Radiobutton的选择
	 * 
	 * @param locator
	 *            Radiobutton控件定位符
	 * @param isSelected
	 *            选择状态
	 */
	public static void radioClick(String locatorexpression, boolean flag) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
    	WebElement element = getDriver().findElement(locatorexpression);

    	Select radioButton = new Select(element);

		radioButton.setSelected(flag);
	}

	/**
	 * 模拟Combobox的选择,指定序号选中
	 * 
	 * @param locatorexpression Combobox控件定位符
	 * @param index 选项序号
	 */
	public void comboSelect(String locatorexpression, int index) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
    	WebElement element = getDriver().findElement(locatorexpression);

		ComboBox combo = new ComboBox(element);

        combo.selectByIndex(index);
	}
	
	/**
	 * 模拟Combobox的选择,指定 下拉选择的值 选中
	 * 
	 * @param locatorexpression Combobox控件定位符
	 * @param value 选项值
	 */
	public void comboSelect(String locatorexpression, String value) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
    	WebElement element = getDriver().findElement(locatorexpression);

		ComboBox combo = new ComboBox(element);
        combo.selectByVisibleText(value);
	}
	
	/**
	 * 模拟键盘动作
	 * 
	 * @param key 键盘动作
	 * 
	 */
//	public static void keyPress(Parameter poParam) {
//		String sKey = poParam.getString("key");
//		Integer iCount = poParam.getInt("count");
//
//	    for (int i=0;i<iCount;i++){
//			KeyBoard.keyPress(sKey);
//			Utility.sleep(1000);
//			logger.info("执行" + sKey + "操作");
//	    }
//	}
	/**
	 * 在不打开window对话框的情况下进行文件上传，
	 * 对应普通型的上传控件input，其属性type=file;
	 * 
	 * @param poParam
	 */
//	public static void uploadFile(Parameter poParam)
//
//	{
//		ElementLocator oLocator = ElementLocator.create(poParam
//				.getString("locator"));
//		CharSequence oText = poParam.getString("text");
//		String sFileSelector = poParam.getString("FileSelector");
//		
//        String path;
//		
//		if(sFileSelector.equals("本地"))
//		{
//			path = oText.toString();
//		}
//		else if(sFileSelector.equals("工程"))
//		{
//			path = ProjectRunner.getProjectPath() + (oText.toString());
//			//F:\autotest\SAT2.0\workspace\示例工程//file\123.jpg
//			path = path.replace("/","\\").replace("\\\\", "\\");
//		}
//		else
//		{
//			path = oText.toString();
//		}
//		logger.info("待上传文件是： " + path);
//
//		WebElement element = current().findElement(oLocator);
//
//		element.sendKeys(path);
//	}
	
	/**
	 * 模拟手工操作在打开window对话框的情况下进行文件上传，对应改进型第三方插件类的控件
	 * 比如属性type=flash
	 * @param poParam
	 */
//	public static void uploadFileAu3(Parameter poParam)
//	{
//		String sWinTitle = poParam.getString("winTitle");
//		String sInputID = poParam.getString("inputID");
//		String sBtnID = poParam.getString("btnID");
//		String sFilePath = poParam.getString("filePath");
//		String sFileSelector = poParam.getString("FileSelector");
//		
//		String path;
//		
//		if(sFileSelector.equals("本地"))
//		{
//			path = sFilePath;
//		}
//		else if(sFileSelector.equals("工程"))
//		{
//			path = ProjectRunner.getProjectPath() + sFilePath;
//			path = path.replace("/","\\").replace("\\\\", "\\");
//		}
//		else
//		{
//			path = sFilePath;
//		}
//		logger.info("待上传文件的地址：" + path);
////		File oUploadFile = new File(path);
////		File oAu3File = new File(ProjectRunner.getProjectPath() + exePath);
//		
//		String exePath = Path.getUploadFileAu3Path();
//		logger.info("执行文件上传的au3脚本地址：" + exePath);
//		
//		try
//		{
//			AutoIt.exeAu3(exePath, sWinTitle, sInputID, sBtnID, path);
//		}
//		catch(Exception e)
//		{
//			throw new RuntimeException("执行文件上传au3脚本失败，请检查！", e);
//		}		
//	}
	
	/**
	 * 获取页面控件数
	 * @param locatorexpression 控件定位符
	 * 
	 */
	public int getElementExistedSize(String locatorexpression) {	
    	//ElementLocator locator = ElementLocator.create(locatorexpression);
    	List<WebElement> elements = getDriver().findElements(locatorexpression);
		return elements.size();		
	}

    
	/**
	 * 手机浏览器向下滚动 查找元素
	 * @param locatorexpression 控件定位符
	 * @author baozhida
	 * @date 2016-04-12
	 */
	public void findElementByScroll(String locatorexpression) {	
		//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().scrollFindElement(locatorexpression);
		if(element == null){throw new RuntimeException("元素未找到!");}
	}
	
	/**
	 * 滚动手机浏览器查找元素，如果找到则点击
	 * @param locatorexpression 控件定位符
	 * @author baozhida
	 * @date 2016-04-12
	 */
	public static void clickElementByScroll(String locatorexpression) {	
		//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().scrollFindElement(locatorexpression);
		if(element == null){throw new RuntimeException("元素未找到!");}
		element.click();
	}
    
    
	/**
	 * 滚动手机浏览器查找元素，找到元素之后，浏览器滚动到元素纵坐标向上偏移offset像素位置
	 * @param locatorexpression 控件定位符
	 * @param offset 垂直方向的偏移量
	 * @author baozhida
	 * @date 2016-04-12
	 */
	public static void findElementandScroll(String locatorexpression,int offset) {	
		//ElementLocator locator = ElementLocator.create(locatorexpression);
		WebElement element = getDriver().scrollFindElement(locatorexpression, offset);
		if(element == null){throw new RuntimeException("元素未找到!");}
	}

}
