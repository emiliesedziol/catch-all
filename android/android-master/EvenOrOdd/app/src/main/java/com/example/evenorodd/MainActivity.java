package com.example.evenorodd;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main activity";

    private TextView mDisplayNumber;

    private int mResult = 0;

    private TextView mDisplayResult;

    private Button mEvenButton;
    private Button mOddButton;
    private Button mAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to number
        mDisplayNumber = (TextView) findViewById(R.id.numberTextView);

        // get ref to result text
        mDisplayResult = (TextView) findViewById(R.id.resultTextView);

        //get references to odd button and setup the click listener
        mOddButton = (Button) findViewById(R.id.oddButton);
        mOddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkResult(false)) {
                    mDisplayResult.setText("Try again, this isn't an odd number");
                    mDisplayResult.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    mDisplayResult.setText("Yes, it is Odd");
                    mDisplayResult.setTextColor(Color.BLUE);
                }
                changeGuessButtonState(false);
                changeAgainButtonState(true);
            }
        });

        // get references to even  and setup the click listener
        mEvenButton = (Button) findViewById(R.id.evenButton);
        mEvenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkResult(true)) {
                    mDisplayResult.setText("Good job, it is Even");
                    mDisplayResult.setTextColor(Color.BLUE);
                }
                else {
                    mDisplayResult.setText("hmmm, are you sure");
                    mDisplayResult.setTextColor(Color.parseColor("#ff0000"));
                }
                changeGuessButtonState(false);
                changeAgainButtonState(true);
            }
        });

        // get again button and setup the click listener
        mAgainButton = (Button) findViewById(R.id.againButton);
        mAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberGenerator();
                mDisplayResult.setText("NEW Number, is it even or odd??");

                mDisplayResult.setTextColor(Color.parseColor("#000000"));
                mOddButton.setBackgroundColor(Color.parseColor("#00bfff"));
                mOddButton.setTextColor(Color.BLACK);
                mOddButton.setClickable(true);

                mEvenButton.setBackgroundColor(Color.parseColor("#00bfff"));
                mEvenButton.setTextColor(Color.BLACK);
                mEvenButton.setClickable(true);

                mAgainButton.setBackgroundColor(Color.BLACK);
                mAgainButton.setTextColor(Color.WHITE);

                changeAgainButtonState(false);

            }
        });

        // generate first number
        numberGenerator();
        mDisplayNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 100);

    }

    // method to generate a random number
    void numberGenerator() {
        Random r = new Random();
        int low = 1;
        int high = 999;
        mResult = r.nextInt(high) + low;

        Log.d(TAG, "new number: " + mResult);

        String displayNumberAsString = Integer.toString(mResult);
        mDisplayNumber.setText(displayNumberAsString);

    }

    boolean checkResult(boolean userGuess) {
        boolean userGuessCorrectly = false;
        boolean numberEvenOrOdd = false;
        if ((mResult % 2) == 0) {
            numberEvenOrOdd = true;
        }
        if ((userGuess && numberEvenOrOdd) || (!userGuess && numberEvenOrOdd) ) {
            userGuessCorrectly = true;
        }
        Log.d(TAG, "new number is " + userGuessCorrectly);
        return userGuessCorrectly;
    }

    void changeGuessButtonState(boolean enable) {

/*        float alphaValue = .75f;
        if (enable) {
            alphaValue = 1.0f;
        } */
        mEvenButton.setClickable(enable);
       // mEvenButton.setAlpha(alphaValue);
        mOddButton.setClickable(enable);
      //  mOddButton.setAlpha(alphaValue);
        mAgainButton.setClickable(!enable);

        mOddButton.setBackgroundColor(Color.BLACK);
        mOddButton.setTextColor(Color.WHITE);
        mEvenButton.setBackgroundColor(Color.BLACK);
        mEvenButton.setTextColor(Color.WHITE);

    }
    void changeAgainButtonState(boolean enable) {
        if (enable) {
            mAgainButton.setBackgroundColor(Color.parseColor("#00bfff"));
            mAgainButton.setTextColor(Color.WHITE);
        }
        else {
            mAgainButton.setBackgroundColor(Color.BLACK);
            mAgainButton.setTextColor(Color.WHITE);
        }
        mAgainButton.setClickable(enable);
    }
}
