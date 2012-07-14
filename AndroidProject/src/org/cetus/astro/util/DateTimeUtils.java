/*
 * Copyright (C) 2011-2012 Inaki Ortiz de Landaluce Saiz
 * 
 * This program is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program. If not, see 
 * <http://www.gnu.org/licenses/>
 */
package org.cetus.astro.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class DateTimeUtils {

	/**
	 * Calculates the difference between the Universal Time and the Terrestrial
	 * Time (also known as Dynamical Time) given a year within the 21st century.
	 * The exact value of such difference can be deduced only from observations.
	 * However an approximate value can be obtained through interpolation by
	 * means of an expression due to Chapront and Francou and issued by the
	 * Bureau des Longitudes in Paris on December 1977.
	 * 
	 * @param year
	 *            year within the current century.
	 */
	public static double deltaTimeCurrentCentury(int year) {
		// calculate time measured in centuries from epoch 2000.0
		double t = (double) (year - 2000) / 100;
		return (102 + 102 * t + 25.3 * t * t + (year - 2100) * 0.37);
	}

	/**
	 * Converts a Julian Day into a GregorianCalendar
	 * 
	 * @param jd
	 *            the Julian Day
	 * @return a GregorianCalendar for the given Julian Day
	 * @throws IllegalArgumentException
	 *             thrown when Julian Day is negative
	 */
	public static GregorianCalendar convertJulianDayToCalendar(double jd) throws IllegalArgumentException, ParseException {

		if (jd < 0) {
			throw new IllegalArgumentException("Unsupported argument " + jd + ". Julian Day must be positive");
		}
		// calculate integer and decimal parts of jd + 0.5
		double j = jd + 0.5;
		int z = (int) j;
		double f = j % 1;

		int a = 0;
		if (z < 2299161) {
			a = z;
		} else {
			int alpha = (int) ((z - 1867216.25) / 36524.25);
			a = z + 1 + alpha - (int) (alpha / 4);
		}

		int b = a + 1524;
		int c = (int) ((b - 122.1) / 365.25);
		int d = (int) (365.25 * c);
		int e = (int) ((b - d) / 30.6001);

		double day = b - d - (int) (30.6001 * e) + f;
		int month;
		if (e < 14) {
			month = e - 1;
		} else {
			month = e - 13;
		}
		int year;
		if (month > 2) {
			year = c - 4716;
		} else {
			year = c - 4715;
		}

		return DateTimeUtils.parseCalendar(year, month, day, DateTimeUtils.getGmtTimeZone());
	}

	/**
	 * Formats a date into a date/time string using the default TimeZone for
	 * this host.
	 * 
	 * @param date
	 *            the date to be formatted
	 * @param pattern
	 *            the pattern describing the date and time format
	 * @return the formatted time string
	 */
	public static String formatDate(Date date, String pattern) {
		return formatDate(date, pattern, TimeZone.getDefault());
	}

	/**
	 * Formats a date into a date/time string
	 * 
	 * @param date
	 *            the date to be formatted
	 * @param pattern
	 *            the pattern describing the date and time format
	 * @param zone
	 *            the time zone
	 * @return the formatted time string
	 */
	public static String formatDate(Date date, String pattern, TimeZone zone) {
		DateFormat dfmt = new SimpleDateFormat(pattern);
		dfmt.setTimeZone(zone);
		return dfmt.format(date);
	}

	/**
	 * Returns the GMT time zone
	 * 
	 * @return the GMT time zone
	 */
	public static TimeZone getGmtTimeZone() {
		return TimeZone.getTimeZone("GMT");
	}

	/**
	 * Calculates whether the given date is Gregorian, i.e. is after 1582
	 * October 15th.
	 * 
	 * @param year
	 *            year value
	 * @param month
	 *            month of the year (first month is 1)
	 * @param day
	 *            day of the month including fraction of hours, minutes, seconds
	 *            and milliseconds
	 * @return true if date is after the beginning of the Gregorian calendar,
	 *         false otherwise
	 */
	public static boolean isGregorianDate(int year, int month, double day) {
		// start of Gregorian calendar is 15/10/1582
		Calendar gregorianZero = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gregorianZero.set(Calendar.YEAR, 1582);
		gregorianZero.set(Calendar.MONTH, 9); // October
		gregorianZero.set(Calendar.DAY_OF_MONTH, 15);

		Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, (int) day);

		return (calendar.after(gregorianZero));
	}

	/**
	 * Parses a date assuming all arguments are based on a Gregorian or Julian
	 * calendar.
	 * 
	 * @param year
	 *            year value
	 * @param month
	 *            month of the year (first month is 1)
	 * @param day
	 *            day of the month including fraction of hours, minutes, seconds
	 *            and milliseconds
	 * @param zone
	 *            the given time zone
	 * @return a calendar
	 */
	public static GregorianCalendar parseCalendar(int year, int month, double day, TimeZone zone) {
		// calculate hour, minutes, seconds and milliseconds
		double hour = (day % 1) * 24; // decimal part of a day times 24
		double minute = (hour % 1) * 60; // decimal part of an hour times 60
		double second = (minute % 1) * 60; // decimal part of a minute times 60
		double millisecond = (second % 1) * 1000;

		return parseCalendar(year, month, (int) day, (int) hour, (int) minute, (int) second, (int) Math.round(millisecond), zone);
	}

	/**
	 * Parses a date assuming all arguments are based on a Gregorian calendar.
	 * 
	 * @param year
	 *            year value
	 * @param month
	 *            month of the year (first month is 1)
	 * @param day
	 *            day of the month (first day is 1)
	 * @param hour
	 *            hour of the day for the 24-hour clock
	 * @param minute
	 *            minute value within the hour
	 * @param second
	 *            second value within the minute
	 * @param millisecond
	 *            milliseconds value within the second
	 * @param zone
	 *            the given time zone
	 * @return a Gregorian calendar
	 */
	public static GregorianCalendar parseCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond, TimeZone zone) {
		GregorianCalendar calendar = new GregorianCalendar(zone);

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, (int) day);
		calendar.set(Calendar.HOUR_OF_DAY, (int) hour);
		calendar.set(Calendar.MINUTE, (int) minute);
		calendar.set(Calendar.SECOND, (int) second);
		calendar.set(Calendar.MILLISECOND, (int) Math.round(millisecond));

		return calendar;
	}

	/**
	 * Parses a date using a pattern describing the date and time format and the
	 * default TimeZone for this host.
	 * 
	 * @param date
	 *            the date in string format
	 * @param pattern
	 *            the pattern describing the date and time format
	 * @return a Date parsed from the string
	 * @throws ParseException
	 */
	public static Date parseDate(String date, String pattern) throws ParseException {
		return parseDate(date, pattern, TimeZone.getDefault());
	}

	/**
	 * Parses a date using a pattern describing the date and time format.
	 * 
	 * @param date
	 *            date in string format
	 * @param pattern
	 *            pattern describing the date and time format
	 * @param zone
	 *            the given time zone
	 * @return a Date parsed from the string
	 * @throws ParseException
	 */
	public static Date parseDate(String date, String pattern, TimeZone zone) throws ParseException {
		DateFormat dfmt = new SimpleDateFormat(pattern);
		dfmt.setTimeZone(zone);
		return dfmt.parse(date);
	}

}