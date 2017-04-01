package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ObstetricsStepDefs {
	private HtmlUnitDriver driver = null;
	
	@Given("^I log in as HCP 3$")
	public void login_hcp_3() {
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000003");
		pass.sendKeys("pw");
		pass.submit();
		Assert.assertEquals("iTrust - HCP Home", driver.getTitle());
	}
	
	@Given("^I navigate to the Obstetrics Visit Patient locator page$")
	public void go_to_select_obstetrics_patient() {
		driver.findElement(By.linkText("Obstetrics Data")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		Assert.assertTrue(driver.getCurrentUrl().contains("viewObstetricsData.xhtml"));
	}
	
	@When("^I search for Person Random$")
	public void search_for_patient() {
		driver.findElement(By.id("searchBox")).sendKeys("Person Random");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("Person Random");
	}
	
	@When("^select Person Random$")
	public void select_patient() {
		driver.findElement(By.xpath("//input[@value='Person Random']")).submit();
		Assert.assertEquals("View Obstetrics Report", driver.getTitle());
		Assert.assertTrue(driver.getPageSource().contains("Obstetrics Report"));
		//Assert.assertTrue(driver.getPageSource().contains("Person, Random"));
	}
	
	@When("enter -1 for number of children in a prior pregnancy")
	public void enter_negative_children() {
		//
	}

	@Then("^their lack of obstetrics visit information is displayed")
	public void obstetrics_info_displayed()  {
		// No obstetrics visit data for the patient
		Assert.assertFalse(driver.getPageSource().contains("Past Obstetrics visits"));
	}
}