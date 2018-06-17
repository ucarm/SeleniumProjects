package com.cbt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicAuthentication {
	public static void main(String[] args) throws InterruptedException {
//		TASK #3
//		1. open chrome
//		2. go to url "http://newtours.demoaut.com"
//		3. enter username "tutorial"
//		4. enter password "tutorial"
//		5. click on sign in button
//		6. verify if the title cointains 
//			Expected : find a flight
		System.setProperty("webdriver.chrome.driver","/home/ussr/selenium dependencies/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://newtours.demoaut.com");
		
		driver.findElement(By.name("userName")).sendKeys("tutorial");
//		Finds element by the locators
//		Locators == By objects
//		By.name is a a locator by name
		driver.findElement(By.name("password")).sendKeys("tutorial");
		driver.findElement(By.name("login")).click();
		
		if(driver.getTitle().contains("Find a Flight"))
			System.out.println("page contains \"find a flight\"");
		else {
			System.out.println("page doesnt contain \"find a flight\"");
			
		}
		
		
		//2nd testing scene
		driver.get("http://testing-ground.scraping.pro/login");
		driver.findElement(By.id("usr")).sendKeys("admin");
		driver.findElement(By.id("pwd")).sendKeys("123456");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		
		
		String expected = "WELCOME";
		String actual = driver.getPageSource();
//		System.out.println(actual);
		if (actual.contains(expected)) {
			System.out.println("pass");
		} else {
			System.out.println("fail");
			System.out.println("Expected: \t" + expected);
			System.out.println("Actual: \t" + actual);
		}
		Thread.sleep(2000);
		driver.close();
	}
}
