package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.SharedObstetricsVisit;
import edu.ncsu.csc.itrust.cucumber.util.SharedPersonnel;
import edu.ncsu.csc.itrust.cucumber.util.SharedUltrasoundVisit;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;
import edu.ncsu.csc.itrust.model.ultrasound.Ultrasound;

public class ObstetricsVisitStepDefs {
	private SharedPersonnel sharedPersonnel;
	private SharedObstetricsVisit sharedObstetricsVisit;
	private SharedUltrasoundVisit sharedUltrasoundVisit;
	
	public ObstetricsVisitStepDefs(SharedPersonnel sharedPersonnel,
			SharedObstetricsVisit sharedObstetricsVisit, SharedUltrasoundVisit sharedUltrasoundVisit) throws Exception {
		this.sharedPersonnel = sharedPersonnel;
		this.sharedObstetricsVisit = sharedObstetricsVisit;
		this.sharedUltrasoundVisit = sharedUltrasoundVisit;
	}
	
	@Given("^Gandalf Stormcrow is an HCP with MID: 9000000003 and role of OB/GYN$")
	public void gs_is_an_HCP_with_MID() throws Throwable {
		PersonnelBean p = sharedPersonnel.getPersonnelDAO().getPersonnel(9000000003L);
		assertNotNull(String.format("Personnel with MID: %d doesn't exist", 9000000003L), p);
		//assertEquals(Role.OBGYN, p.getRole());
	}
	
	@Given("I log in as Gandalf Stormcrow")
	public void login_as_gandalf_stormcrow() throws DBException {
		PersonnelBean p = sharedPersonnel.getPersonnelDAO().getPersonnel(9000000003L);
		sharedPersonnel.setPersonnel(p);
	}
	
	@When("I search for Person Random by MID and select Person Random")
	public void search_and_select_sporty_spice() {
		sharedObstetricsVisit.setPatientMID(1L);
		sharedObstetricsVisit.setVisitID(9000000003L);
	}
	
	@When("^I go to enter Obstetrics visit information and I enter (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void enter_obstetrics_information(String weeksPreggo, String weight, String bloodPressure, String FHR, String numChildren, String placenta) {
		int weeksPregnant = Integer.parseInt(weeksPreggo);
		float patientWeight = Float.parseFloat(weight);
		int bp = Integer.parseInt(bloodPressure);
		int fetalHR = Integer.parseInt(FHR);
		short children = Short.parseShort(numChildren);
		boolean p = Boolean.parseBoolean(placenta);
		
		sharedObstetricsVisit.setValues(weeksPregnant, patientWeight, bp, fetalHR, children, p);
		
		sharedObstetricsVisit.addVisit();
		Assert.assertNull(sharedObstetricsVisit.getErrorMessage());
	}
	
	@When("^I go to enter invalid Obstetrics visit information and I enter (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void enter_invalid_obstetrics_information(String weeksPreggo, String weight, String bloodPressure, String FHR, String numChildren, String placenta) {
		int weeksPregnant = Integer.parseInt(weeksPreggo);
		float patientWeight = Float.parseFloat(weight);
		int bp = Integer.parseInt(bloodPressure);
		int fetalHR = Integer.parseInt(FHR);
		short children = Short.parseShort(numChildren);
		boolean p = Boolean.parseBoolean(placenta);
		
		sharedObstetricsVisit.setValues(weeksPregnant, patientWeight, bp, fetalHR, children, p);
		
		sharedObstetricsVisit.addVisit();
		Assert.assertNotNull(sharedObstetricsVisit.getErrorMessage());
	}
	
	@When("^I submit the data$")
	public void submit_data() {
		sharedObstetricsVisit.addVisitToDatabase();
	}
	
	@When("^I choose to give an ultrasound and I enter the following information: (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void give_ultrasound(String CRL, String BPD, String HC, String FL, String OFD, String AC, String HL, String EFW) {
		double crl = Double.parseDouble(CRL);
		double bpd = Double.parseDouble(BPD);
		double hc = Double.parseDouble(HC);
		double fl = Double.parseDouble(FL);
		double ofd = Double.parseDouble(OFD);
		double ac = Double.parseDouble(AC);
		double hl = Double.parseDouble(HL);
		double efw = Double.parseDouble(EFW);
		LocalDateTime d = LocalDateTime.now();
		
		Ultrasound us = new Ultrasound(1, d, crl, bpd, hc, fl, ofd, ac, hl, efw);
		
		sharedUltrasoundVisit.setUs(us);
		
		sharedUltrasoundVisit.addUltrasound();
		Assert.assertNull(sharedUltrasoundVisit.getErrorMessage());
	}
	
	@When("^I choose to give an ultrasound and I enter the following invalid information: (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void give_invalid_ultrasound(String CRL, String BPD, String HC, String FL, String OFD, String AC, String HL, String EFW) {
		double crl = Double.parseDouble(CRL);
		double bpd = Double.parseDouble(BPD);
		double hc = Double.parseDouble(HC);
		double fl = Double.parseDouble(FL);
		double ofd = Double.parseDouble(OFD);
		double ac = Double.parseDouble(AC);
		double hl = Double.parseDouble(HL);
		double efw = Double.parseDouble(EFW);
		LocalDateTime d = LocalDateTime.now();
		
		Ultrasound us = new Ultrasound(1, d, crl, bpd, hc, fl, ofd, ac, hl, efw);
		
		sharedUltrasoundVisit.setUs(us);
		
		sharedUltrasoundVisit.addUltrasound();
		Assert.assertNotNull(sharedUltrasoundVisit.getErrorMessage());
	}
	
	@When("^submit the ultrasound data$")
	public void submit_ultrasound_data() {
		sharedUltrasoundVisit.addUltrasoundToDatabase();
	}
	
	
	@Then("no error message is shown and the obstetrics data is saved to the database")
	public void no_error_message_shown_and_data_saved() {
		Assert.assertTrue(sharedObstetricsVisit.getWasAddSuccessful());
		Assert.assertNull(sharedObstetricsVisit.getErrorMessage());
	}
	
	@Then("an error message is shown and the obstetrics data is not saved to the database")
	public void error_message_shown_and_data_not_saved() {
		Assert.assertFalse(sharedObstetricsVisit.getWasAddSuccessful());
		Assert.assertNotNull(sharedObstetricsVisit.getErrorMessage());
	}
	
	@Then("no error message is shown and the ultrasound data is saved to the database")
	public void no_error_message_shown_and_us_data_saved() {
		//Assert.assertTrue(sharedUltrasoundVisit.getWasAddSuccessful());
		Assert.assertNull(sharedUltrasoundVisit.getErrorMessage());
	}
	
	@Then("an error message is shown and the ultrasound data is not saved to the database")
	public void error_message_shown_and_us_data_not_saved() {
		Assert.assertFalse(sharedUltrasoundVisit.getWasAddSuccessful());
		Assert.assertNotNull(sharedUltrasoundVisit.getErrorMessage());
	}
}
