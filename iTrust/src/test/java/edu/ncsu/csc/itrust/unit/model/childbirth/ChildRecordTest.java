package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildRecord;

/**
 * Tests the ChildRecord class
 * @author bmhogan
 */
public class ChildRecordTest {
	private boolean sex = true;
	private String delType = "vaginal delivery";
	private LocalDateTime d = LocalDateTime.now();
	private long momID = 1554L;
	private long visitID = 12L;
	

	@Test
	public void testChildRecord() {
		ChildRecord cr = new ChildRecord(sex, delType, d, momID, visitID);
		
		assertTrue(cr.getSex());
		assertEquals(delType, cr.getDeliveryType());
		assertEquals(d, cr.getDateOfBirth());
		assertEquals(momID, cr.getMotherMID().longValue());
		assertEquals(visitID, cr.getVisitID().longValue());
		
		// Change the values and retest
		boolean sex2 = false;
		String delType2 = "none";
		LocalDateTime d2 = LocalDateTime.MIN;
		long momID2 = 12345L;
		long visitID2 = 123L;
		
		cr.setSex(sex2);
		cr.setDeliveryType(delType2);
		cr.setDateOfBirth(d2);
		cr.setMortherMID(momID2);
		cr.setVisitID(visitID2);
		
		assertFalse(cr.getSex());
		assertEquals(delType2, cr.getDeliveryType());
		assertEquals(d2, cr.getDateOfBirth());
		assertNotEquals(d, cr.getDateOfBirth());
		assertEquals(momID2, cr.getMotherMID().longValue());
		assertEquals(visitID2, cr.getVisitID().longValue());
	}

}
