package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import javax.servlet.http.Part;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsOfficeVisitForm;

public class ObstetricsOfficeVisitFormTest {
	private ObstetricsOfficeVisitForm ov;

	private static final long ID = 1l;
	private static final long MID = 102l;
	private static final float WEIGHT = 185.23f;
	private static final int SYSTOLIC_BLOOD_PRESSURE = 52;
	private static final int DIASTOLIC_BLOOD_PRESSURE = 52;
	private static final int FETAL_HEART_RATE = 73;
	private static final int PREGNANCIES = 8;
	private static final LocalDateTime DATE1 = LocalDateTime.of(2017, 01, 01, 0, 0);
	private static final LocalDateTime DATE2 = LocalDateTime.of(2017, 02, 02, 0, 0);
	private static final LocalDateTime DATE3 = LocalDateTime.of(2017, 03, 03, 0, 0);
	private static final long TYPE = 7l;
	private static final long WEEKS_PREGGO = 20;
	
	@Test
	public void testObstetricsData() {
		ov = new ObstetricsOfficeVisitForm(99l);
		ov.setVisitID(ID);
		ov.setPatientMID(MID);
		ov.setWeight(WEIGHT);
		ov.setSystolicBloodPressure(SYSTOLIC_BLOOD_PRESSURE);
		ov.setDiastolicBloodPressure(DIASTOLIC_BLOOD_PRESSURE);
		ov.setFetalHR(FETAL_HEART_RATE);
		ov.setPregnancies(PREGNANCIES);
		ov.setLastMenstrualPeriod(DATE1);
		ov.setDate(DATE2);
		ov.setExpectedDeliveryDate(DATE3);
		ov.setApptTypeID(TYPE);
		ov.setWeeksPregnant(WEEKS_PREGGO);
		
		Assert.assertEquals(DATE1, ov.getLastMenstrualPeriod());
		Assert.assertEquals(DATE1.toLocalDate().toString(), ov.getLastMenstrualPeriodString());
		Assert.assertEquals(DATE2.toLocalDate(), ov.getDateString());
		Assert.assertEquals(DATE2, ov.getDate());
		Assert.assertEquals(DATE3.toLocalDate().toString(), ov.getExpectedDeliveryDate().toString());
		assertEquals("1", ov.getVisitID().toString());
		assertEquals("102", ov.getPatientMID().toString());
		assertEquals("185.23", ov.getWeight().toString());
		assertEquals("52", ov.getSystolicBloodPressure().toString());
		assertEquals("52", ov.getDiastolicBloodPressure().toString());
		assertEquals("73", ov.getFetalHR().toString());
		assertEquals("8", ov.getPregnancies().toString());
		assertEquals("7", ov.getApptTypeID().toString());
		assertEquals("20", ov.getWeeksPregnant().toString());
	}
	
	@Test
	public void testObstetricsOfficeVisitForm() {
		try {
			ov = new ObstetricsOfficeVisitForm();
			fail(); //should get a NullPointerException
		} catch (NullPointerException e) {
			// do nothing, we expected this exception
		}
		
		ov = new ObstetricsOfficeVisitForm(99l);
		
		// Test all of the random lines that were missed in the class
		ov.setLastMenstrualPeriod(null);
		assertEquals("", ov.getLastMenstrualPeriodString());
		
		ov.setcrownRumpLen(0.05);
		assertEquals(0.05, ov.getcrownRumpLen(), 0.005);
		
		ov.setbiparietalDia(0.0432);
		assertEquals(0.0432, ov.getbiparietalDia(), 0.005);
		
		ov.setheadCirc(1);
		assertEquals(1, ov.getheadCirc(), 0.005);
		
		ov.setfemurLen(12);
		assertEquals(12, ov.getfemurLen(), 0.005);
		
		ov.setoccipotoFrontalDia(32);
		assertEquals(32, ov.getoccipotoFrontalDia(), 0.005);
		
		ov.setabdomincalCirc(15);
		assertEquals(15, ov.getabdomincalCirc(), 0.005);
		
		ov.sethumerusLen(9);
		assertEquals(9, ov.gethumerusLen(), 0.005);
		
		ov.setestimatedFetalWeight(8.5);
		assertEquals(8.5, ov.getestimatedFetalWeight(), 0.005);
		
		// Pass in parameters
		ov.setVisitID(ID);
		ov.setPatientMID(MID);
		ov.setWeight(WEIGHT);
		ov.setSystolicBloodPressure(SYSTOLIC_BLOOD_PRESSURE);
		ov.setDiastolicBloodPressure(DIASTOLIC_BLOOD_PRESSURE);
		ov.setFetalHR(FETAL_HEART_RATE);
		ov.setPregnancies(PREGNANCIES);
		ov.setLastMenstrualPeriod(DATE1);
		ov.setDate(DATE2);
		ov.setExpectedDeliveryDate(DATE3);
		ov.setApptTypeID(TYPE);
		ov.setWeeksPregnant(WEEKS_PREGGO);
		
		try {
			ov.submit();
			fail();
		} catch (NullPointerException e) {
			//do nothing
		}
	}
}