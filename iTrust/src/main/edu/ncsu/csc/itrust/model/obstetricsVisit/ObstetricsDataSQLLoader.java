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

public class ObstetricsDataSQLLoader implements SQLLoader<ObstetricsData>{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ObstetricsData> loadList(ResultSet rs) throws SQLException {
		ArrayList<ObstetricsData> list = new ArrayList<ObstetricsData>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ObstetricsData loadSingle(ResultSet rs) throws SQLException {
		ObstetricsData retVisit = new ObstetricsData();
		retVisit.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		retVisit.setLmp(rs.getTimestamp("lmp").toLocalDateTime());
		retVisit.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
		
		return retVisit;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ObstetricsData ov, boolean newInstance)
			throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO ObstetricsData(visitID, patientMID, lmp, edd, weeksPregnant) "
					+ "VALUES (?, ?, ?, ?, ?);";

		} else {
			Timestamp date = Timestamp.valueOf(ov.getDateCreated());
			stmt = "UPDATE ObstetricsData SET "
					+ "patientMID=?, "
					+ "lmp=?, "
					+ "edd=?, "
					+ "WHERE dateCreated=" + date + ";";
		}
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, ov.getPatientMID());
		
		Timestamp ts1 = Timestamp.valueOf(ov.getLmp());
		ps.setTimestamp(2, ts1);
		Timestamp ts2 = Timestamp.valueOf(ov.getEDD());
		ps.setTimestamp(3, ts2);
		
		return ps;
	}
}
