package com.ucar;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DropdownTest {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://tutorialehtml.com/en/html-tutorial-drop-down-lists-menu/");
		
		// to handle the dropdown list selnium uses Select class. We have to create a select object to be able to 

		//1. We need to find slect tag

		WebElement selectTag = driver.findElement(By.name("my_html_select_box"));

		// 2. create a select object from the select tag

		Select list = new Select(selectTag);

		//3.Returns the selcted element

		list.getFirstSelectedOption();

		//4.Print the selcted value: option 1

		

		WebElement selected = list.getFirstSelectedOption();
		System.out.println(selected.getText());

		//option 2
		System.out.println(list.getFirstSelectedOption().getText());
		//print all available options
		//returns list of all options

		List<WebElement>options = list.getOptions();

		System.out.println("--------------");
		for (WebElement webElement : options) {

			System.out.println(webElement.getText());

		}

		System.out.println("----------------");
		//select using the visible text
		list.selectByVisibleText("Madrid");
		System.out.println("selected:\t"+list.getFirstSelectedOption().getText());

		

		//select using the index

		list.selectByIndex(2);
		System.out.println("selected:\t"+ list.getFirstSelectedOption().getText());

		//select using value
		//list.selectByValue("ffsdf");
		//list.deselectAll();

		System.out.println("*********************************");

		Select secondList = new Select(driver.findElement(By.cssSelector("select[multiple='yes']")));

		secondList.selectByVisibleText("New York");
		secondList.selectByVisibleText("Madrid");

		List<WebElement>allSelectedOptions = secondList.getAllSelectedOptions();
		for (WebElement webElement : allSelectedOptions) {
			System.out.println(webElement.getText());

		}

		System.out.println(allSelectedOptions.size());
		secondList.deselectAll();
		allSelectedOptions = secondList.getAllSelectedOptions();
		System.out.println(allSelectedOptions.size());
		
	}
}
