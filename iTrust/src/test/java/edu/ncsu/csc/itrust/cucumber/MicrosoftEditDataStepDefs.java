package edu.ncsu.csc.itrust.cucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class MicrosoftEditDataStepDefs  {
	private HtmlUnitDriver driver = null;
	
	@Given("^I have logged in as an HCP, HCP 1$")
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
	
	@And("^Navigate to the Microsoft Data page")
	public void navigate_to_fitbit_data() throws Throwable {
		driver.findElement(By.linkText("Microsoft Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
	}
	
	@When("^I will select a valid patient$")
	public void select_patient() throws Throwable {
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - Microsoft Data", driver.getTitle());
	}
	
	@And("^Select a valid Date from the calendar$")
	public void input_date() throws Throwable {
		assertEquals("iTrust - Microsoft Data", driver.getTitle());
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/13/2029");
		driver.findElement(By.name("confirm")).click();
	}
	
	@And("^Enter data into the Microsoft Band fields$")
	public void enter_new_data() throws Throwable {
		driver.findElement(By.name("calories")).clear();
		driver.findElement(By.name("calories")).sendKeys("256");
		driver.findElement(By.name("activity_hours")).clear();
		driver.findElement(By.name("activity_hours")).sendKeys("110");
		driver.findElement(By.name("steps")).clear();
		driver.findElement(By.name("steps")).sendKeys("2222");
		driver.findElement(By.name("dist")).clear();
		driver.findElement(By.name("dist")).sendKeys("2223");
		driver.findElement(By.name("floors")).clear();
		driver.findElement(By.name("floors")).sendKeys("7");
		driver.findElement(By.name("hr_lowest")).clear();
		driver.findElement(By.name("hr_lowest")).sendKeys("100");
		driver.findElement(By.name("hr_average")).clear();
		driver.findElement(By.name("hr_average")).sendKeys("100");
		driver.findElement(By.name("hr_highest")).clear();
		driver.findElement(By.name("hr_highest")).sendKeys("100");
		driver.findElement(By.name("min_uv_exposure")).clear();
		driver.findElement(By.name("min_uv_exposure")).sendKeys("100");
	}
	
	@When("^Submit the new data to the microsoft table$")
	public void submit_the_page() throws Throwable {
		driver.findElement(By.name("submitButton")).click();
	}
	
	@Then("^A message will tell that the data is submitted correctly$")
	public void check_message() throws Throwable {				
		assertTrue(driver.getPageSource().contains("Information Successfully"));

	}
	
	@And("^Other HPC's will see the same data for the patient$")
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
		driver.findElement(By.linkText("Microsoft Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		 
		//Find Patient
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - Microsoft Data", driver.getTitle());
		
		//Select Data
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/13/2029");
		driver.findElement(By.name("confirm")).click();
		
		//Confirm information
		assertEquals("256", driver.findElement(By.name("calories")).getAttribute("value"));
		assertEquals("110", driver.findElement(By.name("activity_hours")).getAttribute("value"));
		assertEquals("2222", driver.findElement(By.name("steps")).getAttribute("value"));
		assertEquals("2223.0", driver.findElement(By.name("dist")).getAttribute("value"));
		assertEquals("7", driver.findElement(By.name("floors")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("hr_lowest")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("hr_average")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("hr_highest")).getAttribute("value"));
		assertEquals("100", driver.findElement(By.name("min_uv_exposure")).getAttribute("value"));

	}

}