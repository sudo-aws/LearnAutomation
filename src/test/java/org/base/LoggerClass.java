package org.base;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class LoggerClass {
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;

	public static void initLogger() {
		String path = System.getProperty("user.dir") + File.separator + "automation Report.html";
		sparkReporter = new ExtentSparkReporter(path);
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setDocumentTitle("Test Automation Report");
		sparkReporter.config().setReportName("Tests results");

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("HostName", "Praveen Desktop");
		extent.setSystemInfo("UserName", "Praveen");
	}

	public static void logResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case failed", ExtentColor.RED));
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case failed", ExtentColor.RED));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - Test Case passed", ExtentColor.GREEN));
		}
	}

	public static void getTestMethodName(Method testMethod) {
		logger = extent.createTest(testMethod.getName());

	}

	public static void logInfo(String message) {
		logger.info(message);
	}

	public static void flush() {
		extent.flush();
	}
}
