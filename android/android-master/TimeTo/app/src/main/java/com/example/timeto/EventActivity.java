package com.example.timeto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            int row = extras.getInt("rowId");
            Log.d(TAG, "Row = " + row);
        }
    }
}
