package com.practo;

import java.io.*;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FindHospitals {
	
	//Declaring driver as an object of WebDriver.
	static WebDriver driver = null;
	
	//Method to launch the browser depending upon user choice.
	public static void launchBrowser() throws IOException {
		
		//Location of config.properties file.
		String propPath = System.getProperty("user.dir")
				+ "/config.properties";
		
		//Opening config.propertis file.
		FileInputStream file = new FileInputStream(new File(propPath));
		Properties prop = new Properties();
		prop.load(file);
		file.close();
		
		//Getting browser name from config.properties.
		String browser = prop.getProperty("browser");
		
		//If browser is Mozilla Firefox.
		if (browser.equalsIgnoreCase("firefox")) {

			FirefoxOptions ops = new FirefoxOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.gecko.driver",
					prop.getProperty("driverLocationFirefox"));

			driver = new FirefoxDriver();
		}
		
		//If browser is Google Chrome.
		else if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions ops = new ChromeOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver",
					prop.getProperty("driverLocationChrome"));

			driver = new ChromeDriver(ops);
		}
		
		//If browser is Microsoft Edge.
		else if (browser.equalsIgnoreCase("edge")) {

			System.setProperty("webdriver.edge.driver",
					prop.getProperty("driverLocationEdge"));

			driver = new EdgeDriver();
		}
	}

	public static void main(String[] args) throws Exception {
		
		//Calling method to launch browser.
		launchBrowser();
		
		//Maximizing browser window and opening Practo website.
		driver.manage().window().maximize();
		driver.get("https://www.practo.com/");
		
		//Calling class to handle operations on HomePage.
		HomePageSearch.searchHospitals(driver);
		
		//Calling class to handle operations on Search Page.
		HospitalsResults.filterHospitals(driver);
		
		//Calling class to handle operations on Diagnostics page.
		DiagnosticsCities.topCitiesSearch(driver);
		
		//Calling class to handle operations on Corporate Wellness Page.
		CorporateWellness.alertMessage(driver);
		
		//Closing browser after all operations are done.
		driver.quit();
	}

}
