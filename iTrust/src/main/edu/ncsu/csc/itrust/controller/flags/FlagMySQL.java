package edu.ncsu.csc.itrust.controller.flags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class FlagMySQL {
	
	private FlagMySQLLoader loader;
	private DataSource ds;
	private FlagValidator validator;


	public FlagMySQL() throws DBException {
		loader = new FlagMySQLLoader();
		try {
			Context ctx = new InitialContext();
			this.ds = ((DataSource) (((Context) ctx.lookup("java:comp/env"))).lookup("jdbc/itrust"));
		} catch (NamingException e) {
			throw new DBException(new SQLException("Context Lookup Naming Exception: " + e.getMessage()));
		}
		validator = new FlagValidator();
	}
	
	public FlagMySQL(DataSource ds) {
		loader = new FlagMySQLLoader();
		this.ds = ds;
		validator = new FlagValidator();
	}
	
	public boolean add(Flag f) throws DBException {
		boolean successfullyAdded = false;
		Connection conn = null;
		PreparedStatement addStatement = null;
		try {
			validator.validate(f);
		} catch (FormValidationException e1) {
			throw new DBException(new SQLException(e1.getMessage()));
		}
		try {
			conn = ds.getConnection();
			addStatement = loader.loadParameters(conn, addStatement, f, true);
			int exitStatus = addStatement.executeUpdate();
			successfullyAdded = (exitStatus > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, addStatement);
		}
		return successfullyAdded;
	}
	
	public List<Flag> getByPatientID(long MID) throws DBException {
		Connection conn = null;
		PreparedStatement query = null;
		ResultSet results = null;
		List<Flag> flagList = null;
		try {
			conn = ds.getConnection();
			query = conn.prepareStatement("SELECT * FROM flags WHERE MID = ? AND pregID = ?");
			query.setLong(1, MID);
			query.setLong(2, 1);
			results = query.executeQuery();

			flagList = loader.loadList(results);
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
		return flagList;
	}
}
