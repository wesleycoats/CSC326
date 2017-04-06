package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.childbirthVisit.ChildRecordForm;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

/**
 * Tests the ChildRecordForm class
 * @author rvisador
 */
public class ChildRecordFormTest {
	private ChildRecordForm crf;
	private SessionUtils sesh;
	private static final String DATE = "2017-04-04";
	private static final String TIME = "12:34 AM";
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(2017, 4, 4, 00, 34);
	
	@Before
	public void setUp() throws Exception {
		DAOFactory dao = TestDAOFactory.getTestInstance();
		sesh = Mockito.mock(SessionUtils.class);
		DataSource ds = Mockito.mock(DataSource.class);
		
		sesh.setSessionVariable("pid", 1L);
		sesh.setSessionVariable("officeVisit", 1l);
		crf = new ChildRecordForm(dao, sesh, ds);
	}
	
	@Test
	public void testGettersSetters() {
		crf.setDateOfBirth(DATE);
		assertEquals(DATE, crf.getDateOfBirth());
		crf.setTimeOfBirth(TIME);
		assertEquals(TIME, crf.getTimeOfBirth());
		crf.setDeliveryType("Delivered");
		assertEquals("Delivered", crf.getDeliveryType());
		crf.setEmail("butts@ncus.edu");
		assertEquals("butts@ncus.edu", crf.getEmail());
		crf.setFirstName("John");
		assertEquals("John", crf.getFirstName());
		crf.setLastName("Lee");
		assertEquals("Lee", crf.getLastName());
		crf.setSex(true);
		assertTrue(crf.getSex());
		crf.setMessege("Everything failed horribly");
		assertEquals("Everything failed horribly" , crf.getMessege());
		crf.setDateTimeOfBirth();
		Assert.assertEquals(DATE_TIME, crf.getDateTimeOfBirth());
		crf.setMotherMID(42l);
		assertEquals("42", crf.getMotherMID().toString());
		crf.setOfficeID(99l);
		assertEquals("99", crf.getOfficeID().toString());
		try {
			crf.submit();
		} catch(NullPointerException e) {
			assertEquals("Everything failed horribly", crf.getMessege());
		}
		
		crf.setDateOfBirth("poop");
		crf.setDateTimeOfBirth();
		assertNull(crf.getDateTimeOfBirth());
		crf.submit();
		assertEquals("Input not Valid.", crf.getMessege());
	}
	
	@Test
	public void testLogging(){
		assertEquals(true, crf.logBabyBorn(sesh));
		assertEquals(true, crf.logCreateBabyRecord(sesh, 12L));
		
	}
}