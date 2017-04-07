package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.SQLLoader;

public class ObstetricsVisitSQLLoader implements SQLLoader<ObstetricsVisit>{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObstetricsVisit> loadList(ResultSet rs) throws SQLException {
		ArrayList<ObstetricsVisit> list = new ArrayList<ObstetricsVisit>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObstetricsVisit loadSingle(ResultSet rs) throws SQLException {
		ObstetricsVisit retVisit = new ObstetricsVisit();
		retVisit.setVisitID(Long.parseLong(rs.getString("visitID")));
		retVisit.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		retVisit.setWeeksPregnant(getIntOrNull(rs, "weeksPregnant"));
		retVisit.setWeight(getFloatOrNull(rs, "weight"));
		retVisit.setSystolicBloodPressure(getIntOrNull(rs, "systolicBloodPressure"));
		retVisit.setDiastolicBloodPressure(getIntOrNull(rs, "diastolicBloodPressure"));
		retVisit.setFetalHeartRate(getIntOrNull(rs, "fetalHeartRate"));
		retVisit.setPregnancies(getIntOrNull(rs, "pregnancies"));
		retVisit.setPlacentaObserved(Boolean.parseBoolean(rs.getString("placentaObserved")));
		
		return retVisit;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ObstetricsVisit ov, boolean newInstance)
			throws SQLException {
		String stmt = "";
		int i = 1;
		stmt = "INSERT INTO obstetricsVisit(visitID, patientMID, weeksPregnant, weight, "
				+ "systolicBloodPressure, diastolicBloodPressure, fetalHeartRate, pregnancies, placentaObserved) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(i++, ov.getVisitID());
	
		ps.setLong(i++, ov.getPatientMID());
		setIntOrNull(ps, i++, ov.getWeeksPegnant());
		setFloatOrNull(ps, i++, ov.getWeight());
		setIntOrNull(ps, i++, ov.getSystolicBloodPressure());
		setIntOrNull(ps, i++, ov.getDiastolicBloodPressure());
		setIntOrNull(ps, i++, ov.getFetalHeartRate());
		setIntOrNull(ps, i++, ov.getPregnancies());
		ps.setBoolean(i++, ov.getPlacentaObserved());
		
		return ps;
	}
	
	/**
	 * Get the integer value if initialized in DB, otherwise get null.
	 * 
	 * @param rs 
	 * 		ResultSet object
	 * @param field
	 * 		name of DB attribute 
	 * @return Integer value or null
	 * @throws SQLException when field doesn't exist in the result set
	 */
	public Integer getIntOrNull(ResultSet rs, String field) throws SQLException {
		Integer ret = rs.getInt(field);
		if (rs.wasNull()) {
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Get the float value if initialized in DB, otherwise get null.
	 * 
	 * @param rs 
	 * 		ResultSet object
	 * @param field
	 * 		name of DB attribute 
	 * @return Float value or null
	 * @throws SQLException when field doesn't exist in the result set
	 */
	public Float getFloatOrNull(ResultSet rs, String field) throws SQLException {
		Float ret = rs.getFloat(field);
		if (rs.wasNull()) {
			ret = null;
		}
		return ret;
	}
	
	/**
	 * Set integer placeholder in statement to a value or null
	 * 
	 * @param ps
	 * 		PreparedStatement object
	 * @param index
	 * 		Index of placeholder in the prepared statement
	 * @param value
	 * 		Value to set to placeholder, the value may be null 
	 * @throws SQLException
	 * 		When placeholder is invalid
	 */
	public void setIntOrNull(PreparedStatement ps, int index, Integer value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.INTEGER);
		} else {
			ps.setInt(index, value);
		}
	}
	
	/**
	 * Set float placeholder in statement to a value or null
	 * 
	 * @param ps
	 * 		PreparedStatement object
	 * @param index
	 * 		Index of placeholder in the prepared statement
	 * @param value
	 * 		Value to set to placeholder, the value may be null 
	 * @throws SQLException
	 * 		When placeholder is invalid
	 */
	public void setFloatOrNull(PreparedStatement ps, int index, Float value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.FLOAT);
		} else {
			ps.setFloat(index, value);
		}
	}
}
