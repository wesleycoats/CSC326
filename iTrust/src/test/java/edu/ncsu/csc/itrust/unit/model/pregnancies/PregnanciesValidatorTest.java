package edu.ncsu.csc.itrust.unit.model.pregnancies;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.model.pregnancies.PregnanciesValidator;

public class PregnanciesValidatorTest {
	
	private PregnanciesValidator validator;
	private Pregnancies p;
	
	private static final long MID = 5;
	private static final String delType = "vaginal delivery vacuum assist";
	private static final int year = 2004;
	private static final double hours = 5.4;
	private static final double weight = 35.2;
	private static final int weeksPregnant = 27;
	private static final short numChildren = 2;
	
	private static final long INV_MID = -5;
	private static final String INV_delType = "c-section";
	private static final int INV_year = 2034;
	private static final int INV_year2 = 1800;
	private static final double INV_hours = -5.4;
	private static final int INV_weeksPregnant = -2;
	private static final short INV_numChildren = -1;

	@Test
	public void testValidate() {
		validator = new PregnanciesValidator();
		
		// Create a pregnancy object with the valid values and make sure it is correctly validated
		p = new Pregnancies(MID, delType, year, hours, weight, weeksPregnant, numChildren);
		try {
			validator.validate(p);
		} catch (FormValidationException e) {
			fail();
		}
		
		// Now try changing the MID and seeing if it still validates like it should
		p.setPatientMID(11);
		try {
			validator.validate(p);
		} catch (FormValidationException e) {
			fail();
		}
		
		// Now create new pregnancy objects with bad values
		p = new Pregnancies(INV_MID, delType, year, hours, weight, weeksPregnant, numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid patient MID"));
		}
		
		p = new Pregnancies(MID, INV_delType, year, hours, weight, weeksPregnant, numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid delivery type"));
		}
		
		p = new Pregnancies(MID, delType, INV_year, hours, weight, weeksPregnant, numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Year of conception is invalid. Cannot be in the future or more than 150 years in the past"));
		}
		
		p = new Pregnancies(MID, delType, INV_year2, hours, weight, weeksPregnant, numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Year of conception is invalid. Cannot be in the future or more than 150 years in the past"));
		}
		
		p = new Pregnancies(MID, delType, year, INV_hours, weight, weeksPregnant, numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Hours in labor cannot be negative"));
		}
		
		p = new Pregnancies(MID, delType, year, hours, weight, INV_weeksPregnant, numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Number of weeks pregnant cannot be negative"));
		}
		
		p = new Pregnancies(MID, delType, year, hours, weight, weeksPregnant, INV_numChildren);
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Number of children cannot be negative"));
		}
		
		// Now, create a blank Pregnancy object and make sure that validator throws the correct message
		p = new Pregnancies();
		try {
			validator.validate(p);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Cannot validate Pregnancy: no patient MID"));
		}

	}

}
