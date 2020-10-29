package com.practo;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

/*
 * This page class is to handle all the operations related to the 
 * Diagnostics page and getting the names of the top cities from the 
 * page and store them in a list.
 * */
public class DiagnosticsCities {

	public static void topCitiesSearch(WebDriver driver) throws Exception {
		
		//Creating wait object of 10 seconds for page loading.
		WebDriverWait waitLoad = new WebDriverWait(driver, 10);
		
		//Going back to main page.
		driver.navigate().to("https://www.practo.com/");
		
		//Waiting for the Diagnostics link to be clickable.
		waitLoad.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[text() = 'Diagnostics']")));
		
		//Clicking on the Diagnostics link.
		driver.findElement(By.xpath("//div[text() = 'Diagnostics']")).click();
		
		//Waiting for Cities dialog box to appear.
		waitLoad.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[text() = 'TOP CITIES']")));
		
		Thread.sleep(1000);				//Waiting for complete list loading.
		
		//Storing WebElements of all the Top Cities.
		List<WebElement> topCities = driver
				.findElements(By
						.xpath("//li[@class = 'u-text--center']/div[@class = 'u-margint--standard o-f-color--primary']"));
		
		System.out.println("\nTop Cities");
		
		//Storing the names of all the Top Cities in a list.
		List<String> cities = new ArrayList<String>();
		for (int i = 0; i < topCities.size(); i++) {
			cities.add(topCities.get(i).getText());
		}
		
		//Printing the names of all the Top Ciites.
		for (int i = 0; i < cities.size(); i++) {
			System.out.println((i + 1) + "\t" + cities.get(i));
		}
	}
}
