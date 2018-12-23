package com.matcher.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.matcher.databeans.Event;
import com.matcher.util.DTLog;

public class Event_Dao implements Db_Dao {


	private static final String EVENT_TABLE_NAME = "matcher_event";


	private String[] columnNames = new String[] {"id", "datetime", "location", "peoplewhoattended"};

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, datetime TIMESTAMP, location TEXT, peoplewhoattended TEXT);";
	private static final String INSERT_EVENT = "insert into " + EVENT_TABLE_NAME + "(datetime, location, peoplewhoattended) values (?, ?, ?);";
	private static final String DELETE_EVENT = "delete from " + EVENT_TABLE_NAME + " where id = ?;";
	private static final String UPDATE_EVENT = "update " + EVENT_TABLE_NAME + " set datetime = ?, location = ?, peoplewhoattended = ?, where id = ?;";
	private static final String RETRIEVE_EVENT_BY_ID = "select * from " + EVENT_TABLE_NAME + " where id = ?;";
	private static final String RETRIEVE_EVENT_BY_LOCATION = "select * from " + EVENT_TABLE_NAME + " where location = ?;";

	public Event_Dao(){

		
		this.createTable();
		
	}

	@Override
	public long add(Object item) {

		try{
			
			if(!(item instanceof Event)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(INSERT_EVENT);
			Event event = (Event)item;
			insertStmt.setTimestamp(1, new Timestamp(event.getDatetime()), Calendar.getInstance());
			insertStmt.setString(2, event.getLocation());
			insertStmt.setString(3, event.getPeopleWhoAttended());

			boolean result = insertStmt.execute();
			if(result) {
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
	public long remove(Object item) {

		try{
			
			if(!(item instanceof Event)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(DELETE_EVENT);
			Event event = (Event)item;
			insertStmt.setInt(1, event.getId());
			
			boolean result = insertStmt.execute();
			if(result) {
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
			
			if(!(item instanceof Event)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(UPDATE_EVENT);
			Event event = (Event)item;
			insertStmt.setTimestamp(1, new Timestamp(event.getDatetime()), Calendar.getInstance());
			insertStmt.setString(2, event.getLocation());
			insertStmt.setString(3, event.getPeopleWhoAttended());
		

			boolean result = insertStmt.execute();
			if(result) {
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
	
	public Event retrieveEvent(Event event) {

//		if(event == null || ( event.getId() < 1) || (event.getLocation() == null || event.getLocation().trim().equals(""))) {
//			return null;
//		}
		
		if(event == null || ( event.getId() < 1) ) {
			return null;
		}
		
		try{
			

			PreparedStatement insertStmt = DbHelper.openDB(RETRIEVE_EVENT_BY_ID);
			insertStmt.setInt(1, event.getId());
			
			boolean result = insertStmt.execute();
			if(result) {
				ResultSet resultset = insertStmt.getResultSet();
				resultset.next();
				int id = (Integer)resultset.getObject("id");
				Timestamp createdate = resultset.getTimestamp("datetime");
				long createdatelong = createdate.getTime();
				String location = (String)resultset.getObject("location");
				String peopleWhoAttended = (String)resultset.getObject("peoplewhoattended");
				
				Event tempEvent = new Event();
				tempEvent.setId(id);
				tempEvent.setDatetime(createdatelong);
				tempEvent.setLocation(location);
				tempEvent.setPeopleWhoAttended(peopleWhoAttended);
				
				return tempEvent;
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

	@Override
	public long removeAll() {
		try{

			DbHelper.deleteTable(EVENT_TABLE_NAME);
			return rt_code_success;

		}catch(SQLException e) {
			DTLog.Print(this.getClass().getName(), DTLog.error, e.getMessage());
			return rt_code_deleteerror;
		}
	}

	public long createTable() {

		try{
			

			PreparedStatement insertStmt = DbHelper.openDB(CREATE_TABLE);
			
			boolean result = insertStmt.execute();
			if(result) {
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

	
}
