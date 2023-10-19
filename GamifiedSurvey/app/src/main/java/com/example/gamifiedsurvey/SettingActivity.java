package com.example.gamifiedsurvey;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference ref;
    TextView tname, tage, tprofession, temail;
    EditText name, age, profession, email;
    Button saveUser, browse, back;
    String penguin;
    FirebaseAuth mAuth;
    String uid;
    ImageView profilepic;
    Uri filepath;
    Bitmap bitmap;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        mAuth = FirebaseAuth.getInstance( );
        uid = FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( );

        tname = findViewById(R.id.tname);
        tage = findViewById(R.id.tage);
        temail = findViewById(R.id.temail);
        tprofession = findViewById(R.id.tprof);


        profilepic = findViewById(R.id.pic);
        browse = findViewById(R.id.browse);
        name = findViewById(R.id.nameed);
        email = findViewById(R.id.edemail);
        profession = findViewById(R.id.edprof);
        age = findViewById(R.id.edage);

        saveUser = (Button) findViewById(R.id.Save);
        back = findViewById(R.id.button4);

//        and_id = Settings.Secure.getString(getApplicationContext( ).getContentResolver( ),
//                Settings.Secure.ANDROID_ID);
        rand= new Random();
        int i = rand.nextInt(30 - 1)+1;
        penguin="P"+i;
        Log.d("penguinid",penguin);
        db = FirebaseDatabase.getInstance( );
        ref = db.getReference(uid);

back.setOnClickListener(new View.OnClickListener( ) {
    @Override
    public void onClick(View v) {
        startActivity(new Intent( SettingActivity.this,MainActivity.class ));
    }
});
        saveUser.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                abc( );
            }

        });
        browse = (Button) findViewById(R.id.browse);

        browse.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(SettingActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener( ) {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,PermissionToken token) {
                                token.continuePermissionRequest( );
                            }
                        }).check( );
            }
        });

        saveUser.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                abc( );
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData( );
            try {
                InputStream inputStream = getContentResolver( ).openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profilepic.setImageBitmap(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }




    public void abc(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();
        String n= name.getText().toString();
        String a = age.getText().toString();
        String e = email.getText().toString();
        String p= profession.getText().toString();
      //  String nt= time_.getText().toString();

        ref=db.getReference(uid).child("ProfileData");
        ref.child("Name").setValue(n);
        ref.child("Age").setValue(a);
        ref.child("Profession").setValue(p);
        ref.child("Email").setValue(e);
      //  ref.child("NTime").setValue(nt);
       // ref.child("Uid").setValue(uid);
        ref.child("UniId").setValue(penguin);
        Toast.makeText(SettingActivity.this,"Your penguin id is "+penguin,Toast.LENGTH_SHORT).show( );
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference(uid);
        storageRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>( ) {
                    @Override
                    public void onSuccess(Uri uri) {
                       ref.child("Image").setValue(uri.toString());
                    }
                });

            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>( ) {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100* snapshot.getBytesTransferred()/snapshot.getTotalByteCount() );
                        dialog.setMessage("Uploaded : "+ (int)percent + " % ... ");

                    }
                });

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void getPerm(){
       // Dexter.withContext(SettingActivity.this)
        Dexter.withActivity(SettingActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener( ) {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent( Intent.ACTION_PICK );
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image file"),1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest,PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest( );
                    }
                });

    }

}

