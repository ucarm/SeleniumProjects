package pomdesign;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.Utils;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AllOrdersPage;
import pages.OrderPage;
import pages.WebOrdersLoginPage;
import utilities.MyUtils;

public class OrderPageTest {
	WebDriver driver;
	OrderPage orderPage;
	AllOrdersPage allOrdersPage;
	WebOrdersLoginPage loginPage;
	Faker data;

	String username = "Tester";
	String password = "test";

	// values to verify the saved order
	String productName;
	int quantity;
	String fullName;
	String street;
	String city;
	String state;
	String zip;
	String cardType;
	String CardNr;
	String exp;

	@BeforeClass
	public void beforeClass() {
		System.out.println("Set up selenium");
		WebDriverManager.chromedriver().setup();
		data = new Faker();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

	}

	@BeforeMethod
	public void setUpApplication() {
		loginPage = new WebOrdersLoginPage(driver);
		allOrdersPage = new AllOrdersPage(driver);
		orderPage = new OrderPage(driver);
	}

	@Test(description = "Login, click o the orders page", priority = 1)
	public void goToOrderPage() {
		MyUtils.takeScreenShot(driver, "Login Page before Login");
		loginPage.login(driver, username, password);
		MyUtils.takeScreenShot(driver, "Logged In");
		allOrdersPage.orderTab.click();
		MyUtils.takeScreenShot(driver, "Orders page clicked");
		try {
			assertTrue(orderPage.orderTitle.getText().contains("Order"));
		} catch (AssertionError e) {
			MyUtils.takeScreenShot(driver, "ERROR -Login is failed ");
			e.getMessage();
		}
	}

	@Test(description = "Fill out the Order form and click on process button", priority = 2)
	public void processTheLoginPage() throws InterruptedException {
		// select a random index 1-3 inclusive to choose a random product;
		int productIndex = data.number().numberBetween(0, 3);
		productName = productNameConverter(productIndex);
		quantity = data.number().numberBetween(1, 1000);
		fullName = data.name().fullName();
		street = data.address().streetAddress();
		city = data.address().cityName();
		state = data.address().stateAbbr();
		zip = data.address().zipCode().substring(0, 5);
		int cardTypeIndex = data.number().numberBetween(1, 4);
		cardType = cardTypeConverter(cardTypeIndex);
		CardNr = data.finance().creditCard().replaceAll("-", "");
		exp = generateExpiration();
		// all values are generated

		orderPage.selectProductDropDown(productIndex);
		orderPage.quantity.clear();
		orderPage.quantity.sendKeys(String.valueOf(quantity));
		orderPage.calculateButton.click();
		orderPage.customerName.sendKeys(fullName);
		orderPage.street.sendKeys(street);
		orderPage.city.sendKeys(city);
		orderPage.state.sendKeys(state);
		orderPage.zip.sendKeys(zip);

		MyUtils.takeScreenShot(driver, "Order Page data is entered");

		orderPage.selectCreditCardTypeByIndex(cardTypeIndex);
		orderPage.creditCardNum.sendKeys(CardNr);
		orderPage.expDate.sendKeys(exp);
		// click on process button

		MyUtils.takeScreenShot(driver, "Credit card data is entered in order page");

		orderPage.processButton.click();

		String actual = orderPage.messageAfterProcess.getText().trim();
		String expected = "New order has been successfully added.";
		try {
			assertEquals(actual, expected);
			MyUtils.takeScreenShot(driver, "new order is added.");

		} catch (AssertionError e) {
			MyUtils.takeScreenShot(driver, "Error something unexpected detected in order page");

		}
	}

	@Test(description = "verify if all are saved into the viewAllOrders page", priority = 3)
	public void allOrdersVerification() {
		// go to view all orders page.
		allOrdersPage.viewAllOrders.click();

		// print all the rows;
		List<WebElement> allOrdersList = allOrdersPage.ordersTable.findElements(By.tagName("tr"));
		for (WebElement orders : allOrdersList) {
			System.out.println(orders.getText());
		}
		// select the most added entry (row 2) ans save the output

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
		LocalDate localdate = LocalDate.now();
		String dateToBeDisplayed = localdate.format(dateFormatter);

		String actual = allOrdersList.get(1).getText();
		String expected = fullName + " " + productName + " " + quantity + " " + dateToBeDisplayed + " " + street + " "
				+ city + " " + state + " " + zip + " " + cardType + " " + CardNr + " " + exp;

		System.out.println("-------------\nActual\t\t:" + actual + "\n" + "Expected\t:" + expected + "\n-------------");

		assertEquals(actual, expected);
	}

	@AfterClass
	public void afterClass() {
		allOrdersPage.logout();
		System.out.println("Done with driver. ");
		driver.close();
	}

	public String productNameConverter(int productIndex) {
		String result = "";
		switch (productIndex) {
		case 0:
			result = "MyMoney";
			break;
		case 1:
			result = "FamilyAlbum";
			break;
		case 2:
			result = "ScreenSaver";
			break;
		}
		return result;
	}

	public String cardTypeConverter(int cardTypeIndex) {
		String result = "";
		switch (cardTypeIndex) {
		case 1:
			result = "Visa";
			break;
		case 2:
			result = "MasterCard";
			break;
		case 3:
			result = "American Express";
			break;
		}
		return result;
	}

	public String generateExpiration() {
		// TODO Auto-generated method stub
		String result = "";
		int year = data.number().numberBetween(19, 26);
		int monthInt = data.number().numberBetween(1, 13);
		if (monthInt < 10)
			result += "0" + monthInt;
		else
			result += monthInt;
		return result + "/" + year;
	}

}
