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
	

	@Test
	public void testChildRecord() {
		ChildRecord cr = new ChildRecord(sex, delType, d, 1l, 1l);
		
		assertTrue(cr.getSex());
		assertEquals(delType, cr.getDeliveryType());
		assertEquals(d, cr.getDateOfBirth());
		
		// Change the values and retest
		boolean sex2 = false;
		String delType2 = "none";
		LocalDateTime d2 = LocalDateTime.MIN;
		
		cr.setSex(sex2);
		cr.setDeliveryType(delType2);
		cr.setDateOfBirth(d2);
		
		assertFalse(cr.getSex());
		assertEquals(delType2, cr.getDeliveryType());
		assertEquals(d2, cr.getDateOfBirth());
		assertNotEquals(d, cr.getDateOfBirth());
		assertEquals("1", cr.getMotherMID().toString());
		assertEquals("1", cr.getVisitID().toString());
	}

}