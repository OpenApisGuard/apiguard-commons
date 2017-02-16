package org.apiguard.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormater {
	
	private final static String dateTimeFormatPattern = "yyyy-MM-dd HH:mm:ss z";
	private final static DateFormat format = new SimpleDateFormat(dateTimeFormatPattern);
	
	public static String toString(Date date) {
		return format.format(date);
	}

}
