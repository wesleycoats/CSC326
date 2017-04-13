package edu.ncsu.itrust.unit.model.pregnancyConditions;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.pregnancyConditions.PregnancyConditionsValidator;

public class PregnancyConditionsValidatorTest {
	private PregnancyConditionsValidator validator;

	@Test
	public void test() {
		validator = new PregnancyConditionsValidator();
		try {
			validator.validate(0, "E032");
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid Patient MID"));
		}
		
		try {
			validator.validate(1, "E032");
		} catch (FormValidationException e) {
			fail();
		}
		
		try {
			validator.validate(1, "08109-6");
		} catch (FormValidationException e) {
			fail();
		}
		
		try {
			validator.validate(1, "0338-1021-41");
		} catch (FormValidationException e) {
			fail();
		}
		
		try {
			validator.validate(1, "NaN");
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid ND/ICD-10 code"));
		}
	}

}
