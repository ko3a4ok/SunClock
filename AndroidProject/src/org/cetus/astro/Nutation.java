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

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class Nutation {

	private double deltaLon;
	private double deltaEps;

	/**
	 * Creates a Nutation instance for a given julian day. The julian day value
	 * may be standard Julian Day (JD, referred to Universal Time) or Julian
	 * Ephemeris Day (JDE, referred to) Dynamical/Terrestrial Time).
	 * 
	 * @param jd
	 *            the julian day value
	 */
	public Nutation(JulianDay jd) {
		// calculate time measured in Julian centuries of 36525 ephemeris days
		// from
		// the epoch J2000.0
		calculateNutation(jd.getTimeFromJ2000());
	}

	/**
	 * Creates a Nutation instance for a given time.
	 * 
	 * @param t
	 *            the time measured in Julian centuries of 36525 ephemeris days
	 *            from the epoch J2000.0
	 */
	public Nutation(double t) {
		calculateNutation(t);
	}

	/**
	 * Returns the delta component along the ecliptic due to nutation
	 * 
	 * @return the nutation in longitude in arcseconds
	 */
	public double getDeltaLongitude() {
		return deltaLon;
	}

	/**
	 * Returns the delta component perpendicular to the ecliptic due to nutation
	 * 
	 * @return the nutation in obliquity in arcseconds
	 */
	public double getDeltaObliquity() {
		return deltaEps;
	}

	/* Note: This is a low accuracy algorithm */
	private void calculateNutation(double t) {
		double t2 = t * t;

		// calculate mean longitude of the Sun and Moon referred to the mean
		// equinox
		// of the date o(t^2)
		double mlonSun = 280.4665 + 36000.7698 * t;
		double mlonSunRadians = Math.toRadians(mlonSun);
		double mlonMoon = 218.3165 + 481267.8813 * t;
		double mlonMoonRadians = Math.toRadians(mlonMoon);

		// calculate the longitude of the ascending node of the Moon's mean
		// orbit
		// on the ecliptic measured from the mean equinox of the date o(t^4)
		double omega = 125.04452 - 1934.136261 * t + 0.0020708 * t2 + t * t2 / 450000;
		double omegaRadians = Math.toRadians(omega);

		// set delta longitude and obliquity
		this.deltaLon = -17.20 * Math.sin(omegaRadians) - 1.32 * Math.sin(2 * mlonSunRadians) - 0.23 * Math.sin(2 * mlonMoonRadians) + 0.21 * Math.sin(2 * omegaRadians);
		this.deltaEps = 9.20 * Math.cos(omegaRadians) + 0.57 * Math.cos(2 * mlonSunRadians) + 0.10 * Math.cos(2 * mlonMoonRadians) - 0.09 * Math.cos(2 * omegaRadians);

	}
}