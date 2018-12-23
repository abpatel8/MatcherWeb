package com.matcher.dao;

import java.sql.*;
import java.util.ArrayList;

import com.matcher.databeans.Event;
import com.matcher.databeans.EventResults;
import com.matcher.databeans.Person;
import com.matcher.util.DTLog;

public class EventResults_Dao implements Db_Dao {


	private static final String EVENT_RESULTS_TABLE_NAME = "matcher_eventresults";


	private String[] columnNames = new String[] {"eventid", "personid", "peoplesaidyesto", "peoplesaidnoto", "notes"};

	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + EVENT_RESULTS_TABLE_NAME + "(eventid integer NOT NULL, personid integer NOT NULL, peoplesaidyesto text, peoplesaidnoto text, notes text, FOREIGN KEY (eventid) REFERENCES matcher_event(id), FOREIGN KEY (personid) REFERENCES matcher_person(id));";
	private static final String INSERT_EVENT_RESULTS = "insert into " + EVENT_RESULTS_TABLE_NAME + "(eventid, personid, peoplesaidyesto, peoplesaidnoto, notes) values (?, ?, ?, ?, ?);";
	private static final String DELETE_EVENT_RESULTS = "delete from " + EVENT_RESULTS_TABLE_NAME + " where eventid = ? and personid = ?;";
	private static final String UPDATE_EVENT_RESULTS = "update " + EVENT_RESULTS_TABLE_NAME + " set peoplesaidyesto = ?, peoplesaidnoto = ?, notes = ? where eventid = ? and personid = ?;";
	private static final String RETRIEVE_EVENT_RESULTS_BY_ID = "select * from " + EVENT_RESULTS_TABLE_NAME + " where eventid = ?;";
	
	public EventResults_Dao(){

		this.createTable();
		
	}

	@Override
	public long add(Object item) {

		try{
			
			if(!(item instanceof EventResults)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(INSERT_EVENT_RESULTS);
			EventResults eventResults = (EventResults)item;
			insertStmt.setLong(1, eventResults.getEventId());
			insertStmt.setLong(2, eventResults.getPersonId());
			insertStmt.setString(3, eventResults.getPeopleWhoSaidYes());
			insertStmt.setString(4, eventResults.getPeopleWhoSaidNo());
			insertStmt.setString(5, eventResults.getNotes());

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
			
			if(!(item instanceof EventResults)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(DELETE_EVENT_RESULTS);
			EventResults eventResults = (EventResults)item;
			insertStmt.setInt(1, eventResults.getEventId());
			insertStmt.setInt(2, eventResults.getPersonId());
			
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
			
			if(!(item instanceof EventResults)){
				return rt_code_unknownerror;
			}

			PreparedStatement insertStmt = DbHelper.openDB(UPDATE_EVENT_RESULTS);
			EventResults eventResults = (EventResults)item;
			insertStmt.setInt(1, eventResults.getEventId());
			insertStmt.setInt(2, eventResults.getPersonId());
			insertStmt.setString(3, eventResults.getPeopleWhoSaidYes());
			insertStmt.setString(4, eventResults.getPeopleWhoSaidNo());
			insertStmt.setString(5, eventResults.getNotes());
		

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
	
	
	public ArrayList<EventResults> retrieveEventResults(Event event) {

		if(event == null || ( event.getId() < 1) || (event.getLocation() == null || event.getLocation().trim().equals(""))) {
			return null;
		}
		
		ArrayList<EventResults> listToReturn = new ArrayList<>();
		
		try{
			

			PreparedStatement insertStmt = DbHelper.openDB(RETRIEVE_EVENT_RESULTS_BY_ID);
			insertStmt.setInt(1, event.getId());
			
			boolean result = insertStmt.execute();
			if(result) {
				ResultSet resultset = insertStmt.getResultSet();
				resultset.next();
				do {
				int eventid = (Integer)resultset.getObject("eventid");
				int personid = (Integer)resultset.getObject("personid");
				String peoplesaidyesto = (String)resultset.getObject("peoplesaidyesto");
				String peoplesaidnoto = (String)resultset.getObject("peoplesaidnoto");
				String notes = (String)resultset.getObject("notes");
				
				EventResults tmpResults = new EventResults();
				tmpResults.setEventId(eventid);
				tmpResults.setPersonId(personid);
				tmpResults.setPeopleWhoSaidYes(peoplesaidyesto);
				tmpResults.setPeopleWhoSaidNo(peoplesaidnoto);
				tmpResults.setNotes(notes);

				listToReturn.add(tmpResults);
				
				}while(resultset.next());
				
				return listToReturn;
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

			DbHelper.deleteTable(EVENT_RESULTS_TABLE_NAME);
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
