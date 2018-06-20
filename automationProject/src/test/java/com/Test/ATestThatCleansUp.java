package com.Test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ATestThatCleansUp {
	
	WebDriver driver;
	@BeforeMethod
	public void beforeMethods() {
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
	}
	
	@Test
	public void searchAmazon() {
		
		driver.get("https://www.amazon.com");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Father's day gift"+Keys.ENTER);
		Assert.assertTrue(driver.getTitle().contains("gift"));
	}
	
	@Test
	public void googleSearch() {
		driver.get("https://www.google.com");
		driver.findElement(By.id("lst-ib")).sendKeys("Selenium CookBook"+Keys.ENTER);
		Assert.assertTrue(driver.getTitle().contains("Selenium CookBook"));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
