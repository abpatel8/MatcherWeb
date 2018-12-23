package com.matcherweb.utils;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

	public static ArrayList<String> getSpecificRequestParameterNames(HttpServletRequest request, String contains) {
		ArrayList<String> listOfNames = new ArrayList<String>();
		Enumeration<String> enumList = request.getParameterNames();
		while (enumList.hasMoreElements()) {
			String tempParamName = enumList.nextElement();
			if (tempParamName.contains(contains)) {
				listOfNames.add(tempParamName);
			}
		}
		return listOfNames;
	}

	public static String getParameterValueSafely(HttpServletRequest request, String paramName, boolean returnBlank,	boolean printValue) {
		String value = request.getParameter(paramName);
		if (value == null && returnBlank) {
			value = "";
		}
		if (printValue && value == null) {
			System.out.println("Parameter: " + paramName + " is NULL");
		} else if (printValue && value != null) {
			System.out.println("Parameter: " + paramName + " , Value: " + value);
		}
		if (value != null) {
			value = value.trim();
		}
		return value;
	}

}
