package com.practo;

import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.Status;

public class getAlertMessage extends CreateReport {

	@DataProvider(name = "TestData")
	public Object[][] getTestData() {
		return new Object[][] {
				{ "Anshul", "Cognizant", "anshul", "9876543210" },
				{ "Amrita", "TCS", "amrita@gmail.com", "987654" } };
	}

	@Test(dataProvider = "TestData")
	public void alertMessage(String name, String compName, String email,
			String phone) throws IOException {

		WebDriver driver = HandleBrowser.launchBrowser();

		WebDriverWait waitLoad = new WebDriverWait(driver, 10);

		test = extent.createTest("Alert Message Verification");

		driver.get("https://www.practo.com/plus/corporate");
		
		waitLoad.until(ExpectedConditions.elementToBeClickable(By
				.cssSelector("button#button-style")));
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("organization_name")).sendKeys(compName);
		driver.findElement(By.id("official_email_id")).sendKeys(email);
		driver.findElement(By.id("official_phone_no")).sendKeys(phone);
		driver.findElement(By.id("button-style")).click();
		System.out.println("\nError Message:");
		String errorMes = driver.switchTo().alert().getText();
		System.out.println(errorMes);
		driver.switchTo().alert().accept();
		
		String data = "**Name: " + name + " **Company: " + compName + " **E-Mail: " + email + " **Phone: " + phone;
		
		test.log(Status.INFO, data);
		test.log(Status.PASS, errorMes);

		HandleBrowser.closeBrowser(driver);
	}
}
