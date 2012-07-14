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

import java.util.TimeZone;

import org.cetus.astro.util.DateTimeUtils;

/**
 * @author Inaki Ortiz de Landaluce Saiz
 * 
 */
public abstract class SunPositionAlgorithm {

  protected int year;
  protected int month;
  protected int day;
  protected int hour;
  protected int minute;
  protected int second;
  protected TimeZone timeZone;
  protected double longitudeInDegrees;
  protected double latitudeInDegrees;

  /**
   * Creates an instance of SunPositionAlgorithm to calculate the sun position
   * for a given date and assuming calendar is Gregorian.
   * 
   * @param year
   *          year
   * @param month
   *          month of the year (first month is 1)
   * @param day
   *          day of the month (first day is 1)
   * @param hour
   *          hour of the day for the 24-hour clock
   * @param minute
   *          minute value within the hour
   * @param second
   *          second value within the minute
   * @param zone
   *          datetime's zone
   * @param longitude
   *          geographical longitude in degrees of the location the sun position
   *          is to be calculated for
   * @param latitude
   *          geographical latitude in degrees of the location the sun position
   *          is to be calculated for
   */
  public SunPositionAlgorithm(int year, int month, int day, int hour,
      int minute, int second, TimeZone zone, double longitude, double latitude) {
    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.second = second;
    this.timeZone = zone;
    this.longitudeInDegrees = longitude;
    this.latitudeInDegrees = latitude;
  }

  /**
   * Creates an instance of SunPositionBuilder to calculate the sun position in
   * different resolutions assuming date time zone is GMT+0.
   * 
   * @param year
   *          year
   * @param month
   *          month of the year (first month is 1)
   * @param day
   *          day of the month (first day is 1)
   * @param hour
   *          hour of the day for the 24-hour clock
   * @param minute
   *          minute value within the hour
   * @param second
   *          second value within the minute
   * @param longitude
   *          geographical longitude in degrees of the location the sun position
   *          is to be calculated for
   * @param latitude
   *          geographical latitude in degrees of the location the sun position
   *          is to be calculated for
   */
  public SunPositionAlgorithm(int year, int month, int day, int hour,
      int minute, int second, double longitude, double latitude) {
    this(year, month, day, hour, minute, second,
        DateTimeUtils.getGmtTimeZone(), longitude, latitude);
  }

  /**
   * Calculates the sun position for the given date assuming purely elliptical
   * motion of the Earth. Hence, perturbations by other bodies are neglected.
   * Corrections by atmosphere retraction and Earth oblateness are applied. This
   * leads to an accuracy of 0.01 degree, approximately fifty times smaller than
   * the sun diameter (~0.5416 degrees).
   * 
   * @return the sun position
   */
  public abstract SunPosition calculateSunPosition();
}