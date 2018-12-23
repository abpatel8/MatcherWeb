package com.matcher.databeans;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EventResults {

	private int eventId;
	private int personId;
	private String peopleSaidYesTo;
	private String peopleSaidNoTo;
	private String notes;

	
	public EventResults() {
		
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getPeopleWhoSaidYes() {
		return peopleSaidYesTo;
	}

	public void setPeopleWhoSaidYes(String peopleWhoSaidYes) {
		this.peopleSaidYesTo = peopleWhoSaidYes;
	}

	public String getPeopleWhoSaidNo() {
		return peopleSaidNoTo;
	}

	public void setPeopleWhoSaidNo(String peopleWhoSaidNo) {
		this.peopleSaidNoTo = peopleWhoSaidNo;
	}
	

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	public ArrayList<Integer> getArrayOfPeopleWhoSaidYes(){
		ArrayList<Integer> listToReturn = new ArrayList<>();
		
		if(this.peopleSaidYesTo == null || this.peopleSaidYesTo.trim().equals("")) {
			return listToReturn;
		}
		
		StringTokenizer st = new StringTokenizer(this.peopleSaidYesTo, ",");
		while(st.hasMoreTokens()) {
			String personIdStr = st.nextToken();
			int personId = Integer.parseInt(personIdStr);
			listToReturn.add(personId);
		}
		
		return listToReturn;
	}
	
	public ArrayList<Integer> getArrayOfPeopleWhoSaidNo(){
		ArrayList<Integer> listToReturn = new ArrayList<>();
		
		if(this.peopleSaidNoTo == null || this.peopleSaidNoTo.trim().equals("")) {
			return listToReturn;
		}
		
		StringTokenizer st = new StringTokenizer(this.peopleSaidNoTo, ",");
		while(st.hasMoreTokens()) {
			String personIdStr = st.nextToken();
			int personId = Integer.parseInt(personIdStr);
			listToReturn.add(personId);
		}
		
		return listToReturn;
	}

	
}
