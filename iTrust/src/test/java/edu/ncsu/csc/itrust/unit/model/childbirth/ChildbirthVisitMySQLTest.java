package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisit;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class ChildbirthVisitMySQLTest {
	private DataSource ds;
	private TestDataGenerator gen;
	
	@Before
	public void setUp() throws FileNotFoundException, SQLException, IOException {
		gen = new TestDataGenerator();
		gen.clearAllTables(); // make a clean slate
		gen.officeVisit4();
		gen.officeVisit8(); // add some office visits to the database
	}

	@Test
	public void test() {
		ChildbirthVisitMySQL cbSQL;
		// Test the null constructor
		try {
			cbSQL = new ChildbirthVisitMySQL();
			fail();
		} catch (DBException e) {
			//Do nothing, because we want an exception thrown
		}
		ds = ConverterDAO.getDataSource();
		cbSQL = new ChildbirthVisitMySQL(ds);
		
		try {
			assertEquals(null, cbSQL.getDateOfVisit(1L));
		} catch (Exception e) {
			fail(); // Shouldn't throw an exception, because there are visits in the database
		}
		//TODO add more tests for getDateOfVisit()
		
		ChildbirthVisit cbvINV1 = new ChildbirthVisit();
		cbvINV1.setPatientMID(null);
		ChildbirthVisit cbvINV2 = new ChildbirthVisit();
		cbvINV2.setEpiduralAnaesthesiaDosage(-34);
		try {
			cbSQL.addChildbirthVisit(cbvINV1);
			fail();
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		try {
			cbSQL.addChildbirthVisit(cbvINV2);
			fail();
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		//TODO add more tests for addChildbirthVisit()
	}
}
