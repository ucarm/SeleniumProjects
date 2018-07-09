package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebOrdersLoginPage {
	
	// we need a constructer to initilize/locate all the items
	public WebOrdersLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	
	// find
	@FindBy(id="ctl00_MainContent_username")
	public WebElement userName;
	

	@FindBy(id="ctl00_MainContent_password")
	public WebElement password;
	

	@FindBy(id="ctl00_MainContent_login_button")
	public WebElement loginButton;
	
	@FindBy(id="ctl00_MainContent_status")
	public WebElement invalidUserNameErrMsg;
	
	public void login(String uid, String pwd) {
		userName.sendKeys(uid);
		password.sendKeys(pwd);
		loginButton.click();
	}
	
}
