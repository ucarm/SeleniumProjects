package com.ucar;

import java.time.LocalDateTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Translate {

	public static void translating(List<String> languages) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.collinsdictionary.com/us/translator");
		String keyword = "Thank you!!!";
		driver.findElement(By.id("input-text")).sendKeys(keyword);

		for(String language : languages) {
			Select dropDown = new Select(driver.findElement(By.id("select-output")));
		//String language = "Spanish";
		dropDown.selectByVisibleText(language);
		driver.findElement(By.className("spinner")).click();
		String answer = driver.findElement(By.id("output-text")).getText();
		System.out.println(keyword + " in "+ language + " is "+ answer);
		}
		
		driver.close();
		System.out.println();
		System.out.println("Test COMPLITED "+ LocalDateTime.now());
	}

	
}