package com.example.aptiv.Model.Helper;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

import com.example.aptiv.View.MainActivity;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.aptiv.Model.Helper.AptivNotification.CHANNEL_ID;


public class ReceiverService2 extends Service {


    MessageListener mMessageListener;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("APTIV Receiver Service")
                .setContentText("IoT Receiver Running")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);



        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {

                String value = new String(message.getContent());
                Log.v("M1", value);

            }

            @Override
            public void onLost(Message message) {

                String value = new String(message.getContent());
                Log.v("M1", value);

            }
        };


        Nearby.getMessagesClient(this).subscribe(mMessageListener);


        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
