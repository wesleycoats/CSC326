package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ObstretricsVisitStepDefs {
	public HtmlUnitDriver driver = null;
	
	@Given("^I log in as Dr Seuss$")
	public void login_dr_seuss() {
		
	}
	
	@Given("^I log in as Kelly Doctor$")
	public void login_kelly_doctor() {
		
	}
	
	@Given("^Dr Seuss is specialized for OB/GYN$")
	public void specialized_for_obgyn() {
		
	}
	
	@Given("^I search for Sporty Spice by MID and select Sporty Spice$")
	public void select_sporty_spice() {
		
	}
	
	@Given("^I go to enter Obstetrics visit information$")
	public void go_to_obstetrics_visit_info() {
		
	}
	
	@Given("^I enter <WeeksPreggo>, <Weight>, <Blood Pressure>, <FHR>, <numChildren>, <Placenta>$")
	public void enter_info() {
		
	}
	
	@Given("^I submit the data$")
	public void submit_data() {
		
	}
	
	@Given("^Sporty Spice is 42 weeks pregnant$")
	public void sporty_spice_is_pregnant() {
	
	}
	
	@Given("^I choose to give an ultrasound$")
	public void give_ultrasound() {
		
	}
	
	
	
	
	@When("^I enter the followig information: <CRL>, <BPD>, <HC>, <FL>, <OFD>, <AC>, <HL>, <EFW>$")
	public void enter_following_info() {
		
	}
	
	@When("^sumbit the ultrasound data$")
	public void submit_ultrasound() {
		
	}
	
	
	
	
	@Then("^no error message is shown and the data is saved to the database$")
	public void data_is_saved() {
		
	}
	
	@Then("^an error message is shown and the data is not saved to the database$")
	public void data_is_not_saved() {
		
	}
	
	@Then("^the next appointment that is scheduled is a Childbirth Visit$")
	public void next_appointment_scheduled_is_childbirth_visit() {
		
	}
	
	@Then("^the next appointment that is scheduled is another Obstetrics Visit$")
	public void next_appointment_scheduled_is_obstetrics_visit() {
		
	}
	
	@Then("^I am unable to create an Obstetrics visit and am prompted to make a general office visit$")
	public void unable_to_make_obstetrics_visit() {
		
	}
	
	
}
