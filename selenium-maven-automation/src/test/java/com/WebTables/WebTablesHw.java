package com.WebTables;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTablesHw {
	// 1) goto
	// https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8
	// 2) Create a HashMap
	// 3) change row number to 100, read all data on first page and put uniquID as a
	// KEY
	// and Applicant info as a Value to a map.
	//
	// applicants.put(29,"Amer, Sal-all@dsfdsf.com-554-434-4324-130000")
	//
	// 4) Click on next page , repeat step 3
	// 5) Repeat step 4 for all pages
	// 6) print count of items in a map. and assert it is matching
	// with a number at the buttom

	WebDriver driver;
	String url = "https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8";
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void readScores() throws InterruptedException {
		// 1) goto
		// https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8
		// 2) Create a HashMap
		driver.get(url);
		Map<Integer, String> allData = new HashMap<>();
		// 3) change row number to 100, read all data on first page and put uniquID as a
		Select dropdown = new Select(driver.findElement(By.id("recPerPage")));
		dropdown.selectByVisibleText("100");
		Thread.sleep(1000);
		int totalNumberofApplication = Integer.parseInt(driver.findElement(By.id("total")).getText());
		int howManyPages = totalNumberofApplication / 100 + 1;
		System.out
				.println("there are " + howManyPages + " pages of records. Total records :" + totalNumberofApplication);
		Map<Integer, String> allResults = new HashMap<>();
		// loop thru all pages
		for (int i = 1; i < howManyPages; i++) {
			Thread.sleep(1000);
			saveTableData("reportTab", allResults);
			// click the next page
			driver.findElement(By.className("nxtArrow")).click();
			System.out.println("Page #" + i + " is processed.");
		}
		printAllResults(allResults);

		int NumberOfEntriesInTheMap = allResults.size();
		System.out.println("+++++++  RESULT +++++++++");
		assertNotEquals(NumberOfEntriesInTheMap, totalNumberofApplication);
		System.out.println("Size of the map  \t: " + NumberOfEntriesInTheMap);
		System.out.println("Number of entries\t: " + totalNumberofApplication);
		System.out.println("+++++++  +++++ +++++++++");
	}

	public void printAllResults(Map allResults) {
		Iterator it = allResults.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove();
		}
	}

	public void printTableData(String id) {
		String rowXpath = "//table[@id='" + id + "']/tbody/tr";
		String colXpath = "//table[@id='" + id + "']/tbody/tr[1]/td";
		int rowsCount = driver.findElements(By.xpath(rowXpath)).size();
		int colsCount = driver.findElements(By.xpath(colXpath)).size();
		System.out.println(rowsCount + "x" + colsCount);

		System.out.println("===================");
		for (int row = 1; row <= rowsCount; row++) {
			for (int col = 1; col <= colsCount; col++)
				System.out
						.print(driver.findElement(By.xpath("(" + rowXpath + ")[" + row + "]/td[" + col + "]")).getText()
								+ "    \t");
			System.out.println();
		}
		System.out.println("===================");
	}

	public void saveTableData(String id, Map AllData) {
		String rowXpath = "//table[@id='" + id + "']/tbody/tr";
		String colXpath = "//table[@id='" + id + "']/tbody/tr[1]/td";
		int rowsCount = driver.findElements(By.xpath(rowXpath)).size();
		int colsCount = driver.findElements(By.xpath(colXpath)).size();
		for (int row = 1; row <= rowsCount; row++) {
			String applicantData = "";
			Integer applicantId = 0;
			for (int col = 1; col <= colsCount; col++) {
				String cellValue = driver.findElement(By.xpath("(" + rowXpath + ")[" + row + "]/td[" + col + "]"))
						.getText();
				if (col == 1) {
					applicantId = Integer.valueOf(cellValue);
				} else {
					applicantData += cellValue + "-";
				}
			}
			// substring to remove the last characher ( - at the end)
			AllData.put(applicantId, applicantData.substring(0, applicantData.length() - 1));
			// System.out.println(applicantId+"\t"+applicantData);

		}
	}

	@AfterClass
	public void afterClass() {
		// driver.close();
	}

}
