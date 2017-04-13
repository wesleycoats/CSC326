package edu.ncsu.csc.itrust.unit.flags;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.controller.flags.Flag;
import edu.ncsu.csc.itrust.controller.flags.FlagValidator;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class FlagValidatorTest {

	@Test
	public void test() {
		FlagValidator validator = new FlagValidator();
		
		Flag f = new Flag(1L, -1L, 1L, "High Blood Pressure");
		try {
			validator.validate(f);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Cannot validate Flag: no patient MID"));
		}
		
		f = new Flag(1L, 0L, 1L, "High Blood Pressure");
		try {
			validator.validate(f);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid patient MID"));
		}
		
		f = new Flag(-1L, 1L, 1L, "High Blood Pressure");
		try {
			validator.validate(f);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Cannot validate Flag: no FID"));
		}
		
		f = new Flag(0L, 1L, 1L, "High Blood Pressure");
		try {
			validator.validate(f);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid patient FID"));
		}
		
		f = new Flag(1L, 1L, 1L, "Bad Flag");
		try {
			validator.validate(f);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Invalid delivery type"));
		}
		
		f = new Flag(1L, 1L, 1L, "Pregnancy relevant pre-existing conditions");
		try {
			validator.validate(f);
		} catch (FormValidationException e) {
			fail();
		}
		
	}

}
