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
package org.cetus.astro;

import org.cetus.astro.util.AngleUtils;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class SiderealTime {

	/**
	 * Calculates the apparent sidereal time for the given julian day. The
	 * correction for nutation is taken into account.
	 * 
	 * @param jd
	 *            the julian day
	 * @return the apparent sidereal time in degrees
	 */
	public static double calculateApparentSiderealTime(JulianDay jd) {
		Nutation nutation = new Nutation(jd);
		return calculateApparentSiderealTime(jd, nutation.getDeltaLongitude(), nutation.getDeltaObliquity());
	}

	/**
	 * Calculates the apparent sidereal time for the given julian day. The
	 * correction for nutation is taken into account.
	 * 
	 * @param jd
	 *            the julian day
	 * 
	 * @param deltaLongitude
	 *            the delta component along the ecliptic due to nutation in
	 *            arcseconds
	 * 
	 * @param deltaObliquity
	 *            the delta component perpendicular to the ecliptic due to
	 *            nutation in arcseconds
	 * 
	 * @return the apparent sidereal time in degrees
	 */
	public static double calculateApparentSiderealTime(JulianDay jd, double deltaLongitude, double deltaObliquity) {
		double deltaPsi = deltaLongitude / 3600; // degrees
		double eps = EclipticObliquity.calculateTrueObliquity(jd.getTimeFromJ2000(), deltaObliquity);
		double ast = calculateMeanSiderealTime(jd) + (Math.cos(Math.toRadians(eps)) * deltaPsi);
		return ast;
	}

	/**
	 * Calculates the mean sidereal time for the given julian day. The
	 * correction for nutation is not taken into account.
	 * 
	 * @param jd
	 *            the julian day
	 * @return the mean sidereal time in degrees
	 */
	public static double calculateMeanSiderealTime(JulianDay jd) {
		double t = jd.getTimeFromJ2000();
		double t2 = t * t;
		// calculate the mean sidereal time at Greenwich for that instant
		double mst = (280.46061837 + 360.98564736629 * (jd.getJD() - 2451545.0) + 0.000387933 * t2 - (t * t2) / 38710000);
		mst = AngleUtils.normalizeAngle(mst, 0, 360);
		return mst;
	}
}
