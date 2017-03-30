package edu.ncsu.csc.itrust.unit.model.ultrasound;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ultrasound.Ultrasound;
import edu.ncsu.csc.itrust.model.ultrasound.UltrasoundValidator;

public class UltrasoundValidatorTest {
	private UltrasoundValidator validator;
	private Ultrasound us;
	
	private long Vmid = 10;
	private double Vcrl = 2.2;
	private double Vbpd = 1.4;
	private double Vhc = 7.4;
	private double Vfl = 0.4;
	private double Vofd = 0.3;
	private double Vac = 2;
	private double Vhl = 3.4;
	private double Vefw = 6.7;
	
	private long INVmid = -1;
	private double INVcrl = -2.2;
	private double INVbpd = -1.4;
	private double INVhc = -7.4;
	private double INVfl = -0.4;
	private double INVofd = -0.3;
	private double INVac = -2;
	private double INVhl = -3.4;
	private double INVefw = -6.7;
	
	private LocalDateTime date;
	private String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	
	private String Vpath = "iTrust/image/ultrasound/honest.jpg";
	private String INVpath1 = "iTrust/image/ultrasound/honest.ppr";
	private String INVpath2 = "iTrust/ultrasound/honest.jpg";

	@Test
	public void testValidate() {
		validator = new UltrasoundValidator();
		
		// First pass us all valid values and  make sure it doesn't throw an exception
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
		} catch (FormValidationException e) {
			fail();
		}
		
		// Now one at a time enter invalid values
		us = new Ultrasound(INVmid, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Patient MID has not been assigned to this Ultrasound"));
		}
		
		us = new Ultrasound(-50, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Patient has an invalid MID"));
		}
		
		us = new Ultrasound(Vmid, date, INVcrl, Vbpd, Vhc, Vfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Crown Rump Length cannot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, INVbpd, Vhc, Vfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Biparietal Diameter cannot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, INVhc, Vfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Head Circumference cannot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, INVfl, Vofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Femur Length cannot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, Vfl, INVofd, Vac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Occipitofrontal Diameter cannot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, INVac, Vhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Abdominal Circumference cannot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, Vac, INVhl, Vefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Humerus Length cannnot be negative"));
		}
		
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, Vac, Vhl, INVefw);
		try {
			validator.validate(us);
			fail();
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Estimated Fetal Weight cannot be negative"));
		}
		
		// Now make sure that the filepath is validated correctly
		us = new Ultrasound(Vmid, date, Vcrl, Vbpd, Vhc, Vfl, Vofd, Vac, Vhl, Vefw);
//		us.setFilePath(Vpath);
//		try {
//			validator.validate(us);
//		} catch (FormValidationException e) {
//			fail();
//		}
		
//		us.setFilePath(INVpath1);
//		try {
//			validator.validate(us);
//			fail();
//		} catch (FormValidationException e) {
//			assertTrue(e.getMessage().contains("Invalid file extension on the ultrasound image"));
//		}
		
//		us.setFilePath(INVpath2);
//		try {
//			validator.validate(us);
//			fail();
//		} catch (FormValidationException e) {
//			assertTrue(e.getMessage().contains("Invalid file location for ultrasound image"));
//		}
//		
	}

}
