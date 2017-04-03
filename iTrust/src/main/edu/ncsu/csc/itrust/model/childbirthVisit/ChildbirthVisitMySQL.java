package edu.ncsu.csc.itrust.model.childbirthVisit;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitSQLLoader;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitValidator;

public class ChildbirthVisitMySQL implements Serializable {
	private static final long serialVersionUID = 5903078182200761772L;
	@Resource(name = "jdbc/itrust2")
	private ChildbirthVisitSQLLoader cbLoader;
	private DataSource ds;
	private ChildbirthVisitValidator validator;
	
	/**
	 * Default constructor for ChildbirthVisitMySQL.
	 * 
	 * @throws DBException if there is a context lookup naming exception
	 */
	public ChildbirthVisitMySQL() throws DBException {
		cbLoader = new ChildbirthVisitSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new ChildbirthVisitValidator();
	}
	
	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 */
	public ChildbirthVisitMySQL(DataSource ds) {
		cbLoader = new ChildbirthVisitSQLLoader();
		this.ds = ds;
		validator = new ChildbirthVisitValidator();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<ChildbirthVisit> getVisitsForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM childbirthVisit WHERE patientMID=?");
				pstring.setLong(1, patientID);
				results = pstring.executeQuery();

				final List<ChildbirthVisit> visitList = cbLoader.loadList(results);
				return this.sortByDate(visitList);
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
	
	public boolean addChildbirthVisit(ChildbirthVisit cb) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(cb);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		
		try {
			conn = ds.getConnection();
			pstring = cbLoader.loadParameters(conn, pstring, cb, true);
			int results = pstring.executeUpdate();
			return results != 0;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}


	public LocalDateTime getDateOfVisit(Long visitID) throws DBException {
		
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		LocalDateTime dateOfVisit = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT visitDate FROM officeVisit WHERE visitID=?");
			pstring.setLong(1, visitID);
			results = pstring.executeQuery();
			if (!results.next()) {
				return null;
			}
			dateOfVisit = results.getTimestamp("visitDate").toLocalDateTime();
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (results != null) {
					results.close();
				}
			} catch (SQLException e) {
				return null;
			} finally {
				DBUtil.closeConnection(conn, pstring);
			}
		}
		if (dateOfVisit == null) {
			return null;
		}
		return dateOfVisit;

	}
	
	private List<ChildbirthVisit> sortByDate(List<ChildbirthVisit> unsorted) throws DBException {
		List<ChildbirthVisit> sorted = new LinkedList<ChildbirthVisit>();
		for(int i = 0; i < unsorted.size(); i++) {
			ChildbirthVisit nextToAdd = unsorted.get(i);
			for(int j = 0; j <= sorted.size(); j++) {
				if(j == sorted.size()) {
					sorted.add(unsorted.get(i));
					break;
				} else {
					LocalDateTime d1 = getDateOfVisit(sorted.get(j).getVisitID());
					LocalDateTime d2 = getDateOfVisit(nextToAdd.getVisitID());
					if(d1.isAfter(d2)) {
						sorted.add(i, nextToAdd);
						break;
					}
				}
			}
		}
		return sorted;
	}

}
