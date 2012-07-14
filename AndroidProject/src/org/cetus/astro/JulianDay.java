/*
 * Copyright (C) 2011-2011 Inaki Ortiz de Landaluce Saiz
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
package org.cetus.astro;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.cetus.astro.util.DateTimeUtils;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class JulianDay {

	private double jd;

	/**
	 * Creates a JulianDay instance for a given date (UTC).
	 * 
	 * @param date
	 *            a date
	 * @return the Julian day
	 */
	public JulianDay(Date date) {
		// get a Gregorian calendar based on the Greenwich Mean Time (UTC)
		GregorianCalendar calendar = new GregorianCalendar(DateTimeUtils.getGmtTimeZone());
		calendar.setTime(date);
		this.jd = calculateJulianDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, (double) calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.HOUR_OF_DAY) / 24.0
				+ (double) calendar.get(Calendar.MINUTE) / (24 * 60) + (double) calendar.get(Calendar.SECOND) / (24 * 60 * 60) + (double) calendar.get(Calendar.MILLISECOND) / (24 * 60 * 60 * 1000));
	}

	/**
	 * Creates a JulianDay instance for a given date assuming all arguments
	 * refer to time zone GMT+0 and are based on a Gregorian calendar.
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
	 * @param milliseconds
	 *            milliseconds value within the second
	 */
	public JulianDay(int year, int month, int day, int hour, int minute, int second, int milliseconds) {
		// identify year, month and day from int values
		this.jd = calculateJulianDay(year, month, (double) day + hour / 24.0 + (double) minute / (24 * 60) + (double) second / (24 * 60 * 60) + (double) milliseconds / (24 * 60 * 60 * 1000));
	}

	/**
	 * Creates a JulianDay instance for a given date assuming all arguments
	 * refer to time zone GMT+0 and are based on a Gregorian calendar.
	 * 
	 * @param year
	 *            year value
	 * @param month
	 *            month of the year (first month is 1)
	 * @param day
	 *            day of the month including fraction of hours, minutes, seconds
	 *            and milliseconds
	 */
	public JulianDay(int year, int month, double day) {
		this.jd = calculateJulianDay(year, month, day);
	}

	/**
	 * Creates a JulianDay instance assuming the given calendar is Gregorian and
	 * time zone is GMT+0.
	 * 
	 * @param calendar
	 *            a Gregorian calendar with time zone GMT+0
	 */
	public JulianDay(Calendar calendar) {
		// identify year, month and day from calendar instance
		this(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar
				.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
	}

	/**
	 * Returns the Julian Day
	 * 
	 * @return the Julian Day value
	 */
	public double getJD() {
		return this.jd;
	}

	/**
	 * Returns the time measured in Julian centuries of 36525 ephemeris days
	 * from the epoch J2000.0
	 * 
	 * @return time measured in Julian centuries of 36525 ephemeris days from
	 *         the epoch J2000.0
	 */
	public double getTimeFromJ2000() {
		// epoch J2000.0 is 2000 January 1.5 or 2451545.0 JD
		return (getJD() - 2451545.0) / 36525;
	}

	/**
	 * Returns the Modified Julian Day
	 * 
	 * @return the Modified Julian Day value
	 */
	public double getMJD() {
		return getJD() - 2400000.5;
	}

	/**
	 * Calculates the Julian Day for a given date assuming all arguments refer
	 * to GMT+0 and are based on a Gregorian calendar.
	 * 
	 * @param year
	 *            year value
	 * @param month
	 *            month of the year (first month is 1)
	 * @param day
	 *            day of the month including fraction of hours, minutes, seconds
	 *            and milliseconds
	 * @return the Julian day
	 */
	private double calculateJulianDay(int year, int month, double day) {
		int y = year;
		int m = month;
		// if month is Jan or Feb, month is 13 or 14 for previous year
		// respectively
		if (month < 2) {
			y = year - 1;
			m = month + 12;
		}
		int a = (int) Math.floor(y / 100);
		int b = 0;
		if (DateTimeUtils.isGregorianDate(year, month, day)) {
			b = (int) (2 - a + (int) (a / 4));
		}

		double jd = (int) (365.25 * (y + 4716)) + (int) (30.6 * (m + 1)) + day + b - 1524.5;
		return jd;
	}
}
