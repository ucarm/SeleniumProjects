package com.cbt;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class testSimple {
	
	static WebDriver driver;
	
	@BeforeClass
	public static void openBrowser(){

		System.setProperty("webdriver.chrome.driver", 
				"/home/ussr/selenium dependencies/drivers/chromedriver");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
//      options.addArguments("window-size=1200x600");
        driver = new ChromeDriver(options);	
		
	}

	@Test
	public void testTitle() {
		driver.get("http://www.google.com");
		String expected= "Google";
		String actual = driver.getTitle();
		assertEquals(expected,actual);
	}
	
	@Test
	public void testUrl() {
		driver.get("http://www.google.com");
		String expected= "http://www.google.com";
		String actual = driver.getCurrentUrl();
		assertTrue(actual.contains("google"));
	}
	
	@AfterClass
	public static void closeBrowser(){
		driver.quit();
	}

}
