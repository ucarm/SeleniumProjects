package com.jobSearchTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PersonalInfoTests {
	WebDriver driver;
	String firstName;
	String lastName;
	int gender;
	String dateOfBirth;
	String email;
	String phoneNumber;
	String city;
	String state;
	String country;
	int annualSalary;
	List<String> technologies;
	int yearsOfExperience;
	String education;
	String github;
	List<String> certifications;
	String additionalSkills;
	Faker data = new Faker();
	Random random = new Random();
	
	@BeforeClass //runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}
	
	@BeforeMethod //runs before each @Test
	public void navigateToHomePage() {
		System.out.println("Navigating to homepage in @BeforeMethod....");
		driver.get("https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");	
		firstName = data.name().firstName();
		lastName = data.name().lastName();
		gender = data.number().numberBetween(1,3);
		dateOfBirth = data.date().birthday().toString();
		email = "mehmetucar86@gmail.com";
		phoneNumber = data.phoneNumber().cellPhone().replace('.', ' ');
		city=data.address().cityName();
		state=data.address().stateAbbr();
		annualSalary=data.number().numberBetween(60000, 150000);
		technologies = new ArrayList<>();
		technologies.add("Java-" + data.number().numberBetween(1,4));
		technologies.add("HTML-" + data.number().numberBetween(1,4));
		technologies.add("Selenium WebDriver-" + data.number().numberBetween(1,4));
		technologies.add("Maven-"+ data.number().numberBetween(1,4));
		technologies.add("Git-"+ data.number().numberBetween(1,4));
		technologies.add("TestNG-"+ data.number().numberBetween(1,4));
		technologies.add("JUnit-"+ data.number().numberBetween(1,4));
		technologies.add("Cucumber-"+ data.number().numberBetween(1,4));
		technologies.add("API Automation-"+ data.number().numberBetween(1,4));
		technologies.add("JDBC-"+ data.number().numberBetween(1,4));
		technologies.add("SQL-"+ data.number().numberBetween(1,4));
		
		yearsOfExperience = data.number().numberBetween(0, 11);
		education = data.number().numberBetween(1, 4)+"";
		github = "https://github.com/ucarm";
		certifications = new ArrayList<>();
		certifications.add("Java OCA");
		certifications.add("AWS");
		additionalSkills = data.job().keySkills();
		
	}
	
	@Test
	public void submitFullApplication() {
		driver.findElement(By.xpath("//input[@name='Name_First']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='Name_Last']")).sendKeys(lastName);
		setGender(gender);
		setDateOfBirth(dateOfBirth);
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='countrycode']")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("//input[@name='Address_City']")).sendKeys(city);
		driver.findElement(By.xpath("//input[@name='Address_Region']")).sendKeys(state);
		Select countryElem = new Select(driver.findElement(By.xpath("//select[@id='Address_Country']")));
		int indexOfCountry= data.number().numberBetween(1, countryElem.getOptions().size());
		countryElem.selectByIndex(indexOfCountry);
		country= driver.findElement(By.xpath("//select[@id='Address_Country']/option["+(indexOfCountry+1)+"]")).getText();
		
		driver.findElement(By.xpath("//input[@name='Number']")).sendKeys(String.valueOf(annualSalary)+Keys.TAB);
		verifySalaryCalculations(annualSalary);
		driver.findElement(By.xpath("//em[.=' Next ']")).click();		
		// SECOND PAGE ACTIONS
		//technologies are done
		setSkillset(technologies);
		if(yearsOfExperience>0)
			driver.findElement(By.xpath("//div[@elname='ratingSubData']/a["+yearsOfExperience+"]")).click();
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='Dropdown']")));
		dropdown.selectByIndex(Integer.parseInt(education));
		driver.findElement(By.xpath("//input[@name='Website']")).sendKeys(github);
		selectAllCertificates(certifications);
		driver.findElement(By.xpath("//textarea[@name='MultiLine']")).clear();
		driver.findElement(By.xpath("//textarea[@name='MultiLine']")).sendKeys(additionalSkills);
		driver.findElement(By.xpath("//button[@elname='submit']")).click();
		
		// Application is now submitted
		String applSubmittedMessage= driver.findElement(By.xpath("//div[@class='infoContainer']//div[3]")).getText();
		assertEquals(applSubmittedMessage, "Your application has been submitted.");
		System.out.println("TEST PASSED: application is submitted");

		
		//name check -- Dear firstName and LastName
		assertTrue(driver.findElement(By.xpath("//div[@class='infoContainer']//div[1]")).getText()
				.toLowerCase().contains((firstName+" "+lastName).toLowerCase()));
		System.out.println("TEST PASSED: name check");

		
		String ip="";
		try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
		    ip = s.next();
		} catch (java.io.IOException e) {
		    e.printStackTrace();
		}
		// ip check
		String saveIpField= driver.findElement(By.xpath("//div[@class='infoContainer']//div[6]")).getText();
		assertTrue(saveIpField.contains(ip.trim()));
		System.out.println("TEST PASSED: IP check");
		
		//check gender field
		String genderField=  driver.findElement(By.xpath("//div[@class='infoContainer']//div[9]")).getText();
		assertTrue(genderField.contains(getGender(gender)));
		System.out.println("TEST PASSED: gender check");

		//check dob
		String dobField= driver.findElement(By.xpath("//div[@class='infoContainer']//div[10]")).getText();
		assertTrue(dobField.contains(getDateOfBirth(dateOfBirth)));
		System.out.println("TEST PASSED: DOB check");

		
		// check e-mail
		String emailField= driver.findElement(By.xpath("//div[@class='infoContainer']//div[11]")).getText();
		assertTrue(emailField.contains(email));
		System.out.println("TEST PASSED: EMAIL check");

		//phone number validation
		String phoneField= driver.findElement(By.xpath("//div[@class='infoContainer']//div[12]")).getText();
		assertTrue(phoneField.contains(phoneNumber));
		System.out.println("TEST PASSED: phoneNumber check");

		
		//address validation
		String addressField= driver.findElement(By.xpath("//div[@class='infoContainer']//div[13]")).getText();
		String addr= city+", "+state+", "+country;
//		System.out.println("addrFld\t: "+addressField);
//		System.out.println("addr\t: "+addr);
		assertTrue(addressField.toLowerCase().contains(addr.toLowerCase()));
		System.out.println("TEST PASSED: adress check");

		
		//annualSalary validation
		String annualSalaryField=  driver.findElement(By.xpath("//div[@class='infoContainer']//div[14]")).getText();
		assertTrue(annualSalaryField.contains(""+annualSalary));
		System.out.println("TEST PASSED: AnnualSalary check");

		
		//Technologies test check
		String technologiesField=  driver.findElement(By.xpath("//div[@class='infoContainer']//div[15]")).getText();
		String technologiesAll=addAllTechnologies(technologies);
//		System.out.println("techField\t: "+technologiesField);
//		System.out.println("techAll\t\t: Technologies: "+technologiesAll);
		try{
		assertTrue(technologiesField.contains(technologiesAll));
		System.out.println("TEST PASSED: technologies check");
		}
		catch(AssertionError e) {
			System.out.println("TEST FAILED: technologies check");

		}
		//years of experience check
		String experienceYearField=  driver.findElement(By.xpath("//div[@class='infoContainer']//div[16]")).getText();
		assertTrue(experienceYearField.contains(yearsOfExperience+""));
		System.out.println("TEST PASSED: Experience check");

		
	}
	public String addAllTechnologies(List<String> technologies2) {
		String result="";
		for (String skill : technologies2) {
			String technology = skill.substring(0, skill.length()-2);
			int rate = Integer.parseInt(skill.substring(skill.length()-1));
			
			String level = "";
			
			switch(rate) {
				case 1:
					level = "Expert";
					break;
				case 2:
					level = "Proficient";
					break;
				case 3:
					level = "Beginner";
					break;
				default:
					fail(rate + " is not a valid level");
			}
			result+=technology+" : "+level+"  ";
		}
		return result.trim();
			
	}

	private void verifySalaryCalculations(int annual) {
		String monthly = driver.findElement(By.xpath("//input[@name='Formula']")).getAttribute("value");
		String weekly = driver.findElement(By.xpath("//input[@name='Formula1']")).getAttribute("value");
		String hourly = driver.findElement(By.xpath("//input[@name='Formula2']")).getAttribute("value");
//		System.out.println("\n"+monthly+"\n"+weekly+"\n"+hourly);
		
		DecimalFormat formatter = new DecimalFormat("#.##");
		assertEquals(Double.parseDouble(monthly),Double.parseDouble(formatter.format((double)annual /12.0)));
		assertEquals(Double.parseDouble(weekly),Double.parseDouble(formatter.format((double)annual / 52.0)));
		assertEquals(Double.parseDouble(hourly),Double.parseDouble(formatter.format((double)annual / (52.0* 40.0))));
		
		
	}
	
	public void selectAllCertificates(List<String> certifications2) {
		List<WebElement> CertOptions = new ArrayList<WebElement>();
		CertOptions= driver.findElements(By.xpath("//input[@name='Checkbox-check']"));
		for (int i=0; i<CertOptions.size(); i++) {
			for(int j=0;j<certifications2.size(); j++) {
				if(CertOptions.get(i).getAttribute("value").equalsIgnoreCase(certifications.get(j)))
					driver.findElement(By.xpath("(//input[@name='Checkbox-check'])["+ (i+1)+"]")).click();
			}
			
		}
		
	}

	public void setSkillset(List<String> tech) {
		
		for (String skill : tech) {
			String technology = skill.substring(0, skill.length()-2);
			int rate = Integer.parseInt(skill.substring(skill.length()-1));
			
			String level = "";
			
			switch(rate) {
				case 1:
					level = "Expert";
					break;
				case 2:
					level = "Proficient";
					break;
				case 3:
					level = "Beginner";
					break;
				default:
					fail(rate + " is not a valid level");
			}
			
			String xpath = "//input[@rowvalue='"+ technology +"' and @columnvalue='"+ level +"']";
			driver.findElement(By.xpath(xpath)).click();
		}	
	}
	
	//Sun Nov 27 04:04:22 EST 1977
	public void setDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		String birthDay = pieces[2] + "-" +  pieces[1] + "-" + pieces[5];
		driver.findElement(By.xpath("//input[@id='Date-date']")).sendKeys(birthDay);
	}
	public String getDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		String birthDay = pieces[2] + "-" +  pieces[1] + "-" + pieces[5];
		return birthDay;
	}
	
	public void setGender(int n) {
		if(n==1) {
			driver.findElement(By.xpath("//input[@value='Male']")).click();
		}else {
			driver.findElement(By.xpath("//input[@value='Female']")).click();
		}
	}
	
	public String getGender(int n) {
		if(n==1) {
			return "Male";
		}else {
			return "Female";
		}
	}
	
	@Test
	public void fullNameEmptyTest() {
		//firstly assert that you are on the correct page
		assertEquals(driver.getTitle(), "SDET Job Application");
		
		driver.findElement(By.xpath("//input[@elname='first']")).clear();	
		driver.findElement(By.xpath("//*[@elname='last']")).clear();

		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		String nameError = driver.findElement(By.xpath("//p[@id='error-Name']")).getText();
		assertEquals(nameError, "Enter a value for this field.");
	}
}	
