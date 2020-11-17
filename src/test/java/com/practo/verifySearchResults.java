package com.practo;

import org.testng.annotations.*;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.utils.CreateReport;
import com.utils.HandleBrowser;

public class verifySearchResults extends CreateReport {

	public static void performLocalitySearch(WebDriver driver)
			throws InterruptedException {

		WebDriverWait waitResults = new WebDriverWait(driver, 5);
		driver.findElement(
				By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))
				.click();
		driver.findElement(By.cssSelector("i.icon-ic_cross_solid")).click();
		Thread.sleep(500);
		driver.findElement(
				By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))
				.sendKeys("Bangalore");
		waitResults.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[contains(text() , 'Bangalore')]")));
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[contains(text() , 'Bangalore')]"))
				.click();
	}

	public static void performServiceSearch(WebDriver driver)
			throws InterruptedException {

		WebDriverWait waitResults = new WebDriverWait(driver, 5);
		driver.findElement(
				By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))
				.sendKeys("Hospital");
		Thread.sleep(500);
		waitResults.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[text() = 'Hospital']")));
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[text() = 'Hospital']")).click();
	}

	@Test
	public void checkSearchPage() throws Exception {

		WebDriver driver = HandleBrowser.launchBrowser();

		WebDriverWait waitPageLoad = new WebDriverWait(driver, 15);

		test = extent.createTest("Verify Search Results");

		driver.get("https://www.practo.com/");

		try {
			performLocalitySearch(driver);
		} catch (Exception e) {

			try {
				performLocalitySearch(driver);
			} catch (Exception err) {
				err.printStackTrace();
				System.out.println("Execution Failed. \nPlease Retry.");
				driver.quit();
				AssertJUnit.fail();
				System.exit(-1);
			}
		}

		String expectedCity = "Bangalore";
		String actualCity = driver.findElement(
				By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))
				.getAttribute("value");

		AssertJUnit.assertEquals("Incorrect City", expectedCity, actualCity);

		test.log(Status.INFO, "Expected City: " + expectedCity);
		test.log(Status.INFO, "Actual City: " + actualCity);
		if (expectedCity.equalsIgnoreCase(actualCity)) {
			test.log(Status.PASS, "City Verified.");
		} else {
			test.log(Status.FAIL, "City Verification Failed.");
		}

		try {
			performServiceSearch(driver);
		} catch (Exception e) {

			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))
					.clear();
			try {
				performServiceSearch(driver);
			} catch (Exception err) {
				err.printStackTrace();
				System.out.println("Execution Failed. \nPlease Retry.");
				driver.quit();
				AssertJUnit.fail();
				System.exit(-1);
			}
		}
		
		waitPageLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//span[@data-qa-id = 'all_filters']")));

		String expectedTitle = "Best Hospitals in Bangalore - Book Appointment Online, View Fees, Reviews | Practo";
		String actualTitle = driver.getTitle();

		AssertJUnit.assertEquals("Wrong Page Opened.", expectedTitle,
				actualTitle);

		test.log(Status.INFO, "Expected Title: " + expectedTitle);
		test.log(Status.INFO, "Actual Title: " + actualTitle);
		if (expectedTitle.equalsIgnoreCase(actualTitle)) {
			test.log(Status.PASS, "Search Verified.");
		}
		else{
			test.log(Status.FAIL, "Search Verification Failed.");
		}

		HandleBrowser.closeBrowser(driver);
	}
}
