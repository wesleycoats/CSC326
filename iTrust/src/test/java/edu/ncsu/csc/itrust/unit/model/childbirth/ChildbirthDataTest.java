package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildRecord;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthData;
import edu.ncsu.csc.itrust.model.childbirthVisit.DrugRecord;

/**
 * Tests the ChildbirthData class
 * @author bmhogan
 */
public class ChildbirthDataTest {
	private Long patientMID = 11L;
	private Long visitID = 37L;
	private String prefDel = "vaginal delivery";
	private boolean scheduled = true;
	private List<DrugRecord> drList = new ArrayList<DrugRecord>();
	private List<ChildRecord> crList = new ArrayList<ChildRecord>();
	
	private Long patientMID2 = 1L;
	private Long visitID2 = 95L;
	private String prefDel2 = "vaginal delivery vacuum assist";
	private boolean scheduled2 = false;
	private List<DrugRecord> drList2 = new ArrayList<DrugRecord>();
	private List<ChildRecord> crList2 = new ArrayList<ChildRecord>();

	@Test
	public void testChildbirth() {
		// First populate the Lists
		DrugRecord d1 = new DrugRecord(1L, "", 13);
		DrugRecord d2 = new DrugRecord(10L, "", 47);
		DrugRecord d3 = new DrugRecord(15L, "hello", 31);
		drList.add(d1);
		drList2.add(d2);
		drList2.add(d3);
		
		ChildRecord c1 = new ChildRecord(true, "", LocalDateTime.now(), 1l, 1l);
		ChildRecord c2 = new ChildRecord(true, "1", LocalDateTime.now(), 1l, 1l);
		ChildRecord c3 = new ChildRecord(false, "13", LocalDateTime.now(), 1l, 1l);
		crList.add(c1);
		crList.add(c2);
		crList2.add(c3);
		
		ChildbirthData cbd = new ChildbirthData(patientMID, visitID, prefDel, scheduled, drList, crList, drList);
		
		assertEquals(patientMID, cbd.getPatientMID());
		assertEquals(visitID, cbd.getVisitID());
		assertEquals(prefDel, cbd.getPreferredDelivery());
		assertEquals(scheduled, cbd.getScheduled());
		assertEquals(drList, cbd.getAdministeredDrugs());
		assertEquals(crList, cbd.getChildrenBorn());
		
		
		
		cbd.setPatientMID(patientMID2);
		cbd.setVisitID(visitID2);
		cbd.setPreferredDelivery(prefDel2);
		cbd.setScheduled(scheduled2);
		cbd.setAdministeredDrugs(drList2);
		cbd.setChildrenBorn(crList2);
		
		assertEquals(patientMID2, cbd.getPatientMID());
		assertEquals(visitID2, cbd.getVisitID());
		assertEquals(prefDel2, cbd.getPreferredDelivery());
		assertEquals(scheduled2, cbd.getScheduled());
		assertEquals(drList2, cbd.getAdministeredDrugs());
		assertEquals(crList2, cbd.getChildrenBorn());
	}

}
