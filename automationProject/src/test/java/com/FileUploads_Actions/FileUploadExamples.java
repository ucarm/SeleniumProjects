package com.FileUploads_Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUploadExamples {
	
	@Test
	public void testDileUpload() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		String url="https://the-internet.herokuapp.com/upload";
		driver.get(url);
		WebElement chooseFile= driver.findElement(By.id("file-upload"));
		WebElement upload = driver.findElement(By.id("file-submit"));
		String fileLocation= "/home/ussr/Desktop/2BigScreens.png";
		String fileLocation1="/home/ussr/Desktop/2BigScreens.png";
		chooseFile.sendKeys(fileLocation);
		chooseFile.sendKeys(fileLocation1);
		upload.click();
		
		
	}

}
