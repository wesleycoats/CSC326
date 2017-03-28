/**
 * 
 */
package edu.ncsu.csc.itrust.model.obstetricsVisit;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;

/**
 * @author rvisador
 *
 */
@ManagedBean
public class ObstetricsDataMySQL implements Serializable {
	private static final long serialVersionUID = 3514174592439259251L;
	@Resource(name = "jdbc/itrust2")
	private ObstetricsDataSQLLoader ovLoader;
	private DataSource ds;

	/**
	 * Default constructor for obstetricsDataMySQL.
	 * 
	 * @throws DBException if there is a context lookup naming exception
	 */
	public ObstetricsDataMySQL() throws DBException {
		ovLoader = new ObstetricsDataSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 */
	public ObstetricsDataMySQL(DataSource ds) {
		ovLoader = new ObstetricsDataSQLLoader();
		this.ds = ds;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ObstetricsData> getVisitsForPatient(Long patientID) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		ResultSet results = null;
		if (ValidationFormat.NPMID.getRegex().matcher(Long.toString(patientID)).matches()) {
			try {
				conn = ds.getConnection();
				pstring = conn.prepareStatement("SELECT * FROM obstetrics WHERE patientMID=?  ORDER BY dateCreated DESC");
				pstring.setLong(1, patientID);
				results = pstring.executeQuery();

				final List<ObstetricsData> visitList = ovLoader.loadList(results);
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
	
	public boolean addObstetricsData(ObstetricsData ov) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		
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

	public boolean update(ObstetricsData ov) throws DBException {
		boolean retval = false;
		Connection conn = null;
		PreparedStatement pstring = null;
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
	
	public LocalDateTime getLmpDate(long patientMID) {
		LocalDateTime date = null;
		List<ObstetricsData> unsorted;
		try {
			unsorted = this.getVisitsForPatient(patientMID);
			if(unsorted.size() > 0) {
				date = unsorted.get(0).getDateCreated();
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
}