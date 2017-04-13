package edu.ncsu.csc.itrust.unit.model.hospital;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.hospital.Hospital;

public class HospitalTest {
	private Hospital h;
	@Test
	public void test() {
		// Try all of the constructors
		h = new Hospital();
		assertEquals("", h.getHospitalID());
		assertEquals("", h.getHospitalName());
		assertEquals("", h.getHospitalAddress());
		assertEquals("", h.getHospitalCity());
		assertEquals("", h.getHospitalState());
		assertEquals("", h.getHospitalZip());
		
		h = new Hospital("id");
		assertEquals("id", h.getHospitalID());
		assertEquals("", h.getHospitalName());
		assertEquals("", h.getHospitalAddress());
		assertEquals("", h.getHospitalCity());
		assertEquals("", h.getHospitalState());
		assertEquals("", h.getHospitalZip());
		
		h = new Hospital("id2", "name");
		assertEquals("id2", h.getHospitalID());
		assertEquals("name", h.getHospitalName());
		assertEquals("", h.getHospitalAddress());
		assertEquals("", h.getHospitalCity());
		assertEquals("", h.getHospitalState());
		assertEquals("", h.getHospitalZip());
		
		h = new Hospital("id3", "name2", "addr", "city", "state", "zip");
		assertEquals("id3", h.getHospitalID());
		assertEquals("name2", h.getHospitalName());
		assertEquals("addr", h.getHospitalAddress());
		assertEquals("city", h.getHospitalCity());
		assertEquals("state", h.getHospitalState());
		assertEquals("zip", h.getHospitalZip());
	}
}
