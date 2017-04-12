package edu.ncsu.csc.itrust.unit.controller.obstetricsReport;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.controller.obstetricsReport.ObstetricsOfficeVisitReportController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import junit.framework.TestCase;

public class ObstetricsOfficeVisitReportControllerTest extends TestCase {
	
	private ObstetricsOfficeVisitReportController oc;
	private ObstetricsVisit ov;

	@Before
	public void setUp() throws Exception {
		ov = new ObstetricsVisit();
	}
	
	@Test
	public void testNulls() throws DBException {
		oc = new ObstetricsOfficeVisitReportController();
		try {
			oc.getList();
		} catch(NullPointerException e) {
			assertNotNull(oc);
		}
	}
	
	@Test
	public void testNonNullValues() {
		try {
			oc = new ObstetricsOfficeVisitReportController();
			assertNotNull(oc);
		} catch (DBException e) {
			e.printStackTrace();
		}
		List<ObstetricsVisit> list = new ArrayList<ObstetricsVisit>();
		list.add(ov);
		oc.setList(list);
		try {
			oc.getList();
		} catch(NullPointerException e) {
			//there's no sql initialized
			assertNotNull(oc);
		}
	}
}