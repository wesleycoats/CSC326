package edu.ncsu.csc.itrust.cucumber;
import edu.ncsu.csc.itrust.model.childbirthVisit.*;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.cucumber.util.SharedChildbirthVisit;
import edu.ncsu.csc.itrust.cucumber.util.SharedPersonnel;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.PersonnelBean;

public class ChildbirthVisitStepDefs {

	private SharedPersonnel sharedPersonnel;
	private SharedChildbirthVisit sharedChildbirthVisit;
	
	
	@Given("I log in as Kelly Doctor")
	public void login_as_kelly_doctor() throws DBException {
		PersonnelBean p = sharedPersonnel.getPersonnelDAO().getPersonnel(9000000000L);
		sharedPersonnel.setPersonnel(p);
	}
	
	@When("I search for Princess Peach by MID and select Princess Peach")
	public void search_and_select_princess_peach() {
		sharedChildbirthVisit.setPatientMID(21L);
		sharedChildbirthVisit.setVisitID(9000000000L);
	}
	
	@When("I search for Daria Griffin by MID and select Daria Griffin")
	public void search_and_select_daria_griffin() {
		sharedChildbirthVisit.setPatientMID(104L);
		sharedChildbirthVisit.setVisitID(9000000000L);
	}
	
	@When("Princess Peach has a childbirth visit scheduled during an obstetrics visit")
	public void princess_peach_has_childbirth_visit_scheduled() {
		
	}
	
	@When("I go to enter childbirth information and I enter (.+), (.+), (.+), (.+), (.+), (.+), (.+)")
	public void enter_childbirth_info(String epiduralAnaesthesiaDosage, String magnesiumSulfateDosage, String nitrousOxideDosage, String pethidineDosage, String pitocinDosage, String preferredDelivery, String scheduled) {
		int anaesthesia = Integer.parseInt(epiduralAnaesthesiaDosage);
		int magnesium = Integer.parseInt(magnesiumSulfateDosage);
		int nitrousOxide = Integer.parseInt(nitrousOxideDosage);
		int pethidine = Integer.parseInt(pethidineDosage);
		int pitocin = Integer.parseInt(pitocinDosage);
		String preferred = preferredDelivery;
		boolean sched = Boolean.parseBoolean(scheduled);
		
		sharedChildbirthVisit.setValues(anaesthesia, magnesium, nitrousOxide, pethidine, pitocin, preferred, sched);
		
		sharedChildbirthVisit.addVisit();
		Assert.assertNull(sharedChildbirthVisit.getErrorMessage());
	}
	
	@When("^I submit the data$")
	public void submit_data() {
		sharedChildbirthVisit.addVisitToDatabase();
	}
	
	@When("Princess Peach has fraternal twins Toad and Toadette at 2:30 on May 17 of the current year")
	public void princess_peach_has_twins() {
		
	}
	
	@When("a childbirth hospital visit is scheduled")
	public void childbirth_visit_scheduled() {
		
	}
	
	@When("Daria Griffin gives birth and I enter info for Carly Griffin")
	public void daria_griffin_gives_birth_to_carly() {
		
	}
	
	@When("I search for Daryl Griffin instead of Daria Griffin")
	public void wrong_search_daryl_daria() {
		
	}
	
	@When("I search MID 103 instead of 104")
	public void wrong_mid() {
		
	}
	
	@Then("new patient file(s) is/are created for the baby(s)")
	public void new_patient_files_created() {
		
	}
	
	@Then("vaginal delivery is set by default")
	public void vaginal_delivery_default() {
		
	}
	
	@Then("her son Chuck Griffin is entered into the system with birthdate 15:00 June 1st 2017")
	public void chuck_griffin_entered() {
		
	}
	
	
	
	
	
	
	
	
}
