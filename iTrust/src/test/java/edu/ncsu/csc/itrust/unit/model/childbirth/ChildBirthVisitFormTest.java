package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildBirthVisitForm;

public class ChildBirthVisitFormTest {
	private Long patientMID = 1L;
	private Long visitID = 2L;
	private Long preferredDelivery = 3L;
	private Boolean scheduled = true;;

	@Test
	public void test() {
		ChildBirthVisitForm cbvf = new ChildBirthVisitForm();
		
		cbvf.setPatientMID(patientMID);
		cbvf.setVisitID(visitID);
		cbvf.setPreferredDelivery(preferredDelivery);
		cbvf.setScheduled(scheduled);
		
		assertEquals(patientMID, cbvf.getPatientMID());
		assertEquals(visitID, cbvf.getVisitID());
		assertEquals(preferredDelivery, cbvf.getPreferredDelivery());
		assertTrue(cbvf.getScheduled());
		
	}

}
