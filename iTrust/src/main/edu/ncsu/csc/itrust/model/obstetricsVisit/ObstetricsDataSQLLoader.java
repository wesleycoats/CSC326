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
		retVisit.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
		retVisit.setLmp(rs.getTimestamp("lmp").toLocalDateTime());
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
			stmt = "INSERT INTO obstetrics(patientMID, dateCreated, lmp, edd) "
					+ "VALUES (?, ?, ?, ?);";
			ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, ov.getPatientMID());
			Timestamp ts0 = Timestamp.valueOf(ov.getDateCreated());
			ps.setTimestamp(2, ts0);
			Timestamp ts1 = Timestamp.valueOf(ov.getLmp());
			ps.setTimestamp(3, ts1);
			Timestamp ts2 = Timestamp.valueOf(ov.getEDD());
			ps.setTimestamp(4, ts2);
		} else {
			long id = ov.getPatientMID();
			Timestamp date = Timestamp.valueOf(ov.getDateCreated());
			stmt = "UPDATE obstetrics SET "
					+ "patientMID=?, "
					+ "lmp=?, "
					+ "edd=?, "
					+ "WHERE dateCreated=" + date
					+ "AND patientMID=" + id + ";";
			ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
			Timestamp ts1 = Timestamp.valueOf(ov.getLmp());
			ps.setTimestamp(1, ts1);
			Timestamp ts2 = Timestamp.valueOf(ov.getEDD());
			ps.setTimestamp(2, ts2);
		}
		return ps;
	}
}
