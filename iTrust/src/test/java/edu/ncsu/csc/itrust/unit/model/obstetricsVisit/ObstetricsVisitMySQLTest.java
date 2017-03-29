package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;

public class ObstetricsVisitMySQLTest extends TestCase {
	private ObstetricsVisit ov;

	private static final long ID = 3l;
	private static final long MID = 0l;
	private static final float WEIGHT = 185.23f;
	private static final int BLOOD_PRESSURE = 52;
	private static final int FETAL_HEART_RATE = 73;
	private static final int PREGNANCIES = 8;
	private static final boolean PLACENTA_OBSERVED = true;
	private static final int WEEKS_PREGNANT = 3;
	
	private DataSource ds;
	private ObstetricsVisitMySQL ovsql;
	private TestDataGenerator gen;

	@Mock
	private DataSource mockDataSource;
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;
	
	@Override
	public void setUp() throws Exception {
		ov = new ObstetricsVisit();
		ov.setVisitID(ID);
		ov.setPatientMID(MID);
		ov.setWeight(WEIGHT);
		ov.setBloodPressure(BLOOD_PRESSURE);
		ov.setFetalHeartRate(FETAL_HEART_RATE);
		ov.setPregnancies(PREGNANCIES);
		ov.setPlacentaObserved(PLACENTA_OBSERVED);
		ov.setWeeksPregnant(WEEKS_PREGNANT);
		
		ds = ConverterDAO.getDataSource();
		ovsql = new ObstetricsVisitMySQL(ds);
		gen = new TestDataGenerator();
		gen.uc94();

		mockDataSource = Mockito.mock(DataSource.class);
		
		mockConnection = Mockito.mock(Connection.class);
		mockResultSet = Mockito.mock(ResultSet.class);
		mockPreparedStatement = Mockito.mock(PreparedStatement.class);
	}
	
	@Test
	public void testGetVisitsForPatient() throws Exception {
		List<ObstetricsVisit> list101 = ovsql.getVisitsForPatient(0L);
		assertEquals(1, list101.size());
		List<ObstetricsVisit> list102 = ovsql.getVisitsForPatient(102L);
		assertEquals(0, list102.size());
		
		ovsql.addObstetricsVisit(ov);
		list101 = ovsql.getVisitsForPatient(0L);
		assertEquals(2, list101.size());
		
		LocalDateTime date = ovsql.getMostRecentVisitForPatient(0);
		assertEquals("2017-03-16", date.toLocalDate().toString());
	}
}