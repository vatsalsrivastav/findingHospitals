package com.practo;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.testng.AssertJUnit;
import org.testng.annotations.*;

import com.aventstack.extentreports.Status;
import com.utils.CreateReport;
import com.utils.HandleBrowser;

public class verifyTopCities extends CreateReport {

	@Test
	public static void countCities() throws Exception {

		WebDriver driver = HandleBrowser.launchBrowser();

		test = extent.createTest("Verifying Diagnostics Cities");

		WebDriverWait waitLoad = new WebDriverWait(driver, 10);

		driver.manage().window().maximize();
		driver.get("https://www.practo.com/");
		waitLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[text() = 'Diagnostics']")));
		driver.findElement(By.xpath("//div[text() = 'Diagnostics']")).click();
		waitLoad.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[text() = 'TOP CITIES']")));
		Thread.sleep(1000);
		List<WebElement> topCities = driver
				.findElements(By
						.xpath("//li[@class = 'u-text--center']/div[@class = 'u-margint--standard o-f-color--primary']"));
		String mesCities = "No. of Top Cities Retrieved: "
				+ Integer.toString(topCities.size());
		test.log(Status.INFO, mesCities);
		if (topCities.size() > 0) {
			test.log(Status.PASS, "Top Cities Retrieved");
		} else {
			test.log(Status.FAIL, "Top Cities Not Retrieved Correctly");
			AssertJUnit.fail();
		}

		HandleBrowser.closeBrowser(driver);
	}
}
