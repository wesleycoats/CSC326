package edu.ncsu.csc.itrust.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ObstetricsVisitStepDefs {
	private HtmlUnitDriver driver = null;
	
	@Given("I log in as Gandalf Stormcrow")
	public void login_as_gandalf_stormcrow() {
		driver = new HtmlUnitDriver();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:8080/iTrust/");
		WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("j_username")));
		WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("j_password")));
		user.sendKeys("9000000003");
		pass.sendKeys("pw");
		pass.submit();
		Assert.assertEquals("iTrust - HCP Home", driver.getTitle());
	}

	
	@When("^I go to enter Obstetrics visit information$")
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
	
	@When("^I go to enter Obstetrics visit information but am only able to create a visit$")
	public void create_obstetrics_visit_by_non_obgyn() {
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
		// Make sure that we are taken back to the page to view OfficeVisits for that patient, as we aren't allowed
		// to enter information for Obstetrics visits
		Assert.assertTrue(driver.getCurrentUrl().contains("viewOfficeVisit.xhtml"));
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
	
	@When("I enter (.+), (.+), (.+), (.+), (.+), (.+)")
	public void enter_obstetrics_information(String weeksPreggo, String weight, String bloodPressure, String FHR, String numChildren, String placenta) {	
		Assert.assertTrue(driver.getCurrentUrl().contains("obstetricsOfficeVisit")); // make sure on the right page
		// LMP will be a constant throughout all of these tests since this functionality was tested in UC93
		// TODO create LMP constant to pass
		
		// Send weeksPreggo
		//TODO
		// Now send the  weight
		driver.findElementById("obinfo:obovweight").sendKeys(weight);
		// Send the blood pressure
		driver.findElementById("obinfo:obovbp").sendKeys(bloodPressure);
		// Send FHR
		driver.findElementById("obinfo:obovfetalhr").sendKeys(FHR);
		// Send numChildren
		driver.findElementById("obinfo:obovmulti").sendKeys(numChildren);
		// Send placenta
		//TODO
	}
	
	@When("I submit the data")
	public void submit_data() {
		Assert.assertTrue(driver.getCurrentUrl().contains("obstetricsOfficeVisit")); // make sure on the right page still
		
		// Find the submit button and hit it
		driver.findElementById("obinfo:submitVisitButton").click();
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
