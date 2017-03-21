package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class AppointmentRequestStepDefs  {
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as Patient 2 here$")
	public void login_for_appointment_requests() throws Throwable{
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("2");
		pass.sendKeys("pw");
		pass.submit();


//		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
	}
	
	@And("^Navigate to the appointment request page$")
	public void navigate_to_appointment_request() throws Throwable {
		driver.findElement(By.linkText("Appointment Requests")).click();
		assertEquals("iTrust - Appointment Requests", driver.getTitle());
		
	}
	
	@When("^I input test values$")
	public void input_test_dates() throws Throwable {
//		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/select[2]/option[2]"));
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("01/23/2019");
		driver.findElement(By.name("comment")).clear();
		driver.findElement(By.name("comment")).sendKeys("test");
	}
	
	@When("^Submit the page$")
	public void submit_test_dates() throws Throwable {
		driver.findElement(By.name("request")).click();
	}
	
	@Then("^HCP 3 should have an appointment request that matches$")
	public void check_requests() throws Throwable {
		
//		System.out.println(driver.getCurrentUrl());
				
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000003");
		pass.sendKeys("pw");
		pass.submit();

		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.linkText("Appointment Requests")).click();
		assertEquals("iTrust - View My Appointment Requests", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Request from: Andy Programmer"));
		assertTrue(driver.getPageSource().contains("Appointment type: General Checkup"));
		assertTrue(driver.getPageSource().contains("At time: 01/23/2019 01:00 AM"));
		assertTrue(driver.getPageSource().contains("Comment: test"));

	}

}