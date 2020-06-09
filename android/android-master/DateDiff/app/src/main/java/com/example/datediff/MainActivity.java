package com.example.datediff;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button mCurrentDateButton;
    Button mButtonDateOne;
    Button mButtonDateTwo;
    Button mButtonCalculate;
    TextView mDiffTextView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // *****************************************************
        // create overall linear layout
        // *****************************************************
        LinearLayout mainLinearLayout = new LinearLayout(this);

        // set orientation to vertical
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // set size ot parent
        LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);

        // create title text manually
        TextView titleTextView = new TextView(this);
        titleTextView.setId(1000);  // warning message disappears when '@SuppressLint*"ResourceType") is added above

        titleTextView.setText(getResources().getString(R.string.title));
        TextViewCompat.setTextAppearance(titleTextView, R.style.fontTitle);
        titleTextView.setGravity(Gravity.CENTER);

        mainLinearLayout.addView(titleTextView);

        // create listener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateText = Integer.toString(month + 1) + "/" + Integer.toString(dayOfMonth)
                        + "/" + Integer.toString(year);
                setTextCurrentDateButton(dateText);

                Log.d(TAG, "date changed");
            }
        };

        // calendar object to get today's date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // dialog - picker
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, year, month, day);

        // create textview label
        final TextView dateOneLabel = new TextView(this);
        dateOneLabel.setText(getResources().getString(R.string.enterDateOne));
        mainLinearLayout.addView(dateOneLabel);

        // create buttons
        mButtonDateOne = new Button(this);
        mButtonDateOne.setText(getResources().getString(R.string.dateOneButton));
        mButtonDateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDateButton = mButtonDateOne;
                datePickerDialog.show();
                Log.d(TAG, "click on button one");
            }
        });
        mainLinearLayout.addView(mButtonDateOne);

        final TextView dateTwoLabel = new TextView(this);
        dateTwoLabel.setText(getResources().getString(R.string.enterDateTwo));
        mainLinearLayout.addView(dateTwoLabel);

        mButtonDateTwo = new Button(this);
        mButtonDateTwo.setText(getResources().getString(R.string.dateTwoButton));
        mButtonDateTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDateButton = mButtonDateTwo;
                datePickerDialog.show();
                Log.d(TAG, "click on button two");
            }
        });
        mainLinearLayout.addView(mButtonDateTwo);

        mButtonCalculate = new Button(this);
        mButtonCalculate.setText(getResources().getString(R.string.calculateButton));
        mButtonCalculate.setPadding(40, 20, 40, 20);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lp.setMargins(250, 40, 250, 40);
        mButtonCalculate.setLayoutParams(lp);
        mButtonCalculate.setBackgroundColor(R.color.colorPrimary);
        mButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDateDiff(getDiffInDays());

                Log.d(TAG, "calculate button clicked");
            }
        });
        mainLinearLayout.addView(mButtonCalculate);

        // calculate display difference in a textView
        mDiffTextView = new TextView(this);
 //       mDiffTextView.setText(getResources().getString(R.string.diffText));
        TextViewCompat.setTextAppearance(mDiffTextView, R.style.fontDiffDisplay);
        mDiffTextView.setPadding(100, 40, 100, 20);
        mDiffTextView.setGravity(Gravity.CENTER);
        mainLinearLayout.addView(mDiffTextView);



        setContentView(mainLinearLayout);
    }

    // set date for a button
    void setTextCurrentDateButton(String dateText) {
        mCurrentDateButton.setText(dateText);
    }

    int getDiffInDays() {
        int diffDays = 0;

        // both java and android has a simpleDateFormat, Java is being used
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");

        try {
            java.util.Date dateOne = simpleDateFormat.parse(mButtonDateOne.getText().toString());
            java.util.Date dateTwo = simpleDateFormat.parse(mButtonDateTwo.getText().toString());

            diffDays = (int) ((dateTwo.getTime() - dateOne.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            Log.d(TAG, "problem with one or both dates");
        }

        return diffDays;
    }
    void setDateDiff(int diff) {
        String diffText;

        if (diff == 0) {
            diffText = "Same Date";
        }
        else {
            diffText = "Difference " + Integer.toString(diff) + " ";
            if (diff > 1) {
                diffText += "days";
            }
            else {
                diffText += "hmmmm";
            }
        }
        mDiffTextView.setText(diffText);
    }
}
