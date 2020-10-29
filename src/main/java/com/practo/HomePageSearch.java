package com.practo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

/*
 * This page class is to handle the different operations related to the 
 * task of searching for hospitals in the given location.
 * */
public class HomePageSearch {

	public static void searchHospitals(WebDriver driver) throws InterruptedException {
		
		//Creating wait object of 5 seconds for AJAX loading.
		WebDriverWait waitResults = new WebDriverWait(driver , 5);
		
		//Creating wait object of 15 seconds for page loading.
		WebDriverWait waitPageLoad = new WebDriverWait(driver , 15);
		
		//Try-Catch block to enter city name in search field.
		try {
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))		//Clicking on location search box.
					.click();
			
			driver.findElement(By.cssSelector("i.icon-ic_cross_solid")).click();		//Clicking on x icon to clear field.
			
			Thread.sleep(500);			//Waiting for field to load.
			
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))		//Writing in location search box.
					.sendKeys("Bangalore");
			
			waitResults.until(ExpectedConditions.elementToBeClickable(By				//Waiting for suggestion to load.
					.xpath("//div[contains(text() , 'Bangalore')]")));
			
			Thread.sleep(500);			//Waiting for field to load.
			
			driver.findElement(
					By.xpath("//div[contains(text() , 'Bangalore')]")).click();			//Clicking on required search option.
		} catch (Exception e) {
			
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))		//Clicking on location search box.
					.click();
			
			driver.findElement(By.cssSelector("i.icon-ic_cross_solid")).click();		//Clicking on x icon to clear field.
			
			Thread.sleep(500);			//Waiting for field to load.
			
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-locality']"))		//Writing in location search box.
					.sendKeys("Bangalore");
			
			waitResults.until(ExpectedConditions.elementToBeClickable(By				//Waiting for suggestion to load.
					.xpath("//div[contains(text() , 'Bangalore')]")));
			
			Thread.sleep(500);			//Waiting for field to load.
			
			driver.findElement(							
					By.xpath("//div[contains(text() , 'Bangalore')]")).click();			//Clicking on required search option.
		}
		
		//Try-Catch block to enter hospital in search field.
		try {
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))	//Writing on service search box.
					.sendKeys("Hospital");
			
			Thread.sleep(500);			//Waiting for field to load.
			
			waitResults.until(ExpectedConditions.elementToBeClickable(By			//Waiting for suggestion to load.
					.xpath("//div[text() = 'Hospital']")));	
			
			Thread.sleep(500);			//Waiting for field to load.
			
			driver.findElement(By.xpath("//div[text() = 'Hospital']")).click();		//Clicking on required search option.
		} catch (Exception e) {
			
			driver.findElement(														
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))	//Clearing text field.
					.clear();
			
			driver.findElement(
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))	//Writing on service search box.
					.sendKeys("Hospital");
			
			Thread.sleep(500);			//Waiting for field to load.
			
			waitResults.until(ExpectedConditions.elementToBeClickable(By			//Waiting for suggestion to load.
					.xpath("//div[text() = 'Hospital']")));
			
			Thread.sleep(500);			//Waiting for field to load.
			
			driver.findElement(By.xpath("//div[text() = 'Hospital']")).click();		//Clicking on required search option.
		}
		
		//Waiting for results to load after search is performed.
		waitPageLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//span[@data-qa-id = 'all_filters']")));
	}
}
