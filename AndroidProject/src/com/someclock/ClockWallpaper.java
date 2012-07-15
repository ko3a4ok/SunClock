package com.someclock;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.service.wallpaper.WallpaperService;
import com.badlogic.gdx.backends.android.livewallpaper.AndroidApplicationLW;
import com.eightbitmage.gdxlw.LibdgxWallpaperListener;
import com.eightbitmage.gdxlw.LibdgxWallpaperService;
import com.someclock.java.OrthographicCameraController;

/**
 * Created with IntelliJ IDEA.
 * User: ko3a4ok
 * Date: 7/15/12
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClockWallpaper extends LibdgxWallpaperService implements SensorEventListener {

    private OrthographicCameraController cameraController;
    private SensorManager sensorManager;

    @Override
    public WallpaperService.Engine onCreateEngine() {
        return new ExampleLibdgxWallpaperEngine(this);
    }

    public class ExampleLibdgxWallpaperEngine extends LibdgxWallpaperEngine {

        public ExampleLibdgxWallpaperEngine(
                LibdgxWallpaperService libdgxWallpaperService) {
            super(libdgxWallpaperService);
        }

        @Override
        protected void initialize(AndroidApplicationLW androidApplicationLW) {

            LibdgxWallpaperListener app = new LibdgxWallpaperListener() {

                @Override
                public void offsetChange(float v, float v1, float v2, float v3, int i, int i1) {

                }

                @Override
                public void setIsPreview(boolean b) {

                }
            };

            setWallpaperListener(app);

            androidApplicationLW.initialize(cameraController = new OrthographicCameraController(), false);
            sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(ClockWallpaper.this,
                    sensorManager.getDefaultSensor(type),
                    SensorManager.SENSOR_DELAY_NORMAL);

        }

    }
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;


        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        cameraController.setChord(y, z, x);
        //cameraController.setAngle(x);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    int type = Sensor.TYPE_ORIENTATION;
}