package com.example.gamifiedsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    ImageView img;
    TextView name,prof,age, email, Tr;
    FirebaseDatabase db;
    DatabaseReference ref;
    String uid;
    Button back;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        img=findViewById(R.id.ppic);
        name=findViewById(R.id.name);
        prof=findViewById(R.id.prof);
        age=findViewById(R.id.age);
        email=findViewById(R.id.email);
        Tr=findViewById(R.id.TotalResponses);
        back=findViewById(R.id.btnBack);
        auth = FirebaseAuth.getInstance( );
        FirebaseUser user = auth.getCurrentUser( );
        uid = user.getUid( );
//        uid = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                Settings.Secure.ANDROID_ID);

        db=FirebaseDatabase.getInstance();
        ref=db.getReference(uid).child("ProfileData");
        ref.addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Name").exists()){
                name.setText(snapshot.child("Name").getValue().toString().trim());}
                else
                    name.setText("");
                if(snapshot.child("Profession").exists())
                {
                    prof.setText(snapshot.child("Profession").getValue().toString().trim()); }
                else{
                    prof.setText("");}

                if(snapshot.child("Age").exists()){
                    age.setText(snapshot.child("Age").getValue().toString().trim());}
                else
                   age.setText("");

                if(snapshot.child("Email").exists()){
                    email.setText(snapshot.child("Email").getValue().toString().trim());}
                else
                    email.setText("");


                if(snapshot.child("TotalResponses").exists()){
                    Tr.setText(snapshot.child("TotalResponses").getValue().toString().trim());}
                else
                    Tr.setText("");

                if(snapshot.child("Image").exists()){
                    String url = snapshot.child("Image").getValue().toString();
                        Picasso.get().load(url).into(img);
                }

                else {
                   // img.setImageDrawable(R.drawable.av1);
                    Picasso.get().load(R.drawable.av1).into(img);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
back.setOnClickListener(new View.OnClickListener( ) {
    @Override
    public void onClick(View v) {
        startActivity(new Intent( Profile.this, MainActivity.class ));
    }
});

    }
}