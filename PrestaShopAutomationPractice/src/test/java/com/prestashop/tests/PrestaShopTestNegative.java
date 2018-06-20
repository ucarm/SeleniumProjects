package com.prestashop.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestaShopTestNegative{
	WebDriver driver;
	Faker faker;
	String email;
	String pass;
	
	@BeforeMethod
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		faker= new Faker();
		driver.get("http://automationpractice.com");
		//click on login link
		driver.findElement(By.xpath("//a[@class='login']")).click();
		pass= faker.internet().password(8, 12);
		email= faker.internet().emailAddress();


	}
	
	@Test
	public void wrongCredentialsTest() throws InterruptedException {
		
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pass);
		// click on sign in button
		driver.findElement(By.id("SubmitLogin")).click();
		Thread.sleep(1000);
		boolean isDisplayed= driver.getPageSource().contains("Authentication failed.");
		Assert.assertTrue(isDisplayed);
	}
	
	@Test
	public void  invalidEmailTest() throws InterruptedException {
		// generate a fake email address with the help of faker and sendKeys to login
		email= faker.internet().avatar();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pass);
		// click on sign in button
		driver.findElement(By.id("SubmitLogin")).click();
		Thread.sleep(1000);
		boolean isDisplayed= driver.getPageSource().contains("Invalid email address");
		Assert.assertTrue(isDisplayed);
	}
	
	@Test
	public void  blankEmailTest() throws InterruptedException {
		email= "";// blank email
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pass);
		// click on sign in button
		driver.findElement(By.id("SubmitLogin")).click();
		Thread.sleep(1000);
		boolean isDisplayed= driver.getPageSource().contains("An email address required");
		Assert.assertTrue(isDisplayed);
	}
	
	@Test
	public void  blankPassTest() throws InterruptedException {
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
		// generate fake password
		pass= ""; // empty string
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pass);
		// click on sign in button
		driver.findElement(By.id("SubmitLogin")).click();
		Thread.sleep(1000);
		boolean isDisplayed= driver.getPageSource().contains("Password is required");
		Assert.assertTrue(isDisplayed);
	}
	

	
	@AfterMethod
	public void afterMethod() {
		driver.close();
		System.gc();
	}
	
	
}
