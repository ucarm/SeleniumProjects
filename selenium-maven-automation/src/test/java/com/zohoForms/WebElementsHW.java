package com.zohoForms;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
/*
 * Homework:
 *  https://forms.zohopublic.com/murodil/form/SeleniumWebElements/formperma/eCecYgX4WMcmjxvXVq6UdhA2ABXIoqPAxnAF8H8CCJg
 * 	Loop through each textbox and enter random names
 *  Loop through each dropDown and randomly select by index
 *  Loop through each checkBoxes and select each one
 *  Loop through each radioButton and click one by one by waiting one second intervals
 *  click all buttons
 */

public class WebElementsHW {
	WebDriver driver;
	Faker data;

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(
				"https://forms.zohopublic.com/murodil/form/SeleniumWebElements/formperma/eCecYgX4WMcmjxvXVq6UdhA2ABXIoqPAxnAF8H8CCJg");
		data = new Faker();
	}

	@Test
	public void test1() throws InterruptedException {
		// Loop through each textbox and enter random names
		List<WebElement> textBoxes = driver.findElements(By.xpath("//input[@type='text']"));
		for (WebElement webElement : textBoxes) {
			webElement.sendKeys(data.name().fullName());
		}

		// Loop through each dropDown and randomly select by index
		List<WebElement> dropDowns = driver.findElements(By.xpath("//select"));
		for (WebElement webElement : dropDowns) {
			Select dropdown = new Select(webElement);
			dropdown.selectByIndex(data.number().numberBetween(1, 4));
		}

		// Loop through each checkBoxes and select each one
		List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
		for (WebElement webElement : checkBoxes) {
			webElement.click();
		}

		// Loop through each radioButton and click one by one by waiting one second
		// intervals
		List<WebElement> radioButtons = driver.findElements(By.xpath("//input[@type='radio']"));
		for (WebElement webElement : radioButtons) {
			Thread.sleep(1000);
			webElement.click();
		}
		//click all buttons
		List<WebElement> buttons = driver.findElements(By.xpath("//button"));
		for (WebElement webElement : buttons) {
			Thread.sleep(1000);
			webElement.click();
			Thread.sleep(1000);
		}
		
		Thread.sleep(1000);
		String submittedMsg= driver.findElement(By.xpath("//span[@id='richTxtMsgSpan']//div[1]")).getText();
		assertTrue(submittedMsg.contains("Good Job"));
		
	}

	@AfterClass
	public void afterClass() {
		driver.close();
		System.gc();
	}
}
