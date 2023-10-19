package com.example.gamifiedsurvey;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


Button setting, start, profile;
    FirebaseDatabase db;
    DatabaseReference ref;
    String uid;
    TextView coinns, pengID;
    FirebaseAuth auth;

    private static final int REQUEST_CODE_LOCATION_PERMISSION =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinns =findViewById(R.id.textView11);
        setting = findViewById(R.id.set);
        start= findViewById(R.id.button);
        profile=findViewById(R.id.button3);
        pengID=findViewById(R.id.textView12);
        db= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);

        FirebaseMessaging.getInstance().subscribeToTopic("All")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "";
                        if (!task.isSuccessful()) {
                            msg = "";
                        }

                    }
                });
        ref = db.getReference(uid).child("ProfileData").child("TotalCoins");
        ref.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                int coins= Integer.parseInt(snapshot.getValue().toString());
                coinns.setText("      "+coins);}
                else{
                    coinns.setText("      "+0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref=db.getReference(uid).child("ProfileData").child("UniId");
        ref.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pID= snapshot.getValue().toString();
                    pengID.setText("Penguin id: "+pID);
                }
                else {
                    pengID.setText("Penguin id: " );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ScreenTimeBroadcastReceiver
                screenTimeBroadcastReceiver = new ScreenTimeBroadcastReceiver();
        IntentFilter lockFilter = new IntentFilter();
        lockFilter.addAction(Intent.ACTION_SCREEN_ON);
        lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenTimeBroadcastReceiver, lockFilter);



        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),SettingActivity.class));

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MainActivity.this,StatisticsActivity.class ));
            }
        });
        profile.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MainActivity.this,Profile.class ));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode,permission,grantResult);
        if(requestCode== REQUEST_CODE_LOCATION_PERMISSION && grantResult.length>0){
            if(grantResult[0]== PackageManager.PERMISSION_GRANTED){
//                startLocationService();
            }
            else {
                Toast.makeText(this,"Location permission denied ",Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isLocationServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null){
            for (ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationServices.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }

                }
            }
            return false;
        }
        return false;
    }
}