package edu.ncsu.csc.itrust.controller.flags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.controller.flags.Flag;
import edu.ncsu.csc.itrust.model.SQLLoader;

public class FlagMySQLLoader implements SQLLoader<Flag> {

	@Override
	public List<Flag> loadList(ResultSet rs) throws SQLException {
		List<Flag> list = new ArrayList<Flag>();
		while(rs.next())
			list.add(loadSingle(rs));
		return list;
	}

	@Override
	public Flag loadSingle(ResultSet rs) throws SQLException {
		long FID = rs.getLong("FID");
		long MID = rs.getLong("MID");
		long pregID = rs.getLong("pregID");
		String flagType = rs.getString("flagType");
		return new Flag(FID, MID, pregID, flagType);
	}

	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, Flag f,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO flags(FID, MID, pregID, flagType) "
					+ "VALUES (? ,?, ?, ?);";

		} else {
			long id = f.getMid();
			long pregID = f.getPregID();
			stmt = "UPDATE flags SET FID=?, "
					+ "MID=?, "
					+ "pregID=?, "
					+ "flagType=? "
					+ "WHERE MID=" + id + "AND pregID=" + pregID + ";";
		}
		ps = conn.prepareStatement(stmt);
		ps.setLong(1, f.getFid());
		ps.setLong(2, f.getMid());
		ps.setLong(3, f.getPregID());
		ps.setString(4, f.getFlagType());
		return ps;
	}

}
