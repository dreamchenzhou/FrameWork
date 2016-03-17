package com.umframework.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 
 * @author martin.zheng
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateManager
{
	public static final SimpleDateFormat sdf_standard = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private DateManager()
	{

	}

	public static String toString(Date date)
	{
		String text = sdf_standard.format(date);
		return text;
	}

	public static String toString(Calendar calendar)
	{
		return toString(calendar.getTime());
	}

	public static String toString(long milliseconds)
	{
		return toString(new Date(milliseconds));
	}

	public static String toString(Date date, String pattern)
	{
		SimpleDateFormat sdf_custom = new SimpleDateFormat(pattern);
		String text = sdf_custom.format(date);
		return text;
	}

	public static String toString(Calendar calendar, String pattern)
	{
		return toString(calendar.getTime(), pattern);
	}

	public static String toString(long milliseconds, String pattern)
	{
		return toString(new Date(milliseconds), pattern);
	}

	public static Date toDate(String string) throws ParseException
	{
		Date date = sdf_standard.parse(string);
		return date;
	}

	public static Calendar toCalendar(String string) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		Date date = sdf_standard.parse(string);
		calendar.setTime(date);
		return calendar;
	}

	public static long toMilliseconds(String string) throws ParseException
	{
		long milliseconds = 0;
		Date date = sdf_standard.parse(string);
		milliseconds = date.getTime();
		return milliseconds;
	}

	public static Date toDate(String string, String pattern)
			throws ParseException
	{
		SimpleDateFormat sdf_custom = new SimpleDateFormat(pattern);
		Date date = sdf_custom.parse(string);
		return date;
	}

	public static Calendar toCalendar(String string, String pattern)
			throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		Date date = toDate(string, pattern);
		calendar.setTime(date);
		return calendar;
	}

	public static long toMilliseconds(String string, String pattern)
			throws ParseException
	{
		long milliseconds = 0;
		Date date = toDate(string, pattern);
		milliseconds = date.getTime();
		return milliseconds;
	}
}
