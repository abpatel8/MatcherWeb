package com.matcher.databeans;

public class Event {

	private int id;
	private long datetime;
	private String location;
	private String peopleWhoAttended;
	
	public Event() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPeopleWhoAttended() {
		return peopleWhoAttended;
	}

	public void setPeopleWhoAttended(String peopleWhoAttended) {
		this.peopleWhoAttended = peopleWhoAttended;
	}
	
	
}
