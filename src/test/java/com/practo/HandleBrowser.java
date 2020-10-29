package com.practo;

import java.io.*;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class HandleBrowser {

	static WebDriver driver = null;

	public static WebDriver launchBrowser() throws IOException {

		String propPath = System.getProperty("user.dir") + "/config.properties";
		FileInputStream file = new FileInputStream(new File(propPath));
		Properties prop = new Properties();
		prop.load(file);
		file.close();

		String browser = prop.getProperty("testBrowser");

		if (browser.equalsIgnoreCase("firefox")) {

			FirefoxOptions ops = new FirefoxOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.gecko.driver",
					prop.getProperty("driverLocationFirefox"));

			driver = new FirefoxDriver();
		}

		else if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions ops = new ChromeOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver",
					prop.getProperty("driverLocationChrome"));

			driver = new ChromeDriver(ops);
		}

		else if (browser.equalsIgnoreCase("edge")) {

			System.setProperty("webdriver.edge.driver",
					prop.getProperty("driverLocationEdge"));

			driver = new EdgeDriver();
		}

		else if (browser.equalsIgnoreCase("remote")) {

			String Node = prop.getProperty("nodeIP");
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");

			driver = new RemoteWebDriver(new URL(Node), cap);
		}

		return driver;
	}

	public static void closeBrowser(WebDriver driver) {
		driver.quit();
	}
}
