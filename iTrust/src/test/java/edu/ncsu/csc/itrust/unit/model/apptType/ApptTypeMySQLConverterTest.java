package edu.ncsu.csc.itrust.unit.model.apptType;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.apptType.ApptType;
import edu.ncsu.csc.itrust.model.apptType.ApptTypeMySQLConverter;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class ApptTypeMySQLConverterTest {
	private DataSource ds;
	private ApptTypeMySQLConverter converter;
	private TestDataGenerator gen;

	@Test
	public void test() throws FileNotFoundException, SQLException, IOException {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.appointmentType();
		
		try {
			converter = new ApptTypeMySQLConverter();
			fail(); 
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		ds = ConverterDAO.getDataSource();
		converter = new ApptTypeMySQLConverter(ds);
		
		try {
			assertEquals("General Checkup", converter.getApptTypeName(1L));
		} catch (DBException e) {
			fail();
		}
		try {
			assertEquals("Child Birth", converter.getApptTypeName(8L));
		} catch (DBException e) {
			fail();
		}
		
		try {
			assertEquals(8, converter.getAll().size());
		} catch (DBException e) {
			fail();
		}
		
		try {
			converter.getApptTypeIDs("General Checkup");
		} catch (DBException e) {
			fail();
		}
		try {
			converter.getApptTypeIDs("Child Birth");
		} catch (DBException e) {
			fail();
		}
		try {
			converter.getApptTypeIDs("Null Apt");
		} catch (DBException e) {
			fail(); // Even though this ApptType doesn't exist, no exception should be thrown
		}
		
		// Test getByID
		ApptType a1 = new ApptType();
		ApptType a2 = new ApptType();
		a1.setID(1L);
		a2.setID(8L);
		a1.setName("General Checkup");
		a2.setName("Child Birth");
		
		try {
			assertEquals(a1.getName(), converter.getByID(1L).getName());
			assertEquals(a1.getID(), converter.getByID(1L).getID());
		} catch (DBException e) {
			fail();
		}
		try {
			assertEquals(a2.getName(), converter.getByID(8L).getName());
			assertEquals(a2.getID(), converter.getByID(8L).getID());
		} catch (DBException e) {
			fail();
		}
		try {
			converter.getByID(13L);
			fail();
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		
		// Now test out add(..)
		ApptType aNew = new ApptType();
		aNew.setID(9L);
		aNew.setName("New Appt");
		aNew.setDuration(15);
		aNew.setPrice(130000);
		
		try {
			assertTrue(converter.add(aNew));
			assertEquals(9L, converter.getAll().size());
		} catch (DBException e) {
			fail();
		}
	}
}
