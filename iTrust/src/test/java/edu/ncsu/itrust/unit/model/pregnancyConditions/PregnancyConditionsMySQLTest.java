package edu.ncsu.itrust.unit.model.pregnancyConditions;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.pregnancyConditions.PregnancyConditionsMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class PregnancyConditionsMySQLTest {
	private PregnancyConditionsMySQL pcSQL;
	private DataSource ds;

	@Test
	public void test() throws FileNotFoundException, SQLException, IOException {
		try {
			pcSQL = new PregnancyConditionsMySQL();
			fail();
		} catch (DBException e) {
			// do nothing, we expect a failure here
		}
		
		ds = ConverterDAO.getDataSource();
		pcSQL = new PregnancyConditionsMySQL(ds);
		TestDataGenerator gen = new TestDataGenerator();
        gen.clearAllTables();
        
        try {
			assertEquals(0, pcSQL.getAllByMID(1L).size());
		} catch (DBException e) {
			fail();
		}
        // Load some test data to the database with gen
        try {
        	gen.LaborDeliveryReportData();
        } catch (Exception e) {
        	e.printStackTrace();
        	fail();
        }
        // Assert that the MySQL methods are working correctly
        try {
        	ArrayList<String> user1 = pcSQL.getAllByMID(1L);
        	ArrayList<String> user21 = pcSQL.getAllByMID(21L);
        	assertEquals(3, user1.size());
        	assertEquals(1, user21.size());
        	
        	assertEquals("0338-1021-41", user1.get(1));
        	assertEquals("E032", user21.get(0));
        } catch (DBException e) {
        	fail();
        }
        
        // Now try to add a new entry to the table
        // First try to add bad inputs
        try {
        	pcSQL.add(0, "E032");
        	fail();
        } catch (DBException e) {
        	// Do nothing, we expect an exception here
        }
        try {
        	pcSQL.add(1, "E1119");
        	fail();
        } catch (DBException e) {
        	// Do nothing, we expect an exception here
        }
        // Now try to add a valid entry
        try {
        	pcSQL.add(1, "O210");
        } catch (DBException e) {
        	fail();
        }
        
        //Assert that it was added
        try {
        	ArrayList<String> user1 = pcSQL.getAllByMID(1L);
        	assertEquals(4, user1.size());
        	assertEquals("O210", user1.get(3));
        } catch (DBException e) {
        	fail();
        }
	}

}
