package edu.ncsu.csc.itrust.model.fitBit;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.model.old.beans.loaders.BeanLoader;

/**
 * A loader for PersonnelBeans.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class FitBitBeanLoader implements BeanLoader<FitBitBean> {
	@Override
	public List<FitBitBean> loadList(ResultSet rs) throws SQLException {
		List<FitBitBean> list = new ArrayList<FitBitBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public FitBitBean loadSingle(ResultSet rs) throws SQLException {
		FitBitBean fb = new FitBitBean();
		fb.setPatient(rs.getLong("patient"));
		fb.setCalBurned(rs.getInt("cal_burned"));
		fb.setSteps(rs.getInt("steps"));
		fb.setFloors(rs.getInt("floors"));
		fb.setDistance(rs.getDouble("distance"));
		fb.setMinSeden(rs.getInt("min_seden"));
		fb.setMinLightActive(rs.getInt("min_light_active"));
		fb.setMinFairActive(rs.getInt("min_fair_active"));
		fb.setMinVeryActive(rs.getInt("min_very_active"));
		fb.setActivityCal(rs.getInt("activity_cal"));
		fb.setDate((rs.getDate("workout_date")));
		fb.setID(rs.getString("id"));
		return fb;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, FitBitBean fb) throws SQLException {
		int i = 1;
		ps.setLong(i++, fb.getPatient());
		ps.setInt(i++, fb.getCalBurned());
		ps.setInt(i++, fb.getSteps());
		ps.setInt(i++, fb.getFloors());
		ps.setDouble(i++, fb.getDistance());
		ps.setInt(i++, fb.getMinSeden());
		ps.setInt(i++, fb.getMinLightActive());
		ps.setInt(i++, fb.getMinFairActive());
		ps.setInt(i++, fb.getMinVeryActive());
		ps.setInt(i++, fb.getActivityCal());
		ps.setDate(i++, (Date) fb.getDate());
		ps.setString(i++, fb.getID());
		return ps;
	}
}
