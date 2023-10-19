package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class R1 extends AppCompatActivity {
    TextView tvq;
    Button o0, o1, o2, o3, o4;
    int count = 1;
    int ans;
    String Q;
    String uid;
    FirebaseDatabase db;
    DatabaseReference ref;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    String date = sdf.format(calendar.getTime());
    ImageView avt, smavt, n_Avt, FoftenAvt, Voftn_avt, almost_Avt;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r1);
        tvq = findViewById(R.id.tvq);
        o0 = findViewById(R.id.btno0);
        o1 = findViewById(R.id.btno01);
        o2 = findViewById(R.id.btno02);
        o3 = findViewById(R.id.btno03);
        o4 = findViewById(R.id.btno04);
        avt = findViewById(R.id.avtQ);
        n_Avt = findViewById(R.id.n_avt);
        smavt = findViewById(R.id.stAvt);
        almost_Avt = findViewById(R.id.avtAlmost);
        FoftenAvt = findViewById(R.id.fairly_avt);
        Voftn_avt = findViewById(R.id.v_oft_avt);


        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
        //and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
          //      Settings.Secure.ANDROID_ID);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference(uid).child("Responses");

        n_Avt.setVisibility(View.INVISIBLE);
        smavt.setVisibility(View.INVISIBLE);
        almost_Avt.setVisibility(View.INVISIBLE);
        FoftenAvt.setVisibility(View.INVISIBLE);
        Voftn_avt.setVisibility(View.INVISIBLE);


        o0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_Avt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq);
                int selectedoption = getAnswers(o0);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(n_Avt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R1.this,resultpss.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq);
                }


            }
        });
        o1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almost_Avt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq);
                int selectedoption = getAnswers(o1);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(almost_Avt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R1.this,resultpss.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq);
                }
            }
        });
        o2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                smavt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq);
                int selectedoption = getAnswers(o2);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(smavt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R1.this,resultpss.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq);
                }


            }
        });

        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoftenAvt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq);
                int selectedoption = getAnswers(o3);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(FoftenAvt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R1.this,resultpss.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq);
                }
            }
        });
        o4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voftn_avt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq);
                int selectedoption = getAnswers(o4);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(Voftn_avt);
                overridePendingTransition(0, 0);
                if (ques == "Q10") {
                    Intent intent = new Intent(R1.this,resultpss.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq);
                }
            }
        });


    }


    public int getAnswers(Button button) {
        String option = button.getText().toString();
        switch (option) {
        case "Never":
            ans = 0;
            break;
        case "Almost Never":
            ans = 1;
            break;
        case "Sometimes":
            ans = 2;
            break;
        case "Fairly Often":
            ans = 3;
            break;
        case "Very Often":
            ans = 4;
            break;

        }
        return ans;

    }

    public String getQuestion(TextView textView) {
        String question = textView.getText().toString();
        switch (question) {
        case "In last 6 days, how often have you been upset because of something happened unexpectedly?":
            Q = "Q1";
            break;
        case "In last 6 days, how often have you felt that you were unable to control important things in your life?":
            Q = "Q2";
            break;
        case "In last 6 days, how often have you felt nervous and stressed?":
            Q = "Q3";
            break;
        case "In last 6 days, how often have you felt confident about your ability to handle your personal problems?":
            Q = "Q4";
            break;
        case "In last 6 days, how often have you felt that things going your way?":
            Q = "Q5";
            break;
        case "In last 6 days, how often have you found that you could not cope with all the things that you had to do?":
            Q = "Q6";
            break;
        case "In last 6 days, how often have you been able to control irritations in your life?":
            Q = "Q7";
            break;
        case "In last 6 days, how often have you felt that you were on top of things?":
            Q = "Q8";
            break;
        case "In last 6 days, how often have you been angered because of things that happened that were outside of your control?":
            Q = "Q9";
            break;
        case "In last 6 days, how often have you felt difficulties were piling up so high that you could not overcome them?":
            Q = "Q10";
            break;

        }

        return Q;
    }


    public int tvchange(TextView textview) {

        switch (count) {
        case 1:
            textview.setText(R.string.Q2);
            count = count + 1;
            break;
        case 2:

            textview.setText(R.string.Q3);
            count = count + 1;

            break;
        case 3:

            textview.setText(R.string.Q4);
            count = count + 1;
            break;
        case 4:

            textview.setText(R.string.Q5);
            count = count + 1;
            break;
        case 5:
            textview.setText(R.string.Q6);
            count = count + 1;
            break;
        case 6:
            textview.setText(R.string.Q7);
            count = count + 1;
            break;
        case 7:
            textview.setText(R.string.Q8);
            count = count + 1;
            break;
        case 8:
            textview.setText(R.string.Q9);
            count = count + 1;
            break;
        case 9:
            textview.setText(R.string.Q10);
            count = count + 1;
            break;
        }
        return count;


    }

    public void Setvisibility(ImageView imageView) {
        imageView.setVisibility(View.VISIBLE);
        avt.setVisibility(View.INVISIBLE);
        // tvimage.setVisibility(View.INVISIBLE);
        Thread background = new Thread() {
            public void run() {
                try {

                    sleep(700);
                    imageView.setVisibility(View.INVISIBLE);
                    avt.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}