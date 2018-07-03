package com.soundPlayer;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SoundPlayer {
	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		Faker faker= new Faker();
		driver.get("https://soundcloud.com");

		ArrayList<String> searchResults= new ArrayList<String> ();
		for(int i=0; i<searchResults.size(); i++){
			String country = faker.address().country();
			String searchItem= ""+country;
			driver.findElement(By.xpath("//*[@id='content']/div/div/div[2]/div/div[1]/span/form/input"))
			.sendKeys(searchItem+Keys.ENTER);
			driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div/div/div/ul/li[3]/a")).click();
			
			String searchData= driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[3]/div/div/div/ul/div/div")).getText();
			System.out.println("Search results for "+ searchItem +"\t: "+ searchData);
			
			searchResults.add((1)+".\t"+country+"\t:"+searchData);
			driver.close();
		
		}
		
		for (String string : searchResults) {
			System.out.println(string);
		}
		
		
	}

//	private static ArrayList<String> saveResults(StringBuilder sb) {
//		// TODO Auto-generated method stub
//		ArrayList<String> allSongs = new ArrayList<String>();
//		while(sb.co)
//		
//		return allSongs;
//	}
}
