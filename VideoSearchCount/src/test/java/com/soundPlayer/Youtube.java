package com.soundPlayer;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Youtube {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		Faker faker= new Faker();
		ArrayList<String> results= new ArrayList<String>(); 
		
		int i=1;
		while(i<2) {
			WebDriver driver = new ChromeDriver();
			driver.get("https://www.google.com/");
			String search= faker.music().instrument();
			driver.findElement(By.cssSelector("input[label='Search']")).sendKeys(search+Keys.ENTER);
//			StringBuilder pageSource= new StringBuilder(driver.getPageSource());
//			String searchResult=getHowMany(pageSource);
			
			
			//			if(searchResult.isEmpty())
//				String searchResult =driver.findElement(By.cssSelector("p[class='num-results first-focus']")).getText();
//			System.out.println(searchResult);
//			results.add(i+": "+search+"\t "+searchResult);
			i++;
			driver.close();
		}
		System.out.println(results);
	}

//	private static String getHowMany(StringBuilder pageSource) {
//		int index1=pageSource.indexOf("resultCount");
//		System.out.println(index1);
//		int index2= pageSource.indexOf("results\"},\"button\":{\"toggleButtonRenderer\"");
//		return pageSource.substring(index1+28, index2);
//	}

}
