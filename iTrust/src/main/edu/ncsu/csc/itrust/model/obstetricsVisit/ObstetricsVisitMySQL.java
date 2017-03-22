/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ValidationFormat;

/**
 * @author rvisador
 *
 */
@ManagedBean
public class ObstetricsVisitMySQL implements Serializable {
	private static final long serialVersionUID = 5903078182200761772L;
	@Resource(name = "jdbc/itrust2")
	private ObstetricsVisitSQLLoader ovLoader;
	private DataSource ds;
	private ObstetricsVisitValidator validator;

	/**
	 * Default constructor for obstetricsVisitMySQL.
	 * 
	 * @throws DBException if there is a context lookup naming exception
	 */
	public ObstetricsVisitMySQL() throws DBException {
		ovLoader = new ObstetricsVisitSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new ObstetricsVisitValidator();
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 */
	public ObstetricsVisitMySQL(DataSource ds) {
		ovLoader = new ObstetricsVisitSQLLoader();
		this.ds = ds;
		validator = new ObstetricsVisitValidator();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ObstetricsVisit> getVisitsForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM obstetricsVisit WHERE patientMID=?");

				pstring.setLong(1, patientID);

				results = pstring.executeQuery();

				final List<ObstetricsVisit> visitList = ovLoader.loadList(results);
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
	
	public boolean addObstetricsVisit(ObstetricsVisit ov) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(ov);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		
		try {
			conn = ds.getConnection();
			pstring = ovLoader.loadParameters(conn, pstring, ov, true);
			int results = pstring.executeUpdate();
			return results != 0;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}

	public List<ObstetricsVisit> getAll() throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsVisit");
			results = pstring.executeQuery();
			final List<ObstetricsVisit> visitList = ovLoader.loadList(results);
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

	public ObstetricsVisit getByVisitID(long id) throws DBException {
		ObstetricsVisit ret = null;
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		List<ObstetricsVisit> visitList = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT * FROM obstetricsVisit WHERE visitID=?");

			pstring.setLong(1, id);

			results = pstring.executeQuery();

			/* May update with loader instead */
			visitList = ovLoader.loadList(results);
			if (visitList.size() > 0) {
				ret = visitList.get(0);
			}
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
		return ret;
	}

	public boolean update(ObstetricsVisit ov) throws DBException {
		boolean retval = false;
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(ov);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		int results;

		try {
			conn = ds.getConnection();
			pstring = ovLoader.loadParameters(conn, pstring, ov, false);
			results = pstring.executeUpdate();
			retval = (results > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
		return retval;
	}

	public LocalDate getDateOfVisit(final Long visitID) {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		java.sql.Date dateOfVisit = null;
		try {
			conn = ds.getConnection();
			pstring = conn.prepareStatement("SELECT visitDate FROM officeVisit WHERE visitID=?");
			pstring.setLong(1, visitID);
			results = pstring.executeQuery();
			if (!results.next()) {
				return null;
			}
			dateOfVisit = results.getDate("visitDate");
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

		return dateOfVisit.toLocalDate();
	}
	
	public List<ObstetricsVisit> sortByDate(List<ObstetricsVisit> unsorted) {
		List<ObstetricsVisit> sorted = new LinkedList<ObstetricsVisit>();
		for(int i = 0; i < unsorted.size(); i++) {
			ObstetricsVisit nextToAdd = unsorted.get(i);
			for(int j = 0; j <= sorted.size(); j++) {
				if(j == sorted.size()) {
					sorted.add(unsorted.get(i));
					break;
				} else {
					LocalDate d1 = getDateOfVisit(sorted.get(j).getVisitID());
					LocalDate d2 = getDateOfVisit(nextToAdd.getVisitID());
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