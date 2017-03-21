package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import java.sql.ResultSet;
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
		ds = ConverterDAO.getDataSource();
		ovsql = new ObstetricsVisitMySQL(ds);
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc93();

		mockDataSource = Mockito.mock(DataSource.class);
		
		mockConnection = Mockito.mock(Connection.class);
		mockResultSet = Mockito.mock(ResultSet.class);
		mockPreparedStatement = Mockito.mock(PreparedStatement.class);
	}
	
	@Test
	public void testGetVisitsForPatient() throws Exception {
		List<ObstetricsVisit> list101 = ovsql.getVisitsForPatient(101L);
		assertEquals(1, list101.size());
		List<ObstetricsVisit> list102 = ovsql.getVisitsForPatient(102L);
		assertEquals(0, list102.size());
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<ObstetricsVisit> list = ovsql.getAll();
		assertEquals(2, list.size());
	}
	
	@Test
	public void testGetByVisitID() throws Exception {
		ObstetricsVisit gotten = ovsql.getByVisitID(26);
		assertEquals("101", gotten.getPatientMID().toString());
		assertNull(ovsql.getByVisitID(25));
	}
}
