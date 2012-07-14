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
package org.cetus.astro.coords;

import org.cetus.astro.util.AngleUtils;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class EquatorialCoordinates extends SkyCoordinates {

	/**
	 * Creates a new instance of EquatorialCoordinates.
	 * 
	 * @param ra
	 *            The right ascension in hours
	 * @param dec
	 *            The declination in degrees
	 */
	public EquatorialCoordinates(double ra, double dec) {
		super(ra, dec);
	}

	/**
	 * Returns the Declination in degrees.
	 */
	public double getDec() {
		return getLatitude();
	}

	/**
	 * Returns the Right Ascension in hours.
	 */
	public double getRa() {
		return getLongitude();
	}

	/**
	 * Converts equatorial to horizontal coordinates given the sidereal time and
	 * the geographical observer's location.
	 * 
	 * @param siderealTime
	 *            sidereal time in degrees
	 * @param geoLongitude
	 *            geographical longitude in degrees of the observer's location
	 * @param geoLatitude
	 *            geographical latitude in degrees of the observer's location
	 * @return
	 */
	public HorizontalCoordinates toHorizontal(double siderealTime, double geoLongitude, double geoLatitude) {
		double hourAngle = AngleUtils.normalizeAngle(siderealTime - geoLongitude - getLongitude(AngleUnit.DEGREES), 0, 360);
		double hourAngleRadians = Math.toRadians(hourAngle);
		double geoLatRadians = Math.toRadians(geoLatitude);
		double decRadians = getLatitude(AngleUnit.RADIANS);

		double azimuthRadians = Math.atan2(Math.sin(hourAngleRadians), (Math.cos(hourAngleRadians) * Math.sin(geoLatRadians) - Math.tan(decRadians) * Math.cos(geoLatRadians)));
		double altRadians = Math.asin(Math.sin(geoLatRadians) * Math.sin(decRadians) + Math.cos(geoLatRadians) * Math.cos(decRadians) * Math.cos(hourAngleRadians));

		return new HorizontalCoordinates(Math.toDegrees(azimuthRadians), Math.toDegrees(altRadians));
	}

	/**
	 * Returns the default unit for the Declination.
	 */
	@Override
	public AngleUnit getLatitudeUnit() {
		return AngleUnit.DEGREES;
	}

	/**
	 * Returns the default unit for the Right Ascension.
	 */
	@Override
	public AngleUnit getLongitudeUnit() {
		return AngleUnit.HOURS;
	}

	/**
	 * Returns the maximum value for the Declination expressed in the given
	 * units.
	 * 
	 * @param unit
	 *            Declination unit
	 */
	@Override
	public double getMaxLatitude(AngleUnit unit) throws IllegalArgumentException {
		if (unit.equals(AngleUnit.DEGREES)) {
			return +90d;
		} else {
			throw new IllegalArgumentException("Declination in " + unit.getName() + " not supported");
		}
	}

	/**
	 * Returns the maximum value for the Right Ascension expressed in the given
	 * units.
	 * 
	 * @param unit
	 *            Right Ascension unit
	 */
	@Override
	public double getMaxLongitude(AngleUnit unit) throws IllegalArgumentException {
		if (unit.equals(AngleUnit.HOURS)) {
			return 24d;
		} else if (unit.equals(AngleUnit.DEGREES)) {
			return 360d;
		} else {
			throw new IllegalArgumentException("Right ascension in " + unit.getName() + " not supported");
		}
	}

	/**
	 * Returns the minimum value for the Declination expressed in the given
	 * units.
	 * 
	 * @param unit
	 *            Declination unit
	 */
	@Override
	public double getMinLatitude(AngleUnit unit) throws IllegalArgumentException {
		if (unit.equals(AngleUnit.DEGREES)) {
			return -90d;
		} else {
			throw new IllegalArgumentException("Declination in " + unit.getName() + " not supported");
		}
	}

	/**
	 * Returns the minimum value for the Right Ascension expressed in the given
	 * units.
	 * 
	 * @param unit
	 *            Right Ascension unit
	 */
	@Override
	public double getMinLongitude(AngleUnit unit) throws IllegalArgumentException {
		if (unit.equals(AngleUnit.HOURS)) {
			return 0d;
		} else if (unit.equals(AngleUnit.DEGREES)) {
			return 0d;
		} else {
			throw new IllegalArgumentException("Right ascension in " + unit.getName() + " not supported");
		}
	}
}