package edu.ncsu.csc.itrust.unit.model.microsoftBand;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandBean;
import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandDAO;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class MicrosoftBandDAOTest extends TestCase{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private MicrosoftBandDAO microsoftDAO = factory.getMicrosoftBandDAO();
	private MicrosoftBandBean newBean;
	private MicrosoftBandBean mbb;
	TestDataGenerator gen;
	Date d;
	
	@Override
	protected void setUp() throws Exception {
		//Necessary object construction
        this.mbb = new MicrosoftBandBean();
        
        //Create Date Object to be used
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setLenient(false);
		java.util.Date date = null;
        try {
			date = format.parse("02/23/2097");
		} catch (ParseException e1) {
			Assert.fail("setup failed. Date not parsed.");
		}
        d = new java.sql.Date(date.getTime());
        
        //create a bean to pass in
        mbb.setPatient(90L);
        mbb.setActivityHours(15);
        mbb.setCalories(205);
        mbb.setFloors(3);
        mbb.setHRAverage(85);
        mbb.setHRHighest(170);
        mbb.setHRLowest(70);
        mbb.setMinUVExposure(400);
        mbb.setID("hi");
        mbb.setSteps(394);
        mbb.setDistance(54.3f);
        mbb.setDate(d);
        
        this.gen = new TestDataGenerator();
		gen.clearAllTables();
	}
	
	@Test
    public void testAddNewWorkout() throws FileNotFoundException, SQLException, IOException{
		try {
			newBean = microsoftDAO.getWorkoutByID("hi");
			if(newBean != null) {
				microsoftDAO.editWorkout(mbb);
			} else {
				microsoftDAO.addNewWorkout(mbb);
			}
			newBean = microsoftDAO.getWorkoutByID("hi");
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
        Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityHours());
        Assert.assertEquals(205, newBean.getCalories());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getHRAverage());
        Assert.assertEquals(170, newBean.getHRHighest());
        Assert.assertEquals(70, newBean.getHRLowest());
        Assert.assertEquals(400, newBean.getMinUVExposure());
        Assert.assertEquals("hi", newBean.getID());
        Assert.assertEquals(394, newBean.getSteps());
    }
    
	@Test
    public void testGetWorkoutsForPatient() throws FileNotFoundException, SQLException, IOException{
		try {
			newBean = microsoftDAO.getWorkoutByID("hi");
			if(newBean != null) {
				microsoftDAO.editWorkout(mbb);
			} else {
				microsoftDAO.addNewWorkout(mbb);
			}
			newBean = microsoftDAO.getAllPatientWorkouts(90L).get(0);
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
        Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityHours());
        Assert.assertEquals(205, newBean.getCalories());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getHRAverage());
        Assert.assertEquals(170, newBean.getHRHighest());
        Assert.assertEquals(70, newBean.getHRLowest());
        Assert.assertEquals(400, newBean.getMinUVExposure());
        Assert.assertEquals("hi", newBean.getID());
        Assert.assertEquals(394, newBean.getSteps());
    }
	
	@Test
    public void testGetByDates() throws FileNotFoundException, SQLException, IOException{
		try {
			newBean = microsoftDAO.getWorkoutByID("hi");
			if(newBean != null) {
				microsoftDAO.editWorkout(mbb);
			} else {
				microsoftDAO.addNewWorkout(mbb);
			}
			newBean = microsoftDAO.getByDateRange(d, d, 90L).get(0);
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
        Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityHours());
        Assert.assertEquals(205, newBean.getCalories());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getHRAverage());
        Assert.assertEquals(170, newBean.getHRHighest());
        Assert.assertEquals(70, newBean.getHRLowest());
        Assert.assertEquals(400, newBean.getMinUVExposure());
        Assert.assertEquals("hi", newBean.getID());
        Assert.assertEquals(394, newBean.getSteps());
    }
}