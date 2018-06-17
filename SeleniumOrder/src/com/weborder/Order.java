package com.weborder;
import java.time.LocalDate;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Order {
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver","/home/ussr/selenium dependencies/drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
		driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
		driver.findElement(By.id("ctl00_MainContent_login_button")).click();
		
		Random rand= new Random();
		int randNum= rand.nextInt(100)+1;
		
		driver.findElement(By.cssSelector("a[href='Process.aspx']")).click();
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(""+randNum);
		
		String randStr= "";
		int sizeOfStr = rand.nextInt(9)+1;
		for (int i=0; i<sizeOfStr; i++ ) 
			randStr+=(rand.nextInt(25)+64)+"";
		
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys("John "+randStr+" Smith.");
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys("123 Any st");
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys("Anytown");
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys("Virginia");
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys("77777");
		
		int selectCard=rand.nextInt(3);
		
		driver.findElement(By.cssSelector("input[id='ctl00_MainContent_fmwOrder_cardList_"+selectCard+"']")).click();
		StringBuilder CreditCardNum= new StringBuilder();
		if(selectCard==0)
			CreditCardNum.append("4");
		else if(selectCard==1)
			CreditCardNum.append("5");
		else if(selectCard==2)
			CreditCardNum.append("3");
		
		for(int i=0; i<15; i++)
			CreditCardNum.append(rand.nextInt(10));
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(CreditCardNum.toString());
		
		LocalDate ex= LocalDate.now();
		ex.plusYears(1);
		String Month="";
		if(ex.getMonth().getValue()<10) 
			Month="0"+ex.getMonth().getValue();	
		else
			Month= ex.getMonth().getValue()+"";		
		String Expiration= (Month+"/"+ex.getYear());
		Expiration = Expiration.substring(0,Expiration.length()-2);
		
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys(Expiration);
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
		
		if(driver.getPageSource().contains("New order has been successfully added."))
			System.out.println("Pass");
		else 
			System.out.println("Fail");
		
	}
	
}
