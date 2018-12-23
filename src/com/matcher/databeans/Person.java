package com.matcher.databeans;

public class Person {

	private int id;
	private String name;
	private String email;
	private String phoneNumber;
	private String gender;
	private String birthdate; //YYYY-MM-DD
	private long createDate_UnixTS;
//	LinkedList<Person> listOfPeople_Yes = new LinkedList<>();
//	LinkedList<Person> listOfPeople_No = new LinkedList<>();
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public long getCreateDate_UnixTS() {
		return createDate_UnixTS;
	}

	public void setCreateDate_UnixTS(long createDate_UnixTS) {
		this.createDate_UnixTS = createDate_UnixTS;
	}
	
}
