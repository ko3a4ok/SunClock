package com.someclock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.someclock.java.Main;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((TextView)findViewById(R.id.text)).setText(new Main().getSome(13));
    }
}
