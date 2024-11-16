package org.objects;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.base.Utils;

public class HotelsLoginPageObjects{

	Utils utils;
	WebDriver driver;
	public HotelsLoginPageObjects(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
		PageFactory.initElements(driver, this);
	}

//	@FindBy(xpath = "//button[@aria-label='Where to?']")

	@FindBy(xpath = "//form[@id='lodging_search_form']/child::div/div/child::div[1]")
	private WebElement whereTo;

	@FindBy(xpath = "//input[@id='destination_form_field']")
	private WebElement whereToButton;

	@FindBy(xpath = "(//ul[@data-stid='destination_form_field-results']/child::div/li)[1]")
	private WebElement locSelect;

//	@FindBy(xpath = "(//form[@id='lodging_search_form']//button[@aria-label])[2]")
	@FindBy(xpath = "//form[@id='lodging_search_form']/child::div/div/child::div[2]")
	private WebElement datesField;

	@FindBy(xpath = "(//span[@class='uitk-align-center uitk-month-label'])[1]")
	private WebElement fromMonth;
	@FindBy(xpath = "(//span[@class='uitk-align-center uitk-month-label'])[2]")
	private WebElement toMonth;

	@FindBy(xpath = "(//button[@type='button'])[9]")
	private WebElement leftArrow;
	@FindBy(xpath = "(//button[@type='button'])[10]")
	private WebElement rightArrow;

	@FindBy(xpath = "(//*[@data-stid='month-table'])[1]//tr/td/div/div[2]")
	private List<WebElement> getFirstDates;

	@FindBy(xpath = "(//*[@data-stid='month-table'])[2]//tr/td/div/div[2]")
	private List<WebElement> getSecondDates;

	@FindBy(xpath = "(//button[@type='button'])[11]")
	private WebElement doneButton;

	@FindBy(xpath = "//label[text()='Dates']/following-sibling::input")
	private WebElement dateRangeText;

	@FindBy(xpath = "//form[@id='lodging_search_form']/div/div/div[2]//button")
	WebElement getDateText;

//	@FindBy(xpath = "//*[@id='lodging_search_form']/descendant::div[11]//button/preceding-sibling::input")
	@FindBy(xpath = "//label[text()='Dates']/following-sibling::input")
	WebElement hiddenEle;

	public void whereTo(String place) {
		whereTo.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(200));
		whereToButton.sendKeys(place);
		locSelect.click();
	}

	public String[] getMonthText(WebElement element) {
		utils.elementToBeVisible(element, 10);
		String[] month = element.getText().trim().split(" ");
		return month;
	}

	public void navigateToMonth(String fromInput) {
		String fromMon = fromInput.split(" ")[1];
		String fromYear = fromInput.split(" ")[2];

		datesField.click();
		while (true) {
			String[] monthText = getMonthText(fromMonth);
			String monthtDisplayed = monthText[0];
			String yearDisplayed = monthText[1];

			if (monthtDisplayed.equalsIgnoreCase(fromMon) && yearDisplayed.equalsIgnoreCase(fromYear)) {
				System.out.println("Month and year selected is: " + monthtDisplayed + " " + yearDisplayed);
				break;
			}
			rightArrow.click();
		}
	}

	public void selectDate(String fromInput, List<WebElement> dates) {
		String fromDate = fromInput.split(" ")[0];
		for (WebElement dateElement : dates) {
			String date = dateElement.getText();
			if (date.equalsIgnoreCase(fromDate)) {
				System.out.println("Selected from date is : " + date);
				dateElement.click();
				break;
			}
		}
	}

	public String validateAndFormatDate(String inputDate) {
		String desiredFormat = "dd MMMM yyyy"; // Desired output format
		String[] dateFormats = { "d MMM yyyy", "dd MMM yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "dd MMMM yyyy" };

		for (String dateFormat : dateFormats) {
			DateTimeFormatter ipDateFormatter = DateTimeFormatter.ofPattern(dateFormat);

			try {
				LocalDate date = LocalDate.parse(inputDate, ipDateFormatter);

				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(desiredFormat)
						.withResolverStyle(ResolverStyle.LENIENT);
				return date.format(outputFormatter);
			} catch (DateTimeParseException e) {
			}
		}

		// If no formats match, return null (invalid date format)
		return null;
	}

	// Method 2: Validate if the date is in the past, present, or future
	public boolean validateDateRelativeToNow(String formattedDate) {
		if (formattedDate == null || formattedDate.isEmpty()) {
			return false;
		}

		// Parse the formatted date back to a LocalDate object
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		LocalDate dateToValidate;
		try {
			dateToValidate = LocalDate.parse(formattedDate, formatter);
		} catch (DateTimeParseException e) {
			return false;
		}

		LocalDate currentDate = LocalDate.now();
		return !dateToValidate.isBefore(currentDate);
	}

	public boolean validateDate(String fromInput) {
		String formattedDate = validateAndFormatDate(fromInput);
		return validateDateRelativeToNow(formattedDate);
	}

	public void datePicker(String fromInput, String toInput) {
		boolean from = validateDate(fromInput);
		boolean to = validateDate(toInput);
		if (from && to) {
			navigateToMonth(fromInput);
			selectDate(fromInput, getFirstDates);

			String fromMon = fromInput.split(" ")[1];
			String toMon = toInput.split(" ")[1];

			if (fromMon.equalsIgnoreCase(toMon)) {
				selectDate(toInput, getFirstDates);
			} else {
				selectDate(toInput, getSecondDates);
			}
		} else {
			System.out.println("Provide correct date");
		}
		doneButton.click();
		utils.elementToBeVisible(getDateText, 10);
		String[] text = getDateText.getText().trim().split("-");
		System.out.println(text[0] + text[1]);
//		String[] fromSplit = text[0].split(" ");
//		String[] toSplit = text[1].split(" ");

	}
}
