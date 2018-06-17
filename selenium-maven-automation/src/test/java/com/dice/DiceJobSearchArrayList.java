package com.dice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearchArrayList {
	public static void main(String[] args) {
		//Set up chrome driver path. This is where bonigarcia comes in :)		
		//Type the "java developer" keyword in search box.
		
		ArrayList<String> keyword = new ArrayList<String>();
		keyword.add("Java Developer");
		keyword.add("Ruby Developer");
		keyword.add("C# Developer");
		keyword.add("JavaScript Developer");
		keyword.add("C Developer");
		keyword.add("Python Developer");
		keyword.add("Automation Engineer");
		keyword.add("AWS Developer");
		keyword.add("Business Analyst");
		keyword.add("Software Architecture");
		keyword.add("Scrum Master");
		keyword.add("Network Administrator");
		keyword.add("Data Analyst");
		keyword.add("Perl Developer");
		keyword.add("Swift Developer");
		keyword.add("Scala Developer");
		keyword.add("PHP Developer");
		keyword.add("SQL Developer");
		keyword.add("C++ Developer");
		keyword.add("Rust Developer");
		
		for (int i = 0; i < keyword.size(); i++) {

			WebDriverManager.chromedriver().setup();
			
			//Invoke/open Selenium Webdriver
			WebDriver driver = new ChromeDriver();
			
			//Makes the webpage gp ullscreen when opened
			driver.manage().window().fullscreen();	
			
			//Set universal wait time in case web page is slow
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//Step 1. Lunch browser and navigate to https://dice.com
			//Expected: dice home page should be displayed	
			String url = "https://dice.com";
			driver.get(url);
			
			//Confirm that expected title which is "Job Search for Technology Professionals | Dice.com" home page is displayed
			//This can be found under "head"
			String actualTitle = driver.getTitle();
			String expectedTitle = "Job Search for Technology Professionals | Dice.com";
			
			//Check if it the title is found
			if(actualTitle.equals(expectedTitle)) {
				System.out.println("Step PASS. Dice homepage successfully loaded.");
			} else {
				System.out.println("Step FAIL. Dice homepage did not load successfully");
				//Stop the code if the web site does not load. Even better, show and error message
				//then stop. 
				throw new RuntimeException("Step FAIL. Dice homepage did not load successfully");
				
			}
				
			//Clear search box.
			driver.findElement(By.id("search-field-keyword")).clear();
			
			//String keyword = "java developer";
			driver.findElement(By.id("search-field-keyword")).sendKeys(keyword.get(i));

	
			
			//Clear the address box and type 22102
			String location = "77498";
			driver.findElement(By.id("search-field-location")).clear();
			driver.findElement(By.id("search-field-location")).sendKeys(location);
			
			//Click the "Find tech jobs" button
			driver.findElement(By.id("findTechJobs")).click();
			
			//Print some text from the page
			String count = driver.findElement(By.id("posiCountId")).getText();
			//System.out.println(count);
			
			//Ensure count is more than 0. To do that we have to convert the string count which is
			//2,515 needs to be converted to integer. count.replaceAll(",", "") deletes comma and 
			//replaces it with empty string.
			int countResult = Integer.parseInt(count.replace(",", ""));
			
			if(countResult > 0) {
				System.out.println("Step PASS :" + keyword.get(i) + " search returned " + countResult + 
						" result in " + location);
			} else {
				System.out.println("Step FAIL :" + keyword.get(i) + " search returned " + countResult + 
						" result in " + location);
			}
			
			//Close the browser
			driver.close();
			
			
		}
		
	}

}

