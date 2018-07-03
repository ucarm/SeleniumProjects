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

public class YoutubeArtistSearch {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		
		Faker faker= new Faker();
		ArrayList<YoutubeSearchResults> allResults= new ArrayList<>();
		WebDriver driver;
//		driver.manage().window().fullscreen();
//		driver.manage().window().maximize();
		YoutubeSearchResults result;
		int i=1;
		while(i<3) {
			result= new YoutubeSearchResults();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get("https://www.youtube.com/");
			Thread.sleep(1000);
			String searchName= faker.artist().name();
			result.setName(searchName);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@id='container']//div[@id='search-input']/input")).sendKeys(searchName+Keys.ENTER);
			Thread.sleep(1000);
			result.setFullSearchResult(driver.findElement(By.xpath("//div[@id='container']//yt-formatted-string[@id='result-count']")).getText());
			Thread.sleep(1000);
			result.setSearchCount(searchCount(result.getFullSearchResult()));
			allResults.add(result);
			System.out.println(result);
			i++;
			System.gc();
			driver.close();
		}
		System.out.println("\nARTIST NAME \t SEARCH COUNT");
		for (YoutubeSearchResults youtubeSearchResults : allResults) {
			System.out.println(youtubeSearchResults.getName()+"\t\t"+youtubeSearchResults.getSearchCount());
		}
		
		System.out.println("\nWHO IS the MOST FAMOUS?");
		YoutubeSearchResults famous= theMostSearchResult(allResults);
		System.out.println(famous.getName() +" has the maximum number of search results on Youtube."
												+"\nResult Count: "+famous.getSearchCount());
	}

	private static YoutubeSearchResults theMostSearchResult(ArrayList<YoutubeSearchResults> allResults) {
		int max= allResults.get(0).getSearchCount();
		int maxIndex=0;
		for (int i=0; i<allResults.size(); i++) {
			if(allResults.get(i).getSearchCount()>max) {
				maxIndex=i;
				max=allResults.get(i).getSearchCount();
			}
		}
		return allResults.get(maxIndex);
	}

	private static int searchCount(String fullSearchResult) {
		String num="";
		for(int i=0; i<fullSearchResult.length(); i++) {
			if(Character.isDigit(fullSearchResult.charAt(i)))
				num+=fullSearchResult.charAt(i);
		}
		return Integer.parseInt(num);
	}

	

}
