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
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsData;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsDataMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;

public class ObstetricsDataMySQLTest extends TestCase {
	
	private DataSource ds;
	private ObstetricsDataMySQL ovsql;
	private TestDataGenerator gen;

	@Mock
	private DataSource mockDataSource;
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;
	private ObstetricsDataMySQL mockOvsql;
	
	@Override
	public void setUp() throws Exception {
		ds = ConverterDAO.getDataSource();
		ovsql = new ObstetricsDataMySQL(ds);
		gen = new TestDataGenerator();
		gen.uc93();

		mockDataSource = Mockito.mock(DataSource.class);
		
		mockConnection = Mockito.mock(Connection.class);
		mockResultSet = Mockito.mock(ResultSet.class);
		mockPreparedStatement = Mockito.mock(PreparedStatement.class);
	}
	
	@Test
	public void testGetVisitsForPatient() throws Exception {
		List<ObstetricsData> list101 = ovsql.getVisitsForPatient(1L);
		assertEquals(1, list101.size());
		List<ObstetricsData> list102 = ovsql.getVisitsForPatient(102L);
		assertEquals(0, list102.size());
	}
}