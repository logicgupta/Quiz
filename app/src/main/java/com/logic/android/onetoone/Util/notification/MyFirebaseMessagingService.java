package com.logic.android.onetoone.Util.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.logic.android.onetoone.basicvideochat.R;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static int NOTIFICATTION_CHANEL=123;
    NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        if (remoteMessage.getData().size()>0){

            Log.d("NotificationService","Message Pay Load"+remoteMessage.getData());
        }
        if (remoteMessage.getNotification()!=null){
            handleNotification(remoteMessage);
            Log.d("NotificationService","Message Notification"+remoteMessage.getNotification().getBody());
        }

    }

    public void handleNotification(RemoteMessage remoteMessage){

        notificationManager= (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.O){

            handleChannel();
        }
            int notificationID=new Random().nextInt(6000);
            Uri ringtone= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notification=new NotificationCompat.Builder(this,"1024")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setSound(ringtone);

            notificationManager.notify(notificationID,notification.build());


    }
    public void handleDataNotification(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void handleChannel(){
        CharSequence adminChannelName = "logic";
        String adminChannelDescription = "Hi";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel("1021", adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
