package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;

public class ObstetricsVisitTest {
	private ObstetricsVisit ov;

	private static final long ID = 1l;
	private static final long MID = 102l;
	private static final float WEIGHT = 185.23f;
	private static final int SYSTOLIC_BLOOD_PRESSURE = 52;
	private static final int DIASTOLIC_BLOOD_PRESSURE = 52;
	private static final int FETAL_HEART_RATE = 73;
	private static final int PREGNANCIES = 8;
	private static final boolean PLACENTA_OBSERVED = true;
	private static final int WEEKS_PREGNANT = 3;
	
	@Test
	public void testObstetricsData() {
		ov = new ObstetricsVisit();
		ov.setVisitID(ID);
		ov.setPatientMID(MID);
		ov.setWeight(WEIGHT);
		ov.setSystolicBloodPressure(SYSTOLIC_BLOOD_PRESSURE);
		ov.setDiastolicBloodPressure(DIASTOLIC_BLOOD_PRESSURE);
		ov.setFetalHeartRate(FETAL_HEART_RATE);
		ov.setPregnancies(PREGNANCIES);
		ov.setPlacentaObserved(PLACENTA_OBSERVED);
		ov.setWeeksPregnant(WEEKS_PREGNANT);
		
		assertEquals("1", ov.getVisitID().toString());
		assertEquals("102", ov.getPatientMID().toString());
		assertEquals("185.23", ov.getWeight().toString());assertEquals("52", ov.getSystolicBloodPressure().toString());
		assertEquals("52", ov.getDiastolicBloodPressure().toString());
		assertEquals("73", ov.getFetalHeartRate().toString());
		assertEquals("8", ov.getPregnancies().toString());
		assertTrue(ov.getPlacentaObserved());
		assertEquals((Integer) 3, ov.getWeeksPegnant());
		
		try {
			ov = new ObstetricsVisit(ID, MID, WEIGHT, SYSTOLIC_BLOOD_PRESSURE, DIASTOLIC_BLOOD_PRESSURE, FETAL_HEART_RATE, PREGNANCIES, PLACENTA_OBSERVED);
		} catch (NullPointerException e) {
			// Do nothing, we wanted to throw this
		}
	}
}