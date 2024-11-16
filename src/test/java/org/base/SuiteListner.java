package org.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

public class SuiteListner implements ITestListener, IAnnotationTransformer {

	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryClass.class);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		LoggerClass.logger.fail("Test Failed: " + result.getThrowable());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		LoggerClass.logger.pass("Test Passed");
	}
}
