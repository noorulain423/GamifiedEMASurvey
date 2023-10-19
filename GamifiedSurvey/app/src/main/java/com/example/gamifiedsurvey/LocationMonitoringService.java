package com.example.gamifiedsurvey;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;


/**
 * Created by devdeeds.com on 27-09-2017.
 */

public class LocationMonitoringService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, SensorEventListener, StepListener  {
    private static final String TAG = com.example.gamifiedsurvey.LocationMonitoringService.class.getSimpleName();
    GoogleApiClient mLocationClient;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps=0;
    LocationRequest mLocationRequest = new LocationRequest();
    Location mCurrentLocation;
    private final ArrayList<UsageStats> mPackageStats = new ArrayList<>();
    static final String EXTRA_RESULT_INTENT = "resultIntent";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
    private static final String CHANNEL_WHATEVER = "background service";
    private static final int NOTIFY_ID = 9906;
    String uid, uname,iiid;
    BroadcastReceiver mReceiver=null;
    private int sec = 0;
    private boolean is_running;
    private boolean was_running;
    private int seconds = 0;
    private String android_id;
    // Is the stopwatch running?
    private boolean running;
    Intent sintent;
    private boolean wasRunning;

    int count=0;
    String time_t="0:0";

    Timer T;
FirebaseAuth auth;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int i) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;


    }



    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
//        if (intent.getAction() == null) {
          //  sintent=intent;
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
//            uid = intent.getStringExtra(EXTRA_ID);
//            uname = intent.getStringExtra(EXTRA_Name);
            iiid=intent.getStringExtra(EXTRA_RESULT_INTENT);

            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
            simpleStepDetector = new StepDetector();
            simpleStepDetector.registerListener(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService();
            }
            foregroundify();
  //     }
      //  auth = FirebaseAuth.getInstance( );
        //FirebaseUser user = auth.getCurrentUser( );
   //     uid = user.getUid( );
       uid = Settings.Secure.getString(getApplicationContext().getContentResolver(),
              Settings.Secure.ANDROID_ID);
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(10000); //10 secs
        mLocationRequest.setFastestInterval(20000); //2 secs


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();




        boolean screenOn = false;

        try{
            // Get ON/OFF values sent from receiver ( AEScreenOnOffReceiver.java )
            screenOn = intent.getBooleanExtra("screen_state", false);

        }catch(Exception e){}

        //  Toast.makeText(getBaseContext(), "Service on start :"+screenOn,
        //Toast.LENGTH_SHORT).show();

        if (!screenOn) {

            // your code here
            // Some time required to start any service

            is_running = true;

           // Toast.makeText(getBaseContext(), "Screen on, ", Toast.LENGTH_SHORT).show();

        } else {
            is_running = false;
            // your code here
            // Some time required to stop any service to save battery consumption

           // Toast.makeText(getBaseContext(), "Screen off,", Toast.LENGTH_SHORT).show();
        }

        return START_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new AEScreenOnOffReceiver();
        registerReceiver(mReceiver, filter);
        running_Timer();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

    }




    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.
            return;
        }
        mLocationClient.connect();
        if(mLocationClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

        }
        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    protected void stopLocationUpdates() {
        if(mLocationClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mLocationClient, this);
            Log.d(TAG, "Location update stopped .......................");
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");
    }

    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");
       mLocationClient.connect();
        if (location != null) {
            mCurrentLocation=location;
                    if (null != mCurrentLocation) {
                        startLocationUpdates();
                        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                       }
            Calendar calendar = Calendar.getInstance();
            String date1 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
                            String add=addresses.get(0).getAddressLine(0);
                            DatabaseReference reftoTableone1=null;
                            reftoTableone1 = FirebaseDatabase.getInstance().getReference("New Locations").child(uid);
//

                            HashMap<String, String> map1 = new HashMap<String, String>();
                            map1.put("address", add);
                            reftoTableone1.setValue(map1);
                            DatabaseReference reftoTableone2=null;
                            reftoTableone2 = FirebaseDatabase.getInstance().getReference("New Date").child(uid);

                            HashMap<String, String> map2 = new HashMap<String, String>();
                            map2.put("date", date1);
                            reftoTableone2.setValue(map2);
                            DatabaseReference reftoTableone=null;
                            reftoTableone = FirebaseDatabase.getInstance().getReference("Locations").child(uid).child(date1).child(add);

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("address", add);
                            map.put("steps", String.valueOf(numSteps));
                            map.put("mobile_usage", time_t);


                            reftoTableone.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("New Locations").child(uid);
                                    ref.keepSynced(true);
                                    ref.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                            //Toast.makeText(com.example.gamifiedsurvey.LocationMonitoringService.this, "child changed", Toast.LENGTH_SHORT).show();
                                            DatabaseReference ref = null;
                                            ref = FirebaseDatabase.getInstance().getReference("Locations").child(uid).child(date1).child(add);
                                            ref.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {

                                                        String m_usage = dataSnapshot.child("mobile_usage").getValue().toString();
                                                        String nsteps = dataSnapshot.child("steps").getValue().toString();
                                                        if (m_usage != null) {
                                                            time_t = m_usage;
                                                            numSteps = Integer.valueOf(nsteps);

                                                        } else {
                                                            time_t = "0:0";
                                                            numSteps = 0;
                                                        }

                                                    } else {
                                                        time_t = "0:0";
                                                        numSteps = 0;
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("New Date").child(uid);
                                    ref3.keepSynced(true);
                                    ref3.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                           // Toast.makeText(com.example.gamifiedsurvey.LocationMonitoringService.this, "child changed", Toast.LENGTH_SHORT).show();
                                            time_t = "0:0";
                                            numSteps = 0;

                                        }
                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                    }
                    // Do something here on the main thread
                    Log.d("Handlers", "Called on main thread");
                    // Repeat this the same runnable code block again another 2 seconds
                    // 'this' is referencing the Runnable object


        }





    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mLocationClient, mLocationRequest, this);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundService(){
        String NOTIFICATION_CHANNEL_ID = "com.example.gamifiedsurvey";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE).setNotificationSilent()
                .build();
        startForeground(2, notification);
    }

    private void foregroundify() {
        NotificationManager mgr=
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O &&
                mgr.getNotificationChannel(CHANNEL_WHATEVER)==null) {
            mgr.createNotificationChannel(new NotificationChannel(CHANNEL_WHATEVER,
                    "Whatever", NotificationManager.IMPORTANCE_DEFAULT));
        }

        NotificationCompat.Builder b=
                new NotificationCompat.Builder(this, CHANNEL_WHATEVER);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);

        b.setContentTitle("Survey App")
                .setSmallIcon(R.mipmap.ic_launcher).setNotificationSilent()
                .setTicker("Titan Cloud Servers- E");
        startForeground(NOTIFY_ID, b.build());
    }

    private PendingIntent buildPendingIntent(String action) {
        Intent i=new Intent(this, getClass());

        i.setAction(action);

        return( PendingIntent.getService(this, 0, i, 0));
    }
    private void running_Timer()
    {

        final Handler handle = new Handler();
        handle.post(new Runnable() {
            @Override
            public void run()
            {
                int hrs = sec / 3600;
                int mins = (sec % 3600) / 60;
                int secs = sec % 60;
                time_t = String.format(Locale.getDefault(), "    %d:%02d:%02d   ", hrs,mins, secs);

                if (is_running) {
                    sec++;
                }
                handle.postDelayed(this, 1000);
            }
        });
    }

}

