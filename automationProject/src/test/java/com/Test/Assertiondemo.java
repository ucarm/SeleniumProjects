package com.Test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Assertiondemo {

	@Test
	public void testOne() {
		System.out.println("Asserting first");
		Assert.assertTrue(true);
		System.out.println("Done asserting first");
	}
	@Test
	public void testTwo() {
		System.out.println("Asserting second");
		Assert.assertTrue(false);
		System.out.println("Done asserting Second");
	}
	
	@AfterMethod
	public void cleanUp() {
		System.out.println("cleaning up");
	}
}
