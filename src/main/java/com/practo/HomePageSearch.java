package com.practo;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

/*
 * This page class is to handle the different operations related to the 
 * task of searching for hospitals in the given location.
 * */
public class HomePageSearch {
	
	//To enter search location.
	public static void performLocalitySearch(WebDriver driver) throws InterruptedException{

		//Creating wait object of 5 seconds for AJAX loading.
		WebDriverWait waitResults = new WebDriverWait(driver , 5);
		
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
	
	//To enter required service.
	public static void performServiceSearch(WebDriver driver) throws InterruptedException{
		
		//Creating wait object of 5 seconds for AJAX loading.
		WebDriverWait waitResults = new WebDriverWait(driver , 5);
				
		driver.findElement(
				By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))	//Writing on service search box.
				.sendKeys("Hospital");
		
		Thread.sleep(500);			//Waiting for field to load.
		
		waitResults.until(ExpectedConditions.elementToBeClickable(By			//Waiting for suggestion to load.
				.xpath("//div[text() = 'Hospital']")));	
		
		Thread.sleep(500);			//Waiting for field to load.
		
		driver.findElement(By.xpath("//div[text() = 'Hospital']")).click();		//Clicking on required search option.
	}
	
	//Performing overall search.
	public static void searchHospitals(WebDriver driver) throws InterruptedException {
		
		//Creating wait object of 15 seconds for page loading.
		WebDriverWait waitPageLoad = new WebDriverWait(driver , 15);
		
		//Try-Catch block to enter city name in search field.
		try {
			performLocalitySearch(driver);				//First attempt to enter location.
		} catch (Exception e) {
			
			try{
				performLocalitySearch(driver);			//Second attempt to enter location.
			} catch (Exception err){
				err.printStackTrace();
				System.out.println("Execution Failed. \nPlease Retry.");
				driver.quit();
				System.exit(-1);						//Exiting program after two unsuccessful attempts.
			}
		}
		
		//Try-Catch block to enter Hospital in search field.
		try {
			performServiceSearch(driver);				//First attempt to enter service.
		} catch (Exception e) {
			
			driver.findElement(														
					By.xpath("//input[@data-qa-id = 'omni-searchbox-keyword']"))	//Clearing service text field.
					.clear();
			
			try{
				performServiceSearch(driver);			//Second attempt to enter location.
			} catch (Exception err){
				err.printStackTrace();
				System.out.println("Execution Failed. \nPlease Retry.");
				driver.quit();
				System.exit(-1);						//Exiting program after two unsuccessful attempts.
			}
		}
		
		try{
		//Waiting for results to load after search is performed.
		waitPageLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//span[@data-qa-id = 'all_filters']")));
		} catch (TimeoutException e){
			e.printStackTrace();
			System.out.println("Page Load Failed. \nPlease Retry.");
			driver.quit();
			System.exit(-1);
		}
		
		System.out.println("Search Successful.\n");
	}
}
