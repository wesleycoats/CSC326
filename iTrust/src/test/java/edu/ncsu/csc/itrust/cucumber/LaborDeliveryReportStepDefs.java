package edu.ncsu.csc.itrust.cucumber;

import javax.sql.DataSource;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitMySQL;
import edu.ncsu.csc.itrust.model.icdcode.ICDCodeMySQL;
import edu.ncsu.csc.itrust.model.ndcode.NDCCodeMySQL;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class LaborDeliveryReportStepDefs {
	/** The MID for Person Random */
	private static final Long PRMID = 1L;
	private DataSource ds;
	private PregnanciesMySQL pSQL;
	private TestDataGenerator gen;
	private ChildbirthVisitMySQL cbSQL;
	private ObstetricsVisitMySQL osSQL;
	private ICDCodeMySQL icdSQL;
	private NDCCodeMySQL ndSQL;
	
	/** Used for adding allergies to the database, I think? I honestly have no idea */
	private AllergyDAO allergyDAO;
	// Have no idea how to add a pre-existing condition to patients
	
	
	
	
	@Given("I enter 1 past pregnancies for Person Random")
	public void enter_past_pregnancy() {
		//TODO
	}
	
	@Given("I enter 1 past childbirth visit for Person Random")
	public void enter_childbirth_visit() {
		//TODO
	}
	
	@Given("I enter 2 past Obstetrics visits for Person Random")
	public void enter_past_obstetrics_visit() {
		//TODO
	}
	
	@Given("I decide that Person Random has no STDs")
	public void has_no_std() {
		//TODO
	}
	
	@Given("I decide that Person Random was diagnosed with Cancer")
	public void does_have_cancer() {
		//TODO
	}
	
	@Given("god wanted Person Random was allergic to Penicillin")
	public void is_allergic_to_penicillin() {
		//TODO
	}
	
	@When("I go to view the delivery report")
	public void go_to_view_labor_report() {
		//TODO
		// Login as gandalf using Selenium and then go to the labor report page
	}
	
	@Then("all of this information is correctly displayed")
	public void information_correctly_displayed() {
		//TODO
	}
}
