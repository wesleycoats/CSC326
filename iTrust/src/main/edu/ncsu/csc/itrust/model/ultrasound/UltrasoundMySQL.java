package edu.ncsu.csc.itrust.model.ultrasound;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ValidationFormat;

/**
 * Provides an interface to access the Ultrasound database
 * @author bhmhogan
 */
public class UltrasoundMySQL implements Serializable {
	private static final long serialVersionUID = -8591212164935338829L;
	@Resource(name = "jdbc/itrust2")
	private UltrasoundSQLLoader loader;
	private UltrasoundValidator validator;
	private DataSource ds;
	
	/**
	 * Constructor used for testing purposes
	 * @param ds
	 */
	public UltrasoundMySQL(DataSource ds) {
		this.ds = ds;
		loader = new UltrasoundSQLLoader();
		validator = new UltrasoundValidator();
	}
	
	/**
	 * Default constructor
	 */
	public UltrasoundMySQL() throws DBException {
		loader = new UltrasoundSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new UltrasoundValidator();
	}
	
	/**
	 * Returns all of the Ultrasound Objects in the Database
	 * @return
	 * @throws DBException 
	 */
	public List<Ultrasound> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM ultrasound");
			results = pstring.executeQuery();
			final List<Ultrasound> visitList = loader.loadList(results);
			return visitList;
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
				DBUtil.closeConnection(conn, pstring);
			}
		}
	}
	
	/**
	 * Returns a list of all of the Ultrasound objects in the database for the specific MID
	 * @param MID
	 * @return
	 * @throws DBException 
	 */
	public List<Ultrasound> getByMID(long MID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM ultrasound WHERE patientMID=?");

				pstring.setLong(1, MID);

				results = pstring.executeQuery();

				final List<Ultrasound> visitList = loader.loadList(results);
				return visitList;
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
					DBUtil.closeConnection(conn, pstring);

				}
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the Ultrasound for the given MID that was taken on the specified Date
	 * @param MID
	 * @param date
	 * @return
	 * @throws DBException 
	 */
	public Ultrasound getByMIDAndDate(long MID, String date) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(MID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM ultrasound WHERE patientMID=? AND ultrasoundDate=?");

				pstring.setLong(1, MID);
				pstring.setString(2, date);

				results = pstring.executeQuery();

				final List<Ultrasound> visitList = loader.loadList(results);
				return visitList.get(0);
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
					DBUtil.closeConnection(conn, pstring);

				}
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Adds an Ultrasound to the database
	 * @param us
	 * @return
	 * @throws DBException 
	 */
	public boolean addUltrasound(Ultrasound us) throws DBException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			validator.validate(us);
		} catch (FormValidationException e) {
			throw new DBException(new SQLException(e.getMessage()));
		}
		try {
			conn = ds.getConnection();
			stmt = loader.loadParameters(conn, stmt, us, true);
			int results = stmt.executeUpdate();
			return results != 0;
		} catch (SQLException e) {
			throw  new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
	}
	
	public boolean update(Ultrasound us) throws DBException {
		boolean retVal = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			validator.validate(us);
		} catch (FormValidationException e) {
			throw new DBException(new SQLException(e.getMessage()));
		}
		int results;
		
		try {
			conn = ds.getConnection();
			stmt = loader.loadParameters(conn, stmt, us, false);
			results = stmt.executeUpdate();
			retVal = (results > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, stmt);
		}
		return retVal;
	}

}
