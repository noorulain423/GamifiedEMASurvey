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

public class resultpss extends AppCompatActivity {
    String date,   LName;
    String uid;
    FirebaseDatabase db;
    DatabaseReference ref, getdata, getname, setscoresInProfile;
    int r1,r2,r3,r4,r5,r6,r7,r8,r9,r10;
    TextView tv, tvres, coin;
    int NoOfResponses, c;
    Button next;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpss);
        next=findViewById(R.id.btnnext6);
        coin= findViewById(R.id.textView9);
        Intent intent=getIntent();
       date= intent.getStringExtra("Date");
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        tv=findViewById(R.id.rtv);
        tvres=findViewById(R.id.textView7);
        db = FirebaseDatabase.getInstance();
        Log.d("datemsg",date);
        ref = db.getReference(uid).child("Responses").child(date);
        setscoresInProfile=db.getReference(uid).child("ProfileData");
        ref.addValueEventListener(new ValueEventListener(){

                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                                          int r1= Integer.parseInt(snapshot.child("Q1").getValue().toString().trim());
                                          int r2= Integer.parseInt(snapshot.child("Q2").getValue().toString().trim());
                                          int r3= Integer.parseInt(snapshot.child("Q3").getValue().toString().trim());
                                          int r4= Integer.parseInt(snapshot.child("Q4").getValue().toString().trim());
                                          int r5= Integer.parseInt(snapshot.child("Q5").getValue().toString().trim());
                                          int r6= Integer.parseInt(snapshot.child("Q6").getValue().toString().trim());
                                          int r7= Integer.parseInt(snapshot.child("Q7").getValue().toString().trim());
                                          int r8= Integer.parseInt(snapshot.child("Q8").getValue().toString().trim());
                                          int r9= Integer.parseInt(snapshot.child("Q9").getValue().toString().trim());
                                          int r10= Integer.parseInt(snapshot.child("Q10").getValue().toString().trim());
                                          Log.d("r1",String.valueOf(r1));
                                          Log.d("r2",String.valueOf(r2));
                                          Log.d("r3",String.valueOf(r3));
                                          Log.d("r4",String.valueOf(r4));
                                          Log.d("r5",String.valueOf(r5));
                                          Log.d("r6",String.valueOf(r6));
                                          Log.d("r7",String.valueOf(r7));
                                          Log.d("r8",String.valueOf(r8));
                                          Log.d("r9",String.valueOf(r9));
                                          Log.d("r10",String.valueOf(r10));

                                          int updater4= PositiveResponses(r4);
                                          int updater5= PositiveResponses(r5);
                                          int updater7= PositiveResponses(r7);
                                          int updater8= PositiveResponses(r8);

                                          Log.d("Valr4",String.valueOf(updater4));
                                          Log.d("Valr5",String.valueOf(updater5));
                                          Log.d("Valr7",String.valueOf(updater7));
                                          Log.d("Valr8",String.valueOf(updater8));

                                          int r = r1+r2+r3+updater4+updater5+r6+updater7+updater8+r9+r10;
DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child(uid).child("Responses").child(date);
dbref.child("Result").setValue(r);
                                          Log.d("rvalue",String.valueOf(r));
                                          if(r>=0 && r<=13) {
                                              tv.setText("Your perceived stress level is 'LOW' ");}
                                          else if (r>=14 && r<=26){
                                              tv.setText(" Your perceived stress level is 'MODERATE' ");}
                                          else if( r>=27 && r <=40){
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
                Log.d("UniId",LName);}
                else{
                    LName="Anonymous";
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
                    tvres.setText(" You have jumped "+NoOfResponses+ " steps !");
                    setscoresInProfile.child("TotalResponses").setValue(NoOfResponses);
                    c= NoOfResponses*3;
                    setscoresInProfile.child("TotalCoins").setValue(c);
                    coin.setText("You have earned "+c + " ");

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
                Intent intent = new Intent(resultpss.this,MsgScreen.class);
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