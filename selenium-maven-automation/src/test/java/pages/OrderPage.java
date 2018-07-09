package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OrderPage {

	public OrderPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//div[@class='content']/h2")
	public WebElement orderTitle;
	
	@FindBy(xpath="//select[@id='ctl00_MainContent_fmwOrder_ddlProduct']")
	public WebElement productDropDown;
	
	public void selectProductDropDown(int selectionId) {
		Select dropdown= new Select(productDropDown);
		dropdown.selectByIndex(selectionId);
	}
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtQuantity")
	public WebElement quantity;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtUnitPrice")
	public WebElement pricePerUnit;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtDiscount")
	public WebElement discount ;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtTotal")
	public WebElement total;
	
	@FindBy(xpath="//input[@value='Calculate']")
	public WebElement calculateButton;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtName")
	public WebElement customerName;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox2")
	public WebElement street;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox3")
	public WebElement city;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox4")
	public WebElement state;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox5")
	public WebElement zip;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_cardList_0")
	public WebElement visa ;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_cardList_1")
	public WebElement masterCard;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_cardList_2")
	public WebElement amEx;
	
	public void selectCreditCardTypeByIndex(int index) {
		switch(index) {
		case 1:
			visa.click();
			break;
			
		case 2:
			masterCard.click();
			break;
			
		case 3:
			amEx.click();
			break;
		}
	}
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox6")
	public WebElement creditCardNum;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox1")
	public WebElement expDate;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_InsertButton")
	public WebElement processButton;
	
	@FindBy(xpath="//div[@class='buttons_process']//strong")
	public WebElement messageAfterProcess;
	
	
}
