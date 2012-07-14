package com.someclock;

import java.util.Calendar;
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

import com.mhuss.AstroLib.DateOps;
import com.mhuss.AstroLib.Latitude;
import com.mhuss.AstroLib.Longitude;
import com.mhuss.AstroLib.ObsInfo;
import com.mhuss.AstroLib.PlanetData;
import com.mhuss.AstroLib.Planets;

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

		text.setText(msg.toString());
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
}
