package com.cyberFrameWork;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class iframeTest extends TestBase{
	
	@Test
	public void iframetest() {
		driver.get("http://the-internet.herokuapp.com/iframe");
		
		assertTrue(driver.findElement(By.linkText("Elemental Selenium")).isDisplayed());
		
		// iframe can't be catched by selenium. Only one html 	
		// Solution is : find Iframe save it as a webelement
		WebElement iframe=  driver.findElement(By.tagName("iframe"));
		
		driver.switchTo().frame(iframe);
		
		driver.findElement(By.id("tinymce")).sendKeys(Keys.END+" more text is added");

		// move back to original content
		driver.switchTo().defaultContent();
		assertTrue(driver.findElement(By.linkText("Elemental Selenium")).isDisplayed());

	}
}
