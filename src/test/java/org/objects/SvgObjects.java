package org.objects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.base.Utils;

public class SvgObjects {
	Utils utils;
	WebDriver driver;

	public SvgObjects(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[local-name()='svg']//*[name()='g' and @class='highcharts-series-group']//*[name()='rect']")
	private List<WebElement> vBars;

	@FindBy(xpath = "//*[local-name()='svg']//*[name()='g' and @class='highcharts-label highcharts-tooltip highcharts-color-undefined']//*[name()='text']//*[name()='tspan']")
	private List<WebElement> vBarsText;

	public void moveToElement() throws InterruptedException {
		utils.moveToElementandGetText(vBars, vBarsText);
	}
}
