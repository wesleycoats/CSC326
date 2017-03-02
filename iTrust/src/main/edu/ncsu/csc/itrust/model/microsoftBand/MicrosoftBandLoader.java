package edu.ncsu.csc.itrust.model.microsoftBand;

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
public class MicrosoftBandLoader implements BeanLoader<MicrosoftBandBean> {
	@Override
	public List<MicrosoftBandBean> loadList(ResultSet rs) throws SQLException {
		List<MicrosoftBandBean> list = new ArrayList<MicrosoftBandBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	@Override
	public MicrosoftBandBean loadSingle(ResultSet rs) throws SQLException {
		MicrosoftBandBean fb = new MicrosoftBandBean();
		fb.setPatient(rs.getLong("patient"));
		fb.setCalories(rs.getInt("calories"));
		fb.setSteps(rs.getInt("steps"));
		fb.setFloors(rs.getInt("floors"));
		fb.setDistance(rs.getFloat("distance"));
		fb.setHRLowest(rs.getInt("HR_lowest"));
		fb.setHRAverage(rs.getInt("HR_average"));
		fb.setHRHighest(rs.getInt("HR_highest"));
		fb.setMinUVExposure(rs.getInt("min_UV_exposure"));
		fb.setActivityHours(rs.getInt("activity_hours"));
		fb.setDate((rs.getDate("workout_date")));
		fb.setID(rs.getString("id"));
		return fb;
	}

	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, MicrosoftBandBean fb) throws SQLException {
		int i = 1;
		ps.setLong(i++, fb.getPatient());
		ps.setInt(i++, fb.getCalories());
		ps.setInt(i++, fb.getSteps());
		ps.setInt(i++, fb.getFloors());
		ps.setFloat(i++, fb.getDistance());
		ps.setInt(i++, fb.getHRLowest());
		ps.setInt(i++, fb.getHRAverage());
		ps.setInt(i++, fb.getHRHighest());
		ps.setInt(i++, fb.getMinUVExposure());
		ps.setInt(i++, fb.getActivityHours());
		ps.setDate(i++, (Date) fb.getDate());
		ps.setString(i++, fb.getID());
		return ps;
	}
}
