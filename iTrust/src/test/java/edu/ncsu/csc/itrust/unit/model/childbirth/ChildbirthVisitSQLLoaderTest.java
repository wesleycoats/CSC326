package edu.ncsu.csc.itrust.unit.model.childbirth;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.childbirthVisit.ChildbirthVisitSQLLoader;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisitSQLLoader;
import junit.framework.TestCase;

public class ChildbirthVisitSQLLoaderTest extends TestCase {
	private static String MOCK_COLUMN_NAME = "mock_column";
	private static Integer MOCK_COLUMN_INDEX = 0;
	private static Integer MOCK_INT_VALUE = 1031;
	private static Float MOCK_FLOAT_VALUE = 1031.1f;
	
	private ChildbirthVisitSQLLoader cbLoader;
	
	@Mock
	private ResultSet mockIntValResultSet;
	@Mock
	private ResultSet mockIntNullResultSet;
	@Mock
	private ResultSet mockFloatValResultSet;
	@Mock
	private ResultSet mockFloatNullResultSet;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private Connection mockConnection;

	@Before
	public void setUp() throws Exception {
		cbLoader = new ChildbirthVisitSQLLoader();
		
		// Mock a ResultSet containing int value
		mockIntValResultSet = Mockito.mock(ResultSet.class);
		Mockito.when(mockIntValResultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(mockIntValResultSet.getString("visitID")).thenReturn("1");
		Mockito.when(mockIntValResultSet.getString("patientMID")).thenReturn("1");
		Mockito.when(mockIntValResultSet.getString("preferredDelivery")).thenReturn("vaginal delivery");
		Mockito.when(mockIntValResultSet.getInt("pitocinDosage")).thenReturn(1);
		Mockito.when(mockIntValResultSet.getInt("noDosage")).thenReturn(1);
		Mockito.when(mockIntValResultSet.getInt("pethidineDosage")).thenReturn(1);
		Mockito.when(mockIntValResultSet.getInt("eaDosage")).thenReturn(1);
		Mockito.when(mockIntValResultSet.getInt("msDosage")).thenReturn(1);
		Mockito.when(mockIntValResultSet.getInt("rhGlobDosage")).thenReturn(1);

		Mockito.when(mockIntValResultSet.wasNull()).thenReturn(false);
		
		// Mock a ResultSet containing null int value
		mockIntNullResultSet = Mockito.mock(ResultSet.class);
		Mockito.when(mockIntNullResultSet.getInt(MOCK_COLUMN_NAME)).thenReturn(0);
		Mockito.when(mockIntNullResultSet.wasNull()).thenReturn(true);
		
		// Mock a ResultSet containing int value
		mockFloatValResultSet = Mockito.mock(ResultSet.class);
		Mockito.when(mockFloatValResultSet.getFloat(MOCK_COLUMN_NAME)).thenReturn(MOCK_FLOAT_VALUE);
		Mockito.when(mockFloatValResultSet.wasNull()).thenReturn(false);
		
		// Mock a ResultSet containing null int value
		mockFloatNullResultSet = Mockito.mock(ResultSet.class);
		Mockito.when(mockFloatNullResultSet.getFloat(MOCK_COLUMN_NAME)).thenReturn(0.0f);
		Mockito.when(mockFloatNullResultSet.wasNull()).thenReturn(true);
		
		// Mock a PreparedStatement with a placeholder
		mockPreparedStatement = Mockito.mock(com.mysql.jdbc.PreparedStatement.class);
		Mockito.doCallRealMethod().when(mockPreparedStatement).setLong(Mockito.anyInt(), Mockito.anyLong());;
		
		// Mock a Connection with a placeholder
		mockConnection = Mockito.mock(com.mysql.jdbc.Connection.class);
		Mockito.when(mockConnection.prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockPreparedStatement);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadList() {
		try {
			cbLoader.loadList(mockIntValResultSet);
		} catch (SQLException e) {
			fail();
		}
	}

//	@Test
//	public void testLoadParameters() {
//		Long visitID = 1L;
//		Long patientMID = 2L;
//		String preferredDelivery = "3";
//		Boolean scheduled = true;
//		Integer pitocinDosage = 5;
//		Integer nitrousOxideDosage = 6;
//		Integer pethidineDosage = 7;
//		Integer epiduralAnaesthesiaDosage = 8;
//		Integer magnesiumSulfateDosage = 9;
//		Integer rhGlobulinDosage = 10;
//		ChildbirthVisit cb = new ChildbirthVisit();
//		cb.setPatientMID(patientMID);
//		cb.setVisitID(visitID);
//		cb.setPreferredDelivery(preferredDelivery);
//		cb.setScheduled(scheduled);
//		cb.setPitocinDosage(pitocinDosage);
//		cb.setNitrousOxideDosage(nitrousOxideDosage);
//		cb.setPethidineDosage(pethidineDosage);
//		cb.setEpiduralAnaesthesiaDosage(epiduralAnaesthesiaDosage);
//		cb.setMagnesiumSulfateDosage(magnesiumSulfateDosage);
//		cb.setRhGlobulinDosage(rhGlobulinDosage);
//		
//		try {
//			cbLoader.loadParameters(mockConnection, mockPreparedStatement, cb, true);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}

}
