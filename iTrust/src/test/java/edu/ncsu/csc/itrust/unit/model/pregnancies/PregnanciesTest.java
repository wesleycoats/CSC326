package edu.ncsu.csc.itrust.unit.model.pregnancies;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;

public class PregnanciesTest {
	private Pregnancies p;
	
	private static final long MID = 5;
	private static final String delType = "vaginal delivery vacuum assist";
	private static final int year = 2004;
	private static final double hours = 5.4;
	private static final double weight = -3;
	private static final int weeksPregnant = 27;
	private static final short numChildren = 1;
	
	private long ID;
	
	@Test
	public void testPregnancies() {
		p = new Pregnancies();
		assertEquals(-1, p.getPatientMID());
		assertEquals(-1, p.getHoursInLabor(), 0.005);
		assertEquals(-1, p.getYearOfConception());
		assertEquals(-1, p.getNumChildren());
		assertEquals(-1, p.getWeightGain(), 0.005);
		assertNull(p.getDelType());
		
		p = new Pregnancies(MID, delType, year, hours, weight, weeksPregnant, numChildren);
		assertEquals(MID, p.getPatientMID());
		assertEquals(delType, p.getDelType());
		assertEquals(year, p.getYearOfConception());
		assertEquals(hours, p.getHoursInLabor(), 0.005);
		assertEquals(weight, p.getWeightGain(), 0.005);
		assertEquals(weeksPregnant, p.getWeeksPregnant());
		assertEquals(numChildren, p.getNumChildren());
		
		// Test  that the id was correctly set
		ID = MID + (numChildren*31) + (year + 31) + Math.round(hours * 31 * 31);
		assertEquals(ID, p.getPregID());
		
		// Misc tests for coverage requirements
		p = new Pregnancies();
		p.setWeeksPregnant(14);
		assertEquals(14, p.getWeeksPregnant());
		
		p.setPatientMID(1L);
		assertEquals(1, p.getPatientMID());
		
		p.setDelType("Null");
		assertEquals("Null", p.getDelType());
		
		p.setYearOfConception(1998);
		assertEquals(1998, p.getYearOfConception());
		
		p.setHoursInLabor(14.7);
		assertEquals(14.7, p.getHoursInLabor(), 0.005);
		
		p.setWeightGain(31.2);
		assertEquals(31.2, p.getWeightGain(), 0.005);
		
		p.setNumChildren((short) 3);
		assertEquals(3, p.getNumChildren());
	}

}