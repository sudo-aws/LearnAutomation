package org.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.base.BaseClass;
import org.base.LoggerClass;
import org.objects.FlipKartSearchPageObjects;
import org.objects.HotelsLoginPageObjects;
import org.objects.HyrHomeObjects;
import org.objects.SvgObjects;

public class TestClass extends BaseClass {

	FlipKartSearchPageObjects fliplKart;
	HotelsLoginPageObjects hotel;
	HyrHomeObjects hyr;
	SvgObjects svg;

	@Test(enabled = true)
	public void flipkart() throws InterruptedException {
		fliplKart = new FlipKartSearchPageObjects(driver);
		utils.openUrl("https://flipkart.com");
		LoggerClass.logInfo("test 1");
		fliplKart.searchProduct("Mobile phone");
		LoggerClass.logInfo("seracgd the prod");
		String str = "LAVA Hero Shakti Keypad Mobile|1.8 inch Big Display|Auto Call Recording|Wireless FM";
		fliplKart.selectDesiredProduct(str);
		LoggerClass.logInfo("selected");
	}

	@Test(enabled = true)
	public void hotelSearchLocation() {
		hotel = new HotelsLoginPageObjects(driver);
		utils.openUrl("https://hotels.com");
		LoggerClass.logInfo("hotels");
		hotel.whereTo("chennai");
		LoggerClass.logInfo("chennai");
		String fromDate = "14 February 2025";
		String toDate = "16 February 2025";
		hotel.datePicker(fromDate, toDate);
		LoggerClass.logInfo("dateselected");
	}

	@Test(enabled = true)
	public void hyrWindowSession() {
		hyr = new HyrHomeObjects(driver);
		utils.openUrl("https://www.hyrtutorials.com/p/window-handles-practice.html");
		LoggerClass.logInfo("hyr window");
		hyr.goToWindowhandleSession();
		LoggerClass.logInfo("window handle");
		hyr.openNewWindow(1, "Praveen");
	}

	@Test(enabled = true)
	public void svg() throws InterruptedException {
		svg = new SvgObjects(driver);
		utils.openUrl("https://emicalculator.net/");
		LoggerClass.logInfo("svg");
		svg.moveToElement();
		LoggerClass.logInfo("moving svg");
	}

	@Test
	public void fail() {
		LoggerClass.logInfo("fail started");
		Assert.assertTrue(false);
		LoggerClass.logInfo("failed");
	}

}
