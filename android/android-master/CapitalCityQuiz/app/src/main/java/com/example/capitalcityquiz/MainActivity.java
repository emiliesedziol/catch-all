package com.example.capitalcityquiz;

import android.content.Intent;
// import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(MainActivity.this, GameActivity.class);
                theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(theIntent);
            }
        });

 /*       CountDownTimer time = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent theIntent = new Intent(MainActivity.this, GameActivity.class);
                theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(theIntent);
            }
        }.start(); */
    }
}
