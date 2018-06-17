package com.cbt;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Googletest {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", 
				"/home/ussr/selenium dependencies/drivers/chromedriver");
//		1. Open Chrome
//		Chrome driver directory is below
//		/home/ussr/selenium dependencies/drivers/chromedriver
		
		WebDriver driver= new ChromeDriver();
		
//		2. goto url www.google.com
		driver.get("https://www.google.com");
		
//		3. Verify title (Expected Google)
		String expected = "Google1";
		String actual = driver.getTitle();
		
		if(expected.equals(actual)) {
			System.out.println("Pass");
		}
		else {
			System.out.println("fail");
			System.out.println("Expected\t: "+expected);
			System.out.println("Actual\t\t: "+actual);
		}
		
	}

}
