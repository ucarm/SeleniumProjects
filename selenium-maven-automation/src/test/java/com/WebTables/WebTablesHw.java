package com.WebTables;

import static org.testng.Assert.assertNotEquals;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
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
	WebDriver driver;
	String url = "https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8";
	SoftAssert softAssert = new SoftAssert();
	// following final variable is needed to return back to System.out after saving the external Report.TXT file
	final PrintStream returnBacktoRegularOutput = System.out;
	String fileName;

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// The test output can't be handled by the Jva console. Because it has more than
		// 2k lines. Outputs a txt file
		// output_.txt file has all the lines command has
		LocalDateTime now = LocalDateTime.now();
		// local date time for naming the output file
		try {
			fileName= "Report_Output_" + now.toString() + "_.txt";
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			System.setOut(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("=========================\nCOMPANY Name\t\t: ****** INC\nTESTING project link\t: " + url
				+ "\nTESTER\t\t: UcarM");
		System.out.println("TESTING Started at \t: " + now + "\n=========================\n");
	}

	@Test
	public void readScores() throws InterruptedException {
		// 1) goto
		// https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8
		// 2) Create a HashMap
		driver.get(url);
		// 3) change row number to 100, read all data on first page and put uniquID as a
		Select dropdown = new Select(driver.findElement(By.id("recPerPage")));
		dropdown.selectByVisibleText("100");
		Thread.sleep(1000);
		int totalNumberofApplication = Integer.parseInt(driver.findElement(By.id("total")).getText());
		int howManyPages = totalNumberofApplication / 100 + 1;
		System.out.println(
				"There are " + howManyPages + " pages of records.\nTotal records :" + totalNumberofApplication);
		Map<Integer, String> allResults = new HashMap<>();
		// loop thru all pages
		for (int i = 1; i < howManyPages - 20; i++) {
			Thread.sleep(500);
			saveTableData("reportTab", allResults);
			// click the next page
			driver.findElement(By.className("nxtArrow")).click();
			System.out.println("Page #" + i + " is processed.");
		}
		printAllResults(allResults);
		Thread.sleep(1000);
		int NumberOfEntriesInTheMap = allResults.size();
		Thread.sleep(1000);
		assertNotEquals(NumberOfEntriesInTheMap, totalNumberofApplication);
		System.out.println("\nSize of the map  \t: " + NumberOfEntriesInTheMap);
		System.out.println("\nNumber of entries\t: " + totalNumberofApplication + "\n");
	}

	public void printAllResults(Map<Integer, String> allResults) {
		Iterator it = allResults.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}

	public void saveTableData(String id, Map<Integer, String> allResults) {
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
			// substring to remove the last character ( - at the end)
			allResults.put(applicantId, applicantData.substring(0, applicantData.length() - 1));

		}
	}

	@AfterClass
	public void afterClass() {
		driver.close();
		String now = LocalDateTime.now().toString();
		System.out.println("=========================\nTESTING COMPLETED at :" + now);
		System.setOut(returnBacktoRegularOutput);
		System.out.println("Report file is generated....  "+fileName);

	}

}
