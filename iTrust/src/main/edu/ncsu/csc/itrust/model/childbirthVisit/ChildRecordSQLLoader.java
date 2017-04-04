package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.SQLLoader;

public class ChildRecordSQLLoader implements SQLLoader<ChildRecord>{

	@Override
	public List<ChildRecord> loadList(ResultSet rs) throws SQLException {
		ArrayList<ChildRecord> list = new ArrayList<ChildRecord>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public ChildRecord loadSingle(ResultSet rs) throws SQLException {
		Long motherMID = rs.getLong("motherID");
		Long visitID = rs.getLong("visitID");
		Boolean sex = rs.getBoolean("sex");
		String deliveryType = rs.getString("actualDelivery");
		Date dateOfBirth = rs.getDate("dateOfBirth");
		LocalDateTime ldtOfBirth = LocalDateTime.ofInstant(dateOfBirth.toInstant(), ZoneId.systemDefault());

		return new ChildRecord(sex, deliveryType, ldtOfBirth, motherMID, visitID);
	}

	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ChildRecord cr,
			boolean newInstance) throws SQLException {
		String stmt = "";
		int i = 1;
		stmt = "INSERT INTO childbirthChildren (motherID, visitID, sex, actualDelivery, "
				+ "dateOfBirth) "
				+ "VALUES (?, ?, ?, ?, ?)";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, cr.getMotherMID());
		ps.setLong(2, cr.getVisitID());
		ps.setBoolean(3, cr.getSex());
		ps.setString(4, cr.getDeliveryType());
		ps.setDate(5, new java.sql.Date(Date.from(cr.getDateOfBirth().atZone(ZoneId.systemDefault()).toInstant()).getTime()));
		return ps;
	}

}
