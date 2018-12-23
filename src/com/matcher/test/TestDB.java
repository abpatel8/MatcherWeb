package com.matcher.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.matcher.dao.Db_Dao;
import com.matcher.dao.EventResults_Dao;
import com.matcher.dao.Event_Dao;
import com.matcher.dao.Person_Dao;
import com.matcher.databeans.Event;
import com.matcher.databeans.EventResults;
import com.matcher.databeans.Person;
import com.matcher.util.DTLog;
import com.matcher.util.Utils;

public class TestDB {
	
	public long startTime = 0;
	public long endTime = 0;
	
	public TestDB() {
		
		startTime = System.currentTimeMillis();
		
		String testFileName = "C:\\Users\\ankit\\OneDrive\\Synced_Backup\\Dev_Related\\eclipse_workspace\\Matcher\\test_file.csv";
		
		/*
		 * 1. Clear all the people / events / event-results from the database
		 */
		Person_Dao pdao = new Person_Dao();
		long result = pdao.removeAll();
		if(result == Db_Dao.rt_code_success) {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Completed Deleting People");
		}else {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Error Deleting People");
		}
		
		Event_Dao edao = new Event_Dao();
		result = edao.removeAll();
		if(result == Db_Dao.rt_code_success) {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Completed Deleting Events");
		}else {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Error Deleting Events");
		}
		
		EventResults_Dao erdao = new EventResults_Dao();
		result = erdao.removeAll();
		if(result == Db_Dao.rt_code_success) {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Completed Deleting Event-Results");
		}else {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Error Deleting Event-Results");
		}
		
		/*
		 * 2. Read the people from the test file
		 */
		ArrayList<Person> people = readTestFile(testFileName);
		
		/*
		 * 3. add the people to the db
		 */
		addPeopleToDB(people);
		
		
		/*
		 * 4. Create event
		 */
		Event event = new Event();
		event.setDatetime(System.currentTimeMillis());
		event.setLocation("Southside SF");
		StringBuffer sb = new StringBuffer();
		for(Person p : people) {
			Person tempPerson = pdao.retrievePerson(p);
			int id = tempPerson.getId();
			sb.append(id + ",");
		}
		
		String peopleWhoAttended = sb.toString();
		if(peopleWhoAttended.endsWith(",")) {
			peopleWhoAttended = peopleWhoAttended.substring(0,  peopleWhoAttended.length()-1);
		}
		event.setPeopleWhoAttended(peopleWhoAttended);
		
		edao = new Event_Dao();
		long evtResult = edao.add(event);
		if(evtResult == Db_Dao.rt_code_success) {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Completed creating event");
		}
		
		
		/*
		 * 5. Collect event results
		 */
		ArrayList<EventResults> listOfER = new ArrayList<>();
		ArrayList<Person> peopleWhoAttendedList = new ArrayList<>();
		erdao = new EventResults_Dao();
		//retrieve all the people from the event
		// for each person, add in a few Yes' and No's
		edao = new Event_Dao();
		event = new Event();
		event.setId(1);
		event = edao.retrieveEvent(event);
		if(event != null) {
			String peopleWhoAttendedStr = event.getPeopleWhoAttended();
			if(peopleWhoAttendedStr != null) {
				StringTokenizer st = new StringTokenizer(peopleWhoAttendedStr, ",");
				DTLog.Print(this.getClass().getSimpleName(), DTLog.debug, "People Who Attended: " + peopleWhoAttendedStr);
				while(st.hasMoreTokens()) {
					String personIdStr = st.nextToken();
					int personId = Integer.parseInt(personIdStr);
					EventResults onePersonsResults = new EventResults();
					onePersonsResults.setEventId(event.getId());
					onePersonsResults.setPersonId(personId);
					
					String yes = "";
					String no = "";
					switch(personId){
					case 1: 
						yes = "2";
						no = "4";
						break;
					case 2:
						yes = "1,3";
						no = "";
						break;
					case 3:
						yes = "2,4";
						no = "";
						break;
					case 4:
						yes = "3";
						no = "1";
						break;
						
					}
					
					onePersonsResults.setPeopleWhoSaidNo(no);
					onePersonsResults.setPeopleWhoSaidYes(yes);
//					onePersonsResults.setPeopleWhoSaidNo("2,3");
//					onePersonsResults.setPeopleWhoSaidYes("4,5,6");
					listOfER.add(onePersonsResults);
					erdao.add(onePersonsResults);
				}
			}
		}
		/*
		 * 6. run the matching 
		 */
		erdao = new EventResults_Dao();
		listOfER = erdao.retrieveEventResults(event);
		Map<Integer, ArrayList<Integer>> mapOfPeopleWhoSaidYestoKey = new TreeMap<>();
		Map<Integer, ArrayList<Integer>> mapOfPeopleWhoSaidNotoKey = new TreeMap<>();
		for(int p=0;p<listOfER.size();p++) {
			EventResults tempResult = listOfER.get(p);
			int keyPersonId = tempResult.getPersonId();
			DTLog.Print(this.getClass().getSimpleName(), DTLog.debug, "Going Through The Results Of: " + keyPersonId);
			ArrayList<Integer> peopleWhoSaidYesTo = tempResult.getArrayOfPeopleWhoSaidYes();
			DTLog.Print(this.getClass().getSimpleName(), DTLog.debug,  keyPersonId + " said Yes to:" + peopleWhoSaidYesTo.toString());
			for(int personId : peopleWhoSaidYesTo) {
				if(mapOfPeopleWhoSaidYestoKey.containsKey(personId)) {
					ArrayList<Integer> tempYesList = mapOfPeopleWhoSaidYestoKey.get(personId);
					tempYesList.add(keyPersonId);
					mapOfPeopleWhoSaidYestoKey.put(personId, tempYesList);
				}else {
					ArrayList<Integer> tempYesList = new ArrayList<>();
					tempYesList.add(keyPersonId);
					mapOfPeopleWhoSaidYestoKey.put(personId, tempYesList);
				}
			}
			
			ArrayList<Integer> peopleWhoSaidNoTo = tempResult.getArrayOfPeopleWhoSaidNo();
			DTLog.Print(this.getClass().getSimpleName(), DTLog.debug,  keyPersonId + " said No to:" + peopleWhoSaidNoTo.toString());
			for(int personId : peopleWhoSaidNoTo) {
				if(mapOfPeopleWhoSaidNotoKey.containsKey(personId)) {
					ArrayList<Integer> tempNoList = mapOfPeopleWhoSaidNotoKey.get(personId);
					tempNoList.add(keyPersonId);
					mapOfPeopleWhoSaidNotoKey.put(personId, tempNoList);
				}else {
					ArrayList<Integer> tempNoList = new ArrayList<>();
					tempNoList.add(keyPersonId);
					mapOfPeopleWhoSaidNotoKey.put(personId, tempNoList);
				}
			}
		}
		
		Set<Integer> yesKeySet = mapOfPeopleWhoSaidYestoKey.keySet();
		Iterator<Integer> yesKeyIter = yesKeySet.iterator();
		while(yesKeyIter.hasNext()) {
			int personId = yesKeyIter.next();
//			StringBuffer peopleWhoSaidYesSB = new StringBuffer();
			ArrayList<Integer> peopleWhoSaidYesArr = mapOfPeopleWhoSaidYestoKey.get(personId);
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Person ID: " + personId + ", was said YES to by: " + peopleWhoSaidYesArr.toString());
		}
		
		Set<Integer> noKeySet = mapOfPeopleWhoSaidNotoKey.keySet();
		Iterator<Integer> noKeyIter = noKeySet.iterator();
		while(noKeyIter.hasNext()) {
			int personId = noKeyIter.next();
//			StringBuffer peopleWhoSaidYesSB = new StringBuffer();
			ArrayList<Integer> peopleWhoSaidNoArr = mapOfPeopleWhoSaidNotoKey.get(personId);
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Person ID: " + personId + ", was DENIED by: " + peopleWhoSaidNoArr.toString());
		}
		
		
		endTime = System.currentTimeMillis();
		DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Processing Time: " +  Utils.getRuntime(endTime - startTime));
		
		
	}
	
	public ArrayList<Person> readTestFile(String fileName) {
		
		ArrayList<Person> fileData = new ArrayList<>();
		
		try {

			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			int count = 0;
	
			String fullLine;
			while ((fullLine = br.readLine()) != null) {
				
				if(fullLine.startsWith("#")) {
					continue;
				}
				
				count++;
				
				StringTokenizer st = new StringTokenizer(fullLine, ",");
				while(st.hasMoreTokens()) {
					String name = st.nextToken();
					String email = st.nextToken();
					String phonenumber = st.nextToken();
					String gender = st.nextToken();
					
					Person tempPerson = new Person();
					tempPerson.setName(name);
					tempPerson.setEmail(email);
					tempPerson.setPhoneNumber(phonenumber);
					tempPerson.setGender(gender);
					fileData.add(tempPerson);
				}
			
				
			} // ENd while()
			
			

			in.close();
			
			DTLog.Print(this.getClass().getSimpleName(), DTLog.info, "Completed Reading File, total records read: " + count);
			


		} catch (Exception e) {
			DTLog.Print(this.getClass().getSimpleName(), DTLog.error, "Error Reading File: " + e.getMessage());

		}
		
		return fileData;
		
	}
	
	public void addPeopleToDB(ArrayList<Person> people) {
		
		if(people == null || people.size()<1) {
			return;
		}
		
		for(Person p : people) {
			Person_Dao dao = new Person_Dao();
			dao.add(p);
		}
		
	}

	public static void main(String[] args) {
		
		TestDB test = new TestDB();
		
	}
	
}
