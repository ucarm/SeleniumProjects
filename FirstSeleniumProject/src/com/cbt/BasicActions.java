package com.cbt;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class BasicActions {
	WebDriver driver;
	String baseUrl;
	@Before
	void setUp() throws Exception {
		
		driver=new FirefoxDriver();
		baseUrl= "https://www.chase.com/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //this is not mandatory
		driver.manage().window().maximize();
		
	}
	
	@Test
	void test() {
		System.setProperty("webdriver.chrome.driver", 
				"/home/ussr/selenium dependencies/drivers/chromedriver");
		driver.get(baseUrl);
	}
	
	@After
	void tearDown() throws Exception {
	}



}
