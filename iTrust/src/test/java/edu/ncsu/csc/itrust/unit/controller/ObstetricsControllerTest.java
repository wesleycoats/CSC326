package edu.ncsu.csc.itrust.unit.controller;

import java.time.LocalDateTime;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import edu.ncsu.csc.itrust.controller.ObstetricsController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.TestCase;

public class ObstetricsControllerTest extends TestCase {
	
	@Spy private ObstetricsController oc;
	@Spy private SessionUtils sessionUtils;
	
	@Mock private HttpServletRequest mockHttpServletRequest;
	@Mock private HttpSession mockHttpSession;
	@Mock private SessionUtils mockSessionUtils;

	private DataSource ds;
	private TestDataGenerator gen; 


	@Before
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		mockSessionUtils = Mockito.mock(SessionUtils.class);
		oc = Mockito.spy(new ObstetricsController(ds, mockSessionUtils, TestDAOFactory.getTestInstance()));
		Mockito.doNothing().when(oc).printFacesMessage(Matchers.any(FacesMessage.Severity.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		// remove when these modules are built and can be called
		gen = new TestDataGenerator();
		gen.uc93();

		// Setup test ObstetricsData
		//testOD = new ObstetricsData(DEFAULT_PATIENT_MID, LMP, DATE_CREATED);

		// Mock HttpServletRequest
		mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

		// Mock HttpSession
		mockHttpSession = Mockito.mock(HttpSession.class);
	}

	@Test
	public void testNulls() throws DBException {
		oc = Mockito.spy(new ObstetricsController(ds, SessionUtils.getInstance(), TestDAOFactory.getTestInstance()));
		oc.generateOBList();
		oc.generatePregList();
		Assert.assertEquals(0, oc.getobList().length);
		Assert.assertNull(oc.getpregList());
		Assert.assertFalse(oc.isOBGYN());
		Assert.assertTrue(oc.isObstetricsPatient());
		Assert.assertFalse(oc.logCreateObstetrics());
		Assert.assertFalse(oc.logViewObstetrics());
	}
	
	@Test
	public void testNonNullValues() {
		Mockito.doReturn("101").when(mockSessionUtils).getCurrentPatientMID();
		oc.generateOBList();
		oc.generatePregList();
		Assert.assertEquals(1, oc.getobList().length);
		Assert.assertEquals(1, oc.getpregList().length);
		
		oc.setlmp("2017-01-25");
		oc.createInitialObstetrics();
		oc.generateOBList();
		Assert.assertEquals(2, oc.getobList().length);
		
		Assert.assertFalse(oc.isOBGYN());
		
		Assert.assertTrue(oc.logCreateObstetrics());
		Assert.assertTrue(oc.logViewObstetrics());
	}
	
	@Test
	public void testGettersSetters() {
		ObstetricsData[] odList = new ObstetricsData[0];
		oc.setobList(odList);
		Assert.assertEquals(0, oc.getobList().length);
		
		Pregnancies[] pList = new Pregnancies[0];
		oc.setpregList(pList);
		Assert.assertEquals(0, oc.getpregList().length);
		
		String lmp = "2017-12-25";
		oc.setlmp(lmp);
		Assert.assertEquals(lmp, oc.getlmp());
	
	}
}