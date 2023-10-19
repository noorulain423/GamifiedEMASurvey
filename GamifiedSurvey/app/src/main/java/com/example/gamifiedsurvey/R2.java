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

public class R2 extends AppCompatActivity {
    TextView tvq1;
    Button o0, o1, o2, o3, o4;
    int count = 1;
    int ans;
    String Q;
    String and_id, uid;
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
        setContentView(R.layout.activity_r2);

        tvq1 = findViewById(R.id.tvq11);
        o0 = findViewById(R.id.btno);
        o1 = findViewById(R.id.btno1);
        o2 = findViewById(R.id.btno2);
        o3 = findViewById(R.id.btno3);
        o4 = findViewById(R.id.btno4);
        avt = findViewById(R.id.avtQ1);
        n_Avt = findViewById(R.id.n_avt1);
        smavt = findViewById(R.id.stAvt1);
        almost_Avt = findViewById(R.id.avtAlmost1);
        FoftenAvt = findViewById(R.id.fairly_avt1);
        Voftn_avt = findViewById(R.id.v_oft_avt1);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
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
                String ques = getQuestion(tvq1);
                int selectedoption = getAnswers(o0);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(n_Avt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R2.this,resultpss2.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq1);
                }
            }
        });

        o1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                almost_Avt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq1);
                int selectedoption = getAnswers(o1);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(almost_Avt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R2.this,resultpss2.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq1);
                }
            }
        });

        o2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                smavt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq1);
                int selectedoption = getAnswers(o2);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(smavt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R2.this,resultpss2.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq1);
                }
            }
        });

        o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoftenAvt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq1);
                int selectedoption = getAnswers(o3);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(FoftenAvt);
                if (ques == "Q10") {
                    Intent intent = new Intent(R2.this,resultpss2.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq1);
                }
            }
        });

        o4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voftn_avt.setVisibility(View.VISIBLE);
                String ques = getQuestion(tvq1);
                int selectedoption = getAnswers(o4);
                ref.child(date).child(ques).setValue(selectedoption);
                Setvisibility(Voftn_avt);
                overridePendingTransition(0, 0);
                if (ques == "Q10") {
                    Intent intent = new Intent(R2.this,resultpss2.class);
                    intent.putExtra("Date",date);
                    startActivity(intent);
                } else {
                    tvchange(tvq1);
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
        case "In last 24 hours, how often have you felt that you were unable to control important things in your life?":
            Q = "Q2";
            break;

        case "In last 24 hours, how often have you felt confident about your ability to handle your personal problems?":
            Q = "Q4";
            break;

        case "In last 24 hours, how often have you felt that things going your way?":
            Q = "Q5";
            break;

        case "In last 24 hours, how often have you felt difficulties were piling up so high that you could not overcome them?":
            Q = "Q10";
            break;
        }
        return Q;
    }


    public int tvchange(TextView textview) {

        switch (count) {
        case 1:
            textview.setText(R.string.Q44);
            count = count + 1;
            break;
        case 2:
            textview.setText(R.string.Q55);
            count = count + 1;
            break;
        case 3:
            textview.setText(R.string.Q110);
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
