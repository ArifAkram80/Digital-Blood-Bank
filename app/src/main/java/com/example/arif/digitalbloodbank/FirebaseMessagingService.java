package com.example.arif.digitalbloodbank;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by farha on 1/23/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String notification_title = remoteMessage.getNotification().getTitle();
        String Notification_message=remoteMessage.getNotification().getBody();


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo2)
                        .setContentTitle("New Blood Request")
                        .setContentText("Someone need blood!");
                         int mNotificationId = (int) System.currentTimeMillis();

                          NotificationManager mNotifyMgr =
                          (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}