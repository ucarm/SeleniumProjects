package com.amazon;

import java.util.ArrayList;
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

public class ListAmazonResults {
	WebDriver driver;
	List<Double> prices = new ArrayList<Double>();
	List<String> itemNames = new ArrayList<String>();

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void getAllProductsV1() {
		driver.get("https://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Daps&field-keywords=wooden+spoon");
		List<WebElement> descriptions= driver.findElements(By.xpath("//h2")); 
		for (WebElement item : descriptions) {
			System.out.println(item.getText());
		}
		
		List<WebElement> pricesWeb= driver.findElements(By.xpath("//span[@class='sx-price sx-price-large']")); 
		for (WebElement price : pricesWeb) {
			System.out.println(price.getText());
		}
		
		for (int i=0; i<descriptions.size(); i++) {
			System.out.println("============================");
			System.out.println(descriptions.get(i).getText());
			System.out.println(pricesWeb.get(i).getText());
		}
		
		String outerXpath="//div[@class='s-item-container']";
		List<WebElement> wholeItems= driver.findElements(By.xpath(outerXpath));
		System.out.println("Number of items : "+ wholeItems.size());
		
		for(int i=0; i<wholeItems.size(); i++) {
			if(wholeItems.get(i).getText().isEmpty())
					continue;
			String descXpath= "("+outerXpath+")["+(i+1)+"]//h2";
			String priceXpath= "("+outerXpath+")["+(i+1)+"]//span[@class='sx-price sx-price-large']";
			System.out.println("========  ITEM #"+(i+1)+"   ========");
			System.out.println("Descr\t:\t"+driver.findElement(By.xpath(descXpath)).getText());
			System.out.println("Price\t:\t"+driver.findElement(By.xpath(priceXpath)).getText());
			System.out.println("------------------------");

		}
		
		
	}

	@Test
	public void getAllProductsV2() {

	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

}
