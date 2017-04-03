package edu.ncsu.csc.itrust.cucumber;


import cucumber.api.java.en.Given;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.ndcode.NDCCode;
import edu.ncsu.csc.itrust.model.ndcode.NDCCodeMySQL;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.model.prescription.Prescription;
import edu.ncsu.csc.itrust.model.prescription.PrescriptionMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import cucumber.api.java.en.Then;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;


public class PrescriptionReportStepDefs {

	
	private DataSource ds;
	private TestDataGenerator gen;
	private OfficeVisitMySQL oVisSQL;
	private NDCCodeMySQL ndcSQL;
	private PrescriptionMySQL preSQL;
	
	public PrescriptionReportStepDefs(){
		
		this.ds = ConverterDAO.getDataSource();
		this.gen = new TestDataGenerator();
		this.oVisSQL = new OfficeVisitMySQL(ds);
		this.ndcSQL = new NDCCodeMySQL(ds);
		this.preSQL = new PrescriptionMySQL(ds);
	}
	
	
	
	@Given("^UC19.sql has not been loaded$")
	public void sqlNotLoaded(){
		try {
			gen.clearAllTables();
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Given("^UC19.sql has been loaded$")
	public void sqlLoaded(){
		try {
			gen.clearAllTables();
			gen.uc19();
			gen.admin1();
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	
	
	
	@Then("^When I view the prescrip report for (.*) it should have report title first: Andy Programmer, prescription (.*), (.*), (.*), (.*), (.*)$")
	public void viewReportFirst(String id, String prescripName, String ovDate, String startDate, String endDate, String hcp){
		try {
			List <Prescription> preList = preSQL.getPrescriptionsForOfficeVisit(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(0).getVisitID());
			List <NDCCode> ndcList = ndcSQL.getAll();
			Assert.assertEquals(prescripName, ndcList.get(0).getDescription());
			Assert.assertTrue(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(0).getDate().toString().contains(ovDate));
			Assert.assertTrue(preList.get(0).getStartDate().toString().contains(startDate));
			Assert.assertTrue(preList.get(0).getEndDate().toString().contains(endDate));
		}
		catch (NumberFormatException | DBException | SQLException e) {
		    System.out.println(e.toString());
		    fail();
			
		}
	}
	
	@Then("^When I view the prescrip report for (.*) it should have report title second: Andy Programmer, prescription (.*), (.*), (.*), (.*), (.*)$")
	public void viewReportSecond(String id, String prescripName, String ovDate, String startDate, String endDate, String hcp){
		try {
			List <Prescription> preList = preSQL.getPrescriptionsForOfficeVisit(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getVisitID());
			List <NDCCode> ndcList = ndcSQL.getAll();
			Assert.assertEquals(prescripName, ndcList.get(1).getDescription());
			Assert.assertTrue(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getDate().toString().contains(ovDate));
			Assert.assertTrue(preList.get(0).getStartDate().toString().contains(startDate));
			Assert.assertTrue(preList.get(0).getEndDate().toString().contains(endDate));
		}
		catch (NumberFormatException | DBException | SQLException e) {
		    System.out.println(e.toString());
		    fail();
			
		}
	}
	
	@Then("^When I view the prescrip report for (.*) it should have report title third: Andy Programmer, prescription (.*), (.*), (.*), (.*), (.*)$")
	public void viewReportThird(String id, String prescripName, String ovDate, String startDate, String endDate, String hcp){
		try {
			List <Prescription> preList = preSQL.getPrescriptionsForOfficeVisit(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getVisitID());
			List <NDCCode> ndcList = ndcSQL.getAll();
			Assert.assertEquals(prescripName, ndcList.get(2).getDescription());
			Assert.assertTrue(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getDate().toString().contains(ovDate));
			Assert.assertTrue(preList.get(1).getStartDate().toString().contains(startDate));
			Assert.assertTrue(preList.get(1).getEndDate().toString().contains(endDate));
		}
		catch (NumberFormatException | DBException | SQLException e) {
		    System.out.println(e.toString());
		    fail();
			
		}
	}
	
	@Then("^When I view the prescrip report for (.*) it should have report title fourth: Andy Programmer, prescription (.*), (.*), (.*), (.*), (.*)$")
	public void viewReportFourth(String id, String prescripName, String ovDate, String startDate, String endDate, String hcp){
		try {
			List <Prescription> preList = preSQL.getPrescriptionsForOfficeVisit(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getVisitID());
			List <NDCCode> ndcList = ndcSQL.getAll();
			Assert.assertEquals(prescripName, ndcList.get(3).getDescription());
			Assert.assertTrue(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getDate().toString().contains(ovDate));
			Assert.assertTrue(preList.get(2).getStartDate().toString().contains(startDate));
			Assert.assertTrue(preList.get(2).getEndDate().toString().contains(endDate));
		}
		catch (NumberFormatException | DBException | SQLException e) {
		    System.out.println(e.toString());
		    fail();
			
		}
	}
	
	@Then("^When I view the prescrip report for (.*), it should be empty$")
	public void emptyView(String id){
		try {
			List <Prescription> preList = preSQL.getPrescriptionsForOfficeVisit(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(0).getVisitID());
			Assert.assertEquals(0, preList.size());
			List <Prescription> preList2 = preSQL.getPrescriptionsForOfficeVisit(oVisSQL.getVisitsForPatient(Long.parseLong(id)).get(1).getVisitID());
			Assert.assertEquals(0, preList2.size());
		
		} catch (NumberFormatException | DBException | SQLException e) {
			fail();
			e.printStackTrace();
		}
		catch (IndexOutOfBoundsException e){
			//if index is out of bounds then the patient has no prescrips or office visits
			Assert.assertTrue(true);
		}
	}
	
}
