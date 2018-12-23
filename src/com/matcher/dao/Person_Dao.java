package com.matcher.dao;

import java.sql.*;
import java.util.Calendar;

import com.matcher.databeans.Person;
import com.matcher.util.DTLog;
import com.matcher.util.Utils;

public class Person_Dao implements Db_Dao {


	private static final String PERSON_TABLE_NAME = "matcher_person";



	private String[] columnNames = new String[] {"id", "name", "email", "phonenumber", "gender", "birthdate", "createdate"};

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + PERSON_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT UNIQUE, phonenumber TEXT UNIQUE, gender TEXT, birthdate DATE, createdate TIMESTAMP);";
	private static final String INSERT_PERSON = "insert into " + PERSON_TABLE_NAME + "(name, email, phonenumber, gender, birthdate, createdate) values (?, ?, ?, ?, ?, ?);";
	private static final String DELETE_PERSON = "delete from " + PERSON_TABLE_NAME + " where id = ?;";
	private static final String UPDATE_PERSON = "update " + PERSON_TABLE_NAME + " set name = ?, email = ?, phonenumber = ?, gender = ?, birthdate = ? where id = ?;";
	private static final String RETRIEVE_PERSON_BY_EMAIL = "select * from " + PERSON_TABLE_NAME + " where email = ?;";

	public Person_Dao(){

		this.createTable();
		
	}

	@Override
	public long add(Object item) {

		try{
			
			if(!(item instanceof Person)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(INSERT_PERSON);
			Person person = (Person)item;
			insertStmt.setString(1, person.getName());
			insertStmt.setString(2, person.getEmail());
			insertStmt.setString(3, person.getPhoneNumber());
			insertStmt.setString(4, person.getGender());
			insertStmt.setDate(5, Utils.convertToSqlDate(person.getBirthdate()));
			insertStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()), Calendar.getInstance());

			boolean isResultSet = insertStmt.execute();
			if(isResultSet) {
				if(insertStmt.getUpdateCount() == 1){
					return rt_code_success;
				}else {
					return rt_code_itemexists;
				}	
			}else {
				System.out.println(insertStmt.getUpdateCount());
				if(insertStmt.getUpdateCount() == 1){
					return rt_code_success;
				}else {
					return rt_code_itemexists;
				}	
			}
			
			
		}catch(SQLException e){
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			if(e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
				return rt_code_itemexists;
			}else {
				return rt_code_unknownerror;
			}
		}finally {
			try {
				DbHelper.closeDB();	
			}catch(SQLException e){
				DTLog.Print(this.getClass().getName(), DTLog.error, "Error closing DB, e=" + e.getMessage());
				return rt_code_unknownerror;
			}
			
		}

	}

	@Override
	public long remove(Object item) {

		try{
			
			if(!(item instanceof Person)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(DELETE_PERSON);
			Person person = (Person)item;
			insertStmt.setInt(1,person.getId());
			
			boolean isResultSet = insertStmt.execute();
			if(isResultSet) {
				if(insertStmt.getUpdateCount() == 1){
					return rt_code_success;
				}else {
					return rt_code_itemexists;
				}	
			}else {
				return rt_code_unknownerror;
			}
			
			
		}catch(SQLException e){
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			return rt_code_unknownerror;
		}finally {
			try {
				DbHelper.closeDB();	
			}catch(SQLException e){
				DTLog.Print(this.getClass().getName(), DTLog.error, "Error closing DB");
				return rt_code_unknownerror;
			}
			
		}	

	}

	@Override
	public long update(Object item) {
		
		try{
			
			if(!(item instanceof Person)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(UPDATE_PERSON);
			Person person = (Person)item;
			insertStmt.setString(1, person.getName());
			insertStmt.setString(2, person.getEmail());
			insertStmt.setString(3, person.getPhoneNumber());
			insertStmt.setString(4, person.getGender());
			insertStmt.setDate(5, Utils.convertToSqlDate(person.getBirthdate()));
			insertStmt.setTimestamp(6, new Timestamp(person.getCreateDate_UnixTS()), Calendar.getInstance());

			boolean isResultSet = insertStmt.execute();
			if(isResultSet) {
				if(insertStmt.getUpdateCount() == 1){
					return rt_code_success;
				}else {
					return rt_code_itemexists;
				}	
			}else {
				return rt_code_unknownerror;
			}
			
			
		}catch(SQLException e){
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			return rt_code_unknownerror;
		}finally {
			try {
				DbHelper.closeDB();	
			}catch(SQLException e){
				DTLog.Print(this.getClass().getName(), DTLog.error, "Error closing DB");
				return rt_code_unknownerror;
			}
			
		}
	}

	@Override
	public long removeAll() {
		try{

			DbHelper.deleteTable(PERSON_TABLE_NAME);
			return rt_code_success;

		}catch(SQLException e) {
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			return rt_code_deleteerror;
		}
	}

	public long createTable() {

		try{
			

			PreparedStatement insertStmt = DbHelper.openDB(CREATE_TABLE);
			
			boolean isResultSet = insertStmt.execute();
			if(isResultSet) {
				if(insertStmt.getUpdateCount() == 1){
					return rt_code_success;
				}else {
					return rt_code_itemexists;
				}	
			}else {
				return rt_code_unknownerror;
			}
			
			
		}catch(SQLException e){
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			return rt_code_unknownerror;
		}finally {
			try {
				DbHelper.closeDB();	
			}catch(SQLException e){
				DTLog.Print(this.getClass().getName(), DTLog.error, "Error closing DB");
				return rt_code_unknownerror;
			}
			
		}

	}


	public Person retrievePerson(Person person) {

		if(person == null || (person.getEmail() == null || person.getEmail().trim().equals("")) || (person.getPhoneNumber() == null || person.getPhoneNumber().trim().equals(""))) {
			return null;
		}
		String email = person.getEmail();
		try{
			

			PreparedStatement insertStmt = DbHelper.openDB(RETRIEVE_PERSON_BY_EMAIL);
			insertStmt.setString(1, email);
			
			boolean isResultSet = insertStmt.execute();
			if(isResultSet) {
				ResultSet resultset = insertStmt.getResultSet();
				int id = (Integer)resultset.getObject("id");
				String name = (String)resultset.getObject("name");
				String emailStr = (String)resultset.getObject("email");
				String phonenumber = (String)resultset.getObject("phonenumber");
				String gender = (String)resultset.getObject("gender");
				String birthdate = Utils.convertSqlDateToString((Date)resultset.getDate("birthdate"));
				Timestamp createdate = resultset.getTimestamp("createdate");
				long createdatelong = createdate.getTime();
				
				Person tempPerson = new Person();
				tempPerson.setId(id);
				tempPerson.setName(name);
				tempPerson.setEmail(emailStr);
				tempPerson.setPhoneNumber(phonenumber);
				tempPerson.setGender(gender);
				tempPerson.setBirthdate(birthdate);
				tempPerson.setCreateDate_UnixTS(createdatelong);
				
				return tempPerson;
			}else {
				return null;
			}
			
			
		}catch(SQLException e){
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			return null;
		}finally {
			try {
				DbHelper.closeDB();	
			}catch(SQLException e){
				DTLog.Print(this.getClass().getName(), DTLog.error, "Error closing DB");
				return null;
			}
			
		}

	}

	
}
