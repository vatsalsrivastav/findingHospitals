package com.practo;

import org.testng.annotations.*;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class verifySearchResults extends CreateReport{

	@Test
	public void checkSearchPage() throws Exception {

		WebDriver driver = HandleBrowser.launchBrowser();
		
		WebDriverWait waitResults = new WebDriverWait(driver, 5);

		WebDriverWait waitPageLoad = new WebDriverWait(driver, 15);
		
		test = extent.createTest("Verify Search Results");
		
		driver.get("https://www.practo.com/");

		try {
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
			driver.findElement(
					By.xpath("//div[contains(text() , 'Bangalore')]")).click();
		} catch (Exception e) {
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
			driver.findElement(
					By.xpath("//div[contains(text() , 'Bangalore')]")).click();
		}
		
		String expectedCity = "Bangalore";
		String actualCity = driver.findElement(
				By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))
				.getAttribute("value");
		
		AssertJUnit.assertEquals("Incorrect City", expectedCity, actualCity);
		
		test.log(Status.INFO, "Expected City: " + expectedCity);
		test.log(Status.INFO, "Actual City: " + actualCity);
		if(expectedCity.equalsIgnoreCase(actualCity)){
			test.log(Status.PASS , "City Verified.");
		}
		
		try {
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))
					.sendKeys("Hospital");
			Thread.sleep(500);
			waitResults.until(ExpectedConditions.elementToBeClickable(By
					.xpath("//div[text() = 'Hospital']")));
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[text() = 'Hospital']")).click();
		} catch (Exception e) {
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))
					.clear();
			;
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))
					.sendKeys("Hospital");
			Thread.sleep(500);
			waitResults.until(ExpectedConditions.elementToBeClickable(By
					.xpath("//div[text() = 'Hospital']")));
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[text() = 'Hospital']")).click();
		}
		waitPageLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//span[@data-qa-id = 'all_filters']")));
		
		String expectedTitle = "Best Hospitals in Bangalore - Book Appointment Online, View Fees, Reviews | Practo";
		String actualTitle = driver.getTitle();
		
		AssertJUnit.assertEquals("Wrong Page Opened.", expectedTitle, actualTitle);
		
		test.log(Status.INFO, "Expected Title: " + expectedTitle);
		test.log(Status.INFO, "Actual Title: " + actualTitle);
		if(expectedTitle.equalsIgnoreCase(actualTitle)){
			test.log(Status.PASS , "Search Verified.");
		}
		
		HandleBrowser.closeBrowser(driver);
	}
}
