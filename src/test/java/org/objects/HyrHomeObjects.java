package org.objects;

import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.base.Utils;

public class HyrHomeObjects{
	Utils utils;
	WebDriver driver;
	public HyrHomeObjects(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//ul[@id='nav1']/li[4]")
	private WebElement seleniumXpath;

	@FindBy(xpath = "//ul[@id='nav1']/li[4]/child::ul/child::li[6]")
	private WebElement windowHandleXpath;

	@FindBy(xpath = "//input[@class='whTextBox']")
	private WebElement parentTextBox;

	@FindBy(id = "newWindowBtn")
	private WebElement newWindowBtn;

	@FindBy(id = "newTabBtn")
	private WebElement newTabBtn;

	@FindBy(id = "newWindowsBtn")
	private WebElement newWindowsBtn;

	@FindBy(id = "newTabsBtn")
	private WebElement newTabsBtn;

	@FindBy(id = "newTabsWindowsBtn")
	private WebElement newWindowsTabsBtn;

	@FindBy(xpath = "//input[@id='firstName']")
	private WebElement fName;

	public void goToWindowhandleSession() {
		seleniumXpath.click();
		windowHandleXpath.click();
	}

	public void openNewWindow(int targetWindowIndex, String text) {
		String parent = driver.getWindowHandle();
		clickButton(newWindowBtn);
		sendkeysOnWindow(targetWindowIndex, fName, text);
		switchToParentWindow(parent);
	}

	public void enterText(WebElement element, String text) {
		element.sendKeys(text);
	}

	public void clickButton(WebElement element) {
		utils.elementToBeClickable(element, 10);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		element.click();
	}

	public WebDriver switchToWindowByIndex(int targetWindowIndex) {
		Set<String> handles = driver.getWindowHandles();
		int currentWindowIndex = 0;
		for (String handle : handles) {
			if (currentWindowIndex == targetWindowIndex) {
				driver.switchTo().window(handle);
				return driver;
			}
			currentWindowIndex++;
		}
		throw new RuntimeException("Window with index " + targetWindowIndex + " not found");
	}

	public void sendkeysOnWindow(int index, WebElement element, String keys) {
		switchToWindowByIndex(index);
		utils.elementToBeClickable(element, 10);
		driver.manage().window().maximize();
		enterText(element, keys);
		driver.close();
	}

	public void switchToParentWindow(String text) {
		driver.switchTo().window(text);
	}

}
