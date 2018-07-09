package pomdesign;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.WebOrdersLoginPage;

public class WebOrdersLogInTest {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		System.out.println("Set up selenium");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Ignore
	@Test
	public void positiveLogIntest() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
		driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
		driver.findElement(By.id("ctl00_MainContent_login_button")).click();
	}
	
	@Test(priority=1)
	public void positiveLoginUsingPOM() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		WebOrdersLoginPage loginPage= new WebOrdersLoginPage(driver);
//		loginPage.userName.sendKeys("Tester");
//		loginPage.password.sendKeys("test");
//		loginPage.loginButton.click();
		
		loginPage.login("Tester", "test");
	
	}
	
	@Test(priority=2)
	public void invalidUsernameTest() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		WebOrdersLoginPage loginPage= new WebOrdersLoginPage(driver);
		loginPage.userName.sendKeys("invalid");
		loginPage.password.sendKeys("test");
		loginPage.loginButton.click();
		String errMsg= loginPage.invalidUserNameErrMsg.getText();
		assertEquals(errMsg, "Invalid Login or Password.");
	}
	
	@AfterClass
	public void afterClass() {
		System.out.println("Done with driver. ");
		driver.close();
	}
}
