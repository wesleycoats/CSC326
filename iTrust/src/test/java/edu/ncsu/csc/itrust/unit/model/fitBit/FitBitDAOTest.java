package edu.ncsu.csc.itrust.unit.model.fitBit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.fitBit.FitBitBean;
import edu.ncsu.csc.itrust.model.fitBit.FitBitDAO;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class FitBitDAOTest extends TestCase{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private FitBitDAO fitBitDAO = factory.getFitBitDAO();
	private FitBitBean newBean;
	private FitBitBean fbb;
	TestDataGenerator gen;
	Date d;
	Date d1;
	Date d2;
	
	@Override
	protected void setUp() throws Exception {
		//Necessary object construction
        this.fbb = new FitBitBean();
        
        //Create Date Object to be used
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setLenient(false);
		java.util.Date date = null;
		java.util.Date date1 = null;
		java.util.Date date2 = null;
        try {
			date = format.parse("02/23/2097");
			date1 = format.parse("02/22/2097");
			date2 = format.parse("02/24/2097");
		} catch (ParseException e1) {
			Assert.fail("setup failed. Date not parsed.");
		}
        d = new Date(date.getTime());
        d1 = new Date(date1.getTime());
        d2 = new Date(date2.getTime());
        
        //create a bean to pass in
        fbb.setPatient(90L);
        fbb.setActivityCal(15);
        fbb.setCalBurned(205);
        fbb.setFloors(3);
        fbb.setMinSeden(85);
        fbb.setMinLightActive(170);
        fbb.setMinFairActive(70);
        fbb.setMinVeryActive(400);
        fbb.setID("hi");
        fbb.setSteps(394);
        fbb.setDistance(54.3f);
        fbb.setDate(d);
        
        this.gen = new TestDataGenerator();
		gen.clearAllTables();
	}
	
	@Test
    public void testAddNewWorkout() throws FileNotFoundException, SQLException, IOException{
		try {
			newBean = fitBitDAO.getWorkoutByID("hi");
			if(newBean != null) {
				fitBitDAO.editWorkout(fbb);
			} else {
				fitBitDAO.addNewWorkout(fbb);
			}
			newBean = fitBitDAO.getWorkoutByID("hi");
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
        Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityCal());
        Assert.assertEquals(205, newBean.getCalBurned());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getMinSeden());
        Assert.assertEquals(170, newBean.getMinLightActive());
        Assert.assertEquals(70, newBean.getMinFairActive());
        Assert.assertEquals(400, newBean.getMinVeryActive());
        Assert.assertEquals("hi", newBean.getID());
        Assert.assertEquals(394, newBean.getSteps());
        

        fbb.setSteps(7);
        try {
			newBean = fitBitDAO.getWorkoutByID("hi");
			if(newBean != null) {
				fitBitDAO.editWorkout(fbb);
			} else {
				fitBitDAO.addNewWorkout(fbb);
			}
			newBean = fitBitDAO.getWorkoutByID("hi");
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
        Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityCal());
        Assert.assertEquals(205, newBean.getCalBurned());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getMinSeden());
        Assert.assertEquals(170, newBean.getMinLightActive());
        Assert.assertEquals(70, newBean.getMinFairActive());
        Assert.assertEquals(400, newBean.getMinVeryActive());
        Assert.assertEquals("hi", newBean.getID());
        Assert.assertEquals(7, newBean.getSteps());
    }
    
	@Test
    public void testGetWorkoutsForPatient() throws FileNotFoundException, SQLException, IOException{
		try {
			newBean = fitBitDAO.getWorkoutByID("hi");
			if(newBean != null) {
				fitBitDAO.editWorkout(fbb);
			} else {
				fitBitDAO.addNewWorkout(fbb);
			}
			newBean = fitBitDAO.getAllPatientWorkouts(90L).get(0);
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
		Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityCal());
        Assert.assertEquals(205, newBean.getCalBurned());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getMinSeden());
        Assert.assertEquals(170, newBean.getMinLightActive());
        Assert.assertEquals(70, newBean.getMinFairActive());
        Assert.assertEquals(400, newBean.getMinVeryActive());
        Assert.assertEquals("hi", newBean.getID());
        Assert.assertEquals(394, newBean.getSteps());
    }
	
	@Test
    public void testGetByDates() throws FileNotFoundException, SQLException, IOException{
		fbb.setID("new");
		try {
			newBean = fitBitDAO.getWorkoutByID("new");
			if(newBean != null) {
				fitBitDAO.editWorkout(fbb);
			} else {
				fitBitDAO.addNewWorkout(fbb);
			}
			newBean = fitBitDAO.getByDateRange(d1, d2, 90L).get(0);
		} catch (ITrustException e) {
			Assert.fail("DBException, could not create new workout");
			e.printStackTrace();
		}
		
        
        //Assert that the Bean's data is unchanged
		Assert.assertEquals(90L, newBean.getPatient());
        Assert.assertEquals(15, newBean.getActivityCal());
        Assert.assertEquals(205, newBean.getCalBurned());
        Assert.assertEquals(3, newBean.getFloors());
        Assert.assertEquals(85, newBean.getMinSeden());
        Assert.assertEquals(170, newBean.getMinLightActive());
        Assert.assertEquals(70, newBean.getMinFairActive());
        Assert.assertEquals(400, newBean.getMinVeryActive());
        Assert.assertEquals("new", newBean.getID());
        Assert.assertEquals(394, newBean.getSteps());
    }
}