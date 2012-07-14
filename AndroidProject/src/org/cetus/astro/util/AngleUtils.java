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

import java.text.DecimalFormat;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class AngleUtils {

	@SuppressWarnings("unused")
	public static double hmsToDeg(int hour, int minute, double second) {
		return dmsToDeg(hour, minute, second) * 15;
	}

	public static double dmsToDeg(int degree, int arcminute, double arcsecond) {
		return (double) degree + (double) arcminute / 60 + (double) arcsecond / (60 * 60);
	}

	public static String formatDegToHms(double degrees, double min, double max) {
		String s = "";
		// normalize values within the given range
		double hours = normalizeAngle(degrees, min, max) / 15.0;
		// take sign into account
		if (hours < 0) {
			hours = Math.abs(hours);
			s += "-";
		}
		// calculate minutes and seconds;
		double minutes = (hours - (int) hours) * 60.0;
		double seconds = (minutes - (int) minutes) * 60.0;
		s += new DecimalFormat("00").format((int) hours) + "h" + new DecimalFormat("00").format((int) minutes) + "m" + new DecimalFormat("00.000").format(seconds) + "s";
		return s;
	}

	public static String formatDegToDms(double degrees, double min, double max) {
		String s = "";
		// normalize values within the given range
		double deg = normalizeAngle(degrees, min, max);
		// take sign into account
		if (deg < 0) {
			deg = Math.abs(deg);
			s += "-";
		}
		double arcmin = (deg - (int) deg) * 60.0;
		double arcsec = (arcmin - (int) arcmin) * 60.0;
		s += new DecimalFormat("00").format((int) deg) + "d" + new DecimalFormat("00").format((int) arcmin) + "'" + new DecimalFormat("00.00").format(arcsec) + "\"";
		return s;
	}

	/**
	 * Normalizes an angle value between a given range
	 * 
	 * @param value
	 *            the angle value
	 * @param minValue
	 *            minimum value of the range
	 * @param maxValue
	 *            maximum value of the range
	 */
	public static double normalizeAngle(double value, double minValue, double maxValue) {
		double delta = maxValue - minValue;
		return ((value - minValue) % delta + delta) % delta + minValue;
	}

}