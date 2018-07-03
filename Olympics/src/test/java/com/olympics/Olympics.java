package com.olympics;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Olympics {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Go to website
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
	}

	@Test
	public void step1_2() {
		//step2
		List<WebElement> rankingList= driver.findElements(By.xpath("//table//..//caption[.='2016 Summer Olympics medal table']//..//tr/td[1]"));
		//remove the last cell (11-86)
		rankingList.remove(rankingList.size()-1);
		List<Integer> rankings = convertIntToList(rankingList);
		// System.out.println(rankings);
		// System.out.println(sortedList(rankings));
		assertTrue(rankings.equals(sortIntList(rankings)));

	}
	
	@Test
	public void step3_4() {
		//step 3 click on NOC
		driver.findElement(By.xpath("//table//..//caption[.='2016 Summer Olympics medal table']//..//tr/th[.='NOC']")).click();
		List<WebElement> countriesList= driver.findElements(By.xpath("//table//..//caption[.='2016 Summer Olympics medal table']//..//tr/td[2]"));
		List<String> countries = convertStringToList(countriesList);
		assertTrue(countries.equals(sortStringList(countries)));

	}

	private List<Integer> sortIntList(List<Integer> rankings) {
		List<Integer> newSortedList= new ArrayList<Integer>();
		for (Integer each : rankings) {
			newSortedList.add(each);
		}
		Collections.sort(newSortedList);
		return newSortedList;
	}

	private List<Integer> convertIntToList(List<WebElement> rankingList) {
		List<Integer> result = new ArrayList<Integer>();
		for (WebElement each : rankingList) {
			result.add(Integer.valueOf(each.getText()));
		}
		return result;
	}
	
	private List<String> sortStringList(List<String> countriesList) {
		List<String> newSortedList= new ArrayList<String>();
		for (String each : countriesList) {
			newSortedList.add(each);
		}
		Collections.sort(newSortedList);
		return newSortedList;
	}

	private List<String> convertStringToList(List<WebElement> rankingList) {
		List<String> result = new ArrayList<String>();
		for (WebElement each : rankingList) {
			result.add(each.getText());
		}
		return result;
	}



	
	@AfterClass
	public void tearDown() {
		driver.close();
		System.gc();
	}
}
