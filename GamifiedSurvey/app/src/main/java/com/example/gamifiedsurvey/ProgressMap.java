package com.example.gamifiedsurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressMap extends AppCompatActivity {
    Button day1,day2,day3,day4,day5,day6,day7;
    Calendar calendar;
    ImageView loc1,loc2,loc3,loc4,loc5,loc6,loc7;
    String uid;
FirebaseAuth auth;
FirebaseDatabase db;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_map);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid=user.getUid();
        //uid = user.getUid( );
//       and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        db= FirebaseDatabase.getInstance();
        ref=db.getReference().child(uid).child("Days");

        day1=findViewById(R.id.btn1);
        day2=findViewById(R.id.btn2);
        day3=findViewById(R.id.btn3);
        day4=findViewById(R.id.btn4);
        day5=findViewById(R.id.btn5);
        day6=findViewById(R.id.btn6);
        day7=findViewById(R.id.btn7);

        loc1=findViewById(R.id.loc1);
        loc2=findViewById(R.id.loc2);
        loc3=findViewById(R.id.loc3);
        loc4=findViewById(R.id.loc4);
        loc5=findViewById(R.id.loc5);
        loc6=findViewById(R.id.loc6);
        loc7=findViewById(R.id.loc7);
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String FirstTime1 = preferences.getString("FirstTimeInstall2", "");

        if(FirstTime1.equals("Yes")){
            DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child(uid).child("Days");
            reff.addValueEventListener(new ValueEventListener( ) {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE");
                        Date d1 = new Date();
                        String dayOfTheWeek1 = sdf1.format(d1);
                        String Day1 = snapshot.child("FirstDay").getValue().toString();
                        String Day2 = snapshot.child("SecondDay").getValue().toString();
                        String Day3 = snapshot.child("ThirdDay").getValue().toString();
                        String Day4 = snapshot.child("FourthDay").getValue().toString();
                        String Day5 = snapshot.child("FifthDay").getValue().toString();
                        String Day6 = snapshot.child("SixthDay").getValue().toString();
                        String Day7 = snapshot.child("SevenDay").getValue().toString();

                        if(dayOfTheWeek1.equals(Day1)){
                            loc1.setVisibility(View.VISIBLE);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day2.setClickable(false);
                            day2.setEnabled(false);
                            day3.setClickable(false);
                            day3.setEnabled(false);
                            day4.setClickable(false);
                            day4.setEnabled(false);
                            day5.setClickable(false);
                            day5.setEnabled(false);
                            day6.setClickable(false);
                            day6.setEnabled(false);
                            day7.setClickable(false);
                            day7.setEnabled(false);
                        }
                        else if(dayOfTheWeek1.equals(Day2)){
                            loc2.setVisibility(View.VISIBLE);
                            day2.setEnabled(true);
                            day2.setClickable(true);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day3.setClickable(false);
                            day3.setEnabled(false);
                            day4.setClickable(false);
                            day4.setEnabled(false);
                            day5.setClickable(false);
                            day5.setEnabled(false);
                            day6.setClickable(false);
                            day6.setEnabled(false);
                            day7.setClickable(false);
                            day7.setEnabled(false);
                        }
                        else if(dayOfTheWeek1.equals(Day3)){
                            loc3.setVisibility(View.VISIBLE);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day2.setClickable(true);
                            day2.setEnabled(true);
                            day3.setClickable(true);
                            day3.setEnabled(true);
                            day4.setClickable(false);
                            day4.setEnabled(false);
                            day5.setClickable(false);
                            day5.setEnabled(false);
                            day6.setClickable(false);
                            day6.setEnabled(false);
                            day7.setClickable(false);
                            day7.setEnabled(false);
                        }
                        else if(dayOfTheWeek1.equals(Day4)){
                            loc4.setVisibility(View.VISIBLE);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day2.setClickable(true);
                            day2.setEnabled(true);
                            day3.setClickable(true);
                            day3.setEnabled(true);
                            day4.setClickable(true);
                            day4.setEnabled(true);
                            day5.setClickable(false);
                            day5.setEnabled(false);
                            day6.setClickable(false);
                            day6.setEnabled(false);
                            day7.setClickable(false);
                            day7.setEnabled(false);
                        }
                        else if(dayOfTheWeek1.equals(Day5)){
                            loc5.setVisibility(View.VISIBLE);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day2.setClickable(true);
                            day2.setEnabled(true);
                            day3.setClickable(true);
                            day3.setEnabled(true);
                            day4.setClickable(true);
                            day4.setEnabled(true);
                            day5.setClickable(true);
                            day5.setEnabled(true);
                            day6.setClickable(false);
                            day6.setEnabled(false);
                            day7.setClickable(false);
                            day7.setEnabled(false);
                        }
                        else if(dayOfTheWeek1.equals(Day6)){
                            loc6.setVisibility(View.VISIBLE);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day2.setClickable(true);
                            day2.setEnabled(true);
                            day3.setClickable(true);
                            day3.setEnabled(true);
                            day4.setClickable(true);
                            day4.setEnabled(true);
                            day5.setClickable(true);
                            day5.setEnabled(true);
                            day6.setClickable(true);
                            day6.setEnabled(true);
                            day7.setClickable(false);
                            day7.setEnabled(false);
                        }
                        else if(dayOfTheWeek1.equals(Day7)){
                            loc7.setVisibility(View.VISIBLE);
                            day1.setEnabled(true);
                            day1.setClickable(true);
                            day2.setClickable(true);
                            day2.setEnabled(true);
                            day3.setClickable(true);
                            day3.setEnabled(true);
                            day4.setClickable(true);
                            day4.setEnabled(true);
                            day5.setClickable(true);
                            day5.setEnabled(true);
                            day6.setClickable(true);
                            day6.setEnabled(true);
                            day7.setClickable(true);
                            day7.setEnabled(true);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else{
            SharedPreferences.Editor editor= preferences.edit();
            editor.putString("FirstTimeInstall2","Yes");
            editor.apply();
            SimpleDateFormat dateFormat= new SimpleDateFormat("EEE");
            Calendar currentCal = Calendar.getInstance();
            String currentdate=dateFormat.format(currentCal.getTime());
            loc1.setVisibility(View.VISIBLE);
            ref.child("FirstDay").setValue(currentdate);
//            DatabaseReference ref1= FirebaseDatabase.getInstance().getReference().child(and_id).child("Days").child("FirstDay");
//            ref1.setValue(currentdate);
            currentCal.add(Calendar.DATE, 1);
            String Date2=dateFormat.format(currentCal.getTime());
            ref.child("SecondDay").setValue(Date2);
//            DatabaseReference ref2= FirebaseDatabase.getInstance().getReference().child(and_id).child("Days").child("SevenDay");
//            ref2.setValue(Date2);
            Calendar currentCal1 = Calendar.getInstance();
            currentCal1.add(Calendar.DATE, 2);
            String Date3=dateFormat.format(currentCal1.getTime());
            ref.child("ThirdDay").setValue(Date3);

            Calendar currentCal2 = Calendar.getInstance();
            currentCal2.add(Calendar.DATE, 3);
            String Date4=dateFormat.format(currentCal2.getTime());
            ref.child("FourthDay").setValue(Date4);

            Calendar currentCal3 = Calendar.getInstance();
            currentCal3.add(Calendar.DATE, 4);
            String Date5=dateFormat.format(currentCal3.getTime());
            ref.child("FifthDay").setValue(Date5);

            Calendar currentCal4 = Calendar.getInstance();
            currentCal4.add(Calendar.DATE, 5);
            String Date6=dateFormat.format(currentCal4.getTime());
            ref.child("SixthDay").setValue(Date6);

            Calendar currentCal5 = Calendar.getInstance();
            currentCal5.add(Calendar.DATE, 6);
            String endDate=dateFormat.format(currentCal5.getTime());
            DatabaseReference ref6= FirebaseDatabase.getInstance().getReference().child(uid).child("Days").child("SevenDay");
            ref6.setValue(endDate);

        }
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
//        Date d = new Date();
//        String dayOfTheWeek = sdf.format(d);
//        Log.d("day",dayOfTheWeek);
//
//        switch (dayOfTheWeek){
//        case "Mon":
//            loc1.setVisibility(View.VISIBLE);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day2.setClickable(false);
//            day2.setEnabled(false);
//            day3.setClickable(false);
//            day3.setEnabled(false);
//            day4.setClickable(false);
//            day4.setEnabled(false);
//            day5.setClickable(false);
//            day5.setEnabled(false);
//            day6.setClickable(false);
//            day6.setEnabled(false);
//            day7.setClickable(false);
//            day7.setEnabled(false);
//            break;
//        case "Tue":
//            loc2.setVisibility(View.VISIBLE);
//            day2.setEnabled(true);
//            day2.setClickable(true);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day3.setClickable(false);
//            day3.setEnabled(false);
//            day4.setClickable(false);
//            day4.setEnabled(false);
//            day5.setClickable(false);
//            day5.setEnabled(false);
//            day6.setClickable(false);
//            day6.setEnabled(false);
//            day7.setClickable(false);
//            day7.setEnabled(false);
//            break;
//        case "Wed":
//            loc3.setVisibility(View.VISIBLE);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day2.setClickable(true);
//            day2.setEnabled(true);
//            day3.setClickable(true);
//            day3.setEnabled(true);
//            day4.setClickable(false);
//            day4.setEnabled(false);
//            day5.setClickable(false);
//            day5.setEnabled(false);
//            day6.setClickable(false);
//            day6.setEnabled(false);
//            day7.setClickable(false);
//            day7.setEnabled(false);
//            break;
//        case "Thu":
//            loc4.setVisibility(View.VISIBLE);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day2.setClickable(true);
//            day2.setEnabled(true);
//            day3.setClickable(true);
//            day3.setEnabled(true);
//            day4.setClickable(true);
//            day4.setEnabled(true);
//            day5.setClickable(false);
//            day5.setEnabled(false);
//            day6.setClickable(false);
//            day6.setEnabled(false);
//            day7.setClickable(false);
//            day7.setEnabled(false);
//            break;
//        case "Fri":
//            loc5.setVisibility(View.VISIBLE);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day2.setClickable(true);
//            day2.setEnabled(true);
//            day3.setClickable(true);
//            day3.setEnabled(true);
//            day4.setClickable(true);
//            day4.setEnabled(true);
//            day5.setClickable(true);
//            day5.setEnabled(true);
//            day6.setClickable(false);
//            day6.setEnabled(false);
//            day7.setClickable(false);
//            day7.setEnabled(false);
//            break;
//        case "Sat":
//            loc6.setVisibility(View.VISIBLE);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day2.setClickable(true);
//            day2.setEnabled(true);
//            day3.setClickable(true);
//            day3.setEnabled(true);
//            day4.setClickable(true);
//            day4.setEnabled(true);
//            day5.setClickable(true);
//            day5.setEnabled(true);
//            day6.setClickable(true);
//            day6.setEnabled(true);
//            day7.setClickable(false);
//            day7.setEnabled(false);
//            break;
//        case "Sun":
//            loc7.setVisibility(View.VISIBLE);
//            day1.setEnabled(true);
//            day1.setClickable(true);
//            day2.setClickable(true);
//            day2.setEnabled(true);
//            day3.setClickable(true);
//            day3.setEnabled(true);
//            day4.setClickable(true);
//            day4.setEnabled(true);
//            day5.setClickable(true);
//            day5.setEnabled(true);
//            day6.setClickable(true);
//            day6.setEnabled(true);
//            day7.setClickable(true);
//            day7.setEnabled(true);
//            break;
//        }

        day1.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });

        day2.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });

        day3.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });

        day4.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });

        day5.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });

        day6.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });

        day7.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ProgressMap.this,SplashScreen2.class ));
            }
        });
    }
}