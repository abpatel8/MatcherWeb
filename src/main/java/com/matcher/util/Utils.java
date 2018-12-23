package com.matcher.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

	public static String getRuntime(long ms){
		int SECOND = 1000;
		int MINUTE = 60 * SECOND;
		int HOUR = 60 * MINUTE;
		int DAY = 24 * HOUR;

		StringBuffer text = new StringBuffer("");
		if (ms > DAY) {
		  text.append(ms / DAY).append(" days ");
		  ms %= DAY;
		}
		if (ms > HOUR) {
		  text.append(ms / HOUR).append(" hours ");
		  ms %= HOUR;
		}
		if (ms > MINUTE) {
		  text.append(ms / MINUTE).append(" minutes ");
		  ms %= MINUTE;
		}
		if (ms > SECOND) {
		  text.append(ms / SECOND).append(" seconds ");
		  ms %= SECOND;
		}
		text.append(ms + " ms");
		
		return text.toString();
	}
	
	public static String getDateString(int numDays, String format){
		   DateFormat dateFormat = new SimpleDateFormat(format);
		   //get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		   
		   if(numDays != 0){
			   cal.add(Calendar.DATE, numDays);
		   }
		   //System.out.println(cal.getTimeZone().toString());
		   return(dateFormat.format(cal.getTime()));
	}
	
	public static Date convertToSqlDate(String date) {
		if(date == null || date.trim().equals("")) {
			return null;
		}

		Date sqlDate = null;
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date tempDate = dateFormat.parse(date);
			sqlDate = new Date(tempDate.getTime());
		
		}catch(ParseException pe) {
			DTLog.Print(Utils.class.getSimpleName(), DTLog.error, "Date ParseException: " + pe.getMessage());
		}
	
		return sqlDate;
	}
	
	public static String convertSqlDateToString(Date sqlDate) {
		
		if(sqlDate == null) {
			return "";
		}
		
		String dateToReturn = "";
		try {
			
			long datetime = sqlDate.getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(datetime);
			dateToReturn = dateFormat.format(cal.getTime());
		
		}catch(Exception pe) {
			DTLog.Print(Utils.class.getSimpleName(), DTLog.error, "Date Exception: " + pe.getMessage());
		}
		
		return dateToReturn;
		
	}
	
	public static String formatLong(long number){
		try{
			return NumberFormat.getInstance(Locale.ENGLISH).format(number);
		} catch (Exception e){
			return "" + number;
		}
	}
	
	public static String encodeString(String str){
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

}
