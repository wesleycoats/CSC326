package edu.ncsu.csc.itrust.model.microsoftBand;

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
public class MicrosoftBandDAO {

	private transient final DAOFactory factory;
	private transient final MicrosoftBandLoader microsoftLoader;

	/**
	 * The typical constructor.
	 * 
	 * @param factory
	 *            The {@link DAOFactory} associated with this DAO, which is used
	 *            for obtaining SQL connections, etc.
	 */
	public MicrosoftBandDAO(final DAOFactory factory) {
		this.factory = factory;
		microsoftLoader = new MicrosoftBandLoader();
	}

	public List<MicrosoftBandBean> getByDateRange(Date d1, Date d2, long patient) throws DBException, ITrustException {
		List<MicrosoftBandBean> beans = getAllPatientWorkouts(patient);
		List<MicrosoftBandBean> newBeans = new ArrayList<MicrosoftBandBean>();
		for(int i = 0; i < beans.size(); i++) {
			if(!(beans.get(i).getDate().before(d1) || beans.get(i).getDate().after(d2)))
				newBeans.add(beans.get(i));
		}
		return newBeans;
	}

	
	public MicrosoftBandBean addNewWorkout(MicrosoftBandBean mbBean) throws DBException, ITrustException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO microsoft (patient, calories, steps, floors, distance, hr_lowest, hr_average,"
						+ "hr_highest, min_uv_exposure, activity_hours, workout_date, id) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?)")){
			this.microsoftLoader.loadParameters(stmt, mbBean);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return null;
	}

	public MicrosoftBandBean getWorkoutByID(final String id) throws DBException {
		MicrosoftBandBean bean = null;
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM microsoft WHERE id = ?")) {
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				bean = this.microsoftLoader.loadSingle(rs);
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
	public void editWorkout(final MicrosoftBandBean mbBean) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = microsoftLoader
						.loadParameters(conn.prepareStatement("UPDATE microsoft SET patient=?, calories=?,steps=?,"
								+ "floors=?, distance=?, hr_lowest=?, hr_average=?, hr_highest=?, min_uv_exposure=?,"
								+ "activity_hours=?, workout_date=? WHERE id=?"), mbBean)) {
			stmt.setString(12, mbBean.getID());
			this.microsoftLoader.loadParameters(stmt, mbBean);
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
	public List<MicrosoftBandBean> getAllPatientWorkouts(long patientID) throws DBException {
		try (Connection conn = factory.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM microsoft WHERE patient = ?")) {
			stmt.setLong(1, patientID);
			ResultSet rs = stmt.executeQuery();
			List<MicrosoftBandBean> loadlist = microsoftLoader.loadList(rs);
			rs.close();
			return loadlist;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}