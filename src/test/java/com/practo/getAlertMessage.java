package com.practo;

import java.io.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.*;

import com.aventstack.extentreports.Status;
import com.utils.CreateReport;
import com.utils.HandleBrowser;
import com.utils.ReadExcelData;

public class getAlertMessage extends CreateReport {

	@DataProvider(name = "TestData")
	public Object[][] getTestData() {
		Object[][] data = ReadExcelData.getExcelData();
		return data;
	}

	@Test(dataProvider = "TestData")
	public void alertMessage(String name, String compName, String email,
			String phone, String type) throws IOException {

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
		Alert alert = waitLoad.until(ExpectedConditions.alertIsPresent());
		String errorMes = alert.getText();
		System.out.println(errorMes);
		alert.accept();

		String data = "**Name: " + name + " **Company: " + compName
				+ " **E-Mail: " + email + " **Phone: " + phone;

		test.log(Status.INFO, data);
		test.log(Status.INFO, "Test Type: " + type);

		switch (type) {
		case "Invalid Email":
			if (errorMes.equals("Please enter valid email address"))
				test.log(Status.PASS, errorMes);
			else {
				test.log(Status.FAIL, errorMes);
				AssertJUnit.fail();
			}
			break;

		case "Invalid Phone Number":
			if (errorMes.equals("Please enter valid phone no"))
				test.log(Status.PASS, errorMes);
			else {
				test.log(Status.FAIL, errorMes);
				AssertJUnit.fail();
			}
			break;

		case "Invalid Name":
			if (errorMes.equals("Please enter valid name"))
				test.log(Status.PASS, errorMes);
			else {
				test.log(Status.FAIL, errorMes);
				AssertJUnit.fail();
			}
			break;

		case "Valid Data":
			if (errorMes
					.equals("Thanks, for showing We have received your request, our team will contact you shortly."))
				test.log(Status.PASS, errorMes);
			else {
				test.log(Status.FAIL, errorMes);
				AssertJUnit.fail();
			}
			break;
		}

		HandleBrowser.closeBrowser(driver);
	}
}
