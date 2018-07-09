package pomdesign;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AllOrdersPage;
import pages.ProductsPage;
import pages.WebOrdersLoginPage;

public class WebOrdersTest {
	WebDriver driver;
	WebOrdersLoginPage loginPage;
	AllOrdersPage allOrdersPage;
	ProductsPage productsPage;
	String userId = "Tester";
	String password = "test";

	@BeforeClass
	public void beforeClass() {
		System.out.println("Set up selenium");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void setUpApplication() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		loginPage = new WebOrdersLoginPage(driver);
	}

	@Test(description = "Verify labels and tab links are displayed.", priority = 1)
	public void labelsVerification() {
		assertEquals(driver.getTitle(), "Web Orders Login", "Login page is not displayed");
		// loginPage.userName.sendKeys(userId);
		// loginPage.password.sendKeys(password);
		// loginPage.loginButton.click();

		loginPage.login(userId, password);

		allOrdersPage = new AllOrdersPage(driver);
		assertTrue(allOrdersPage.webOrders.isDisplayed(), "WebOrder title is not diplayed");
		assertTrue(allOrdersPage.listOfAllOrders.isDisplayed(), "Lis of All Orders title is not diplayed");
		assertTrue(allOrdersPage.viewAllOrders.isDisplayed(), "View all orders link is not displayed");
		assertTrue(allOrdersPage.viewAllProducts.isDisplayed(), "View all products link is not displayed");
		assertTrue(allOrdersPage.orderTab.isDisplayed(), "Order link is not displayed on the left menu");
	}

	// folowing test verifies the following table
	// MyMoney 		$100 	8%
	// FamilyAlbum 	$80 	15%
	// ScreenSaver 	$20 	10%
	@Test(description = "Verify the default product names, prices, and discount", priority = 2)
	public void availableProductsTest() {
		assertEquals(driver.getTitle(), "Web Orders Login", "Login page is not displayed");
		loginPage.login(userId, password);
		allOrdersPage = new AllOrdersPage(driver);
		allOrdersPage.viewAllProducts.click();
		productsPage = new ProductsPage(driver);
		List<String> expProducts = Arrays.asList("MyMoney", "FamilyAlbum", "ScreenSaver");
		List<String> actProducts = new ArrayList<>();

		// productsPage.productNames.forEach(elem-> actProducts.add(elem.getText()));

		for (WebElement prod : productsPage.productNames) {
			actProducts.add(prod.getText());
		}

		assertEquals(actProducts, expProducts);

		for (WebElement row : productsPage.productRows) {
			// if(row.getText().startsWith("MyMoney"))
			System.out.println(row.getText());
			String[] prodData = row.getText().split(" ");
			switch (prodData[0]) {
			case "MyMoney":
				assertEquals(prodData[1], "$100");
				assertEquals(prodData[2], "8%");
				break;
			case "FamilyAlbum":
				assertEquals(prodData[1], "$80");
				assertEquals(prodData[2], "15%");
				break;
			case "ScreenSaver":
				assertEquals(prodData[1], "$20");
				assertEquals(prodData[2], "10%");
				break;
			}
		}

	}

	

	
	@AfterMethod
	public void logOut() {
		allOrdersPage.logout();
	}

	@AfterClass
	public void afterClass() {
		System.out.println("Done with driver. ");
		driver.close();
	}

}
