package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StatisticsActivity extends AppCompatActivity {
    TextView text;
    Button ok ,cancel;
    Calendar calendar;
    RecyclerView recyclerView;
    MyAdapterLocationList adapter;
    FirebaseRecyclerOptions<Locations> options;
    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseAuth auth;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        text=findViewById(R.id.textView13);
        ok=findViewById(R.id.btnOk);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
      //  uid = user.getUid( );
        uid = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Calendar calendar1 = Calendar.getInstance();
        String date1 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar1.getTime());
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(StatisticsActivity.this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        db = FirebaseDatabase.getInstance( );
        ref = db.getReference( ).child("Locations").child(uid).child(date1);
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Locations").child(android_id).child(date1);
        options= new FirebaseRecyclerOptions.Builder<Locations>().setQuery(ref, Locations.class).build();
        adapter = new MyAdapterLocationList(options);
        recyclerView.setAdapter(adapter);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar= Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if(day==Calendar.SUNDAY){
                    startActivity(new Intent( StatisticsActivity.this,ProgressMap.class ));


                }
                else {
                    startActivity(new Intent( StatisticsActivity.this,ProgressMap.class ));

                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}