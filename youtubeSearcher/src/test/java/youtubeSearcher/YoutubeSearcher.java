package youtubeSearcher;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class YoutubeSearcher {
	WebDriver driver;
	Faker data = new Faker();
	List<YoutubeSEO> result = new ArrayList<YoutubeSEO>();

	@BeforeClass
	public void setup() throws FileNotFoundException, InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(1000);
		driver.manage().window().maximize();
		LocalDateTime now = LocalDateTime.now();
		String fileName = "SEO_Youtube_Report_" + now + ".txt";
		PrintStream out = new PrintStream(new FileOutputStream(fileName));
		System.setOut(out);
	}

	@Test
	public void test1() throws InterruptedException {
		searchRandomNames(result, 10);
		System.out.println("searchCount\tName");
		System.out.println("---------------------");

		for (YoutubeSEO each : result) {
			System.out.println(each.getSearchCount() + "\t\t" + each.getName());
		}
	}

	public void searchRandomNames(List<YoutubeSEO> result2, int n) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			driver.get("https://www.youtube.com/");
			String name = data.name().fullName();
			driver.findElement(By.xpath("//input[@id='search']")).sendKeys(name + Keys.ENTER);
			String fullSearchOutput = driver.findElement(By.xpath("//yt-formatted-string[@id='result-count']"))
					.getText();
			
			Thread.sleep(500);
			int searchCount = convertResulttoInt(fullSearchOutput);
			System.out.println("SearchCount: "+searchCount+"\t\t"+"name:"+name);
			result.add(new YoutubeSEO(name, searchCount));
		}
	}

	public int convertResulttoInt(String fullSearchOutput) {
		String result = "";
		for (int i = 0; i < fullSearchOutput.length(); i++) {
			if (Character.isDigit(fullSearchOutput.charAt(i)))
				result += fullSearchOutput.charAt(i);
		}
		return Integer.valueOf(result);
	}

	@AfterClass
	public void tearDown() {
		driver.close();
		System.gc();
	}

}
