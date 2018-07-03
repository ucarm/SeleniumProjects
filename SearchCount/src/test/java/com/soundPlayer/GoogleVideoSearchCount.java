package com.soundPlayer;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleVideoSearchCount {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		Faker faker= new Faker();
		ArrayList<String> searchResults= new ArrayList<String> ();
		
		for(int i=0; i<3; i++) {
			WebDriver driver = new ChromeDriver();
			String artistName= faker.artist().name();
			driver.get("http://www.google.com");
			driver.findElement(By.xpath("//div[@id='gs_lc0']//input[@maxlength='2048']"))
				.sendKeys(artistName+Keys.ENTER);
			driver.findElement(By.xpath("//a[.='Videos']")).click();
			
			String searchResult= driver.findElement(By.xpath("//div[@id='resultStats']")).getText();
			int searchNumber= convertToNumber(searchResult);
			searchResults.add(i+1+" :\t "+searchNumber);
//			Thread.sleep(2000);
			driver.close();
		}
		System.out.println("SEARCH ITEM :\t # of SEARCH RESULTS ");
		for (String string : searchResults) {
			System.out.println(string);
		}
		
		
	}

	private static int convertToNumber(String searchResult) {
		// TODO Auto-generated method stub
		String temp="";
		StringBuilder searchResult1= new StringBuilder(searchResult);
		for(int i=0; i<searchResult1.length(); i++)
		{
			if(searchResult1.charAt(i)>='0' && searchResult1.charAt(i)<='9' && i< searchResult1.indexOf("result")) {
				temp+=searchResult1.charAt(i);
				System.out.print(searchResult1.charAt(i));
			}
		}
		int res= Integer.parseInt(temp);
		return res;
	}

}
