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
public class AtmosphericRefraction {

  private double pressureMillibars;
  private double temperatureKelvin;
  private double trueAltitudeDegrees;
  private double refractionArcmin;

  /**
   * Creates an instance of AtomsphericRefraction for a given altitude,
   * atmospheric pressure and air temperature
   * 
   * @param altitude
   *          the true altitude in degrees
   * @param pressure
   *          the atmospheric pressure in millibars
   * @param temperature
   *          the air temperature in Kelvin
   */
  public AtmosphericRefraction(double altitude, double pressure,
      double temperature) {
    this.trueAltitudeDegrees = altitude;
    this.pressureMillibars = pressure;
    this.temperatureKelvin = temperature;
    this.refractionArcmin = calculateRefraction();
  }

  /**
   * Creates an instance of AtomsphericRefraction for a given altitude, assuming
   * the atmospheric pressure is 1010 millibars and the air temperature is 283K
   * 
   * @param altitude
   *          the true altitude in degrees
   */
  public AtmosphericRefraction(double altitude) {
    // pressure is 1010 millibars, temperature 10 degrees Celsius
    this(altitude, 1010, 283);
  }

  /**
   * Returns the apparent altitude in degrees once corrected by refraction
   * 
   * @return the apparent altitude in degrees
   */
  public double getApparentAltitude() {
    return this.refractionArcmin / 60 + trueAltitudeDegrees;
  }

  /**
   * Returns the refraction correction in arcminutes
   * 
   * @return the refraction correction in arcminutes
   */
  public double getRefraction() {
    return this.refractionArcmin;
  }

  private double calculateRefraction() {
    double r = 1.02 / Math.tan(Math.toRadians(trueAltitudeDegrees + 10.3
        / (trueAltitudeDegrees + 5.11)));
    // correct from standard conditions of air pressure and temperature
    r = r * (this.pressureMillibars / 1010) * (283 / this.temperatureKelvin);
    return r;
  }
}
