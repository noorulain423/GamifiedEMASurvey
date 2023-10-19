package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class lbcal extends AppCompatActivity {
   FirebaseDatabase db;
   DatabaseReference ref, ref_re;
   RecyclerView recyclerView;
     MyAdapter adapter;
     Button back;
  FirebaseRecyclerOptions<LeaderBoard> options;
  String and_id, uid;
  TextView coins, pengid;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbcal);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        and_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);
        coins=findViewById(R.id.tvv11);
        pengid=findViewById(R.id.tvv12);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("LeaderBoard").child(uid);
        recyclerView = findViewById(R.id.lbrecycleview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(lbcal.this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
      // recyclerView.setLayoutManager(new LinearLayoutManager(this).setReverseLayout(true));
       back=findViewById(R.id.button2);
        db = FirebaseDatabase.getInstance( );
        ref = db.getReference( ).child("LeaderBoard");
        ref.orderByChild("Scores");
        Query query = db.getReference().child("LeaderBoard").orderByChild("Scores");
       options= new FirebaseRecyclerOptions.Builder<LeaderBoard>().setQuery(query, LeaderBoard.class).build();
        adapter = new MyAdapter(options);
        recyclerView.setAdapter(adapter);
       // recyclerView.scrollToPosition(3);


        reference.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("Scores").getValue().toString();
                coins.setText(name);
                String peng_id=snapshot.child("Name").getValue().toString();
                pengid.setText("Your Penguin id is: "+peng_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( lbcal.this, MainActivity.class ));
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