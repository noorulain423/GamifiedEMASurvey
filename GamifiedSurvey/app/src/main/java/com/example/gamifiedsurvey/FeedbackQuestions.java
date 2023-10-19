package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackQuestions extends AppCompatActivity {
    TextView tv_q;
    Button yes, no;
    FirebaseDatabase db;
    DatabaseReference ref;
    String date, q;
    Calendar calendar;
    String uid;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_questions);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//      and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);

        tv_q = findViewById(R.id.textView);
        yes=findViewById(R.id.buttonyes);
        no= findViewById(R.id.buttonNo);

        calendar = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date = sdf1.format(calendar.getTime());

        db = FirebaseDatabase.getInstance();
        ref = db.getReference(uid).child("QuestionAboutApp").child(date);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Log.d("day",dayOfTheWeek);
        changeCount(dayOfTheWeek);


        no.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                ref.child(q).setValue("No");
                Intent i1 = new Intent( FeedbackQuestions.this, FeedbackQuestion2.class );
                i1.putExtra("d",date);
                startActivity(i1);
            }
        });

        yes.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                ref.child(q).setValue("Yes");
                Intent i1 = new Intent( FeedbackQuestions.this, FeedbackQuestion2.class );
                i1.putExtra("d",date);
                startActivity(i1);
            }
        });


    }


    public void changeCount(String day){
        switch (day){
        case "Mon":
            tv_q.setText("Did you enjoy completing the survey?");
            q="EnjoyedCompletingAssessment";
            break;
        case "Tue":
            tv_q.setText("Do you feel comfortable while using this app?");
            q="ComfortableUsing";
            break;
        case "Wed":
            tv_q.setText("This App is easy to use?");
            q="EasyToUse";
            break;
        case "Thu":
            tv_q.setText("Do you find completing the survey burdensome?");
            q="Burdensome";
            break;
        case "Fri":
            tv_q.setText("This App is difficult to use?");
            q="DifficultToUse";
            break;
        case "Sat":
            tv_q.setText("While filling responses, do you feel being judged  by someone?");
            q="FeltJudged";
            break;
        case "Sun":
            tv_q.setText("Is you overall impression of using this app positive?");
            q="PositiveImpression";

            break;
        }
    }


        }

