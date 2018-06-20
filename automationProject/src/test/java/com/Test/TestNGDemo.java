package com.Test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGDemo {
	
	@BeforeClass
	public void setUpClass() {
		System.out.println("Runs before every Class");
	}
	
//	BeforeMethod works before every test case
	@BeforeMethod
	public void setUpMethod() {
		System.out.println("Runs before every method");
	}
	
	@Test
	public void testOne() {
		System.out.println("First One");
	}
	
	@Test
	public void secondOne() {
		System.out.println("Second One");
	}

	@AfterMethod
	public void tearDownMethod() {
		System.out.println("Runs after every class");
	}
	
	@AfterClass
	public void tearDownClass() {
		System.out.println("Runs after every method");
	}


}
