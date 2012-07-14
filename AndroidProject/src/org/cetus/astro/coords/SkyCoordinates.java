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
public abstract class SkyCoordinates {

  private double lon;
  private double lat;

  public SkyCoordinates(double lon, double lat) {
    /* normalize coordinates first */
    normalizeLongitude(lon);
    normalizeLatitude(lon, lat);
    this.lon = lon;
    this.lat = lat;
  }

  /**
   * Calculates the spherical distance between the coordinates of the instance
   * and the ones passed as an argument. This method makes use of the Haversine
   * formula:
   * <ul>
   * 2sin-1 sqrt(sin2((dec1-dec2)/2) + cos(dec1)cos(dec2)sin2((ra1-ra2)/2))
   * </ul>
   * as the formula for the distance in sperical geometry
   * <ul>
   * cos-1(sin(dec1)sin(dec2) + cos(dec1)cos(dec2)cos(ra1-ra2))
   * </ul>
   * is less accurate for smaller distances (due to rounding errors;
   * mathematically speaking it is perfectly correct)
   * <ul>
   * </ul>
   * N.B. There is another form of the Haversine formula that uses the atan2
   * function in the following way:
   * <ul>
   * a=sin2((dec1-dec2)/2) + cos(dec1)cos(dec2)sin2((ra1-ra2)/2)
   * </ul>
   * <ul>
   * distance = 2atan2(sqrt(a), sqrt(1-a))
   * </ul>
   * 
   * @param coordinate
   *          The coordinates of the second point
   * @return distance The distance in arcseconds
   */
  public double distance(SkyCoordinates coordinate)
      throws IllegalArgumentException {
    if (!coordinate.getClass().equals(this.getClass())) {
      throw new IllegalArgumentException("Cannot calculate distance for class "
          + coordinate.getClass().getName());
    }
    double distance;
    double dlon = coordinate.getLongitude(AngleUnit.RADIANS)
        - this.getLongitude(AngleUnit.RADIANS);
    double dlat = coordinate.getLatitude(AngleUnit.RADIANS)
        - this.getLatitude(AngleUnit.RADIANS);

    double a = Math.pow(Math.sin(dlat / 2), 2) // sin2((dec1-dec2)/2)
        + Math.cos(this.getLatitude(AngleUnit.RADIANS))
        * Math.cos(coordinate.getLatitude(AngleUnit.RADIANS))
        * Math.pow(Math.sin(dlon / 2), 2); // cos(dec1)cos(dec2)sin2((ra1-ra2)/2))

    distance = 2 * Math.asin(Math.min(1.0d, Math.sqrt(a)));
    // by using the min function we protect against possible roundoff errors
    // that could sabotage computation of the arcsine if the two points are very
    // nearly antipodal (on opposite sides of the celestial sphere)

    // according to the documentation Math.asin returns the arc sine of an
    // angle,
    // in the range of -pi/2 through pi/2. Therefore it is a function, not
    // multivalued,so we just need to take its absolute value.
    distance = Math.abs(distance);

    /* convert into proper units */
    distance *= AngleUnit.convert(AngleUnit.RADIANS, AngleUnit.ARCSECONDS);
    return distance;
  }

  /**
   * Returns the Latitude for the specific coordinate system in default units.
   */
  public double getLatitude() {
    return this.lat;
  }

  /**
   * Returns the Latitude for the specific coordinate system in given units.
   * 
   * @param unit
   *          Latitude unit
   */
  public double getLatitude(AngleUnit unit) {
    return lat * AngleUnit.convert(getLatitudeUnit(), unit);
  }

  /**
   * Returns the Longitude for the specific coordinate system in default units.
   */
  public double getLongitude() {
    return this.lon;
  }

  /**
   * Returns the Longitude for the specific coordinate system in given units.
   * 
   * @param unit
   *          Longitude units
   */
  public double getLongitude(AngleUnit unit) {
    return lon * AngleUnit.convert(getLongitudeUnit(), unit);
  }

  /**
   * Returns the default unit for the Latitude.
   */
  public abstract AngleUnit getLatitudeUnit();

  /**
   * Returns the default unit for the Longitude.
   */
  public abstract AngleUnit getLongitudeUnit();

  /**
   * Returns the maximum value for the Latitude in default units
   */
  public double getMaxLatitude() {
    return getMaxLatitude(getLatitudeUnit());
  }

  /**
   * Returns the maximum value for the Latitude expressed in the given units.
   * 
   * @param unit
   *          Latitude unit
   */
  public abstract double getMaxLatitude(AngleUnit unit);

  /**
   * Returns the maximum value for the Longitude in default units
   */
  public double getMaxLongitude() {
    return getMaxLongitude(getLongitudeUnit());
  }

  /**
   * Returns the maximum value for the Longitude expressed in the given units.
   * 
   * @param unit
   *          Longitude unit
   */
  public abstract double getMaxLongitude(AngleUnit unit);

  /**
   * Returns the minimum value for the Latitude in default units
   */
  public double getMinLatitude() {
    return getMinLatitude(getLatitudeUnit());
  }

  /**
   * Returns the minimum value for the Latitude expressed in the given units.
   * 
   * @param unit
   *          Latitude unit
   */
  public abstract double getMinLatitude(AngleUnit unit);

  /**
   * Returns the minimum value for the Longitude in default units
   */
  public double getMinLongitude() {
    return getMinLongitude(getLongitudeUnit());
  }

  /**
   * Returns the minimum value for the Longitude expressed in the given units.
   * 
   * @param unit
   *          Right Ascension unit
   */
  public abstract double getMinLongitude(AngleUnit unit);

  /**
   * Normalize the latitude value into the proper range. As this might affect
   * both the latitude and the longitude both values have to be passed as
   * parameters
   * 
   * @param longitude
   *          The longitude in default units
   * @param latitude
   *          The latitude in default units
   */
  public double normalizeLatitude(double longitude, double latitude) {
    // to keep it simple we assume that the offset is always small enough
    // to avoid having to iterate recursively across the next two conditions
    /* positive latitude */
    if (latitude > getMaxLatitude()) {
      // longitude is complementary when latitude gets out of bounds
      // but still can get out of bounds, so normalize longitude afterwards
      longitude += (getMaxLongitude() - getMinLongitude()) / 2;
      normalizeLongitude(longitude);
      // for positive declinations subtract from twice the upper limit
      latitude = 2 * getMaxLatitude() - latitude;
    }
    /* negative latitude */
    if (latitude < getMinLatitude()) {
      // longitude is complementary when latitude gets out of bounds
      longitude += (getMaxLongitude() - getMinLongitude()) / 2;
      normalizeLongitude(longitude);
      // for negative declinations subtract from twice the lower limit
      latitude = 2 * getMinLatitude() - latitude;
    }
    return latitude;
  }

  /**
   * Normalize the longitude value into the proper range
   */
  public double normalizeLongitude(double longitude) {
    return AngleUtils.normalizeAngle(longitude, getMinLongitude(),
        getMaxLongitude());
  }
}