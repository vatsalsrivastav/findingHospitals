package com.main;

import java.io.*;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.practo.CorporateWellness;
import com.practo.DiagnosticsCities;
import com.practo.HomePageSearch;
import com.practo.HospitalsResults;

public class FindHospitals {

	// Declaring driver as an object of WebDriver.
	static WebDriver driver = null;

	// Method to launch the browser depending upon user choice.
	public static void launchBrowser() throws IOException {

		// Location of config.properties file.
		String propPath = System.getProperty("user.dir") + "/config.properties";

		// Opening config.propertis file.
		FileInputStream file = new FileInputStream(new File(propPath));
		Properties prop = new Properties();
		prop.load(file);
		file.close();

		// Getting browser name from config.properties.
		String browser = prop.getProperty("browser");

		// If browser is Mozilla Firefox.
		if (browser.equalsIgnoreCase("firefox")) {

			FirefoxOptions ops = new FirefoxOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.gecko.driver",
					prop.getProperty("driverLocationFirefox"));

			driver = new FirefoxDriver();
		}

		// If browser is Google Chrome.
		else if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions ops = new ChromeOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver",
					prop.getProperty("driverLocationChrome"));

			driver = new ChromeDriver(ops);
		}

		// If browser is Microsoft Edge.
		else if (browser.equalsIgnoreCase("edge")) {

			System.setProperty("webdriver.edge.driver",
					prop.getProperty("driverLocationEdge"));

			driver = new EdgeDriver();
		}
	}

	public static void main(String[] args) throws Exception {

		// Calling method to launch browser.
		launchBrowser();

		// Maximizing browser window and opening Practo website.
		driver.manage().window().maximize();
		driver.get("https://www.practo.com/");
		
		//Verifying correct Home Page opening.
		String expectedTitle = "Practo | Video Consultation with Doctors, Book Doctor Appointments, Order Medicine, Diagnostic Tests";
		String actualTitle = driver.getTitle();
		
		if(actualTitle.equals(expectedTitle)){
			System.out.println("Home Page Opened and Verified.\n");
		}
		
		System.out.println("::Search Page::\n");								//Program step logging.
		// Calling class to handle operations on HomePage.
		HomePageSearch.searchHospitals(driver);
		
		System.out.println("\n::Search Results Page::\n");						//Program step logging.
		// Calling class to handle operations on Search Page.
		HospitalsResults.filterHospitals(driver);
		
		System.out.println("\n::Diagnostics Page::\n");							//Program step logging.
		// Calling class to handle operations on Diagnostics page.
		DiagnosticsCities.topCitiesSearch(driver);
		
		System.out.println("\n::Corporate Wellness Page::\n");					//Program step logging.
		// Calling class to handle operations on Corporate Wellness Page.
		CorporateWellness.alertMessage(driver);

		// Closing browser after all operations are done.
		driver.quit();
	}

}
