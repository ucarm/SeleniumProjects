package com.cyberFrameWork;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	 @AfterMethod
	public void tearDown() {
		driver.quit();
	}

	/*
	 * @param expected title
	 * 
	 * will switch to new tab based on the expected title will switch back to
	 * original window if the expected title is not found
	 * 
	 */
	public void switchByTitle(String title) {
		String originalWin = driver.getWindowHandle();
		for (String each : driver.getWindowHandles()) {
			driver.switchTo().window(each);
			if(driver.getTitle().equals(title))
				return;
		}
		driver.switchTo().window(originalWin);
	}	
}
