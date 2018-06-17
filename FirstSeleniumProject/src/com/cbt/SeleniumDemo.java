package com.cbt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDemo {
	public static void main(String[] args) {
		
// CODE BELOW works only for Firefox		
//		System.setProperty("webdriver.gecko.driver", 
//				"/home/ussr/selenium dependencies/drivers/geckodriver");
//		WebDriver driver = new FirefoxDriver();
//		driver.get("https://www.chase.com/");
		
		
		
//	CODE BELOW WORKS just fine for ChromeDriver		
		System.setProperty("webdriver.chrome.driver", 
				"/home/ussr/selenium dependencies/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.chase.com/");
		
		WebDriver driver1 = new ChromeDriver();
		driver1.get("https://www.bankofamerica.com/");
		
		WebDriver driver2 = new ChromeDriver();
		driver2.get("https://www.discover.com/");
		
		WebDriver driver3 = new ChromeDriver();
		driver3.get("https://www.visa.com/");
		
	}
}
