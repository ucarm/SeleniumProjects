package com.ucar;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooTests {
	WebDriver driver;
	List<String> cities = new ArrayList<String>();
	List<String> countries = new ArrayList<String>();

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://mockaroo.com/");
		
	}

	@BeforeMethod
	public void beforeMethod() {
	}

	@Test(priority = 1)
	public void test1() {
		// 3. Assert title is correct.
		String siteTitle = driver.getTitle();
		String actual = "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel";
		// System.out.println(siteTitle+"\n"+actual);
		assertEquals(actual, siteTitle);
	}

	@Test(priority = 2)
	public void test2() {
		// 4. Assert mockaroo and realistic data generator are displayed
		String top1 = driver.findElement(By.xpath("//div[@class='brand'][contains(text(),'mock')]")).getText();
		String top2 = driver.findElement(By.xpath("//div[@class='tagline'][contains(text(),'realistic')]")).getText();
		assertEquals(top1 + " and " + top2, "mockaroo and realistic data generator");
	}

	@Test(priority = 3)
	public void test3() {
		// 5. Remote all existing fields by clicking on x icon link
		List<WebElement> allremoveableFields = driver
				.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		assertTrue(removeAllFields(allremoveableFields));
	}

	@Test(priority = 4)
	public void test4() {
		// 6. Assert that ‘Field Name’ , ‘Type’, ‘Options’ labels are displayed
		boolean fieldName = driver.findElement(By.xpath("//div[.='Field Name']")).isDisplayed();
		boolean type = driver.findElement(By.xpath("//div[.='Type']")).isDisplayed();
		boolean options = driver.findElement(By.xpath("//div[.='Options']")).isDisplayed();
		assertTrue(fieldName && type && options);
	}

	@Test(priority = 5)
	public void test5() {
		// 7. Assert that ‘Add another field’ button is enabled. Find using xpath with
		// tagname and text. isEnabled() method in selenium

		boolean addFielfButton = driver.findElement(By.xpath("//a[.='Add another field']")).isEnabled();
		assertTrue(addFielfButton);
	}

	@Test(priority = 6)
	public void test6() {
		// 8. Assert that default number of rows is 1000.
		String rows = driver.findElement(By.id("num_rows")).getAttribute("value");
		assertEquals(rows, "1000");
	}

	@Test(priority = 7)
	public void test7() {
		// 9. Assert that default format selection is CSV
		boolean csv = driver.findElement(By.xpath("//div[@id='schema-options']//option[@value='csv']")).isSelected();
		assertTrue(csv);
	}

	@Test(priority = 8)
	public void test8() {
		// 10. Assert that Line Ending is Unix(LF)
		boolean lineEnd = driver.findElement(By.xpath("//div[@id='schema-options']//option[@value='unix']"))
				.isSelected();
		assertTrue(lineEnd);
	}

	@Test(priority = 9)
	public void test9() {
		// 11. Assert that header checkbox is checked and BOM is unchecked
		boolean headerCheckBox = driver.findElement(By.id("schema_include_header")).isSelected();
		boolean BOM = driver.findElement(By.id("schema_bom")).isSelected();
		assertTrue(headerCheckBox && !BOM);
	}

	@Test(priority = 10)
	public void test10() throws InterruptedException {
		String area1 = "City";
		addAnArea(area1);

	}

	public void addAnArea(String area1) throws InterruptedException {
		// 12. Click on ‘Add another field’ and enter name “City”
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		List<WebElement> enterNames = driver.findElements(By.xpath("//input[@placeholder='enter name...']"));
		enterNames.get(enterNames.size() - 1).sendKeys(area1);
		// 13. Click on Choose type and assert that Choose a Type dialog box is
		// displayed.
		List<WebElement> chooseTypes = driver.findElements(By.xpath("//input[@placeholder='choose type...']"));

		chooseTypes.get(chooseTypes.size() - 1).click();
		Thread.sleep(1000);
		boolean popUp = driver.findElement(By.xpath("//div[@class='modal-content']//h3[.='Choose a Type']"))
				.isDisplayed();
		// System.out.println(popUp);
		assertTrue(popUp);
		// 14. Search for “city” and click on City on search results.
		driver.findElement(By.id("type_search_field")).clear();
		driver.findElement(By.id("type_search_field")).sendKeys(area1);
		driver.findElement(By.xpath("//div[@class='examples']")).click();

	}

	@Test(priority = 11)
	public void test11() throws InterruptedException, FileNotFoundException {
		// 15. Repeat steps 12-14 with field name and type “Country”
		String area1 = "Country";
		addAnArea(area1);

	}

	@Test(priority = 12)
	public void test12() throws IOException, InterruptedException {
		// 16. Click on Download Data.
		Thread.sleep(1000);
		driver.findElement(By.id("download")).click();
		// 17. Open the downloaded file using BufferedReader.
		Thread.sleep(2000);
		anaylzeFile("/home/ussr/Downloads/MOCK_DATA.csv");
		// 18. Assert that first row is matching with Field names that we selected.
		String firstLine = cities.get(0) + countries.get(0);
		String expected = "CityCountry";
		assertEquals(firstLine, expected);
	}

	@Test(priority = 13)
	public void test13() {
		// 19. Assert that there are 1000 records
		assertEquals(cities.size() - 1, 1000);
	}

	@Test(priority = 14)
	public void test14() {
		// 20. From file add all Cities to Cities ArrayList
		// 21. Add all countries to Countries ArrayList
		// 20 and 21 done in analyzeFile()

		// 22. Sort all cities and find the city with the longest name and shortest name
		Collections.sort(cities);
		System.out.println("Shortest named city :\t" + shortLong(cities)[0]);
		System.out.println("Shortest named city :\t" + shortLong(cities)[1]);

		// 23. In Countries ArrayList, find how many times each Country is mentioned.
		// and print out
		Set<String> sortedCountry = new TreeSet<>(countries);
		for (String country : sortedCountry) {
			System.out.println(country + "-" + Collections.frequency(countries, country));
		}
		// 24. From file add all Cities to citiesSet HashSet
		Set<String> uniqueCities = new HashSet<>(cities);
		// 25. Count how many unique cities are in Cities ArrayList and assert that it
		// is matching with the count of citiesSet HashSet.
		int NumberOfUniqueCities = uniqueCities.size();
		// from
		// https://stackoverflow.com/questions/12719998/how-to-count-unique-values-in-an-arraylist
		int NumberOfUniqueCitiesExpected = (int) cities.stream().distinct().count();
		assertEquals(NumberOfUniqueCities, NumberOfUniqueCitiesExpected);

	}

	@Test(priority = 15)
	public void test15() {
		// 26. Add all Countries to countrySet HashSet
		// 27. Count how many unique cities are in Countries ArrayList and assert that
		// it is matching with the count of countrySet HashSet.

		Set<String> uniqueCountries = new HashSet<>(countries);
		int NumberOfUniqueCountries = uniqueCountries.size();
		// https://stackoverflow.com/questions/12719998/how-to-count-unique-values-in-an-arraylist
		// This stream method is very useful.:)
		int NumberOfUniqueCountriesExpected = (int) countries.stream().distinct().count();
		assertEquals(NumberOfUniqueCountries, NumberOfUniqueCountriesExpected);
	}

	public String[] shortLong(List<String> cities2) {
		// this method finds out the longest and shortest words in a String List
		// it returns a two dimensional String Array
		// index[0] stores the shortest word
		// index[1] stores the longest word
		String min = cities.get(0);
		String max = cities.get(0);
		for (String city : cities2) {
			if (city.length() < min.length())
				min = city;
			else if (city.length() > max.length())
				max = city;
		}
		String result[] = { min, max };
		return result;

	}

	public boolean removeAllFields(List<WebElement> allremoveableFields) {
		// this removes the default items from mockaroo website.
		try {
			for (WebElement webElement : allremoveableFields) {
				webElement.click();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void anaylzeFile(String fileLocation) {
		// This method loads the CSV file and changes the name
		// of the file by adding timestamp to it
		// this way no need to delete the old MOCK_DATA.csv files from the downloads
		// folder

		String stamp = LocalDateTime.now().toString();
		File file = new File(fileLocation);
		File file_updated = new File("/home/ussr/Downloads/MOCK_DATA" + stamp + ".csv");
		file.renameTo(file_updated);
		// used opencsv dependencies are added.
		try (FileReader fr = new FileReader(file_updated);
				BufferedReader br = new BufferedReader(fr);
				CSVReader reader = new CSVReader(br)) {

			String[] temp;

			while ((temp = reader.readNext()) != null) {
				// System.out.println(temp[0]+"\t"+temp.length+"\t"+temp[temp.length-1]);
				cities.add(temp[0]);
				countries.add(temp[temp.length - 1]);
			}

		} catch (IOException e) {
			System.out.println("File not found");
			System.out.println(e.getStackTrace());
		}

	}

	@AfterMethod
	public void afterMethod() {

	}

	@AfterClass
	public void afterClass() throws IOException {
		driver.close();
		System.gc();

	}

}
