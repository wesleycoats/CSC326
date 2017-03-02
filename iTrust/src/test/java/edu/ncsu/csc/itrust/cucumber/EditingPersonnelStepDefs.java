package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class EditingPersonnelStepDefs {
	
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as Admin$")
	public void login_for_personnel_edits() throws Throwable{
		
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000001");
		pass.sendKeys("pw");
		pass.submit();

//		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
	}
	
	@When("^Navigate to the Edit My Demographics page$")
	public void navigate_to_personnel_edits() throws Throwable {
		driver.findElement(By.linkText("Edit My Demographics")).click();
		assertEquals("iTrust - Edit Personnel", driver.getTitle());
	}
	
	@When("^I edit my first name$")
	public void input_personnel_edits() throws Throwable {
		driver.findElement(By.name("firstName")).clear();
		driver.findElement(By.name("firstName")).sendKeys("Shap");
	}
	
	@When("^Submit personnel page$")
	public void submit_personnel_edits() throws Throwable {
		driver.findElement(By.name("action")).click();
	}
	
	@Then("^Admin should have the new changed name$")
	public void check_personnel_edits() throws Throwable {
		assertFalse(driver.getPageSource().contains("This form has not been validated correctly."));
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[1]/a")).click();
		assertTrue(driver.getPageSource().contains("Welcome, Shap Shifter"));

		
	}
}
	