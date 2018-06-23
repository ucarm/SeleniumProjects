package com.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonTestCases {
	
	WebDriver driver;
	String str;
	
	@BeforeMethod
	public void beforeMethod() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.amazon.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	
	@Test
	public void amazonSearch1() throws InterruptedException {
		str= "Selenium Testing Tools Cookbook";
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(str+Keys.ENTER);
		String resultXpath = "//h2[@class='a-size-medium s-inline  s-access-title  a-text-normal'][.='"+str+"']";
		boolean displayed= driver.findElement(By.xpath(resultXpath)).isDisplayed();
		
		System.out.println(displayed);
		Assert.assertTrue(displayed);
		Thread.sleep(100);
		driver.findElement(By.id("twotabsearchtextbox")).clear();
		Thread.sleep(100);

		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Java OCA Book"+Keys.ENTER);
		displayed= driver.findElement(By.xpath(resultXpath)).isDisplayed();
		Thread.sleep(100);
		System.out.println(displayed);
		Assert.assertFalse(displayed);		
	}
	
	@Test
	public void amazonSearch2() throws InterruptedException {
		//verfiy all the results contain the word "selenium" case insensitive 
		str= "Selenium book";
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(str+Keys.ENTER);
		
//		Save all the search results in a List
		Thread.sleep(1000);
		List<WebElement> searchResults= driver.findElements(
				By.xpath("//h2[@class='a-size-medium s-inline  s-access-title  a-text-normal']"));
		Thread.sleep(1000);
		
		boolean result= verify(searchResults,"selenium");
		Assert.assertFalse(result);
	}
	

	
	@Test
	public void amazonSearch3() throws InterruptedException {
		str= "Selenium book";
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(str+Keys.ENTER);
		//Verify prime check box is not selected
		Thread.sleep(1000);
		WebElement primeCheckbox= driver.findElement(By.xpath("//div[@data-a-input-name='s-ref-checkbox-2470955011']//input[@type='checkbox']"));
		Thread.sleep(1000);
		boolean primeSelected = Boolean.parseBoolean(primeCheckbox.getAttribute("value").toString());
		Assert.assertFalse(primeSelected);
		Thread.sleep(1000);
		List<WebElement> resultsPrimeNotSelectedWeb = driver.findElements(By.xpath("//h2[@class='a-size-medium s-inline  s-access-title  a-text-normal']"));
		List<String> resultsPrimeNotSelected= new ArrayList<String>();
		saveWebElementAsString(resultsPrimeNotSelected,resultsPrimeNotSelectedWeb );
		Thread.sleep(1000);
		//Check the prime checkbox
//		primeCheckbox.sendKeys(Keys.PAGE_DOWN);
		primeCheckbox.click();
		Thread.sleep(1000);
		try{
			primeSelected =  Boolean.parseBoolean(primeCheckbox.getAttribute("value").toString());
		}
		catch(StaleElementReferenceException e) {
			primeCheckbox= driver.findElement(By.xpath("//div[@data-a-input-name='s-ref-checkbox-2470955011']//input[@type='checkbox']"));
			primeSelected =  Boolean.parseBoolean(primeCheckbox.getAttribute("value").toString());

		}
		Thread.sleep(1000);
		Assert.assertTrue(primeSelected);
		List<WebElement> resultsPrimeSelectedWeb = driver.findElements(By.xpath("//h2[@class='a-size-medium s-inline  s-access-title  a-text-normal']"));
		List<String> resultsPrimeSelected= new ArrayList<String>();
		saveWebElementAsString(resultsPrimeSelected,resultsPrimeSelectedWeb );

		Thread.sleep(1000);
		System.out.println("Test after 2nd list loads");

		Thread.sleep(1000);
		
		//Verify that top 5 results are still same
		System.out.println("Before check5");
		boolean first5= verfiyFirst5resultsSame(resultsPrimeNotSelected,resultsPrimeSelected);
		Thread.sleep(10000);
		System.out.println("After check5");
		Assert.assertTrue(first5);
		
		
		
	}
	private void saveWebElementAsString(List<String> resultsPrimeNotSelected,
			List<WebElement> resultsPrimeNotSelectedWeb) {

		for (WebElement webElement : resultsPrimeNotSelectedWeb) {
			resultsPrimeNotSelected.add(webElement.getText());
		}
		
	}

	private boolean verfiyFirst5resultsSame(List<String> resultsPrimeNotSelected,
			List<String> resultsPrimeSelected) {
			System.out.println("FIRST\t-->\tSECOND");
		for(int i=0; i<5;i++) {
			System.out.println("\nFIRST\t"+(i+1)+"\t: "+resultsPrimeNotSelected.get(i)+"\nSECOND\t"+(i+1)+"\t: "+resultsPrimeSelected.get(i));
			if(! resultsPrimeNotSelected.get(i).equals(resultsPrimeSelected.get(i))) {
				return false;
			}
		}
		return true;
	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}
	
	
	private boolean verify(List<WebElement> searchResults, String string) {
		//iterate over searchResults and return true if all of the results contain word String
		int i=1;
		for(WebElement eachResult : searchResults) {
			System.out.println(i+".\t:"+eachResult.getText().toUpperCase().contains(string.toUpperCase())+"\t text:"+eachResult.getText());

			if (!eachResult.getText().toUpperCase().contains(string.toUpperCase())) {
				return false;
			}
			i++;
		}
		return true;
	}
	
}



//	=====	NOTES  ======
//Following css will list all the search results in css
//css - > h2.a-size-medium.s-inline.s-access-title.a-text-normal

//In XPath
//Xpath can handle text, but cssSelector can't
//1- only matches the results on the page 2- can filter by text
//xpath //h2[@class='a-size-medium s-inline  s-access-title  a-text-normal'][contains(text(),'Selenium')]

//in xpath , . means exact text
//	//*[.='text'] --> matches all elements on the page matches "text" 
//xpath complete result output is
//xpath //h2[@class='a-size-medium s-inline  s-access-title  a-text-normal'][.='Selenium Testing Tools Cookbook']

