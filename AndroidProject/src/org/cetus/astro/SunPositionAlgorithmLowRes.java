/*
` * Copyright (C) 2011-2011 Inaki Ortiz de Landaluce Saiz
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
import java.util.TimeZone;

import org.cetus.astro.coords.EquatorialCoordinates;
import org.cetus.astro.coords.HorizontalCoordinates;
import org.cetus.astro.util.AngleUtils;
import org.cetus.astro.util.DateTimeUtils;

/**
 * Implementation of a low accuracy algorithm to calculate the sun position as
 * described by Jean Meeus on Astronomical Algorithms, Willmann-Bell, 2nd Ed.,
 * 2005 (ISBN 978-0943396613).
 * 
 * The algorithm calculates the sun position for the given date assuming purely
 * elliptical motion of the Earth (Keplerian ellipse). Hence, perturbations by
 * the Moon (others than nutation) and the planets are neglected. Corrections by
 * aberration and atmosphere refraction are applied.
 * 
 * This leads to an accuracy of 0.01 degree, approximately fifty times smaller
 * than the sun diameter (~0.5416 degrees).
 * 
 * @author Inaki Ortiz de Landaluce Saiz
 */
public class SunPositionAlgorithmLowRes extends SunPositionAlgorithm {

	/**
	 * Creates an instance of a SunPositionAlgorithm to calculate the sun
	 * position for a given date and assuming calendar is Gregorian.
	 * 
	 * @param year
	 *            year
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
	 * @param zone
	 *            datetime's zone
	 * @param longitude
	 *            geographical longitude in degrees of the observer's location
	 * @param latitude
	 *            geographical latitude in degrees of the observer's location
	 */
	public SunPositionAlgorithmLowRes(int year, int month, int day, int hour, int minute, int second, TimeZone zone, double longitude, double latitude) {
		super(year, month, day, hour, minute, second, zone, longitude, latitude);
	}

	/**
	 * Creates an instance of a SunPositionAlgorithm to calculate the sun
	 * position in different resolutions assuming date time zone is GMT+0.
	 * 
	 * @param year
	 *            year
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
	 * @param longitude
	 *            geographical longitude in degrees of the observer's location
	 * @param latitude
	 *            geographical latitude in degrees of the observer's location
	 */
	public SunPositionAlgorithmLowRes(int year, int month, int day, int hour, int minute, int second, double longitude, double latitude) {
		super(year, month, day, hour, minute, second, longitude, latitude);
	}

	/**
	 * Calculates the sun position for the given date assuming purely elliptical
	 * motion of the Earth.
	 * 
	 * @return the sun position
	 */
	@Override
	public SunPosition calculateSunPosition() {

		// calculate Julian day
		Calendar calendar = DateTimeUtils.parseCalendar(this.year, this.month, this.day, this.hour, this.minute, this.second, 0, this.timeZone);
		JulianDay jd = new JulianDay(calendar);

		// calculate time in Julian centuries from epoch J2000.0
		double t = jd.getTimeFromJ2000();
		double t2 = t * t;

		// calculate geometric mean longitude of the Sun referred to the mean
		// equinox of the date o(t^3)
		double mlon = 280.46646 + 36000.76983 * t + 0.0003032 * t2;
		mlon = AngleUtils.normalizeAngle(mlon, 0, 360);
		// calculate the mean anomaly o(t^3)
		double mano = 357.52911 + 35999.05029 * t - 0.0001537 * t2;
		mano = AngleUtils.normalizeAngle(mano, 0, 360);
		double manoRadians = Math.toRadians(mano);

		// use sun's equation of the center to calculate true geometric
		// longitude
		double c = (1.914602 - 0.004817 * t - 0.000014 * t2) * Math.sin(manoRadians) + (0.019993 - 0.000101 * t) * Math.sin(2 * manoRadians) + 0.000289 * Math.sin(3 * manoRadians);
		double tlon = mlon + c;
		tlon = AngleUtils.normalizeAngle(tlon, 0, 360);

		// calculate the apparent longitude, taking nutation in longitude and
		// aberration into account
		Nutation nutation = new Nutation(t);
		double aberration = -0.00569;
		double nutationLon = nutation.getDeltaLongitude() / 3600;
		double lambda = tlon + aberration + nutationLon;
		double lambdaRadians = Math.toRadians(lambda);

		// calculate the true obliquity of the eclipse corrected for nutation
		// o(t^4)
		double epsilon = EclipticObliquity.calculateTrueObliquity(t, nutation.getDeltaObliquity());
		double epsilonRadians = Math.toRadians(epsilon);

		// convert from ecliptic to equatorial assuming sun's ecliptic latitude
		// is
		// zero (valid for low accuracy calculation only)
		double rasRadians = Math.atan2((Math.sin(lambdaRadians) * Math.cos(epsilonRadians)), Math.cos(lambdaRadians));
		double decRadians = Math.asin(Math.sin(epsilonRadians) * Math.sin(lambdaRadians));

		// convert sun coordinates from equatorial to horizontal
		double rasHours = Math.toDegrees(rasRadians) / 15.0;
		double decDegrees = Math.toDegrees(decRadians);
		double sTime = SiderealTime.calculateApparentSiderealTime(jd, nutation.getDeltaLongitude(), nutation.getDeltaObliquity());
		HorizontalCoordinates h = new EquatorialCoordinates(rasHours, decDegrees).toHorizontal(sTime, longitudeInDegrees, latitudeInDegrees);
		double azimuthDegrees = h.getAzimuth();
		double altitudeDegrees = h.getAltitude();

		// correct altitude from atmospheric refraction
		double altitudeCorrectedDegrees = new AtmosphericRefraction(altitudeDegrees).getApparentAltitude();

		return new SunPosition(azimuthDegrees, altitudeCorrectedDegrees);
	}
}
