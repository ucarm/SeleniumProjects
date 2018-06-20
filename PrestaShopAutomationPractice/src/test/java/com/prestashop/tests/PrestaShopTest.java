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

public class PrestaShopTest {
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
	
	@Test
	public void  loginTest() throws InterruptedException {
		driver.findElement(By.xpath("//input[@id='email_create']")).sendKeys(email);
	//			// click on create an account button
		driver.findElement(By.xpath("//button[@id='SubmitCreate']")).click();
		Thread.sleep(1000);
	//			boolean isDisplayed= driver.getPageSource().contains("Password is required");
	//			Assert.assertTrue(isDisplayed);
		String firstName= faker.name().firstName();
		String lastName= faker.name().lastName();
		String address= faker.address().streetAddress();
		String city= faker.address().city();
		String zip=faker.address().zipCode().substring(0, 5);
		String country= faker.address().country();
		String phoneNumber = faker.phoneNumber().cellPhone();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='customer_firstname']")).sendKeys(firstName);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='customer_lastname']")).sendKeys(lastName);
		Thread.sleep(1000);
//		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='passwd']")).sendKeys(pass);
		Thread.sleep(1000);
//		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys(firstName);
		Thread.sleep(1000);
		driver.findElement(By.id("address1")).sendKeys(address);
//		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys(lastName);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys(city);
		//random number for state selection
		int randomState=faker.random().nextInt(50)+1; //from 1 to 50.
		Thread.sleep(1000);
		driver.findElement(By.xpath("//select[@id='id_state']/option[@value='"+randomState+"']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='postcode']")).sendKeys(zip);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//select[@id='id_country']/option[@value='21']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("phone_mobile")).sendKeys(phoneNumber);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='My address']")).clear();
		driver.findElement(By.xpath("//input[@value='My address']")).sendKeys(address);
		Thread.sleep(1000);
		// click on register button
		driver.findElement(By.id("submitAccount")).click();
		// log out
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='logout']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys(pass);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@id='SubmitLogin']")).click();
		// logged back in
		String fullName= firstName+" "+lastName;
		Thread.sleep(1000);
		String fullNameinpage= driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
		Assert.assertTrue(fullName.equalsIgnoreCase(fullNameinpage));
	}
	
	@AfterMethod
	public void afterMethod() {
		driver.close();
		System.gc();
	}
	
	
}