package edu.ncsu.csc.itrust.unit.model.ultrasound;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.ultrasound.Ultrasound;
import junit.framework.TestCase;

/**
 * Tests the ultrasound class
 * @author bmhogan
 */
public class UltrasoundTest extends TestCase {
	private Ultrasound us;
	private long mid = 10;
	private double crl = 2.2;
	private double bpd = 1.4;
	private double hc = 7.4;
	private double fl = 0.4;
	private double ofd = 0.3;
	private double ac = 2;
	private double hl = 3.4;
	private double efw = 6.7;
	
	private LocalDateTime date;
	private String dateStr;
	private String path = "iTrust/image/ultrasound/honest.jpg";
	
	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		this.dateStr = "24/03/2017";
		
	}

	@Test
	public void testUltrasound() {
		us = new Ultrasound();
		double neg = Double.MIN_NORMAL;
		assertEquals(-1, us.getMID());
		assertEquals(neg, us.getCRL(), 0.005);
		assertEquals(neg, us.getBPD(), 0.005);
		assertEquals(neg, us.getHC(), 0.005);
		assertEquals(neg, us.getFL(), 0.005);
		assertEquals(neg, us.getOFD(), 0.005);
		assertEquals(neg, us.getAC(), 0.005);
		assertEquals(neg, us.getHL(), 0.005);
		assertEquals(neg, us.getEFW(), 0.005);
		assertNull(us.getDate());
		assertEquals("", us.getFilePath());
		

		date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		dateStr = date.format(formatter);
		us = new Ultrasound(mid, date, crl, bpd, hc, fl, ofd, ac, hl, efw);
		
		assertEquals(mid, us.getMID());
		assertEquals(crl, us.getCRL(), 0.005);
		assertEquals(bpd, us.getBPD(), 0.005);
		assertEquals(hc, us.getHC(), 0.005);
		assertEquals(fl, us.getFL(), 0.005);
		assertEquals(ofd, us.getOFD(), 0.005);
		assertEquals(ac, us.getAC(), 0.005);
		assertEquals(hl, us.getHL(), 0.005);
		assertEquals(efw, us.getEFW(), 0.005);
		
		assertEquals(date, us.getDate());
		assertEquals(dateStr, us.getDateString());
		
		assertEquals("", us.getFilePath());
		us.setFilePath(path);
		assertEquals(path, us.getFilePath());
	}
	
	@Test
	public void testDate() {
		us = new Ultrasound();
		
		assertFalse(us.setDateString(""));
		assertFalse(us.setDateString("24"));
		assertFalse(us.setDateString("24/3"));
		assertFalse(us.setDateString("243"));
		assertFalse(us.setDateString("24/3/"));
		assertFalse(us.setDateString("243/2017"));
		
		assertTrue(us.setDateString(dateStr));
		assertEquals(dateStr, us.getDateString());
		
	}
}
