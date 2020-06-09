package com.example.timeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EventDBHelper mEventDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mEventDBHelper = new EventDBHelper(this);

        // get items for display
        ArrayList arrayList = mEventDBHelper.getAllEventTitles();
        // create array adapter and add to list
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        // attached arraylist to adapter
        ListView theList = findViewById(R.id.theListView);
        theList.setAdapter(arrayAdapter);

        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = " Row clicked -> " + Integer.toString(position + 1);
                Log.d(TAG, temp);
               // Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();

                Bundle theBundle = new Bundle();
                theBundle.putInt("rowId", position);

                Intent theIntent = new Intent(MainActivity.this, EventActivity.class);
                theIntent.putExtras(theBundle);
                startActivity(theIntent);
;            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(TAG, "settings selected");
            Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_SHORT).show();
            return true;
        } if (id == R.id.action_add) {
            Log.d(TAG, "Add Menu");

            Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
