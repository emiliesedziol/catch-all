package com.example.timeto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class EventDBHelper extends SQLiteOpenHelper {

    // constructor
    public EventDBHelper(Context context) {
        super(context, "EVENT_DB_NAME", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create database
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getAllEventTitles() {

    ArrayList<String> array_list = new ArrayList<String>();

        String temp = "";
    for (int i=0; i < 10; i++) {
        temp = "Item Number " + Integer.toString(i + 1);
        array_list.add(temp);
    }
    return array_list;
    }
}
