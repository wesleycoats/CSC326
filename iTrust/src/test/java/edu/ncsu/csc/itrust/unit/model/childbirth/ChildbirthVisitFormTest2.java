package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildBirthVisitForm;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * Tests the ChildRecordForm class
 * @author rvisador
 */
public class ChildbirthVisitFormTest2 {
	private ChildBirthVisitForm cbvf;

	@Test
	public void testGettersSetters() {
			cbvf = new ChildBirthVisitForm(Mockito.mock(SessionUtils.class));
			Assert.assertNotNull(cbvf);
			
			cbvf.setEpiduralAnaesthesiaDosage(5);
			assertEquals(5, cbvf.getEpiduralAnaesthesiaDosage().intValue());
			cbvf.setMagnesiumSulfateDosage(10);
			assertEquals(10, cbvf.getMagnesiumSulfateDosage().intValue());
			cbvf.setNitrousOxideDosage(15);
			assertEquals(15, cbvf.getNitrousOxideDosage().intValue());
			cbvf.setOvID("hi");
			assertEquals("hi", cbvf.getOvID());
			cbvf.setPatientMID(20l);
			assertEquals(20, cbvf.getPatientMID().longValue());
			cbvf.setPethidineDosage(25);
			assertEquals(25, cbvf.getPethidineDosage().intValue());
			cbvf.setPitocinDosage(30);
			assertEquals(30, cbvf.getPitocinDosage().intValue());
			cbvf.setPreferredDelivery("Out the Butt");
			assertEquals("Out the Butt", cbvf.getPreferredDelivery());
			cbvf.setScheduled(false);
			assertFalse(cbvf.getScheduled());
			cbvf.setVisitID(35l);
			assertEquals(35, cbvf.getVisitID().longValue());
	}
}