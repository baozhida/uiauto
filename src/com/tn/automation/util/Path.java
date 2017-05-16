package com.tn.automation.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class Path {
	private static final long STABLE_TIME = 1000;

	
	private static SimpleDateFormat moDateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");

	public static final String MAP_PATH = "/maps/";
	private static final String CONFIG_PATH = "/autodependency/config/";
	public static final String LOG_PATH = "/logs/";
	public static final String REPORT_PATH = "/report/";
	public static final String BIN_PATH = "/autodependency/bin/";
	public static final String LIB_PATH = "/autodependency/lib/";
	public static final String ANDROID = "/autodependency/mobile/android/";// android path;
	public static final String APPIUM = "/autodependency/mobile/appium";// appium.bat path;
	public static final String HELP_PATH = "/help/";// 工具目录下help目录
	public static final String JRE_PATH = "/jre/";// 工具目录下jre目录---ByGR
	private static String msCurrentReportFolder = "";

	private static final String RUNTIME_PATH = CONFIG_PATH + "runtime.properties";
	private static final String REPORT_CODE_PATH = CONFIG_PATH + "codebase";
	private static final String REPORT_RESULT_HTML_PATH = CONFIG_PATH + "testresult.html";
	private static final String REPORT_RESULTMAIN_HTML_PATH = CONFIG_PATH + "result.html";
	private static final String REPORT_RESULTCLASSIFY_HTML_PATH = CONFIG_PATH + "resultClassify.html";
	private static final String REPORT_LOG_HTML_PATH = CONFIG_PATH + "testlog.html";
	public static final String IE_DRIVER_LOG_PATH = LOG_PATH + "IEDriverServer.log";
	public static final String HELPDOC_PAHT = HELP_PATH + "SAT用户指南.chm";// 用户指南

	// 脚本工程下地址
	public static final String PROJECT_CONFIG = "/config/";// 用户自定义JAR
															// keyword映射文件
	public static final String DATAPOOLS = "datapools/";// 数据表文件存放目录，在脚本工程下；

	public static final String REFERENCE_PATH = PROJECT_CONFIG + "DataConfig.properties";// 工程目录下config文件
	public static final String TESTPLAN_PATH = PROJECT_CONFIG + "testplan/testplan.tp";// 工程目录下config/testplan/testplan.tp;
	public static final String USER_DEF_KEYWORDSET = "keyword/";// 组合关键字keyword文件夹地址
	public static final String USER_DEF_KEYWORD_LIB = USER_DEF_KEYWORDSET + "lib/";// 用户自定义关键字lib库地址

	// 目录bin下文件地址
	public static final String KILL_ALL_PATH = BIN_PATH + "killAll.exe";
	public static final String CHROME_DRIVER_PATH = BIN_PATH + "chromedriver.exe";
	public static final String FIREFOX_DRIVER_PATH = BIN_PATH + "geckodriver.exe";
	public static final String IE_DRIVER_PATH = BIN_PATH + "IEDriverServer.exe";
	public static final String UPLOAD_FILE_AU3_PATH = BIN_PATH + "uploadFileAu3.exe";

	public static final File xPath = new File("");
	public static final String currentPath = xPath.getAbsolutePath() + "\\";

	// 目录android/adb地址
	public static final String ANDROID_ADB_PATH = ANDROID + "adb/";

	public static String getRuntimePath() {
		return currentPath + RUNTIME_PATH;
	}

	public static String getKillAllPath() {
		return currentPath + KILL_ALL_PATH;
	}

	public static String getCallSAPPath() {
		return getBinPath() + "CallSAP.exe";//
	}

	public static String getAppConfigPath() {
		// return ProjectRunner.getAppPath() + CONFIG_PATH;
		return currentPath + CONFIG_PATH;
	}

	public static String getAppLibPath() {
		return currentPath + LIB_PATH;
	}

	/*
	 * 返回结果链接
	 */
	public static String createNewReportFolder() {
		String sResultLink = "";
		File oReportFolder = new File(System.getProperty("user.dir") + REPORT_PATH);
		if (!oReportFolder.exists()) {
			oReportFolder.mkdirs();
			try {
				FileUtils.copyDirectoryToDirectory(new File(currentPath + REPORT_CODE_PATH), oReportFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		msCurrentReportFolder = "report" + moDateFormat.format(new Date());

		File oDataFolder = new File(getReportDataPath());
		oDataFolder.mkdirs();

		File oSnapshotFolder = new File(getReportSnapshotPath());
		oSnapshotFolder.mkdirs();

		File oStackFolder = new File(getReportStackPath());
		oStackFolder.mkdirs();

		try {
			sResultLink = System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/testresult.html";
			// FileUtils.copyFile(new File(currentPath +
			// REPORT_RESULT_HTML_PATH),
			FileUtils.copyFile(new File(currentPath + REPORT_RESULT_HTML_PATH), new File(sResultLink));

			FileUtils.copyFile(new File(currentPath + REPORT_LOG_HTML_PATH),
					new File(System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/testlog.html"));
			// 添加日志分类功能---By GR
			FileUtils.copyFile(new File(currentPath + REPORT_RESULTMAIN_HTML_PATH),
					new File(System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/result.html"));
			FileUtils.copyFile(new File(currentPath + REPORT_RESULTCLASSIFY_HTML_PATH), new File(
					System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/resultClassify.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sResultLink;
	}

	public static String getReportDataPath() {
		return System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/data/";
	}

	public static String getReportSnapshotPath() {
		return System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/snapshot/";
	}

	public static String getReportStackPath() {
		return System.getProperty("user.dir") + REPORT_PATH + msCurrentReportFolder + "/stack/";
	}

	public static String getBinPath() {
		return System.getProperty("user.dir") + "config/bin/";
	}

	public static String getDataConfigPath() {
		return System.getProperty("user.dir") + REFERENCE_PATH;
	}

	public static String getTestPlanPath() {
		return System.getProperty("user.dir") + TESTPLAN_PATH;
	}

	/**
	 * 
	 * @Title: getUserDefKeywordSetXmlFilePath @Description:
	 * (组合关键字文件夹地址，在工程目录下面) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getUserDefKeywordSetFolderPath() {
		return System.getProperty("user.dir") + USER_DEF_KEYWORDSET;
	}

	/**
	 * 
	 * @Title: getUserDefKeywordLibFolderPath @Description:
	 * (用户自定义关键字lib库地址) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getUserDefKeywordLibFolderPath() {
		return System.getProperty("user.dir") + USER_DEF_KEYWORD_LIB;
	}

	/**
	 * 
	 * @Title: getUserKeywordXMLFolderPath @Description: (用户自定义JAR
	 * keyword映射文件夹地址目录) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getUserKeywordXMLFolderPath() {
		return System.getProperty("user.dir") + PROJECT_CONFIG;
	}

	/**
	 * 
	 * @Title: getChromeDriverPath @Description: (获取
	 * bin目录下chromedriver.exe地址) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getChromeDriverPath() {
		return currentPath + CHROME_DRIVER_PATH;
	}

	/**
	 * 
	 * @Title: getFirefoxDriverPath @Description: (获取
	 * bin目录下geckodriver.exe地址) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getFirefoxDriverPath() {
		return currentPath + FIREFOX_DRIVER_PATH;
	}

	/**
	 * 
	 * @Title: getIEDriverPath @Description: (获取
	 * bin目录下iedriver.exe目录地址) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getIEDriverPath() {
		return currentPath + IE_DRIVER_PATH;
	}

	/**
	 * 
	 * @Title: getAndroidAdbPath @Description:
	 * (获取工具目录下Android/adb地址) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getAndroidAdbPath() {
		return currentPath + ANDROID_ADB_PATH + "adb.exe";
	}

	/**
	 * 
	 * @Title: getProjectConfigFolder @Description:
	 * (获取工程目录下config文件夹) @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getProjectConfigFolder() {
		return System.getProperty("user.dir") + PROJECT_CONFIG;
	}

	/**
	 * 
	 * 功能描述: 获取数据池目录 〈功能详细描述〉
	 *
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static String getProjectDataPoolFolder() {
		return System.getProperty("user.dir") + DATAPOOLS;
	}

	/*
	 * 获取 帮助文档 chm文件地址
	 */
	public static String getSATHelpDocPath() {
		return currentPath + HELPDOC_PAHT;
	}

	/*
	 * 获取 jre文件地址
	 */
	public static String getSATJrePath() {
		return currentPath + JRE_PATH;
	}

	/**
	 * 获取上传文件的au3脚本exe
	 * 
	 * @return 工具autodependency/bin/uploadFileAu3.exe
	 */
	public static String getUploadFileAu3Path() {
		return currentPath + UPLOAD_FILE_AU3_PATH;
	}
	
	public static void waitForStable(long start) {
		long waitTime = STABLE_TIME - System.currentTimeMillis() + start;

		if (waitTime > 0) {
			try {
				System.out.println("Wait for a while <Stable time:" + waitTime
						+ "ms>.");
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				System.out.println("Failed to wait for a while <Stable time:"
						+ waitTime + "ms>.");
			}
		}
	}
	
}