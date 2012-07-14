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
public class SunPosition {

  private double altitude;
  private double azimuth;

  /**
   * Creates an instance of SunPosition for the given horizontal coordinates,
   * both measured in degrees.
   * 
   * @param azimuth
   *          azimuth in degrees
   * @param altitude
   *          altitude in degrees
   */
  public SunPosition(double azimuth, double altitude) {
    this.azimuth = azimuth;
    this.altitude = altitude;
  }

  /**
   * @return the altitude in degrees
   */
  public double getAltitude() {
    return altitude;
  }

  /**
   * @return the azimuth in degrees
   */
  public double getAzimuth() {
    return azimuth;
  }

  /**
   * @param altitude
   *          the altitude to set (degrees)
   */
  public void setAltitude(double altitude) {
    this.altitude = altitude;
  }

  /**
   * @param azimuth
   *          the azimuth to set (degrees)
   */
  public void setAzimuth(double azimuth) {
    this.azimuth = azimuth;
  }
}