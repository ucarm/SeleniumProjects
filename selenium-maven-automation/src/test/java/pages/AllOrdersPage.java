package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AllOrdersPage {
	public AllOrdersPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h1[.='Web Orders']")
	public WebElement webOrders;
	
	@FindBy(css=".login_info")
	public WebElement welcomeMsg;
	
	@FindBy(xpath="//h2[contains(text(),'List of ')]")
	public WebElement listOfAllOrders;
	
	@FindBy(xpath="//a[contains(text(),'View all orders')]")
	public  WebElement viewAllOrders;
	

	@FindBy(linkText="View all products")
	public  WebElement viewAllProducts;
	
	@FindBy(linkText="Order")
	public  WebElement orderTab;
	
	@FindBy(id="ctl00_logout")
	public WebElement logoutLink;
	
	@FindBy(id="ctl00_MainContent_orderGrid")
	public WebElement ordersTable;
	
	public void logout() {
		logoutLink.click();
	}
	
	
}
