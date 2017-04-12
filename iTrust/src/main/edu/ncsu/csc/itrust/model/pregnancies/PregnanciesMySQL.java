package edu.ncsu.csc.itrust.model.pregnancies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.SQLLoader;

public class PregnanciesMySQL {
	@Resource(name = "jdbc/itrust2")
	
	private PregnancyMySQLLoader loader;
	private DataSource ds;
	private PregnanciesValidator validator;
	
	/**
	 * Default constructor for PregnancyMySQL.
	 * @throws DBException if there is a context lookup naming exception
	 */
	public PregnanciesMySQL() throws DBException {
		loader = new PregnancyMySQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new PregnanciesValidator();
	}
	
	/**
	 * Constructor for testing.
	 * @param ds
	 */
	public PregnanciesMySQL(DataSource ds) {
		loader = new PregnancyMySQLLoader();
		this.ds = ds;
		validator = new PregnanciesValidator();
	}
	
	/**
	 * Returns a list of all Pregnancy entries in the database
	 * @return
	 * @throws DBException
	 */
	public List<Pregnancies> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			query = conn.prepareStatement(PregnancyMySQLLoader.SELECT_ALL);
			results = query.executeQuery();

			return loader.loadList(results);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, query);
			}
		}
	}
	
	/**
	 * Adds a Pregnancy object to the database. Returns true if it
	 * was added successfully
	 * @param p
	 * @return
	 * @throws DBException
	 */
	public boolean add(Pregnancies p) throws DBException {
		boolean successfullyAdded = false;
		Connection conn = null;
		PreparedStatement addStatement = null;
		try {
			validator.validate(p);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		try {
			conn = ds.getConnection();
			addStatement = loader.loadParameters(conn, addStatement, p, true);
			int exitStatus = addStatement.executeUpdate();
			successfullyAdded = (exitStatus > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, addStatement);
		}
		return successfullyAdded;
	}
	
	/**
	 * Updates the Pregnancy object in the Database with the same patientMID and
	 * yearOfConception
	 * @param p
	 * @return
	 * @throws DBException
	 */
	public boolean update(Pregnancies p) throws DBException {
		boolean successfullyUpdated = false;
		Connection conn = null;
		PreparedStatement updateStatement = null;
		try {
			validator.validate(p);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		try {
			conn = ds.getConnection();
			updateStatement = loader.loadParameters(conn, updateStatement, p, false);
			int exitStatus = updateStatement.executeUpdate();
			successfullyUpdated = (exitStatus > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, updateStatement);
		}
		return successfullyUpdated;
	}
	
	/**
	 * Returns all Pregnancy objects in the database that have the given 
	 * patient MID
	 * @param MID
	 * @return
	 * @throws DBException
	 */
	public List<Pregnancies> getByPatientID(long MID) throws DBException {
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet results = null;
		List<Pregnancies> pregnancyList = null;
		try {
			conn = ds.getConnection();
			query = conn.prepareStatement(PregnancyMySQLLoader.SELECT_BY_PATIENT_MID);
			query.setLong(1, MID);
			results = query.executeQuery();

			pregnancyList = loader.loadList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, query);
			}
		}
		return pregnancyList;
	}

	/**
	 * Takes care of loading PreparedStatements and loading the results into lists for
	 * PregnancyMySQL
	 * @author bmhogan
	 */
	private class PregnancyMySQLLoader implements SQLLoader<Pregnancies> {
		/** Lab Procedure table name */
		private static final String PREGNANCIES_TABLE_NAME = "pregnancies";
		
		/** Database column names */
		private static final String PATIENT_MID = "patientMID";
		private static final String CONCEPTION_YEAR = "yearOfConception";
		private static final String WEEKS_PREG = "weeksPregnant";
		private static final String HOURS_IN_LABOR = "hoursInLabor";
		private static final String WEIGHT_GAIN = "weightGain";
		private static final String TYPE = "deliveryType";
		private static final String NUM_CHILDREN = "numChildren";
		private static final String EDD = "edd";
		
		private static final String INSERT = "INSERT INTO " + PREGNANCIES_TABLE_NAME + " (" 
				+ PATIENT_MID + ", "
				+ CONCEPTION_YEAR + ", "
				+ WEEKS_PREG + ", "
				+ HOURS_IN_LABOR + ", "
				+ WEIGHT_GAIN + ", "
				+ TYPE + ", "
				+ NUM_CHILDREN + ", "
				+ EDD + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		private static final String UPDATE = "UPDATE " + PREGNANCIES_TABLE_NAME + " SET " 
				+ WEEKS_PREG + "=?, "
				+ HOURS_IN_LABOR + "=?, "
				+ WEIGHT_GAIN + "=?, "
				+ TYPE + "=?, "
				+ NUM_CHILDREN + "=?,"
				+ EDD + "=? WHERE" + PATIENT_MID + " =?, " + CONCEPTION_YEAR + "=?";
		
		public static final String SELECT_BY_PATIENT_MID = "SELECT * from " + PREGNANCIES_TABLE_NAME + " WHERE "
				+ PATIENT_MID + "=?";
		
		public static final String SELECT_ALL = "SELECT * from " + PREGNANCIES_TABLE_NAME;
		
		public List<Pregnancies> loadList(ResultSet rs) throws SQLException {
			List<Pregnancies> list = new ArrayList<Pregnancies>();
			while (rs.next())
				list.add(loadSingle(rs));
			return list;
		}

		public Pregnancies loadSingle(ResultSet rs) throws SQLException {
			long MID = rs.getLong("patientMID");
			String type = rs.getString("deliveryType");
			int year = rs.getInt("yearOfConception");
			double labor = rs.getDouble("hoursInLabor");
			double weight = rs.getDouble("weightGain");
			int weeksPregnant = rs.getInt("weeksPregnant");
			short children = rs.getShort("numChildren");
			LocalDateTime edd = rs.getTimestamp("edd").toLocalDateTime();
			Pregnancies p = new Pregnancies(MID, type, year, labor, weight, weeksPregnant, children);
			p.setEdd(edd);
			return p;
		}
		
		public PreparedStatement loadParameters(Connection conn, PreparedStatement ps, Pregnancies pregnancy, 
				boolean newInstance) throws SQLException {
			StringBuilder query = new StringBuilder(newInstance ? INSERT : UPDATE);
			ps = conn.prepareStatement(query.toString());
			
			if (newInstance) {
				ps.setLong(1, pregnancy.getPatientMID());
				ps.setInt(2, pregnancy.getYearOfConception());
				ps.setInt(3, pregnancy.getWeeksPregnant());
				ps.setDouble(4, pregnancy.getHoursInLabor());
				ps.setDouble(5, pregnancy.getWeightGain());
				ps.setString(6, pregnancy.getDelType());
				ps.setShort(7, pregnancy.getNumChildren());
				ps.setTimestamp(8, Timestamp.valueOf(pregnancy.getEdd()));
			} else {
				ps.setInt(1, pregnancy.getWeeksPregnant());
				ps.setDouble(2, pregnancy.getHoursInLabor());
				ps.setDouble(3, pregnancy.getWeightGain());
				ps.setString(4, pregnancy.getDelType());
				ps.setShort(5, pregnancy.getNumChildren());
				ps.setLong(6, pregnancy.getPatientMID());
				ps.setInt(7, pregnancy.getYearOfConception());
				ps.setTimestamp(8, Timestamp.valueOf(pregnancy.getEdd()));
			}
			return ps;
		}
		
	}
}
