package com.example.gamifiedsurvey;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMsg(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());


    }
    public void getFirebaseMsg(String title, String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "All")
                .setContentTitle(title).setContentText(text).setAutoCancel(true);
        NotificationManagerCompat manager= NotificationManagerCompat.from(this);
        manager.notify(101, builder.build());


    }
}
