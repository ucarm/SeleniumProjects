package com.ucar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VerifySerach {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		String searchTerm ="Father's day gifts"; 
		
		driver.get("http://www.etsy.com");
		WebElement input = driver.findElement(By.id("search-query"));
		input.sendKeys(searchTerm+Keys.ENTER);//page is refreshed. Now directed to results page
		// locate the element again.
		input = driver.findElement(By.id("search-query"));
		
		//get the text of the element (same as getText())
		String actual = input.getAttribute("value");
		System.out.println(actual);
		
		if(actual.equals(searchTerm)) {
			System.out.println("Pass");
		}
		else {
			System.out.println("fail");
			System.out.println("Expected\t: "+searchTerm);
			System.out.println("Found\t: "+actual);
		}
		System.out.println(input.getAttribute("outerHTML"));
		driver.findElement(By.partialLinkText("Sell oN")).click();
		}
		
		
}
