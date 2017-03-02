package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class FitBitEditDataStepDefs  {
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as an HCP, HCP 1$")
	public void login_for_fitbit_data() throws Throwable{
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000000");
		pass.sendKeys("pw");
		pass.submit();
		assertEquals("iTrust - HCP Home", driver.getTitle());
	}
	
	@And("^Navigate to the FitBit Data page$")
	public void navigate_to_fitbit_data() throws Throwable {
		driver.findElement(By.linkText("FitBit Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
	}
	
	@When("^I select a valid patient$")
	public void select_patient() throws Throwable {
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - FitBit Calendar", driver.getTitle());
	}
	
	@And("^Select a Date on the calendar$")
	public void input_date() throws Throwable {
		assertEquals("iTrust - FitBit Calendar", driver.getTitle());
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/13/2029");
		driver.findElement(By.name("confirm")).click();
	}
	
	@And("^Enter new data into the fields$")
	public void enter_new_data() throws Throwable {
		driver.findElement(By.name("cal_burned")).clear();
		driver.findElement(By.name("cal_burned")).sendKeys("256");
		driver.findElement(By.name("activity_cal")).clear();
		driver.findElement(By.name("activity_cal")).sendKeys("110");
		driver.findElement(By.name("steps")).clear();
		driver.findElement(By.name("steps")).sendKeys("2222");
		driver.findElement(By.name("dist")).clear();
		driver.findElement(By.name("dist")).sendKeys("2223");
		driver.findElement(By.name("floors")).clear();
		driver.findElement(By.name("floors")).sendKeys("7");
		driver.findElement(By.name("min_seden")).clear();
		driver.findElement(By.name("min_seden")).sendKeys("100");
		driver.findElement(By.name("min_light_active")).clear();
		driver.findElement(By.name("min_light_active")).sendKeys("100");
		driver.findElement(By.name("min_fair_active")).clear();
		driver.findElement(By.name("min_fair_active")).sendKeys("100");
		driver.findElement(By.name("min_very_active")).clear();
		driver.findElement(By.name("min_very_active")).sendKeys("100");
	}
	
	@When("^Submit the new data page$")
	public void submit_the_page() throws Throwable {
		driver.findElement(By.name("submitButton")).click();
	}
	
	@Then("^The data should be submitted correctly$")
	public void check_message() throws Throwable {				
		assertTrue(driver.getPageSource().contains("Information Successfully"));

	}
	
	@And("^Be consistent when accessed by other HCP's$")
	public void check_consistency() throws Throwable {
		//Log out and log back on as HCP 3
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000003");
		pass.sendKeys("pw");
		pass.submit();

		//Navigate to FitBit Data Page
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.linkText("FitBit Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		 
		//Find Patient
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - FitBit Calendar", driver.getTitle());
		
		//Select Data
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/13/2029");
		driver.findElement(By.name("confirm")).click();
		
		//Confirm information
		assertEquals("256", driver.findElement(By.name("cal_burned")).getAttribute("value"));
		assertEquals("110", driver.findElement(By.name("activity_cal")).getAttribute("value"));
		assertEquals("2222", driver.findElement(By.name("steps")).getAttribute("value"));
		assertEquals("2223.0", driver.findElement(By.name("dist")).getAttribute("value"));
		assertEquals("7", driver.findElement(By.name("floors")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("min_seden")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("min_light_active")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("min_fair_active")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("min_very_active")).getAttribute("value"));

	}

}