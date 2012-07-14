package com.someclock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.cetus.astro.SunPosition;
import org.cetus.astro.SunPositionAlgorithmLowRes;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.mhuss.AstroLib.Astro;
import com.mhuss.AstroLib.DarkCalCalc;
import com.mhuss.AstroLib.DarkCalInput;
import com.mhuss.AstroLib.DateOps;
import com.mhuss.AstroLib.Latitude;
import com.mhuss.AstroLib.Longitude;
import com.mhuss.AstroLib.ObsInfo;
import com.mhuss.AstroLib.PlanetData;
import com.mhuss.AstroLib.Planets;
import com.mhuss.AstroLib.RiseSet;
import com.mhuss.AstroLib.TimeOps;
import com.mhuss.AstroLib.TimePair;

public class SunLocationTestActivity extends Activity {

	private TextView text;

	private static final String TAG = SunLocationTestActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sun_location_test);

		text = (TextView) findViewById(R.id.text);

		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sun_location_test, menu);
		return true;
	}

	private void makeUseOfNewLocation(Location location) {
		GregorianCalendar calendar = new GregorianCalendar();
		double jd = DateOps.calendarToDoubleDay(calendar);
		ObsInfo oi = new ObsInfo(new Latitude(location.getLatitude()), new Longitude(location.getLongitude()));
		PlanetData pde = new PlanetData(Planets.SUN, jd, oi);

		// try {
		// text.setText("Sun Lon = " + Math.toDegrees(pde.getEclipticLon()));
		// text.setText(text.getText() + "\n" + "Sun Lat = " +
		// Math.toDegrees(pde.getEclipticLat()));
		// } catch (NoInitException e) {
		// text.setText(e.toString());
		// }

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		TimeZone zone = calendar.getTimeZone();
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		SunPositionAlgorithmLowRes alg = new SunPositionAlgorithmLowRes(year, month, day, hour, minute, second, zone, longitude, latitude);
		SunPosition position = alg.calculateSunPosition();

		Log.d(TAG, "cur location lon: " + location.getLongitude());
		Log.d(TAG, "cur location lat: " + location.getLatitude());
		Log.d(TAG, "SUN Alg altitude: " + position.getAltitude());
		Log.d(TAG, "SUN Alg azimuth: " + position.getAzimuth());

		StringBuffer msg = new StringBuffer();
		msg.append("cur location lon: " + location.getLongitude());
		msg.append("\n" + "cur location lat: " + location.getLatitude());
		msg.append("\n" + "SUN Alg altitude: " + position.getAltitude());
		msg.append("\n" + "SUN Alg azimuth: " + position.getAzimuth());

		DarkCalInput dci = new DarkCalInput(month, year, location.getLongitude(), location.getLatitude(), TimeOps.tzOffset(calendar));
		DarkCalCalc dark = new DarkCalCalc(dci, true);

		PlanetData pd = new PlanetData(Planets.SUN, jd, oi);
		TimePair times = RiseSet.getTimes(Planets.SUN, jd, oi, pd);
		Date rise = toLocalTime(formatTime(times.a));
		Date set = toLocalTime(formatTime(times.b));
		// msg.append("\nOffset: " + calendar.getTimeZone().getRawOffset());
		msg.append("\nRise: " + rise);
		msg.append("\nSet: " + set);

		text.setText(msg.toString());
		Log.d(TAG, msg.toString());
	}

	private Date toLocalTime(Date date) {
		Calendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		Calendar cur = new GregorianCalendar();
		
		Log.d(TAG, utc.toString());
		Log.d(TAG, cur.toString());
//		return date;
		int year = cur.get(Calendar.YEAR);  
		int month = cur.get(Calendar.MONTH);
		int day = cur.get(Calendar.DATE);
		int hour = date.getHours() + (utc.get(Calendar.HOUR) - cur.get(Calendar.HOUR));
		int minute = date.getYear() + (utc.get(Calendar.MINUTE) - cur.get(Calendar.MINUTE));
		Date out = new Date(year, month, day, hour, minute);
		return out;
	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location
			// provider.
			makeUseOfNewLocation(location);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

	/**
	 * Format a time as a <TT>String</TT> using the format <TT>HH:MM</TT>. <BR>
	 * The returned string will be "--:--" if the time is INVALID.
	 * 
	 * @param t
	 *            The time to format in days
	 * 
	 * @return The formatted String
	 */
	public static Date formatTime(double t) {
		// String ft = "--:--";
		Date out = null;

		if (t >= 0D) {
			// round up to nearest minute
			int minutes = (int) (t * Astro.HOURS_PER_DAY * Astro.MINUTES_PER_HOUR + Astro.ROUND_UP);
			// ft = twoDigits(minutes / Astro.IMINUTES_PER_HOUR) + ":" +
			// twoDigits(minutes % Astro.IMINUTES_PER_HOUR);
			out = new Date(0, 0, 0, minutes / Astro.IMINUTES_PER_HOUR, minutes % Astro.IMINUTES_PER_HOUR);
		}

		return out;
	}

	// -------------------------------------------------------------------------
	// returns String version of two digit number, with leading zero if needed
	// The input is expected to be in the range 0 to 99
	//
	private static String twoDigits(int i) {
		return (i > 9) ? "" + i : "0" + i;
	}
}
