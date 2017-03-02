package edu.ncsu.csc.itrust.model.fitBit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.enums.Role;

/**
 * Used for managing information related to personnel: HCPs, UAPs, Admins
 * 
 * DAO stands for Database Access Object. All DAOs are intended to be
 * reflections of the database, that is, one DAO per table in the database (most
 * of the time). For more complex sets of queries, extra DAOs are added. DAOs
 * can assume that all data has been validated and is correct.
 * 
 * DAOs should never have setters or any other parameter to the constructor than
 * a factory. All DAOs should be accessed by DAOFactory (@see
 * {@link DAOFactory}) and every DAO should have a factory - for obtaining JDBC
 * connections and/or accessing other DAOs.
 */
public class FitBitDAO {

	private transient final DAOFactory factory;
	private transient final FitBitBeanLoader fitbitLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public FitBitDAO(final DAOFactory factory) {
		this.factory = factory;
		fitbitLoader = new FitBitBeanLoader();
	}

	public List<FitBitBean> getByDateRange(Date d1, Date d2, long patient) throws DBException, ITrustException {
		List<FitBitBean> beans = getAllPatientWorkouts(patient);
		List<FitBitBean> newBeans = new ArrayList<FitBitBean>();
		for(int i = 0; i < beans.size(); i++) {
			if(beans.get(i).getDate().before(d2) && beans.get(i).getDate().after(d1))
				newBeans.add(beans.get(i));
		}
		return newBeans;
	}

	/**
	 * Adds an empty personnel, and returns the MID.
	 * 
	 * @return A long indicating the new MID.
	 * @param role
	 *            A {@link Role} enum indicating the personnel's specific role.
	 * @throws DBException
	 * @throws ITrustException
	 */
	public FitBitBean addNewWorkout(FitBitBean fbBean) throws DBException, ITrustException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO fitbit (patient, cal_burned, steps, floors, distance, min_seden, min_light_active,"
						+ "min_fair_active, min_very_active, activity_cal, workout_date, id) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?)")){
			this.fitbitLoader.loadParameters(stmt, fbBean);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return null;
	}

	/**
	 * Retrieves a PersonnelBean with all of the specific information for a
	 * given employee.
	 * 
	 * @param id
	 *            a string of the patient id and date together
	 * @return A FitBitBean representing the workout.
	 * @throws DBException
	 */
	public FitBitBean getWorkoutByID(final String id) throws DBException {
		FitBitBean bean = null;
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fitbit WHERE id = ?")) {
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				bean = this.fitbitLoader.loadSingle(rs);
			}
			rs.close();
			return bean;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Updates the demographics for a personnel.
	 * 
	 * @param p
	 *            The personnel bean with the updated information.
	 * @throws DBException
	 */
	public void editWorkout(final FitBitBean fbBean) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = fitbitLoader
						.loadParameters(conn.prepareStatement("UPDATE fitbit SET patient=?, cal_burned=?,steps=?,"
								+ "floors=?, distance=?, min_seden=?, min_light_active=?, min_fair_active=?, min_very_active=?,"
								+ "activity_cal=?, workout_date=? WHERE id=?"), fbBean)) {
			stmt.setString(12, fbBean.getID());
			this.fitbitLoader.loadParameters(stmt, fbBean);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Returns all the workouts for a single patient
	 * 
	 * @return A java.util.List of personnel.
	 * @throws DBException
	 */
	public List<FitBitBean> getAllPatientWorkouts(long patientID) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fitbit WHERE patient = ?")) {
			stmt.setLong(1, patientID);
			ResultSet rs = stmt.executeQuery();
			List<FitBitBean> loadlist = fitbitLoader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}