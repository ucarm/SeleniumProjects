package com.porsche;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PorscheCheckout {
	public static void main(String[] args) throws InterruptedException {
//		STEP 1.Open browser
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
//		driver.manage().window().fullscreen();
//		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
//		STEP 2.Go to url “https://www.porsche.com/usa/modelstart/”
		driver.get("https://www.porsche.com/usa/modelstart/");

//		STEP 3.Select model 718
		driver.findElement(By.xpath("//a[@href='/usa/modelstart/all/?modelrange=718']")).click();
		
//	   	STEP 4.Remember the price of 718 Cayman
		int priceHomePage= priceConverter(driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[1]/div[2]/div[2]")).getText());
		System.out.println("Base Price : $"+priceHomePage);
		
//		STEP 5. Click on Build & Price under 718 Cayman
		driver.findElement(By.xpath("(//a[@class='m-01-link m-14-build'])[1]")).click();

//		FOLLOWING LINES ARE FOR CHANGING NEW WINDOW FOR DRIVER
//		Store the current window handle
//		String winHandleBefore = driver.getWindowHandle();
//		Perform the click operation that opens new window// Switch to new window opened
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
//		Perform the actions on new window// Close the new window, if that window no more required
//		driver.close();// Switch back to original browser (first window)
//		driver.switchTo().window(winHandleBefore);// Continue with original browser (first window)
//		System.out.println(driver.getPageSource());
		
//		Linux ISSUE DETECTED. Even if I have the latest version of chrome. I kept getting the browser not updated box. Following lines resolves it
		if(driver.findElement(By.xpath("//div[@class='header']")).getText().contains("out of date.")) {
			System.out.println("Found a browser issue");
//			driver.findElement(By.xpath("//li[@class='dlgButton button_button0']/a")).click();
			driver.findElement(By.xpath("//li[1]")).click();
			System.out.println("Now passed the update browser error.");
		}

//		STEP 6. Verify that Base price displayed on the page is same as the price from step 4		
		int priceSecondPage= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[5]")).getText());
		verifyPrices(priceHomePage,priceSecondPage,6);
		
//		STEP 7. Verify that Price for Equipment is 0
		int priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(priceEquipment,0,7);
//		STEP 8. Verify that total price is the sum of base price + Delivery, Processing and Handling Fee
		int totalPrice=  priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
		int deleiveryPrice= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText().toString());
		verifyPrices(totalPrice,(priceSecondPage+deleiveryPrice),8);
		
//		STEP 9.Select color “Miami Blue”
		driver.findElement(By.id("s_exterieur_x_FJ5")).click();
//		STEP 10. Verify that Price for Equipment is Equal to Miami Blue price
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		int priceMiamiBlue= priceConverter(driver.findElement(By.id("s_exterieur_x_FJ5")).getAttribute("data-price"));
		verifyPrices(priceEquipment,priceMiamiBlue,10);
//		STEP 11. Verify that total price is the sum of base price + Price for Equipment + Delivery,		Processing and Handling Fee
		totalPrice=  priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
		deleiveryPrice= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(totalPrice,(priceSecondPage+deleiveryPrice+priceEquipment),11);
//		12. Select 20" Carrera Sport Wheels
		//open the Overview expander first
		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click(); //
		//click on wheels to expand the expandable window
		driver.findElement(By.xpath("//div[@id='submenu_exterieur_x_AA_submenu_x_IRA']")).click();
		//close exterior color
		driver.findElement(By.id("IAF_subHdl")).click();
		//click on color
		driver.findElement(By.xpath("//li[@id='s_exterieur_x_MXRD']//span[@class='img-element']")).click();
	
//		STEP 13. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
		Thread.sleep(1000);

		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		int price20CarreraWheels = priceConverter(driver.findElement(By.id("s_exterieur_x_MXRD")).getAttribute("data-price"));
		verifyPrices(priceEquipment,(priceMiamiBlue+price20CarreraWheels),13);
//		System.out.println(priceEquipment+"\n"+priceMiamiBlue+"\n"+price20CarreraWheels);
		
//		14. Verify that total price is the sum of base price + Price for Equipment + Delivery,Processing and Handling Fee
		Thread.sleep(1000);
		totalPrice=  priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
		Thread.sleep(1000);
		deleiveryPrice= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
		Thread.sleep(1000);
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		Thread.sleep(1000);
		verifyPrices(totalPrice,(priceSecondPage+deleiveryPrice+priceEquipment),14);
		
//		15. Select seats ‘Power Sport Seats (14-way) with Memory Package’
		//open the Overview expander first
		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
		//select interior Colors and Seats
		driver.findElement(By.id("submenu_interieur_x_AI_submenu_x_submenu_parent")).click();
		Thread.sleep(1000);
		//click on seats
		driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Seats']")).click();
		// select ‘Power Sport Seats (14-way) with Memory Package’
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//div[@class='seat'])[2]/span")).click();
		
//		16. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
		Thread.sleep(1000);
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		int pricePowerSportSeats= priceConverter(driver.findElement(By.xpath("(//div[@class='seat'])[2]//div[@class='pBox']/div")).getText());
		Thread.sleep(1000);
		verifyPrices(priceEquipment,(priceMiamiBlue+price20CarreraWheels+pricePowerSportSeats),16);
//		System.out.println(priceMiamiBlue+"\n"+price20CarreraWheels+"\n"+pricePowerSportSeats+"\n"+priceEquipment);
		
//		17. Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		totalPrice=  priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
		deleiveryPrice= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(totalPrice,(priceSecondPage+deleiveryPrice+priceEquipment),17);
//		18. Click on Interior Carbon Fiber
		//open the Overview expander first
		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
		Thread.sleep(1000);
		//click on options
		driver.findElement(By.id("submenu_individualization_x_individual_submenu_x_submenu_parent")).click();
		Thread.sleep(1000);

		//interior choose carbon fiber
		driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Interior Carbon Fiber']")).sendKeys(Keys.PAGE_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.id("submenu_individualization_x_individual_submenu_x_IIC")).click();
		Thread.sleep(1000);
		
//		19. Select Interior Trim in Carbon Fiber i.c.w. Standard Interior 
		driver.findElement(By.id("vs_table_IIC_x_PEKH_x_c01_PEKH")).click();
		Thread.sleep(1000);
		
		
//		20. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior
		Thread.sleep(1000);
		int priceCarbonFiber= priceConverter(driver.findElement(By.xpath("//div[@id='vs_table_IIC_x_PEKH']//div[@class='box']//div[@class='pBox']/div")).getText());
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(priceEquipment,(priceMiamiBlue+price20CarreraWheels+pricePowerSportSeats+priceCarbonFiber),20);
		
		
//		21. Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		
		totalPrice=  priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
		deleiveryPrice= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(totalPrice,(priceSecondPage+deleiveryPrice+priceEquipment),21);
		
//		22. Click on Performance
		//open the Overview expander first
		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
		Thread.sleep(1000);
		//click on performance
		driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Performance']")).click();
		Thread.sleep(1000);

//		23. Select 7-speed Porsche Doppelkupplung (PDK)
		driver.findElement(By.xpath("//div[@data-link-id='M250']")).click();
		Thread.sleep(1000);
		int price7SpeedPDK = priceConverter(driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M250']//div[@class='pBox']/div")).getText());
	
//		24. Select Porsche Ceramic Composite Brakes (PCCB)
		//open the Overview expander first
		driver.findElement(By.xpath("//section[@id='s_conf_submenu']//div[@class='flyout-label-value']")).click();
		Thread.sleep(1000);
		//click on performance
		driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Performance']")).click();		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='subitem-entry'][.='Performance']")).sendKeys(Keys.PAGE_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.id("vs_table_IMG_x_M450_x_c94_M450_x_shorttext")).click();
		int priceOfPCCB = priceConverter(driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M450']//div[@class='pBox']/div")).getText());
		
//		25. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in
//			Carbon Fiber i.c.w. Standard Interior + 7-speed Porsche Doppelkupplung (PDK) +
//			Porsche Ceramic Composite Brakes (PCCB)
		Thread.sleep(1000);
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(priceEquipment,(priceMiamiBlue+price20CarreraWheels+pricePowerSportSeats+priceCarbonFiber
									+price7SpeedPDK+priceOfPCCB),20);
		
//		26. Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		totalPrice=  priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[8]")).getText());
		deleiveryPrice= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[7]")).getText());
		priceEquipment= priceConverter(driver.findElement(By.xpath("(//div[@class='ccaPrice'])[6]")).getText());
		verifyPrices(totalPrice,(priceSecondPage+deleiveryPrice+priceEquipment),26);
//		Close the driver
		driver.close();
		System.out.println("Test is completed. "+LocalDateTime.now());
	
	}
	
	private static void verifyPrices(int priceHomePage, int priceSecondPage, int stepNum) {
//		Verification if both prices are same
		if(priceHomePage==priceSecondPage) {
			System.out.println("PASS Step #"+stepNum +". \t$"+priceHomePage+ "\t= $"+priceSecondPage);
		}
		
		else {
			System.out.println("FAIL.Step #"+stepNum+". \t$"+priceHomePage+ "\t!= $"+priceSecondPage);

		}
		
	}
	
	

	private static int priceConverter(String text) {
		//converts a string into an integer
		String temp="";
		for (int i = 0; i < text.length(); i++) {
			if(Character.isDigit(text.charAt(i))) {
				temp+=text.charAt(i);
//				System.out.println(temp);//TESTING
			}
			else if(text.charAt(i)=='.') {
				break;
			}
		}
		if (temp.length()==0)
			return 0;
		int result= Integer.parseInt(temp);
		return result;
	}
	
}
