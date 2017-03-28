package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ObstetricsVisitStepDefs {
	private HtmlUnitDriver driver = null;
	
	@Given("I log in as Gandalf Stormcrow")
	public void login_as_gandalf_stormcrow() {
		driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000003");
		pass.sendKeys("pw");
		pass.submit();
		Assert.assertEquals("iTrust - HCP Home", driver.getTitle());
	}

	
	@When("I go to enter Obstetrics visit information")
	public void enter_obstetrics_visit() throws Exception {
		Assert.assertTrue(driver.getCurrentUrl().contains("viewOfficeVisit.xhtml"));
		driver.findElementById("newVisitButton").click(); // for some reason this doesn't do anything
		// So navigate manually
		driver.navigate().to("http://localhost:8080/iTrust/auth/hcp-uap/officeVisitInfo.xhtml");
		Assert.assertTrue(driver.getCurrentUrl().contains("officeVisitInfo.xhtml"));
		
		// Find and fill in the Date field
		WebElement dateButton = driver.findElementById("basic_ov_form:ovdate");
		dateButton.sendKeys("3/28/2017 6:00 PM");
		
		// Find the office visit Type selector and choose Obstetrics
		Select apptTypeSelector = new Select(driver.findElementById("basic_ov_form:ovApptType"));
		apptTypeSelector.selectByVisibleText("Obstetrics");
		
		// Hit save
		driver.findElementByName("basic_ov_form:submitVisitButton").click();
		// Make sure that we were taken to the next page
		Assert.assertTrue(driver.getCurrentUrl().contains("obstetricsOfficeVisit.xhtml"));
	}
	
	@When("I search for Sporty Spice by MID and select Sporty Spice")
	public void search_and_select_sporty_spice() {
		Assert.assertEquals("iTrust - HCP Home", driver.getTitle());
		// Navigate to select patient for office visit page
		driver.findElement(By.partialLinkText("Document Office Visit")).click();
		Assert.assertTrue(driver.getCurrentUrl().contains("viewOfficeVisit.xhtml"));
		// Select Sporty Spice as the patient
		driver.findElement(By.id("searchBox")).sendKeys("Person Random");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("Person Random");
		driver.findElement(By.xpath("//input[@value='Person Random']")).submit();
		// Now choose to make an Obstetrics visit
	}
	
	@When("I enter <WeeksPreggo>, <Weight>, <BloodPressure>, <FHR>, <numChildren>, <Placenta>")
	public void enter_obstetrics_information() {
		//TODO
	}
	
	@When("I submit the data")
	public void submit_data() {
		//TODO
	}
	
	//TODO change 42 to number var
	@When("Sporty Spice is 42 weeks pregnant")
	public void enter_age() {
		//TODO
	}
	
	@When("I choose to give an ultrasound")
	public void give_ultrasound() {
		//TODO
	}
	
	@When("I enter the following information: <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>")
	public void enter_ultrasound_data() {
		//TODO
	}
	
	@When("submit the ultrasound data")
	public void submit_ultrasound_data() {
		//TODO
	}
	
	
	@Then("no error message is shown and the data is saved to the database")
	public void no_error_message_shown_and_data_saved() {
		//TODO
	}
	
	@Then("an error message is shown and the data is not saved to the database")
	public void error_message_shown_and_data_not_saved() {
		//TODO
	}
	
	@Then("the next appointment that is scheduled is a Childbirth Visit")
	public void next_appt_childbirth() {
		//TODO
	}
	
	@Then("the next appointment that is scheduled is another Obstetrics Visit")
	public void next_appt_obstetrics() {
		//TODO
	}
	
	@Then("I am unable to create an Obstetrics visit and am prompted to make a general office visit")
	public void unable_to_create_obstetrics_visit() {
		//TODO
	}
	
	
}
