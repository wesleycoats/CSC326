package edu.ncsu.csc.itrust.model.childbirthVisit;

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

public class ChildRecordMySQL implements Serializable {
	
	private static final long serialVersionUID = 5903078182200761772L;
	@Resource(name = "jdbc/itrust2")
	private ChildRecordSQLLoader crLoader;
	private DataSource ds;
	private ChildRecordValidator validator;

	/**
	 * Default constructor for ChildRecordMySQL.
	 * 
	 * @throws DBException if there is a context lookup naming exception
	 */
	public ChildRecordMySQL() throws DBException {
		crLoader = new ChildRecordSQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new ChildRecordValidator();
	}
	
	/**
	 * Constructor for testing.
	 * 
	 * @param ds
	 */
	public ChildRecordMySQL(DataSource ds) {
		crLoader = new ChildRecordSQLLoader();
		this.ds = ds;
		validator = new ChildRecordValidator();
	}
	
	public List<ChildRecord> getByPatientMID(Long mid) throws DBException {
		
		try (Connection conn = ds.getConnection()){
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM childbirthChildren WHERE motherID = ?");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return crLoader.loadList(rs);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	
	}
	
	public boolean addChildRedcord(ChildRecord cr) throws DBException {
		Connection conn = null;
		PreparedStatement pstring = null;
		try {
			validator.validate(cr);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		
		try {
			conn = ds.getConnection();
			pstring = crLoader.loadParameters(conn, pstring, cr, true);
			int results = pstring.executeUpdate();
			return results != 0;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, pstring);
		}
	}


}
