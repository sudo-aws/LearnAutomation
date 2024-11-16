package org.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {

	protected WebDriver driver;
	private Actions act;

	public Utils(WebDriver driver) {
		this.driver = driver;
		this.act = new Actions(driver); // Initialize Actions with driver
	}

	public static WebDriver launchBrowser() {
		WebDriver driver = new ChromeDriver(chromeOptions());
		return driver;
	}

	public void openUrl(String url) {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url);
		LoggerClass.logInfo("url opened");
	}

	public static ChromeOptions chromeOptions() {
		String projectPath = System.getProperty("user.dir");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-popup-blocking");
//		options.addArguments("--incognito");
		options.addArguments("user-data-dir=" + projectPath + "/chrome-profile");
		return options;
	}

	public void cookie(String sessionId, String SessionVal) {
		Cookie session = new Cookie(sessionId, SessionVal);
		driver.manage().addCookie(session);
		driver.navigate().refresh();
	}

	public void elementToBeClickable(WebElement element, int timeUnits) {
		LoggerClass.logInfo("elementToBeClickable invoked");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeUnits));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void elementToBeVisible(WebElement element, int timeUnits) {
		LoggerClass.logInfo("elementToBeVisible invoked");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeUnits));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void isEnabled(WebElement element, int timeUnits) {
		// Check if the "Next" button is enabled, click it if so
		if (element.isEnabled()) {
			element.click();
			elementToBeVisible(element, timeUnits);

		} else {
			LoggerClass.logInfo("Reached the last page. Elements not found.");
			System.out.println();
			return; // If "Next" button is disabled, exit the loop
		}
	}

	public WebElement tryToFindXpath(String element) {
		WebElement webElement;
		try {
			webElement = driver.findElement(By.xpath(element));
		} catch (NoSuchElementException ex) {
			System.out.println("No 'webElement' found. Exiting.");
			return null; // Exit the loop if there's no "Next" button (last page)
		}
		return webElement;
	}

	public void moveToElementandGetText(List<WebElement> elements, List<WebElement> elementTexts)
			throws InterruptedException {
		for (WebElement element : elements) {
			act.moveToElement(element).perform();
			Thread.sleep(600);
			String text= element.getText();
			LoggerClass.logInfo(text);
//			for (WebElement elementText : elementTexts) {
//				String text = elementText.getText();
//				LoggerClass.logInfo(text);
//			}
//			System.out.println();
		}
	}

	public static void screenShot(WebDriver driver, String fileName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(fileName + ".png");
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
