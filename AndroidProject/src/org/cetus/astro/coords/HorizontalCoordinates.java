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
public class HorizontalCoordinates extends SkyCoordinates {

  /**
   * Creates a new instance of HorizontalCoordinates.
   * 
   * @param lon
   *          The Horizontal longitude in degrees
   * @param lat
   *          The Horizontal latitude in degrees
   */
  public HorizontalCoordinates(double lon, double lat) {
    super(lon, lat);
  }

  /**
   * Returns the altitude in degrees.
   */
  public double getAltitude() {
    return getLatitude();
  }

  /**
   * Returns the azimuth in degrees.
   */
  public double getAzimuth() {
    return getLongitude();
  }

  /**
   * This method transforms horizontal coordinates into equatorial ones.
   * 
   * @return the equatorial coordinates
   */
  public EquatorialCoordinates toEquatorial() {
    return null;
  }

  /**
   * Returns the default unit for the Horizontal latitude.
   */
  @Override
  public AngleUnit getLatitudeUnit() {
    return AngleUnit.DEGREES;
  }

  /**
   * Returns the default unit for the Horizontal longitude.
   */
  @Override
  public AngleUnit getLongitudeUnit() {
    return AngleUnit.DEGREES;
  }

  /**
   * Returns the maximum value for the Horizontal latitude expressed in the
   * given units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMaxLatitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES))
      return +90d;
    else
      throw new IllegalArgumentException("Horizontal latitude in "
          + unit.getName() + " not supported");
  }

  /**
   * Returns the maximum value for the Horizontal longitude expressed in the
   * given units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMaxLongitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES))
      return 360d;
    else
      throw new IllegalArgumentException("Horizontal longitude in "
          + unit.getName() + " not supported");
  }

  /**
   * Returns the minimum value for the Horizontal latitude expressed in the
   * given units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMinLatitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES)) {
      return -90d;
    } else {
      throw new IllegalArgumentException("Horizontal latitude in "
          + unit.getName() + " not supported");
    }
  }

  /**
   * Returns the minimum value for the Horizontal longitude expressed in the
   * given units.
   * 
   * @param unit
   *          Latitude unit
   */
  @Override
  public double getMinLongitude(AngleUnit unit) {
    if (unit.equals(AngleUnit.DEGREES)) {
      return 0d;
    } else {
      throw new IllegalArgumentException("Horizontal longitude in "
          + unit.getName() + " not supported");
    }
  }

}
