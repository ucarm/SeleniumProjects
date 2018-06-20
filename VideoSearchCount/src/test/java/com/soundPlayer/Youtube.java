package com.soundPlayer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Youtube {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		
		Faker faker= new Faker();
		ArrayList<String> results= new ArrayList<String>(); 
		WebDriver driver;
//		driver.manage().window().fullscreen();
//		driver.manage().window().maximize();
		String result;
		int i=1;
		while(i<10) {
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			result="";
			driver.get("https://www.youtube.com/");
			Thread.sleep(1000);
			String search= faker.artist().name();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@id='container']//div[@id='search-input']/input")).sendKeys(search+Keys.ENTER);
			Thread.sleep(1000);
			result= driver.findElement(By.xpath("//div[@id='container']//yt-formatted-string[@id='result-count']")).getText();
			Thread.sleep(1000);
			System.out.println(search+"\t: "+result);
			results.add(search+"\t: "+result);
			i++;
			driver.close();
		}
		
		for (String string : results) {
			System.out.println(string);
		}
		
	}

	

}
