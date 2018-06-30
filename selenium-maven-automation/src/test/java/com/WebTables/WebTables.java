package com.WebTables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTables {
	WebDriver driver;
	String url="file:///home/ussr/selenium-workplace/selenium-maven-automation/src/test/java/com/WebTables/webtable.html";

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void readScores() {
		driver.get(url);
		// read all webtable and print
		WebElement table = driver.findElement(By.tagName("table"));
		System.out.println(table.getText());
		
		// find out how many rows we have
		List<WebElement> rows=  table.findElements(By.xpath("//tbody/tr"));
		System.out.println(rows.size()+" Scores found..\n");
		
		//print all table headers. one by one
		//get all headers into a list
		// use a loop to print all
		
		String headerPath="//table[@id='worldCup']/thead/tr/th";
		List<WebElement> headers = driver.findElements(By.xpath(headerPath));
		List<String> expHeader= Arrays.asList("Team1", "Score", "Team2" );
		List<String> actHeader= new ArrayList<>();
		
		
		for (WebElement header : headers) {
			actHeader.add(header.getText());
			System.out.println(header.getText());
		}
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(actHeader, expHeader);
		
		//write xpath and findElement gettext --> needs to print Egypt
		String egyptPath="//table[@id='worldCup']/tbody/tr[3]/td[3]";
		softAssert.assertEquals(driver.findElement(By.xpath(egyptPath)).getText(), "Egypt");
		
		// loop it and print all data
		// get number of rows and columns then nested loop
		String rowXpath="//table[@id='worldCup']/tbody/tr";
		String colXpath= "//table[@id='worldCup']/tbody/tr[1]/td";
		int rowsCount= driver.findElements(By.xpath(rowXpath)).size();
		int colsCount= driver.findElements(By.xpath(colXpath)).size();
		System.out.println(rowsCount+"x"+colsCount);
		
		System.out.println("===================");
		for(int row=1; row<=rowsCount; row++) {
			for(int col=1; col<=colsCount; col++)
				System.out.print(driver.findElement(By.xpath("("+rowXpath+")["+row+"]/td["+col+"]")).getText()+"\t");
			System.out.println();
		}
		
		softAssert.assertAll();
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();
	}
	
}
