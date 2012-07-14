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

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public class EclipticCoordinates extends SkyCoordinates {

  /**
   * Creates a new instance of EclipticCoordinates.
   * 
   * @param lon
   *          The Ecliptic longitude in degrees
   * @param lat
   *          The Ecliptic latitude in degrees
   */
  public EclipticCoordinates(double lon, double lat) {
    super(lon, lat);
  }

  /**
   * Returns the Ecliptic latitude in degrees.
   */
  public double getLat() {
    return getLatitude();
  }

  /**
   * Returns the Ecliptic longitude in degrees.
   */
  public double getLon() {
    return getLongitude();
  }

  /**
   * This method converts ecliptic coordinates into equatorial ones.
   * 
   * @return EquatorialCoordinates instance.
   */
  public EquatorialCoordinates toEquatorial() {
    // TODO
    return null;
  }

  /**
   * Returns the default unit for the Ecliptic latitude.
   */
  @Override
  public AngleUnit getLatitudeUnit() {
    return AngleUnit.DEGREES;
  }

  /**
   * Returns the default unit for the Ecliptic longitude.
   */
  @Override
  public AngleUnit getLongitudeUnit() {
    return AngleUnit.DEGREES;
  }

  /**
   * Returns the maximum value for the Ecliptic latitude expressed in the given
   * units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMaxLatitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES)) {
      return +90d;
    } else {
      throw new IllegalArgumentException("Ecliptic latitude in "
          + unit.getName() + " not supported");
    }
  }

  /**
   * Returns the maximum value for the Ecliptic longitude expressed in the given
   * units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMaxLongitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES)) {
      return 360d;
    } else {
      throw new IllegalArgumentException("Ecliptic longitude in "
          + unit.getName() + " not supported");
    }
  }

  /**
   * Returns the minimum value for the Ecliptic latitude expressed in the given
   * units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMinLatitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES)) {
      return -90d;
    } else {
      throw new IllegalArgumentException("Ecliptic latitude in "
          + unit.getName() + " not supported");
    }
  }

  /**
   * Returns the minimum value for the Ecliptic longitude expressed in the given
   * units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMinLongitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES)) {
      return 0d;
    } else {
      throw new IllegalArgumentException("Ecliptic longitude in "
          + unit.getName() + " not supported");
    }
  }
}