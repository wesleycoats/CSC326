package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;

public class ObstetricsVisitTest extends TestCase {
	private ObstetricsVisit test;

	@Override
	protected void setUp() throws Exception {
		test = new ObstetricsVisit();
		
	}
	
	@Test
	public void testVisitID() {
		long fakeID = 123524839l;
		test.setVisitID(fakeID);
		String input = Long.toString(fakeID);
		Assert.assertEquals(input , Long.toString(test.getVisitID()));		
	}
	
	@Test
	public void testPatientMID() {
		long fakeID = 999992l;
		test.setPatientMID(fakeID);
		String input = Long.toString(fakeID);
		Assert.assertEquals(input , Long.toString(test.getPatientMID()));		
	}

	@Test
	public void testWeeksPregnant() {
		int weeks = 23;
		test.setWeeksPregnant(weeks);
		Assert.assertEquals(weeks, test.getWeeksPegnant());		
	}

	@Test
	public void testLMP() {
		LocalDateTime randDate = LocalDateTime.of(2017, 03, 20, 16, 41);
		test.setLMP(randDate);
		Assert.assertEquals(randDate, test.getLMP());		
	}
	

	@Test
	public void testEDD() {
		LocalDateTime randDate = LocalDateTime.of(2018, 12, 25, 2, 40);
		test.setEDD(randDate);
		Assert.assertEquals(randDate, test.getEDD());	
	}
}
