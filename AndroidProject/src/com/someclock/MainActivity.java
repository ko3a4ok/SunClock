package com.someclock;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.someclock.java.MyCube;
import com.someclock.java.MyFirstTriangle;
import com.someclock.java.OrthographicCameraController;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AndroidApplication implements SensorEventListener {

    private MyCube cameraController;

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraController = new MyCube();
        initialize(cameraController, false);
//        setContentView(R.layout.main);
//        textView = (TextView) findViewById(R.id.text);
        sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(type),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorManager sensorManager;
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;


        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        cameraController.setChord(x, y, z);
//        String s = String.format("%f\n%f\n%f", x, y, z);
//        textView.setText(s);
        //cameraController.setAngle(x);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    int type = Sensor.TYPE_ALL;
    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(type),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
