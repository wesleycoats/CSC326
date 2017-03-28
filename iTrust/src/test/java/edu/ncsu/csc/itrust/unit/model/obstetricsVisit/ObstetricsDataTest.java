package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;

public class ObstetricsDataTest {
	private ObstetricsData od;
	
	private static final long MID = 25l;
	private static final LocalDateTime LMP = LocalDateTime.of(2017, 02, 25, 0, 0);
	private static final LocalDateTime DATE_CREATED = LocalDateTime.of(2017, 03, 25, 0, 0);
	
	@Test
	public void testObstetricsData() {
		od = new ObstetricsData(MID, LMP, DATE_CREATED);
		assertEquals("25", od.getPatientMID().toString());
		assertEquals("2017-02-25T00:00", od.getLmp().toString());
		assertEquals("2017-03-25T00:00", od.getDateCreated().toString());
	}
	
	@Test
	public void testGetEdd() {
		od = new ObstetricsData();
		od.setLmp(LMP);
		assertEquals("2017-12-02T00:00", od.getEdd().toString());
	}
}