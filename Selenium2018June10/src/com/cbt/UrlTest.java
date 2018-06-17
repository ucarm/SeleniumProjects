package com.cbt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class UrlTest {

	public static void main(String[] args) throws InterruptedException {
//		Test Case 2
//		1- open chrome
//		2- go to google.com
//		3- verify the url
		
		System.setProperty("webdriver.chrome.driver","/home/ussr/selenium dependencies/drivers/chromedriver");
		ChromeOptions options = new ChromeOptions();
//	    options.addArguments("headless");
//		above code makes the chrome browser invisible (headless)
		WebDriver driver = new ChromeDriver(options);
		
//		navigate()to() is same as get()
		
		driver.navigate().to("http://www.google.com");
		String expected = "google";
		String actual = driver.getCurrentUrl();
		if(actual.contains(expected)) {
			System.out.println("Pass");
		}
		else {
			System.out.println("failed");
			System.out.println("Expected\t : "+expected);
			System.out.println("Actual\t\t : "+ actual);
		}
		
		Thread.sleep(1000);
		
		driver.navigate().to("http://www.chase.com");
//		Goes back to previous page
		Thread.sleep(1000);

		driver.navigate().back();
//		Goes forward in browsing history
		Thread.sleep(1000);

		driver.navigate().forward();
//		refreshes the current page
		Thread.sleep(1000);

		driver.navigate().refresh();
		
//		driver.close();
//		driver.close(); closes the current tab. If multiple tabs are open. Reat of them are not affected
		driver.quit();
		
	}
}
