package com.example.sensor.sensorsapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.sensor.sensorsapp.Aptiv.CHANNEL_ID;

public class ReceiverService extends Service {


    MessageListener mMessageListener;
    Message mActiveMessage;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

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

                if(new String(message.getContent()).equals("Mute")){

                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);


                }

                else if(new String(message.getContent()).equals("Unmute")){

                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);


                }

                else   if(new String(message.getContent()).equals("HVolume")){

                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);


                }

                else {

                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

                }



            }

            @Override
            public void onLost(Message message) {

                if(new String(message.getContent()).equals("Mute")){


                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);


                }

                else if(new String(message.getContent()).equals("Unmute")){


                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);


                }

                else   if(new String(message.getContent()).equals("HVolume")){


                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);


                }

                else {

                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

                }

            }
        };


        Nearby.getMessagesClient(this).subscribe(mMessageListener);


        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {

        Nearby.getMessagesClient(this).unpublish(mActiveMessage);
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener);

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
