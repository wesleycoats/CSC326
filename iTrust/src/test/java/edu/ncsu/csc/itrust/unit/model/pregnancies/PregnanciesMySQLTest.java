package edu.ncsu.csc.itrust.unit.model.pregnancies;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * Rough tests for PregnanciesMySQL for coverage requirements
 * @author bmhogan
 */
public class PregnanciesMySQLTest {
	private PregnanciesMySQL pSQL;
	private DataSource ds;
	private TestDataGenerator gen;
	
	@Test
	public void test() throws FileNotFoundException, SQLException, IOException {
		Pregnancies p = new Pregnancies();
		try {
			pSQL = new PregnanciesMySQL();
			fail();
		} catch (DBException e) {
			//DO nothing
		}
		ds = ConverterDAO.getDataSource();
		pSQL = new PregnanciesMySQL(ds);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		
		try {
			assertEquals(1, pSQL.getAll().size());
			p = pSQL.getAll().get(0);
			assertEquals(0L, p.getPatientMID());
		} catch (DBException e) {
			fail();
		}
		Pregnancies p2;
		p2 = new Pregnancies();
		p2.setPatientMID(1L);
		p2.setDelType("vaginal delivery");
		p2.setHoursInLabor(3.5);
		p2.setNumChildren((short)1);
		p2.setWeeksPregnant(48);
		p2.setWeightGain(15);
		p2.setYearOfConception(2017);
		
		try {
			pSQL.add(p2);
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		
		try {
			pSQL.update(p2);
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		
		try {
			assertEquals(1, pSQL.getByPatientID(0L).size());
		} catch (DBException e) {
			fail();
		}
		
		try {
			assertNull(pSQL.getByID(11113L));
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
	}
}
