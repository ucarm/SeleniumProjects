package com.FileUploads_Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ActionsExamples {
	WebDriver driver;
	Actions action;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		action = new Actions(driver);

	}

	@Ignore
	@Test
	public void doubleClickTest() {
		driver.get("https://www.primefaces.org/showcase/ui/misc/effect.xhtml");
		WebElement shakes = driver.findElement(By.id("slide_header"));
		// double click --> double clicks on element
		action.doubleClick(shakes).perform();
	}

	@Ignore
	@Test
	public void hoverTest1() {
		driver.get("https://www.amazon.com/");
		WebElement signInHover = driver.findElement(By.xpath("//span[.='Hello. Sign in']"));
		action.moveToElement(signInHover).perform();
		driver.findElement(By.linkText("Your Garage")).click();
	}

	@Ignore
	@Test
	public void hoverTest2() {
		driver.get("https://www.amazon.com/");
		action.moveToElement(driver.findElement(By.xpath("//span[.='Back to top']"))).perform();
		// action.sendKeys(Keys.PAGE_DOWN).perform();
		// different options to scroll down
		// action.sendKeys(Keys.ARROW_DOWN).perform();
	}
	
	@Ignore
	@Test
	public void dragDroptest1() {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/index");
		WebElement source = driver.findElement(By.id("draggable"));
		WebElement target = driver.findElement(By.id("droptarget"));
		action.dragAndDrop(source, target).perform();
		// 0. move the mouse on top of the source
		// 1. Click and hold mouse
		// 2. move to the target element
		// 3. release the mouse
		// this example chains all the operations.
		// build() is used to add all those operation
		action.moveToElement(source).clickAndHold().moveToElement(target).release().build().perform();
		// actions may not be very dependable because it runs on JS. JS may behave
		// differently on different browsers
		action.moveToElement(source).clickAndHold().moveToElement(target).release().build().perform();

		//	action.dragAndDropBy(source, 0, -200).pause(1000).
		//	dragAndDropBy(source, 200, 0).pause(1000).
		//	dragAndDropBy(source, 0, -200).build().perform();

	    		
	}

	@Test
	public void parseString() {
		String str= "He ll lo Gu ys";
		String[] out= parseString(str, " ");
		System.out.println(str);
		for (String string : out) {
			System.out.println(string);
		}
	}
	
	private String[] parseString(String str, String p) {
		List<String> parsed= new ArrayList<String>();
		String temp="";
		for(int i=0; i<str.length(); i++) {
			if(str.substring(i, i+p.length()).equals(p)) {
				parsed.add(temp);
				i+=p.length()-1;
				temp="";
			}
			else if(i==str.length()-1) {
				temp+=str.charAt(i);
				parsed.add(temp);
			}
			else
				temp+=str.charAt(i);
			System.out.println(temp);
		}
		String[] result= new String[parsed.size()];
		result= parsed.toArray(result);
		
		return result;
		
	}


	@AfterClass
	public void tearDown() {
		// driver.close();
		System.gc();
	}
	
}
