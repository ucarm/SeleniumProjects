package com.ucar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VerifySearch {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		String searchTerm ="Father's day gifts"; 
		
		driver.get("http://www.etsy.com");
		WebElement input = driver.findElement(By.id("search-query"));
		input.sendKeys(searchTerm+Keys.ENTER);
		
		
		
		
		
		
		
	}
}
