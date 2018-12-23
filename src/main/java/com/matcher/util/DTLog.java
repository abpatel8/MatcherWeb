package com.matcher.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DTLog {
	
	public static String APPNAME = "SFDMP";

	public static String debug = "DEBUG";
	public static String warn = "WARN";
	public static String info = "INFO";
	public static String error = "ERROR";
	public static boolean debugOn = true;

	public static void Print(String className, String level, String message, String ipAddress){
		
		String DATE_FORMAT = "MM/dd/yyyy - hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance(); // today
		String date = sdf.format(c1.getTime());
		
		
		if(level.equals(DTLog.error)){
			System.err.println(APPNAME + "|" + date + "|" + className + "|Error|" + message + "|" + ipAddress);
		}else if(level.equals(DTLog.debug)){
			if(debugOn){
				System.out.println(APPNAME + "|" + date + "|" + className + "|" + level + "|" + message + "|" + ipAddress);
			}
		}
		else {
			System.out.println(APPNAME + "|" + date + "|" + className + "|" + level + "|" + message + "|" + ipAddress);
		}
		
	}
	
	public static void Print(String className, String level, String message){
		
		
		String DATE_FORMAT = "MM/dd/yyyy - hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance(); // today
		String date = sdf.format(c1.getTime());
		
		
		if(level.equals(DTLog.error)){
			System.err.println(APPNAME + "|" + date + "|" + className + "|Error|" + message);
		}else if(level.equals(DTLog.debug)){
			if(debugOn){
				System.out.println(APPNAME + "|" + date + "|" + className + "|" + level + "|" + message);
			}
		}
		else {
			System.out.println(APPNAME + "|" + date + "|" + className + "|" + level + "|" + message);
		}
		
	}
	
	

}
