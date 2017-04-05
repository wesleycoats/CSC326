package edu.ncsu.csc.itrust.unit.model.apptType;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.apptType.ApptType;

/**
 * Tests ApptType for more test coverage.
 * @author bmhogan
 */
public class ApptTypeTest {
	private long ID = 15L;
	private String name = "Test Appt";
	private int duration = 45;
	private int price = 45000; 

	@Test
	public void test() {
		// Just run through every line in ApptType
		ApptType at = new ApptType();
		assertNull(at.getName());
		assertEquals(0, at.getDuration());
		
		at = new ApptType(name, duration);
		assertEquals(name, at.getName());
		assertEquals(45, at.getDuration());
		
		at.setID(ID);
		assertEquals(ID, at.getID());
		
		at.setPrice(price);
		assertEquals(price, at.getPrice());
		
		at.setName("NewName");
		assertEquals("NewName", at.getName());
		
		at.setDuration(15);
		assertEquals(15, at.getDuration());
	}

}
