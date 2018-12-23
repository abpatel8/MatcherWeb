package com.matcher.dao;


public interface Db_Dao {

	public static final long rt_code_success = 00;
	public static final long rt_code_unknownerror = -99;
	public static final long rt_code_itemexists = -98;
	public static final long rt_code_deleteerror = -97;
	public static final long rt_code_updateerror = -96;
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	public long createTable();
	public long add(Object item);
	public long remove(Object item);
	public long update(Object item);
	public long removeAll();
	
}
