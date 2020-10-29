package com.practo;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

/*
 * This page class is to handle all the operations related with the 
 * Corporate Wellness page of the site and retrieve the error message 
 * given when wrong details are filled in the form and print them.
 * */
public class CorporateWellness {

	public static void alertMessage(WebDriver driver) {
		
		//Creating wait object of 10 seconds for page loading.
		WebDriverWait waitLoad = new WebDriverWait(driver , 10);
		
		//Navigating to the main page.
		driver.navigate().to("https://www.practo.com/");
		
		//Storing window handle of main page.
		String parent = driver.getWindowHandle();
		
		//Waiting for the link to be clickable.
		waitLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//span[text() = 'For Providers']")));
		
		//Clicking on drop down link.
		driver.findElement(By.xpath("//span[text() = 'For Providers']"))
				.click();
		
		//Clicking on Corporate Wellness link.
		driver.findElement(By.xpath("//a[text() = 'Corporate wellness']"))
				.click();

		//Switching tabs to the Corporate Wellness page.
		Set<String> tabs = driver.getWindowHandles();
		Iterator<String> itr = tabs.iterator();
		while (itr.hasNext()) {
			String child = itr.next();
			if (!parent.equals(child)) {
				driver.switchTo().window(child);
			}
		}
		
		//Waiting for the page to load.
		waitLoad.until(ExpectedConditions.elementToBeClickable(By
				.cssSelector("button#button-style")));
		
		//Sending wrong details to the form.
		driver.findElement(By.id("name")).sendKeys("ABC");
		driver.findElement(By.id("organization_name")).sendKeys("ABC");
		driver.findElement(By.id("official_email_id"))
				.sendKeys("ABC@gmail.com");
		driver.findElement(By.id("official_phone_no")).sendKeys("123456");
		driver.findElement(By.id("button-style")).click();
		
		//Printing the error message received from the alert box.
		System.out.println("\nError Message:");
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
	}
}
