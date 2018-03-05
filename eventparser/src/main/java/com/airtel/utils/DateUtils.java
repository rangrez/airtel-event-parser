package com.airtel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static boolean isDatesInaWeek(String fromDate, String toDate) {
		Date startDate = parseDate(fromDate);
		Date endDate = parseDate(toDate);
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, 7);
		if (c.getTime().compareTo(endDate) < 0) {
			return false;
		}
		return true;
	}

	public static Date parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return d;
	}

}
