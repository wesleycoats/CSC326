package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class FitBitReportingStepDefs {

	/**Html unit driver*/
	private HtmlUnitDriver driver = new HtmlUnitDriver();
	/**Base URL*/
	private String BASE_URL = "http://localhost:8080/iTrust/";
	
	@Given("^I log in as the 1st HCP$")
	public void login_for_fitbit_reporting() {
		driver.navigate().to(BASE_URL);
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000000");
		pass.sendKeys("pw");
		pass.submit();
		assertEquals("iTrust - HCP Home", driver.getTitle());
	}
	
	@And("^go to the FitBit data page$")
	public void navigate_to_fitbit_reporting() {
		driver.findElement(By.linkText("FitBit Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	}
	
	@When("^I input a Patient and click enter and confirm the page goes to the calendar landing page$")
	public void select_a_valid_patient() {
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.id("searchBox")).sendKeys("1");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();
		assertEquals("iTrust - FitBit Calendar", driver.getTitle());
	}
	
	@Then("^clicking on the Run Report button the Report page will open$")
	public void fitbit_reporting_button() {
		driver.findElement(By.name("report")).click();
		assertEquals("iTrust - Fitbit Report", driver.getTitle());
	}

	@And("^inputting a start and end date$")
	public void input_reporting_start() {
		assertEquals("FitBit Report For: Person, Random", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/h2")).getText());
	}
	
	@Then("^it will stay on the page$")
	public void input_choose_line_graph() {
		assertEquals("iTrust - Fitbit Report", driver.getTitle());
		//didn't implement more =(
	}
	
}
