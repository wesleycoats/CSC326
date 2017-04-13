package edu.ncsu.csc.itrust.unit.flags;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.controller.flags.Flag;

public class FlagTest {

	@Test
	public void test() {
		Flag f = new Flag(1L, 1L, 1L, "Test Flag");
		assertEquals(1L, f.getFid());
		assertEquals(1L, f.getMid());
		assertEquals(1L, f.getPregID());
		assertEquals("Test Flag", f.getFlagType());
		
		f.setFid(27L);
		assertEquals(27L, f.getFid());
		
		f.setFlagType("New");
		assertEquals("New", f.getFlagType());
		
		f.setMid(12L);
		assertEquals(12L, f.getMid());
		
		f.setPregID(2L);
		assertEquals(2L, f.getPregID());
	}

}
