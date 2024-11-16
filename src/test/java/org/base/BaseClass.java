package org.base;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {
	protected WebDriver driver;
	protected Utils utils;

	@BeforeSuite
	public void setUp() {
		LoggerClass.initLogger();
		driver = Utils.launchBrowser();
		utils = new Utils(driver);
	}

	@BeforeMethod
	public void beforeMethod(Method testMethod) {
		LoggerClass.getTestMethodName(testMethod);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		LoggerClass.logResult(result);
	}

	@AfterSuite
	public void quit() {
		LoggerClass.flush();
		utils.tearDown();
	}
}
