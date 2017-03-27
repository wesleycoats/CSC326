package edu.ncsu.csc.itrust.model.ultrasound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.SQLLoader;

public class UltrasoundSQLLoader implements SQLLoader<Ultrasound> {

	@Override
	public List<Ultrasound> loadList(ResultSet rs) throws SQLException {
		ArrayList<Ultrasound> list = new ArrayList<Ultrasound>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public Ultrasound loadSingle(ResultSet rs) throws SQLException {
		Ultrasound us = new Ultrasound();
		us.setMID(Long.parseLong(rs.getString("patientMID")));
		us.setCRL(Double.parseDouble(rs.getString("CrownRumpLen")));
		us.setBPD(Double.parseDouble(rs.getString("BiparietalDia")));
		us.setHC(Double.parseDouble(rs.getString("HeadCirc")));
		us.setFL(Double.parseDouble(rs.getString("FemurLen")));
		us.setOFD(Double.parseDouble(rs.getString("OccipitofrontalDia")));
		us.setAC(Double.parseDouble(rs.getString("abdominalCirc")));
		us.setHL(Double.parseDouble(rs.getString("humerusLen")));
		us.setEFW(Double.parseDouble(rs.getString("estimatedFetalWeight")));
		us.setFilePath(rs.getString("imagePath"));
		
		// Now grab the date the ultrasound was taken
		us.setDateString(rs.getString("ultrasoundDate"));
		
		return us;
	}

	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, Ultrasound us,
			boolean newInstance) throws SQLException {
		String stmt = "";
		if (newInstance) {
			stmt = "INSERT INTO ultrasound(patientMID, crownRumpLen, BiparietalDia, HeadCirc, FemurLen, "
					+ "OccipitofrontalDia, abdominalCirc, humerusLen, estimatedFetalWeight, ultrasoundDate, "
					+ "imagePath) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			// Now set the values
			ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, us.getMID());
			ps.setDouble(2, us.getCRL());
			ps.setDouble(3, us.getBPD());
			ps.setDouble(4, us.getHC());
			ps.setDouble(5, us.getFL());
			ps.setDouble(6, us.getOFD());
			ps.setDouble(7, us.getAC());
			ps.setDouble(8, us.getHL());
			ps.setDouble(9, us.getEFW());
			ps.setString(10, us.getDateString());
			ps.setString(11, us.getFilePath());
		} else {
			long id = us.getMID();
			String d = us.getDateString();
			stmt = "UPDATE ultrasound SET "
					+ "crownRumpLen=?, "
					+ "BiparietalDia=?, "
					+ "HeadCirc=?, "
					+ "FemurLen=?, "
					+ "OccipitofrontalDia=?, "
					+ "abdominalCirc=?, "
					+ "humerusLen=?, "
					+ "estimatedFetalWeight=?, "
					+ "imagePath=? "
					+ "WHERE patientMID=? AND ultrasoundDate=?";
			// Now set the values
			ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, us.getCRL());
			ps.setDouble(2, us.getBPD());
			ps.setDouble(3, us.getHC());
			ps.setDouble(4, us.getFL());
			ps.setDouble(5, us.getOFD());
			ps.setDouble(6, us.getAC());
			ps.setDouble(7, us.getHL());
			ps.setDouble(8, us.getEFW());
			ps.setString(9, us.getFilePath());
			ps.setLong(10, id);
			ps.setString(11, d);
		}
		return ps;
	}

}
