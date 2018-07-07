package com.cyberFrameWork;

import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class switchTowindow extends TestBase {
	
//	@Test
	public void switchToWindow() {
		driver.get("https://the-internet.herokuapp.com/windows");
		System.out.println(driver.getTitle());
		
		driver.findElement(By.linkText("Click Here")).click();
		
		String firstWindow = driver.getWindowHandle();
		System.out.println("Current window : "+ firstWindow);
		
		Set<String> windowHandles = driver.getWindowHandles();
		
		for (String each : windowHandles) {
			System.out.println(each);
			if(!each.equals(firstWindow))
				driver.switchTo().window(each);
		}
		
		System.out.println(driver.getTitle());
	}
	
	@Test
	public void switchByTitleTest() {
		driver.get("http://the-internet.herokuapp.com/windows");
		System.out.println("Title in old window : "+driver.getTitle());
		driver.findElement(By.linkText("Click Here")).click();
		String expectedTitle= "New Window";
		switchByTitle(expectedTitle);
		System.out.println("Title in new window : "+driver.getTitle());
		assertTrue(driver.getTitle().equals(expectedTitle));
		
	}
}
