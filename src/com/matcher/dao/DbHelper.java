package com.matcher.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

	private static DbHelper helper = new DbHelper();
	private static Connection connection = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;

	private DbHelper() {}

	public static DbHelper getInstance() {
		return helper;
	}

	protected static Statement openDB() throws SQLException {

		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Constants.DATABASE_NAME);
			connection.setAutoCommit(true);
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to
			
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
			throw e;
		}
		
		
		return statement;
	}
	
	protected static PreparedStatement openDB(String sqlStatement) throws SQLException {

		if(sqlStatement == null || sqlStatement.trim().equals("")) {
			throw new SQLException("Prepared Sql Statement was invalid");
		}
		
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_Constants.DATABASE_NAME);
			connection.setAutoCommit(true);
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setQueryTimeout(30); // set timeout to
			
			
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
			throw e;
		}
		
		
		return preparedStatement;
	}
	
	
	protected static void closeDB() throws SQLException {
		
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		
	}
	
	protected static void deleteTable(String tableName) throws SQLException{
		
		if(tableName == null || tableName.trim().equals("")) {
			return;
		}
		
		try {
			
			DbHelper.getInstance();
			statement = DbHelper.openDB();
			statement.executeUpdate("drop table if exists " + tableName + " ;");
			DbHelper.closeDB();
			
		}catch(SQLException e) {
			throw e;
		}
		
		
	}
}
