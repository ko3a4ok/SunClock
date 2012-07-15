package com.someclock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.Number3d;

public class Min3DActivityTest extends RendererActivity implements SensorEventListener {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_min3_dactivity_test);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_min3_dactivity_test, menu);
//        return true;
//    }

	private final float FILTERING_FACTOR = .3f;
	
    private Object3dContainer objModel;
	private Sensor mAccelerometer;
	private Number3d mAccVals;
	private SensorManager mSensorManager;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mAccVals = new Number3d();
	}

	@Override
	public void initScene() {
//		loadText(R.raw.sun_clock);
		scene.lights().add(new Light());

		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "com.someclock:raw/sun_clock", true);
		parser.parse();
		objModel = parser.getParsedObject();
		objModel.scale().x = objModel.scale().y = objModel.scale().z = .3f;
		scene.addChild(objModel);
		
		objModel.position().y = -2.5f;
		objModel.position().z = -3;
		
		
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		
	}

	@Override
	public void updateScene() {
//		objModel.rotation().x++;
//		objModel.rotation().z++;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ORIENTATION)
            return;

		// low-pass filter to make the movement more stable
		mAccVals.x = (float) (-event.values[1] * FILTERING_FACTOR + mAccVals.x * (1.0 - FILTERING_FACTOR));
		mAccVals.y = (float) (event.values[2] * FILTERING_FACTOR + mAccVals.y * (1.0 - FILTERING_FACTOR));
		mAccVals.z = (float) (event.values[0] * FILTERING_FACTOR + mAccVals.z * (1.0 - FILTERING_FACTOR));

		scene.camera().position.x = mAccVals.x * .2f;
        scene.camera().position.y = mAccVals.y * .2f;
        scene.camera().position.z = mAccVals.z * .2f;
		
//		scene.camera().position.x = event.values[1]+5;
//        scene.camera().position.y = event.values[2]+5;
//        scene.camera().position.z = event.values[0]+5;

        scene.camera().target.x = -scene.camera().position.x;
        scene.camera().target.y = -scene.camera().position.y;
        scene.camera().target.z = -scene.camera().position.z;
		
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
    
//	public void loadText(int resourceId) {
//	    // The InputStream opens the resourceId and sends it to the buffer
//	    InputStream is = this.getResources().openRawResource(resourceId);
//	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
//	    String readLine = null;
//
//	    try {
//	        // While the BufferedReader readLine is not null 
//	        while ((readLine = br.readLine()) != null) {
//	    }
//
//	    // Close the InputStream and BufferedReader
//	    is.close();
//	    br.close();
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
}
