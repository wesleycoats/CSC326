package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
		retVisit.setLMP(rs.getTimestamp("lmp").toLocalDateTime());
		retVisit.setEDD(rs.getTimestamp("edd").toLocalDateTime());
		retVisit.setWeeksPregnant(getIntOrNull(rs, "weeksPregnant"));
		
		return retVisit;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ObstetricsVisit ov, boolean newInstance)
			throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO obstetricsVisit(visitID, patientMID, lmp, edd, weeksPregnant) "
					+ "VALUES (?, ?, ?, ?, ?);";

		} else {
			long id = ov.getVisitID();
			stmt = "UPDATE obstetricsVisit SET "
					+ "patientMID=?, "
					+ "lmp=?, "
					+ "edd=?, "
					+ "weeksPregnant=?, "
					+ "WHERE visitID=" + id + ";";
		}
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, ov.getPatientMID());
		
		Timestamp ts1 = Timestamp.valueOf(ov.getLMP());
		ps.setTimestamp(2, ts1);
		Timestamp ts2 = Timestamp.valueOf(ov.getEDD());
		ps.setTimestamp(3, ts2);
		setIntOrNull(ps, 4, ov.getWeeksPegnant());
		
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
}
