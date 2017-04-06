package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisit;

public class ChildbirthVisitTest {
	private Long visitID = 1L;
	private Long patientMID = 2L;
	private String preferredDelivery = "3";
	private Boolean scheduled = true;
	private Integer pitocinDosage = 5;
	private Integer nitrousOxideDosage = 6;
	private Integer pethidineDosage = 7;
	private Integer epiduralAnaesthesiaDosage = 8;
	private Integer magnesiumSulfateDosage = 9;
	private Integer rhGlobDosage = 10;

	@Test
	public void testChildbirthVisit() {
		ChildbirthVisit cbv = new ChildbirthVisit();
		
		cbv.setPatientMID(patientMID);
		cbv.setVisitID(visitID);
		cbv.setPreferredDelivery(preferredDelivery);
		cbv.setScheduled(scheduled);
		cbv.setPitocinDosage(pitocinDosage);
		cbv.setNitrousOxideDosage(nitrousOxideDosage);
		cbv.setPethidineDosage(pethidineDosage);
		cbv.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
		cbv.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
		cbv.setRhGlobulinDosage(rhGlobDosage);
		
		assertEquals(visitID, cbv.getVisitID());
		assertEquals(patientMID, cbv.getPatientMID());
		assertEquals(preferredDelivery, cbv.getPreferredDelivery());
		assertEquals(scheduled, cbv.getScheduled());
		assertEquals(pitocinDosage, cbv.getPitocinDosage());
		assertEquals(nitrousOxideDosage, cbv.getNitrousOxideDosage());
		assertEquals(pethidineDosage, cbv.getPethidineDosage());
		assertEquals(epiduralAnaesthesiaDosage, cbv.getEpiduralAnaesthesiaDosage());
		assertEquals(magnesiumSulfateDosage, cbv.getMagnesiumSulfateDosage());
		assertEquals(rhGlobDosage, cbv.getRhGlobulinDosage());
		
		cbv = new ChildbirthVisit(visitID, patientMID, "now", false);
		assertEquals(visitID, cbv.getVisitID());
		assertEquals(patientMID, cbv.getPatientMID());
		assertFalse(cbv.getScheduled());
		assertEquals("now", cbv.getPreferredDelivery());
	}

}
