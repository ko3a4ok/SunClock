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
public class EclipticObliquity {

  /**
   * Calculates the obliquity of the ecliptic with respect to the mean equator
   * for the given julian day. The correction for nutation is not taken into
   * account.
   * 
   * @param jd
   *          the julian day
   * @return the mean obliquity in degrees
   */
  public static double calculateMeanObliquity(JulianDay jd) {
    return calculateMeanObliquity(jd.getTimeFromJ2000());
  }

  /**
   * Calculates the obliquity of the ecliptic with respect to the mean equator
   * for the given time. The correction for nutation is not taken into account.
   * 
   * @param t
   *          time measured in Julian centuries of 36525 ephemeris days from the
   *          epoch J2000.0
   * @return the mean obliquity in degrees
   */
  public static double calculateMeanObliquity(double t) {
    double t2 = t * t;
    // precision is up to the third term, i.e. o(t^4).
    return AngleUtils.dmsToDeg(23, 26, 21.448)
        - AngleUtils.dmsToDeg(0, 0, 46.8150) * t
        - AngleUtils.dmsToDeg(0, 0, 0.00059) * t2
        + AngleUtils.dmsToDeg(0, 0, 0.001813) * t * t2;
  }

  /**
   * Calculates the obliquity of the ecliptic with respect to the true equator
   * for the given julian day. The correction for nutation is taken into
   * account.
   * 
   * @param jd
   *          the julian day
   * @return the true obliquity in degrees
   */
  public static double calculateTrueObliquity(JulianDay jd) {
    return calculateTrueObliquity(jd.getTimeFromJ2000());
  }

  /**
   * Calculates the obliquity of the ecliptic with respect to the true equator
   * for the given time. The correction for nutation is taken into account.
   * 
   * @param t
   *          the time measured in Julian centuries of 36525 ephemeris days from
   *          the epoch J2000.0
   * @return the true obliquity in degrees
   */
  public static double calculateTrueObliquity(double t) {
    Nutation nutation = new Nutation(t);
    return calculateMeanObliquity(t) + nutation.getDeltaObliquity() / 3600;
  }

  /**
   * Calculates the obliquity of the ecliptic with respect to the true equator
   * for the given time. The correction for nutation is taken into account.
   * 
   * @param t
   *          the time measured in Julian centuries of 36525 ephemeris days from
   *          the epoch J2000.0
   * 
   * @param deltaObliquity
   *          the delta component perpendicular to the ecliptic due to nutation
   *          in arcseconds
   * @return the true obliquity in degrees
   */
  public static double calculateTrueObliquity(double t, double deltaObliquity) {
    return calculateMeanObliquity(t) + deltaObliquity / 3600;
  }
}