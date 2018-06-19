package com.ucar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CheckboxTest {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("http://the-internet.herokuapp.com/checkboxes");
		
//		WebElement firstOne = driver.findElement(By.tagName("input"));
		WebElement firstOne = driver.findElement(By.xpath("//input[@type='checkbox'][1]"));
		WebElement secondOne = driver.findElement(By.xpath("//input[@type='checkbox'][2]"));

		
		System.out.println("FIRST");
		System.out.println(firstOne.isSelected());
		firstOne.click();
		System.out.println(firstOne.isSelected());
		
		

		System.out.println("SECOND");
		System.out.println(secondOne.isSelected());
		secondOne.click();
		System.out.println(secondOne.isSelected());
		
		
		
		
		driver.close();
		
		
	}

}
