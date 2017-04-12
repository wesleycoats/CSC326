package edu.ncsu.csc.itrust.model.pregnancyConditions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class that loads the ResultSet from executing the PreparedStatements
 * @author bmhogan
 */
public class PregnancyConditionsLoader {
	public static final String SELECT_BY_PATIENT_MID = "SELECT * from pregnancyConditions WHERE PatientID=?";
	private static final String INSERT = "INSERT INTO pregnancyConditions (patientID, Code) VALUES(?, ?)";
	
	/** Default constructor */
	public PregnancyConditionsLoader() {}
	
	public ArrayList<String> loadList(ResultSet rs) throws SQLException {
		ArrayList<String> returnList = new ArrayList<String>();
		while (rs.next()) {
			returnList.add(loadSingle(rs));
		}
		return returnList;
	}
	
	public String loadSingle(ResultSet rs) throws SQLException {
		return rs.getString("Code");
	}
	//TODO

	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, long mID, String code) throws SQLException {
		StringBuilder query = new StringBuilder(INSERT);
		ps = conn.prepareStatement(query.toString());
		
		ps.setLong(1, mID);
		ps.setString(2, code);
		return ps;
	}
}