package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.ncsu.csc.itrust.model.SQLLoader;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;

public class ChildbirthVisitSQLLoader implements SQLLoader<ChildbirthVisit> {

	@Override
	public List<ChildbirthVisit> loadList(ResultSet rs) throws SQLException {
		ArrayList<ChildbirthVisit> list = new ArrayList<ChildbirthVisit>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public ChildbirthVisit loadSingle(ResultSet rs) throws SQLException {
		ChildbirthVisit retVisit = new ChildbirthVisit();
		retVisit.setVisitID(Long.parseLong(rs.getString("visitID")));
		retVisit.setPatientMID(Long.parseLong(rs.getString("patientMID")));
		retVisit.setPreferredDelivery(rs.getString("preferredDelivery"));
		retVisit.setScheduled(rs.getBoolean("scheduled"));
		retVisit.setPitocinDosage(rs.getInt("pitocinDosage"));
		retVisit.setNitrousOxideDosage(rs.getInt("noDosage"));
		retVisit.setPethidineDosage(rs.getInt("pethidineDosage"));
		retVisit.setRhGlobulinDosage(rs.getInt("rhGlobDosage"));
		return retVisit;
	}

	@Override
	public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, ChildbirthVisit cb,
			boolean newInstance) throws SQLException {
		String stmt = "";
		int i = 1;
		stmt = "INSERT INTO childbirthVisit(visitID, patientMID, preferredDelivery, scheduled, "
				+ "pitocinDosage, noDosage, pethidineDosage, eaDosage, msDosage, rhGlobDosage) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		ps = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(i++, cb.getVisitID());
		ps.setLong(i++, cb.getPatientMID());
		ps.setString(i++, cb.getPreferredDelivery());
		ps.setBoolean(i++, cb.getScheduled());
		ps.setInt(i++, cb.getPitocinDosage());
		ps.setInt(i++, cb.getNitrousOxideDosage());
		ps.setInt(i++, cb.getPethidineDosage());
		ps.setInt(i++, cb.getEpiduralAnaesthesiaDosage());
		ps.setInt(i++, cb.getMagnesiumSulfateDosage());
		ps.setInt(i++, cb.getRhGlobulinDosage());
		return ps;
	}

}
