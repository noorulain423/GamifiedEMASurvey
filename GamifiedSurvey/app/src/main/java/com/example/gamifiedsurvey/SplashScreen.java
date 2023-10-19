package com.example.gamifiedsurvey;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashScreen extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION =1;
    FirebaseAuth mAuth;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
            isMyServiceRunning(LocationMonitoringService.class);

        }
        else{
            isMyServiceRunning(LocationMonitoringService.class);

        }
       mAuth = FirebaseAuth.getInstance( );
        mAuth.signInAnonymously( ).addOnCompleteListener(this,new OnCompleteListener<AuthResult>( ) {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful( )) {
                    FirebaseUser user = mAuth.getCurrentUser( );
                    uid = user.getUid( );

                } else {
                    Toast.makeText(SplashScreen.this,"Authentication failed.",Toast.LENGTH_SHORT).show( );
                }
            }
        });
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),FirstTimeDisplay.class);
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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {


                return true;
            }
            else {
                Intent startServiceIntent1 = new Intent(SplashScreen.this, LocationMonitoringService.class);
                startServiceIntent1.putExtra(LocationMonitoringService.EXTRA_RESULT_INTENT, "sERVICE");
                startService(startServiceIntent1);


            }
        }
        return false;
    }

}