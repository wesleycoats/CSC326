package edu.ncsu.csc.itrust.model.pregnancyConditions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Allows a user to interface with the pregnancyConditions table in the database.
 * That table takes 2 fields, a PatientMID and a code. That code can either be an ICD-10 code for a 
 * preexisting condition the patient has, or it can be an ND code for a drug allergy that patient has
 * @author bmhogan
 */
public class PregnancyConditionsMySQL {
	@Resource(name = "jdbc/itrust2")
	private PregnancyConditionsValidator validator;
	private DataSource ds;
	private PregnancyConditionsLoader loader;
	
	/**
	 * Default constructor 
	 * @throws DBException
	 */
	public PregnancyConditionsMySQL() throws DBException {
		loader = new PregnancyConditionsLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new PregnancyConditionsValidator();
	}
	
	/**
	 * Constructor used for testing
	 * @param ds
	 */
	public PregnancyConditionsMySQL(DataSource ds) {
		loader = new PregnancyConditionsLoader();
		this.ds = ds;
		validator = new PregnancyConditionsValidator();
	}
	
	/**
	 * Returns all of the entries for the given MID. These will include all of the drug allergies and pre-existing conditions
	 * @param MID
	 * @return
	 * @throws DBException 
	 */
	public ArrayList<String> getAllByMID(long MID) throws DBException {
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet results = null;
		ArrayList<String> conditionList = null;
		try {
			conn = ds.getConnection();
			query = conn.prepareStatement(PregnancyConditionsLoader.SELECT_BY_PATIENT_MID);
			query.setLong(1, MID);
			results = query.executeQuery();

			conditionList = loader.loadList(results);
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
		return conditionList;
	}
	
	/**
	 * Adds a new entry to pregnancyConditions
	 * @param MID
	 * @param code
	 * @return
	 * @throws DBException 
	 */
	public boolean add(long MID, String code) throws DBException {
		boolean successfullyAdded = false;
		Connection conn = null;
		PreparedStatement addStatement = null;
		try {
			validator.validate(MID, code);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		try {
			conn = ds.getConnection();
			addStatement = loader.loadParameters(conn, addStatement, MID, code);
			int exitStatus = addStatement.executeUpdate();
			successfullyAdded = (exitStatus > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, addStatement);
		}
		return successfullyAdded;
	}
}
