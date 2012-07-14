package com.someclock;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.someclock.java.MyFirstTriangle;

public class MainActivity extends AndroidApplication {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new MyFirstTriangle(), false);
    }
}
