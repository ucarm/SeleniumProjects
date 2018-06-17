package com.ucar;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

public class FakerTest {
	
	public static void main(String[] args) {
		System.out.println("Test");
		Faker faker = new Faker();
		String fakeAddr = faker.address().streetAddressNumber() +faker.address().cityName() 
				+" "+faker.address().state()+" "+faker.address().country();
		String ccNum = faker.finance().creditCard();
		String fact= faker.chuckNorris().fact();
		
		System.out.println("Address\t:"+fakeAddr);
		System.out.println("CC \t:"+ccNum);
		System.out.println("Fact \t:"+fact);
		
		System.setProperty("webdriver.chrome.driver","/home/ussr/selenium dependencies/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.chase.com");
		driver.findElement(By.xpath("//*[@id=\"skip-sidemenu\"]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[4]/nav/div/div[2]/div/ul/li[2]/a")).click();
//		Scanner scan= new Scanner(System.in);
//		String username= scan.nextLine();
//		String pass = scan.nextLine();
		String username= faker.name().username();
		String pass = faker.name().fullName();
		
		driver.findElement(By.cssSelector("input#userId-input-field.jpui.input.logon-xs-toggle.clientSideError")).sendKeys(username);
		driver.findElement(By.cssSelector("input#password-input-field.jpui.input.logon-xs-toggle.clientSideError")).sendKeys(pass);
		driver.findElement(By.cssSelector("button#signin-button.jpui.button.focus.fluid.primary")).click();
		
	}
}
