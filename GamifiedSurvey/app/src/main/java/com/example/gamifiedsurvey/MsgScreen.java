package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MsgScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_screen);
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(MsgScreen.this,FeedbackQuestions.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                }
                catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();

    }
    }
