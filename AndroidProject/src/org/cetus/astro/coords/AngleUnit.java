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
public class AngleUnit {

  private int id;
  private String name;

  public static final AngleUnit RADIANS = new AngleUnit(1, "radians");
  public static final AngleUnit DEGREES = new AngleUnit(2, "degrees");
  public static final AngleUnit ARCMINUTES = new AngleUnit(3, "arcminutes");
  public static final AngleUnit ARCSECONDS = new AngleUnit(4, "arcseconds");
  public static final AngleUnit HOURS = new AngleUnit(5, "hours");
  public static final AngleUnit MINUTES = new AngleUnit(6, "minutes");
  public static final AngleUnit SECONDS = new AngleUnit(7, "seconds");

  /**
   * Creates a new instance of AngleUnit
   */
  private AngleUnit(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public double convert(AngleUnit to) {
    return convert(this, to);
  }

  public static double convert(AngleUnit from, AngleUnit to)
      throws IllegalArgumentException {
    if (from.equals(to))
      return 1.0f;
    double factor = 1.0d;
    // perform the conversion.
    // Its enough to define radians to degrees, degrees to arcminutes,
    // arcminutes to arcseconds,
    // hours to degrees,hours to minutes and minutes to seconds. For any other
    // combination apply the
    // chain rule. Then we can calculate any relationship based on just six
    // basic ones.
    switch (from.getId()) {
    case 1: // RADIANS
      if (to.equals(DEGREES))
        factor = Math.toDegrees(1.0d);
      else if (to.equals(ARCMINUTES))
        factor = convert(RADIANS, DEGREES) * convert(DEGREES, ARCMINUTES);
      else if (to.equals(ARCSECONDS))
        factor = convert(RADIANS, DEGREES) * convert(DEGREES, ARCSECONDS);
      else if (to.equals(HOURS))
        factor = convert(RADIANS, DEGREES) * convert(DEGREES, HOURS);
      else if (to.equals(MINUTES))
        factor = convert(RADIANS, HOURS) * convert(HOURS, MINUTES);
      else if (to.equals(SECONDS))
        factor = convert(RADIANS, HOURS) * convert(HOURS, SECONDS);
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    case 2: // DEGREES
      if (to.equals(RADIANS))
        factor = 1.0 / convert(RADIANS, DEGREES);
      else if (to.equals(ARCMINUTES))
        factor = 60.0;
      else if (to.equals(ARCSECONDS))
        factor = convert(DEGREES, ARCMINUTES) * convert(ARCMINUTES, ARCSECONDS);
      else if (to.equals(HOURS))
        factor = 1.0 / convert(HOURS, DEGREES);
      else if (to.equals(MINUTES))
        factor = convert(DEGREES, HOURS) * convert(HOURS, MINUTES);
      else if (to.equals(SECONDS))
        factor = convert(DEGREES, HOURS) * convert(HOURS, SECONDS);
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    case 3: // ARCMINUTES
      if (to.equals(RADIANS))
        factor = convert(ARCMINUTES, DEGREES) * convert(DEGREES, RADIANS);
      else if (to.equals(DEGREES))
        factor = 1.0 / convert(DEGREES, ARCMINUTES);
      else if (to.equals(ARCSECONDS))
        factor = 60.0;
      else if (to.equals(HOURS))
        factor = convert(ARCMINUTES, DEGREES) * convert(DEGREES, HOURS);
      else if (to.equals(MINUTES))
        factor = convert(ARCMINUTES, HOURS) * convert(HOURS, MINUTES);
      else if (to.equals(SECONDS))
        factor = convert(ARCMINUTES, HOURS) * convert(HOURS, SECONDS);
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    case 4: // ARCSECONDS
      if (to.equals(RADIANS))
        factor = convert(ARCSECONDS, DEGREES) * convert(DEGREES, RADIANS);
      else if (to.equals(DEGREES))
        factor = 1.0 / convert(DEGREES, ARCSECONDS);
      else if (to.equals(ARCMINUTES))
        factor = 1.0 / convert(ARCMINUTES, ARCSECONDS);
      else if (to.equals(HOURS))
        factor = convert(ARCSECONDS, DEGREES) * convert(DEGREES, HOURS);
      else if (to.equals(MINUTES))
        factor = convert(ARCSECONDS, HOURS) * convert(HOURS, MINUTES);
      else if (to.equals(SECONDS))
        factor = convert(ARCSECONDS, HOURS) * convert(HOURS, SECONDS);
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    case 5: // HOURS
      if (to.equals(RADIANS))
        factor = convert(HOURS, DEGREES) * convert(DEGREES, RADIANS);
      else if (to.equals(DEGREES))
        factor = 15.0;
      else if (to.equals(ARCMINUTES))
        factor = convert(HOURS, DEGREES) * convert(DEGREES, ARCMINUTES);
      else if (to.equals(ARCSECONDS))
        factor = convert(HOURS, DEGREES) * convert(DEGREES, ARCSECONDS);
      else if (to.equals(MINUTES))
        factor = 60.0;
      else if (to.equals(SECONDS))
        factor = convert(HOURS, MINUTES) * convert(MINUTES, SECONDS);
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    case 6: // MINUTES
      if (to.equals(RADIANS))
        factor = convert(MINUTES, DEGREES) * convert(DEGREES, RADIANS);
      else if (to.equals(DEGREES))
        factor = convert(MINUTES, HOURS) * convert(HOURS, DEGREES);
      else if (to.equals(ARCMINUTES))
        factor = convert(MINUTES, DEGREES) * convert(DEGREES, ARCMINUTES);
      else if (to.equals(ARCSECONDS))
        factor = convert(MINUTES, DEGREES) * convert(DEGREES, ARCSECONDS);
      else if (to.equals(HOURS))
        factor = 1.0 / convert(HOURS, MINUTES);
      else if (to.equals(SECONDS))
        factor = 60.0;
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    case 7: // SECONDS
      if (to.equals(RADIANS))
        factor = convert(SECONDS, DEGREES) * convert(DEGREES, RADIANS);
      else if (to.equals(DEGREES))
        factor = convert(SECONDS, HOURS) * convert(HOURS, DEGREES);
      else if (to.equals(ARCMINUTES))
        factor = convert(SECONDS, DEGREES) * convert(DEGREES, ARCMINUTES);
      else if (to.equals(ARCSECONDS))
        factor = convert(SECONDS, DEGREES) * convert(DEGREES, ARCSECONDS);
      else if (to.equals(HOURS))
        factor = 1.0 / convert(HOURS, SECONDS);
      else if (to.equals(MINUTES))
        factor = 1.0 / convert(MINUTES, SECONDS);
      else
        throw new IllegalArgumentException("Conversion between units "
            + from.getName() + " and " + to.getName() + " not supported");
      break;
    default:
      throw new IllegalArgumentException("Conversion between units "
          + from.getName() + " and " + to.getName() + " not supported");
    }
    return factor;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean equals(Object o) {
    if (o instanceof AngleUnit) {
      return (this.getId() == ((AngleUnit) o).getId());
    } else {
      return false;
    }
  }
}