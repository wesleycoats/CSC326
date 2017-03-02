package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;
import static org.junit.Assert.*;

public class ViewFullCalendarStepDefs  {
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as Patient 2$")
	public void login_calendar_Check() throws Throwable{
		
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
	
	@When("^Navigate to the calendar page$")
	public void navigate_calendar_check() throws Throwable {
		driver.findElement(By.linkText("Full Calendar")).click();
		
	}
	
	@Then("^It loads successfully")
	public void successful_calendar_Check() throws Throwable {
		assertEquals("iTrust - Appointment Calendar", driver.getTitle());

	}
	

}