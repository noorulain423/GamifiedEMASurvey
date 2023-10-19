package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackQuestion2 extends AppCompatActivity {
    TextView tv_q;
Button yes, no;
    FirebaseDatabase db;
    DatabaseReference ref;
    String date,q;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
FirebaseAuth auth;
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_question2);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//         and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        Intent intent = getIntent();
        date=intent.getStringExtra("d");

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//        date = sdf1.format(calendar.getTime());

        tv_q = findViewById(R.id.textViewQ2);
        yes=findViewById(R.id.btnQ2yes);
        no= findViewById(R.id.btnQ2No);


        db = FirebaseDatabase.getInstance();
        ref = db.getReference(uid).child("QuestionAboutApp").child(date);

        changeCount(dayOfTheWeek);


        no.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                ref.child(q).setValue("No");
                startActivity(new Intent( FeedbackQuestion2.this,lbcal.class ));
            }
        });

        yes.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                ref.child(q).setValue("Yes");
                startActivity(new Intent( FeedbackQuestion2.this, lbcal.class ));
            }
        });


    }


    public void changeCount(String day){
        switch (day){
        case "Mon":
            tv_q.setText("4+5=10");
            q="mathsQ9";

            break;
        case "Tue":
            tv_q.setText("Are you enjoying filling the survey?");
            q="EnjoyFillingSurvey";

            break;
        case "Wed":
            tv_q.setText("6*2=8");
            q="mathsQ12";
            break;
        case "Thu":
            tv_q.setText("3 - 2 = 1");
            q="mathsQ1";
            break;
        case "Fri":
            tv_q.setText("2 + 2 + 4 = 8");
            q="mathsQ8";

            break;
        case "Sat":
            tv_q.setText("Do you find completing survey while playing game boring?");
            q="Boring";

            break;
        case "Sun":
            tv_q.setText("Is your overall impression of using the app negative");
            q="NegativeImpression";
            break;
        }

    }

}