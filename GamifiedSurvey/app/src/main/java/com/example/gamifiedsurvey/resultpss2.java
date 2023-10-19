package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class resultpss2 extends AppCompatActivity {
    String date, LName;
    String uid;
    FirebaseDatabase db;
    DatabaseReference ref, getdata, getname, setscoresInProfile;
    TextView tv, tvresponses , coins;
    private int NoOfResponses, c;
    Button next;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpss2);
        next=findViewById(R.id.button5);
        coins=findViewById(R.id.resulttv2);
        Intent intent=getIntent();
        date= intent.getStringExtra("Date");
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        tv=findViewById(R.id.resulttv);
        tvresponses=findViewById(R.id.textView8);
        db = FirebaseDatabase.getInstance();
        Log.d("datemsg",date);
        setscoresInProfile=db.getReference(uid).child("ProfileData");
        ref = db.getReference(uid).child("Responses").child(date);
        ref.addValueEventListener(new ValueEventListener(){

                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                                          int r1= Integer.parseInt(snapshot.child("Q2").getValue().toString().trim());
                                          int r2= Integer.parseInt(snapshot.child("Q4").getValue().toString().trim());
                                          int r3= Integer.parseInt(snapshot.child("Q5").getValue().toString().trim());
                                          int r4= Integer.parseInt(snapshot.child("Q10").getValue().toString().trim());

                                          Log.d("r1",String.valueOf(r1));
                                          Log.d("r2",String.valueOf(r2));
                                          Log.d("r3",String.valueOf(r3));
                                          Log.d("r4",String.valueOf(r4));


                                          int updater4= PositiveResponses(r1);
                                          int updater5= PositiveResponses(r2);
                                          int updater7= PositiveResponses(r3);
                                          int updater8= PositiveResponses(r4);

                                          Log.d("Valr1",String.valueOf(updater4));
                                          Log.d("Valr2",String.valueOf(updater5));
                                          Log.d("Valr3",String.valueOf(updater7));
                                          Log.d("Valr4",String.valueOf(updater8));

                                          int r = updater4+updater5+updater7+updater8;
DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child(uid).child("Responses").child(date);
dbref.child("Result").setValue(r);
                                          Log.d("rvalue",String.valueOf(r));
                                          if(r>=0 && r<=5) {
                                              tv.setText("Your perceived stress level is 'LOW' ");}
                                          else if (r>=6 && r<=11){
                                              tv.setText(" Your perceived stress level is 'MODERATE' ");}
                                          else if( r>=12 && r <=16){
                                              tv.setText("Your perceived stress level is 'HIGH' ");}


                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {
                                          Log.d("Canceledmsg","no data read");

                                      }
                                  }
        );
        getname = db.getReference(uid).child("ProfileData");
        getname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                LName = snapshot.child("UniId").getValue().toString().trim();
                Log.d("UserName",LName);}
                else {
                    LName= "Anonymous";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UserName","Not Found !");
            }
        });
        ref = db.getReference("LeaderBoard").child(uid);

        getdata = db.getReference(uid).child("Responses");
        getdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NoOfResponses = (int) snapshot.getChildrenCount();
                    Log.d("Total responses",String.valueOf(NoOfResponses));
                    ref.child("Name").setValue(LName);
                    ref.child("Scores").setValue(NoOfResponses);
                    tvresponses.setText(" You have jumped "+NoOfResponses+ " steps !");
                    setscoresInProfile.child("TotalResponses").setValue(NoOfResponses);
                    c= NoOfResponses*3;
                    coins.setText(" "+c);
                    setscoresInProfile.child("TotalCoins").setValue(c);
                } else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        next.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultpss2.this,MsgScreen.class);
                startActivity(intent);
            }
        });

    }

    public int PositiveResponses(int r){
        switch (r){
        case 0:
            r=4;
            break;
        case 1:
            r=3;
            break;
        case 2:
            r=2;
            break;
        case 3:
            r = 1 ;
            break;
        case 4:
            r=0;
            break;
        }
        return r;
    }

}