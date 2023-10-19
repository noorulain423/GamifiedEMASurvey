package com.example.gamifiedsurvey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen2 extends AppCompatActivity {
    final Context context = this;
    FirebaseAuth auth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Log.d("day",dayOfTheWeek);
        DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child(uid).child("Days").child("SevenDay");
        reff.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String day = snapshot.getValue().toString();
                Thread background = new Thread() {
                    public void run() {
                        try {
                            // Thread will sleep for 5 seconds
                            sleep(3000);
                            finish( );
                            if (dayOfTheWeek.equals(day)) {
                                startActivity(new Intent(SplashScreen2.this,R1.class));
                            }
                            else {
                                startActivity(new Intent(SplashScreen2.this,R2.class));
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                };
                // start thread
                background.start();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Thread background = new Thread() {
//            public void run() {
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(3000);

                    // After 5 seconds redirect to another intent

//                    switch (dayOfTheWeek){
//                    case "Sun":
//                        startActivity(new Intent(SplashScreen2.this,R1.class));
//                        break;
//                    case "Mon":
//                        startActivity(new Intent(SplashScreen2.this,R2.class));
//                        break;
//                    case "Tue":
//                        startActivity(new Intent(SplashScreen2.this,R2.class));
//                        break;
//                    case "Wed":
//                        startActivity(new Intent(SplashScreen2.this,R2.class));
//                        break;
//                    case "Thu":
//                        startActivity(new Intent(SplashScreen2.this,R2.class));
//                        break;
//                    case "Fri":
//                        startActivity(new Intent(SplashScreen2.this,R2.class));
//                        break;
//                    case "Sat":
//                        startActivity(new Intent(SplashScreen2.this,R2.class));
//                        break;
//                    }
//
//                    //Remove activity
//                    finish();
//                }
//                catch (Exception e) {
//                }
//            }
//        };
//        // start thread
//        background.start();

    }
}