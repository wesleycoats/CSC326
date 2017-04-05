package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildBirthVisitForm;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

public class ChildBirthVisitFormTest {
	
	TestDataGenerator gen;
	ChildbirthVisit cbv;
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
	private ChildBirthVisitForm form;


	
	@Mock
	private SessionUtils mockSessionUtils;
	@Mock
	private OfficeVisitMySQL mockovMySQL;
	@Mock
	private OfficeVisit mockOV;


	@Before
	public void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.testIcdCode();
		
		mockSessionUtils = Mockito.mock(SessionUtils.class);
		Mockito.doReturn(1L).when(mockSessionUtils).getCurrentOfficeVisitId();
		Mockito.doReturn("1").when(mockSessionUtils).getSessionPID();
		
		mockOV = Mockito.mock(OfficeVisit.class);
		Mockito.doReturn(LocalDateTime.ofInstant(new Date(1491430942334L).toInstant(), ZoneId.systemDefault())).when(mockOV).getDate();
		
		mockovMySQL = Mockito.mock(OfficeVisitMySQL.class);
		Mockito.doReturn(mockOV).when(mockovMySQL).getByID(Mockito.anyLong());
		
		
		
		form = new ChildBirthVisitForm(mockSessionUtils);
		form.setPatientMID(patientMID);
		form.setPreferredDelivery(preferredDelivery);
		form.setScheduled(scheduled);
		form.setVisitID(visitID);
		form.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
		form.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
		form.setNitrousOxideDosage(nitrousOxideDosage);
		form.setPethidineDosage(pethidineDosage);
		form.setPitocinDosage(pitocinDosage);
		form.setRHImmuneGlobulin(rhGlobDosage);
		form.setOvID("1");
		form.setPatientMID(patientMID);


	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChildBirthVisitForm() {
		new ChildBirthVisitForm(mockSessionUtils);
	}

	@Test
	public void testSubmit() {
		form.submit();
	}

	@Test
	public void testGetRHImmuneGlobulin() {
		assertEquals(form.getRHImmuneGlobulin(), rhGlobDosage);
	}

	@Test
	public void testGetEpiduralAnaesthesiaDosage() {
		assertEquals(form.getEpiduralAnaesthesiaDosage(), epiduralAnaesthesiaDosage);
	}

	@Test
	public void testGetMagnesiumSulfateDosage() {
		assertEquals(form.getMagnesiumSulfateDosage(), magnesiumSulfateDosage);
	}

	@Test
	public void testGetNitrousOxideDosage() {
		assertEquals(form.getNitrousOxideDosage(), nitrousOxideDosage);
	}
	
	@Test
	public void testGetPethidineDosage() {
		assertEquals(form.getPethidineDosage(), pethidineDosage);
	}

	@Test
	public void testGetPitocinDosage() {
		assertEquals(form.getPitocinDosage(), pitocinDosage);
	}

	@Test
	public void testGetOvID() {
		assertEquals(form.getOvID(), "1");
	}

	@Test
	public void testGetPatientMID() {
		assertEquals(form.getPatientMID(), patientMID);
	}

	@Test
	public void testGetVisitID() {
		assertEquals(form.getVisitID(), visitID);
	}

	@Test
	public void testGetPreferredDelivery() {
		assertEquals(form.getPreferredDelivery(), preferredDelivery);
	}

	@Test
	public void testGetScheduled() {
		assertEquals(form.getScheduled(), scheduled);
	}
	
	@Test
	public void testLogging(){
		assertEquals(false, form.logCreateChildbirthVisit(mockSessionUtils));
		assertEquals(false, form.logUpdateChildbirthVisit(mockSessionUtils));
		assertEquals(false, form.logAddChildbirthDrugs(mockSessionUtils));
	}

}
