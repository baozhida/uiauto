package com.tn.automation.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.tn.automation.browser.WebBrowser;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CommonFunction {

	static CaseResult result = new CaseResult();

	// private static class LazyHolder {
	// private static final CommonFunction INSTANCE = new CommonFunction();
	// }
	// private CommonFunction(){}
	// public static final CommonFunction getInstance() {
	// return LazyHolder.INSTANCE;
	// }
	private WebBrowser driver;

	public CommonFunction(WebBrowser driver) {
		super();
		this.driver = driver;
	}

	/**
	 * 字符串替换
	 * 
	 * @param poParam
	 */
	public String replaceStr(String fromStr, String form, String to) {
		String toStr = fromStr.replace(form, to);
		return toStr;

	}

	/**
	 * 检查页面引用的js文件能否正常请求
	 * 
	 *
	 * 
	 */
	public CaseResult checkJsURLConnect() {
		String jsurl;
		String msg = "校验有错误： \r\n";
		CloseableHttpResponse response = null;
		List<WebElement> elements = driver.findElements("xpath:://script[@src]");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < elements.size(); i++) {
			list.add(elements.get(i).getAttribute("src"));
		}
		boolean flag = true;
		result.reset();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().contains("tuniucdn.com")) {
				jsurl = list.get(i).toString().trim();
				CloseableHttpClient httpclient = HttpClients.createDefault();
				/** 发送get请求 **/
				HttpGet httpGet = new HttpGet(jsurl);
				try {
					response = httpclient.execute(httpGet);
				} catch (IOException e) {
					System.out.println("连接 " + jsurl + " 请求异常");
				}
				/** 请求发送成功，并得到响应 **/
				if (response.getStatusLine().getStatusCode() != 200) {
					flag = false;
					msg = msg + "第" + (i + 1) + " JS URL=" + jsurl + " 请求失败，返回码是 "
							+ response.getStatusLine().getStatusCode() + " ; \r\n";
				}
			}
		}

		if (!flag) {
			result.setKey(flag);
			result.setMsg(msg);
		} else {
			System.out.println("All JS URL connect sucess,return 200");
		}

		return result;

	}

	/**
	 * 检查页面引用的css文件能否正常请求
	 * 
	 * 
	 * 
	 */
	public CaseResult checkCssURLConnect() {
		String cssurl;
		String msg = "校验有错误： \r\n";
		CloseableHttpResponse response = null;
		List<WebElement> elements = driver.findElements("xpath:://link[@href]");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < elements.size(); i++) {
			list.add(elements.get(i).getAttribute("href"));
		}
		boolean flag = true;
		result.reset();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).toString().contains("tuniucdn.com")) {
				cssurl = list.get(i).toString().trim();
				CloseableHttpClient httpclient = HttpClients.createDefault();
				/** 发送get请求 **/
				HttpGet httpGet = new HttpGet(cssurl);
				try {
					response = httpclient.execute(httpGet);
				} catch (IOException e) {
					System.out.println("连接 " + cssurl + " 请求异常");
				}
				/** 请求发送成功，并得到响应 **/
				if (response.getStatusLine().getStatusCode() != 200) {
					flag = false;
					msg = msg + "第" + (i + 1) + " CSS URL=" + cssurl + " 请求失败，返回码是 "
							+ response.getStatusLine().getStatusCode() + " ; \r\n";
				}
			}
		}
		if (!flag) {
			result.setKey(flag);
			result.setMsg(msg);
		} else {
			System.out.println("All CSS URL connect sucess,return 200");
		}
		return result;

	}

	/**
	 * 检查页面引用的image文件能否正常请求
	 * 
	 * 
	 */
	public CaseResult checkPicConnect() {
		String msg = "校验有错误： \r\n";
		boolean flag = true;
		List<WebElement> elements = driver.findElements("xpath:://img");// 获取所有img节点
		StringBuffer sbURL = new StringBuffer();
		String src;
		int Count200 = 0;
		int Counterror = 0;
		int CountSrcnull = 0;

		if (elements.size() == 0) {
			System.out.println("当前页面不存在img节点的图片，请确认！");
		} else {
			System.out.println("当前页面img节点的图片个数=" + elements.size());
			for (int i = 0; i < elements.size(); i++) {
				try {
					src = elements.get(i).getAttribute("SRC").trim();
					// System.out.println("第" + (i + 1) + "个图片的URL=" + src);
					if (src == null) {
						CountSrcnull = CountSrcnull + 1;
					}
				} catch (Exception e) {
					System.out.println("第" + (i + 1) + "个图片img节点不存在SRC属性，请检查!");
					continue;
				}

				CloseableHttpClient httpclient = HttpClients.createDefault();
				/** 发送get请求 **/
				HttpGet httpGet = new HttpGet(src);
				CloseableHttpResponse response = null;
				try {
					response = httpclient.execute(httpGet);
				} catch (IOException e) {
					System.out.println("连接 " + src + " 请求异常");
				}
				/** 请求发送成功，并得到响应 **/
				int code = response.getStatusLine().getStatusCode();
				if (code == 200) {
					Count200 = Count200 + 1;
				} else {
					flag = false;
					Counterror = Counterror + 1;
					msg = msg + "第" + (i + 1) + " imgae URL=" + src + " 请求失败，返回码是 "
							+ response.getStatusLine().getStatusCode() + " ; \r\n";
				}

			}
			System.out.println("图片的URL的SRC是空的个数是" + CountSrcnull);
			System.out.println("图片的URL响应消息码是200的个数是" + Count200);
			System.out.println("图片的URL响应消息码是非200的个数是" + Counterror);

		}
		if (!flag) {
			result.setKey(flag);
			result.setMsg(msg);
		} else {
			System.out.println("All images URL connect sucess,return 200");
		}
		return result;
	}

}
