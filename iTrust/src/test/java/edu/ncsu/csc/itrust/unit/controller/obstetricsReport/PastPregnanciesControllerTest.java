package edu.ncsu.csc.itrust.unit.controller.obstetricsReport;

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

import edu.ncsu.csc.itrust.controller.obstetricsReport.PastPregnanciesController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.pregnancies.Pregnancies;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;
import junit.framework.TestCase;

public class PastPregnanciesControllerTest extends TestCase {
	
	@Spy private PastPregnanciesController oc;
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
		oc = Mockito.spy(new PastPregnanciesController(ds, mockSessionUtils, TestDAOFactory.getTestInstance()));
		Mockito.doNothing().when(oc).printFacesMessage(Matchers.any(FacesMessage.Severity.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		// remove when these modules are built and can be called
		gen = new TestDataGenerator();
		gen.clearAllTables();

		// Setup test ObstetricsData
		//testOD = new ObstetricsData(DEFAULT_PATIENT_MID, LMP, DATE_CREATED);

		// Mock HttpServletRequest
		mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

		// Mock HttpSession
		mockHttpSession = Mockito.mock(HttpSession.class);
	}

	@Test
	public void testNulls() throws DBException {
		oc = Mockito.spy(new PastPregnanciesController(ds, SessionUtils.getInstance(), TestDAOFactory.getTestInstance()));
		oc.generatePregList();
		Assert.assertNull(oc.getpregList());
	}
	
	@Test
	public void testNonNullValues() {
		Mockito.doReturn("101").when(mockSessionUtils).getCurrentPatientMID();
		oc.generatePregList();
	}
	
	@Test
	public void testGettersSetters() {
		Pregnancies[] pList = new Pregnancies[0];
		oc.setpregList(pList);
		Assert.assertEquals(0, oc.getpregList().length);
	
	}
}