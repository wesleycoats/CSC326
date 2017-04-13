package edu.ncsu.csc.itrust.unit.flags;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;

import edu.ncsu.csc.itrust.controller.flags.Flag;
import edu.ncsu.csc.itrust.controller.flags.FlagMySQL;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;

public class FlagMySQLTest {
	private DataSource ds;
	private FlagMySQL fSQL;

	@Test
	public void test() {
		try {
			fSQL = new FlagMySQL();
			fail();
		} catch (DBException e) {
			//Do nothing, we wanted this
		}
		
		ds = ConverterDAO.getDataSource();
		fSQL = new FlagMySQL(ds);
		
		// Try adding a bad flag, to catch the DBException
		Flag f = new Flag(-1L, 1L, 1L, "Twins");
		try {
			assertFalse(fSQL.add(f));
			fail();
		} catch (DBException e) {
			// Do nothing, we wanted an exception here
		}
		
		f = new Flag(1L, 1L, 1L, "Twins");
		try {
			assertTrue(fSQL.add(f));
		} catch (DBException e) {
			fail();
		}
		
		f = new Flag(2L, 1L, 1L, "Advanced Maternal Age");
		try {
			assertTrue(fSQL.add(f));
		} catch (DBException e) {
			fail();
		}
		
		try {
			fSQL.getByPatientID(1L);
		} catch (DBException e) {
			fail();
		}
	}
}
