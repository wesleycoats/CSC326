package edu.ncsu.csc.itrust.cucumber;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisit;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitMySQL;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;
import edu.ncsu.csc.itrust.model.pregnancyConditions.PregnancyConditionsMySQL;

public class LaborDeliveryReportStepDefs {
	/** The MID for Person Random */
	private static final Long PRMID = 1L;
	private DataSource ds;
	private PregnanciesMySQL pSQL;
	private ChildbirthVisitMySQL cbSQL;
	private ObstetricsVisitMySQL osSQL;
	private PregnancyConditionsMySQL pcSQL;
	
	public LaborDeliveryReportStepDefs() {
		ds = ConverterDAO.getDataSource();
		pSQL = new PregnanciesMySQL(ds);
		cbSQL = new ChildbirthVisitMySQL(ds);
		osSQL = new ObstetricsVisitMySQL(ds);
		pcSQL = new PregnancyConditionsMySQL(ds);
	}
	
	
	@Given("I enter 1 past pregnancies for Person Random")
	public void enter_past_pregnancy() {
		Pregnancies p= new Pregnancies(PRMID, "vaginal delivery", 1998, 6.3, 13.4, 43, (short)1);
		p.setEdd(LocalDateTime.now());
		try {
			pSQL.add(p);
		} catch (DBException e) {
			Assert.fail("Should not be throwing an exception when adding a pregnancy to the database");
		}
	}
	
	@Given("I enter 1 past childbirth visit for Person Random")
	public void enter_childbirth_visit() {
		ChildbirthVisit cb = new ChildbirthVisit(1000L, PRMID, "vaginal delivery", true);
		cb.setEpiduralAnaesthesiaDosage(0);
		cb.setMagnesiumSulfateDosage(0);
		cb.setNitrousOxideDosage(0);
		cb.setPethidineDosage(0);
		cb.setPitocinDosage(0);
		cb.setRhGlobulinDosage(0);
		try {
			cbSQL.addChildbirthVisit(cb);
		} catch (DBException e) {
			Assert.fail("Should not be throwing an exception when adding a childbirth to the database");
		}
	}
	
	@Given("I enter 2 past Obstetrics visits for Person Random")
	public void enter_past_obstetrics_visit() {
		ObstetricsVisit ov1 = new ObstetricsVisit();
		ov1.setVisitID(1L);
		ov1.setPatientMID(PRMID.longValue());
		ov1.setWeight(175.4f);
		ov1.setSystolicBloodPressure(141);
		ov1.setDiastolicBloodPressure(80);
		ov1.setFetalHeartRate(134);
		ov1.setPregnancies(1);
		ov1.setPlacentaObserved(true);
		ov1.setWeeksPregnant(13);
		
		ObstetricsVisit ov2 = new ObstetricsVisit();
		ov2.setVisitID(2L);
		ov2.setPatientMID(PRMID);
		ov2.setWeight(179.2f);
		ov2.setSystolicBloodPressure(130);
		ov2.setDiastolicBloodPressure(82);
		ov2.setFetalHeartRate(156);
		ov2.setPregnancies(1);
		ov2.setPlacentaObserved(true);
		ov2.setWeeksPregnant(34);
		try {
			osSQL.addObstetricsVisit(ov1);
			osSQL.addObstetricsVisit(ov2);
		} catch (DBException e) {
			Assert.fail("Should not be throwing an exception when adding an Obstetrics Visit to the database");
		}
	}
	
	@Given("I decide that Person Random has no STDs")
	public void has_no_std() {
		// Unfortunately this method is boring. No STDs means nothing to add to the 
		// pregnancyConditions table in the database, means nothing to do here
	}
	
	@Given("I decide that Person Random was diagnosed with Cancer and Diabetes")
	public void does_have_cancer() {
		try {
			pcSQL.add(PRMID, "C719");
			pcSQL.add(PRMID, "E114");
		} catch (DBException e) {
			Assert.fail("Should not be throwing an exception when adding pregnancy Conditions to the database");
		}
	}
	
	@Given("god wanted Person Random was allergic to Penicillin")
	public void is_allergic_to_penicillin() {
		try {
			pcSQL.add(PRMID, "0338-1021-41");
		} catch (DBException e) {
			Assert.fail("Should not be throwing an exception when adding drug allergies to the database");
		}
	}
	
	@When("I login as Gandalf Stormcrow and go to view the delivery report")
	public void go_to_view_labor_report() {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.navigate().to("http://localhost:8080/iTrust/");
		WebElement user = driver.findElement(By.name("j_username"));
		WebElement pass = driver.findElement(By.name("j_password"));
		user.sendKeys("9000000003");
		pass.sendKeys("pw");
		pass.submit();

		assertEquals("iTrust - HCP Home", driver.getTitle());
		// Login as gandalf using Selenium and then go to the labor report page
		// Go to labor report page
		driver.findElementByPartialLinkText("Labor and Delivery Report").click();
		// Figure out how to select Person Random
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		// Select person random
		driver.findElement(By.id("searchBox")).sendKeys("Person Random");
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("Person Random");
		driver.findElement(By.xpath("//input[@value='Person Random']")).submit();
		
		//assertEquals("Labor and Delivery Report", driver.getTitle());
	}
	
	@Then("all of this information is correctly displayed")
	public void information_correctly_displayed() {
		//TODO
	}
}
