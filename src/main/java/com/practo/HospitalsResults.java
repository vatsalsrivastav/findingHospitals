package com.practo;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import com.utils.CreateExcel;

/*
 * This page class is to handle all the operations related to the process 
 * of going through all the results based on the given criteria and extracting 
 * the required values from it.
 * */
public class HospitalsResults {

	public static void filterHospitals(WebDriver driver) throws Exception {
		
		//Creating wait object of 10 seconds for results loading.
		WebDriverWait waitLoad = new WebDriverWait(driver , 10);
		
		driver.findElement(By.xpath("//span[@data-qa-id = 'all_filters']"))			//Clicking on All Filters button.
				.click();
		
		waitLoad.until(ExpectedConditions.elementToBeClickable(By					//Waiting for filters to load.
				.xpath("//div[@data-qa-id = 'Has_Parking_checkbox']")));
		
		driver.findElement(
				By.xpath("//div[@data-qa-id = 'Has_Parking_checkbox']"))			//Selecting Has Parking constraint.
				.click();

		waitLoad.until(ExpectedConditions.invisibilityOfElementLocated(By			//Waiting for loader to disappear.
				.xpath("//div[@class='page-loader']")));

		waitLoad.until(ExpectedConditions.elementToBeClickable(By					//Waiting for Open 24x7 constraint.
				.xpath("//div[@data-qa-id = 'Open_24X7_checkbox']")));				
		
		driver.findElement(
				By.xpath("//div[@data-qa-id = 'Open_24X7_checkbox']")).click();		//Selecting Open 24x7 constraint.

		waitLoad.until(ExpectedConditions.invisibilityOfElementLocated(By			//Waiting for loader to disappear.
				.xpath("//div[@class='page-loader']")));
		
		//Creating JavaScriptExecutor object to scroll the page.
		JavascriptExecutor jse = (JavascriptExecutor) driver;					

		//Creating XPath based on number of elements loaded on scrolling.
		int ct = 11;
		
		while (true) {
			String xpathLoad = "//div[@data-qa-id = 'hospital_card']" + "["		//Getting XPath of newly loaded element.
					+ ct + "]";
			
			//Scrolling to the bottom of the page.
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight - 10);");
			
			//Try-Catch block to wait for the new elements to load on page scrolling.
			try {
				waitLoad.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(xpathLoad)));
			} catch (TimeoutException e) {
				System.out.println("All Results Loaded.");
				break;			//Breaking loop when all results are loaded.
			}
			ct = ct + 10;
		}
		
		jse.executeScript("window.scrollTo(0, 0);");			//Scrolling to the top of the page.
		
		/*
		 * Getting the names of hospitals and their star ratings from the 
		 * completely loaded results search webpage. Then printing the names 
		 * of the hospitals with rating greater than 3.5 in the console.
		 * */
		System.out.println("\nHospitals With Rating More Than 3.5:");
		
		//Storing the names of the hospitals that have a star rating.
		List<WebElement> hospName = driver						
				.findElements(By
						.xpath("//*[@data-qa-id = 'star_rating']/ancestor::div[@data-qa-id = 'hospital_card']/descendant::h2"));
		
		//Storing the star ratings of all the hospitals in same order as the names.
		List<WebElement> hospRating = driver.findElements(By
				.xpath("//*[@data-qa-id = 'star_rating']"));
		
		//To store hospital names and ratings for creation of excel file.
		Map<String , String> hospData = new LinkedHashMap<String , String>();
		
		//Storing only unique names of the hospitals eliminating repetition.
		Set<String> hospNameList = new LinkedHashSet<String>();
		
		int j = 1;			//To add the serial numbering.
		
		for (int i = 0; i < hospName.size(); i++) {
			//Separating hospitals with rating greater than 3.5.
			if (Double.parseDouble(hospRating.get(i).getAttribute("title")) > 3.5) {
				hospData.put(hospName.get(i).getText() , hospRating.get(i).getAttribute("title"));
				hospNameList.add(hospName.get(i).getText());
			}
		}
		for(String i : hospNameList){					//Printing the names of the hospitals.
			System.out.println(j + "\t" + i);
			j++;
		}
		
		//Calling the class responsible to create the excel file.
		CreateExcel.generateReport(hospData);
	}
}
