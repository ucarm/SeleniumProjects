package utilities;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class MyUtils {
	
	public static void takeScreenShot(WebDriver driver, String message) {
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String timemark= LocalDateTime.now().toString();
		File destFile= new File("./ScreenShots/"+message+"_"+timemark+".png");
		try {
			FileUtils.copyFile(source, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("Something went wrong while taking screenshot.");
			e.getMessage();
		}
		
	}
}
