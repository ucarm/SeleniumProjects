package youtubeSeo;
import static org.testng.Assert.assertEquals;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class YoutubeSearcher {

	WebDriver driver;
	Faker data= new Faker();
	List<YoutubeSEO> results = new ArrayList<>();
	final PrintStream returnBacktoRegularOutput = System.out;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		LocalDateTime now = LocalDateTime.now();
		// local date time for naming the output file
		try {
			String fileName= "Report_Output_" + now.toString() + "_.txt";
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			System.setOut(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void youtubeSearch() throws InterruptedException {
		findYoutubeSearchResults(results, 5);
		System.out.println("---------");
		System.out.println("NAME --> SEARCH COUNT");
		for (YoutubeSEO each : results) {
			System.out.println(each.getName()+" --> "+each.getNumberOfSearchResults());
		}
		System.out.println("---------");
		assertEquals(results.size(), 5);
	}
	
	private void findYoutubeSearchResults(List<YoutubeSEO> results2, int n) throws InterruptedException {
		for (int j = 0; j < n; j++) {
			driver.get("https://www.youtube.com/");
			String name= data.name().fullName();
			driver.findElement(By.xpath("//input[@id='search']")).sendKeys(name+Keys.ENTER);
			WebElement searchResult= driver.findElement(By.xpath("//yt-formatted-string[@id='result-count']"));
			Thread.sleep(1000);
			int numberOfSearchresults= getnumberOfSearchresults(searchResult.getText());
			results.add(new YoutubeSEO(name,numberOfSearchresults));
		}
	}

	public int getnumberOfSearchresults(String text) {
		String number="";
		for (int i=0; i<text.length(); i++) {
			if ( Character.isDigit(text.charAt(i)) ) {
				number+= text.charAt(i);
			}
		}
		try {
		return Integer.valueOf(number);
		}
		catch(NumberFormatException e) {
			return 0;
		}
	}

	@AfterClass
	public void tearDown() {
//		driver.close();
		System.setOut(returnBacktoRegularOutput);
		System.gc();
	}
	
}
