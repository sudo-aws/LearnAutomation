package org.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryClass extends BaseClass implements IRetryAnalyzer {
	int count = 0;
	int retryCount = 2;

	@Override
	public boolean retry(ITestResult result) {
		while (count < retryCount) {
			count++;
			return true;
		}
		return false;
	}
}
