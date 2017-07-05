package org.apiguard.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormater {
	
	private final static String dateTimeFormatPattern = "yyyy-MM-dd HH:mm:ss z";

	private final static DateFormat format = new SimpleDateFormat(dateTimeFormatPattern);

	public static String toString(Date date) {
		return format.format(date);
	}

	public static Date toDate(String date) {
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

}
