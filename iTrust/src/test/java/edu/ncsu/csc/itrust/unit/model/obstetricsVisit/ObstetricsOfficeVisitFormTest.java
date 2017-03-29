package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsOfficeVisitForm;

public class ObstetricsOfficeVisitFormTest {
	private ObstetricsOfficeVisitForm ov;

	private static final long ID = 1l;
	private static final long MID = 102l;
	private static final float WEIGHT = 185.23f;
	private static final String BLOOD_PRESSURE = "52";
	private static final long FETAL_HEART_RATE = 73l;
	private static final String PREGNANCIES = "8";
	private static final String PATH = "This is totally a path.";
	private static final LocalDateTime DATE1 = LocalDateTime.of(2017, 01, 01, 0, 0);
	private static final LocalDateTime DATE2 = LocalDateTime.of(2017, 02, 02, 0, 0);
	private static final LocalDateTime DATE3 = LocalDateTime.of(2017, 03, 03, 0, 0);
	private static final long TYPE = 7l;
	
	@Test
	public void testObstetricsData() {
		ov = new ObstetricsOfficeVisitForm();
		ov.setVisitID(ID);
		ov.setPatientMID(MID);
		ov.setWeight(WEIGHT);
		ov.setBloodPressure(BLOOD_PRESSURE);
		ov.setFetalHR(FETAL_HEART_RATE);
		ov.setMultiple(PREGNANCIES);
		ov.setFilePath(PATH);
		ov.setLastMenstrualPeriod(DATE1);
		ov.setDate(DATE2);
		ov.setExpectedDeliveryDate(DATE3);
		ov.setApptTypeID(TYPE);
		
		Assert.assertEquals(DATE1, ov.getLastMenstrualPeriod());
		Assert.assertEquals(DATE2, ov.getDate());
		Assert.assertEquals(DATE3, ov.getExpectedDeliveryDate());
		assertEquals("1", ov.getVisitID().toString());
		assertEquals("102", ov.getPatientMID().toString());
		assertEquals("185.23", ov.getWeight().toString());
		assertEquals("52", ov.getBloodPressure());
		assertEquals(73, ov.getFetalHR());
		assertEquals("8", ov.getMultiple());
		assertEquals("This is totally a path.", ov.getFilePath());
		assertEquals("7", ov.getApptTypeID().toString());
	}
}