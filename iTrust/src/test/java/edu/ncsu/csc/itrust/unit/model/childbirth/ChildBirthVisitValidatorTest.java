package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisit;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitValidator;

public class ChildBirthVisitValidatorTest {
	private Long visitID = 1L;
	private Long patientMID = 2L;
	private String preferredDelivery = "3";
	private Boolean scheduled = true;
	private Integer pitocinDosage = 5;
	private Integer nitrousOxideDosage = 6;
	private Integer pethidineDosage = 7;
	private Integer epiduralAnaesthesiaDosage = 8;
	private Integer magnesiumSulfateDosage = 9;
	private Integer rhGlobulinDosage = 10;

	@Test
	public void test() {
		ChildbirthVisitValidator validator = new ChildbirthVisitValidator();
		
		ChildbirthVisit cb = new ChildbirthVisit();
		cb.setPatientMID(patientMID);
		cb.setVisitID(visitID);
		cb.setPreferredDelivery(preferredDelivery);
		cb.setScheduled(scheduled);
		cb.setPitocinDosage(pitocinDosage);
		cb.setNitrousOxideDosage(nitrousOxideDosage);
		cb.setPethidineDosage(pethidineDosage);
		cb.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
		cb.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
		cb.setRhGlobulinDosage(rhGlobulinDosage);
		
		// Test each scenario
		cb.setPatientMID(null);
		try {
			validator.validate(cb);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Cannot add childbirth visit information: invalid patient MID"));
		}
		
		cb.setPatientMID(9L);
		try {
			validator.validate(cb);;
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Patient MID: 1-10 digit number not beginning with 9"));
		}
		
		cb.setPatientMID(1L);
		cb.setVisitID(null);
		try {
			validator.validate(cb);;
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Visit ID: Null Visit ID"));
		}
		
		cb.setVisitID(-4L);
		try {
			validator.validate(cb);;
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Visit ID: Negative Visit ID"));
		}
		
		cb.setVisitID(14L);
		cb.setPreferredDelivery(null);
		try {
			validator.validate(cb);;
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Preferred Delivery: Preferred Delivery is Null"));
		}
		
		cb.setPreferredDelivery("vaginal delivery vacuum assist");
		cb.setScheduled(null);
		try {
			validator.validate(cb);;
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Scheduled: Is Null"));
		}
		
		cb.setScheduled(true);
		cb.setPitocinDosage(-1);
		try {
			validator.validate(cb);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Dosage cannot be negative"));
		}
		
		cb.setPitocinDosage(11);
		cb.setNitrousOxideDosage(-4);
		try {
			validator.validate(cb);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Dosage cannot be negative"));
		}
		
		cb.setNitrousOxideDosage(12);
		cb.setPethidineDosage(-3);
		try {
			validator.validate(cb);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Dosage cannot be negative"));
		}
		
		cb.setPethidineDosage(143);
		cb.setEpiduralAnaesthesiaDosage(-54);
		try {
			validator.validate(cb);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Dosage cannot be negative"));
		}
		
		cb.setEpiduralAnaesthesiaDosage(54);
		cb.setMagnesiumSulfateDosage(-3);
		try {
			validator.validate(cb);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Dosage cannot be negative"));
		}
		
		cb.setMagnesiumSulfateDosage(3);
		try {
			validator.validate(cb);
		} catch (FormValidationException e) {
			fail(); //all input should now be correct
		}
	}

}
