package com.example.aptiv.Model.Helper;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class ReceiverService extends Service {
    private MessageListener mMessageListener;
    private int valueCounter;
    private MainActivity mainActivity;
    private BaseViewModel viewModel;
    public Zone DriverZone;
    public Zone PassengerZone;
    public Zone BackseatZone;
    public Zone MiddleZone;
    ArrayList<Integer> list=new ArrayList<Integer>();

    public ReceiverService(MainActivity activity, BaseViewModel model,Zone driverZone, Zone passengerZone,Zone backseatZone,Zone middleZone){
        mainActivity = activity;
        viewModel = model;
        DriverZone = driverZone;
        PassengerZone = passengerZone;
        BackseatZone = backseatZone;
        MiddleZone = middleZone;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String value = new String(message.getContent());
                list.add(Integer.parseInt(value));
                valueCounter++;
                if(valueCounter > 5){
                    for(int i = 0 ; i < list.size() ; i++ ){
                        int values = 0;
                        values = values + list.get(i);
                        value = String.valueOf(values / list.size());
                    }
                    SaveSoundValue(value);
                }
            }
            @Override
            public void onLost(Message message) {
                String value = new String(message.getContent());
                list.add(Integer.parseInt(value));
                valueCounter++;
            }
        };
        Nearby.getMessagesClient(this).subscribe(mMessageListener);
        return START_NOT_STICKY;
    }


    private void SaveSoundValue(String value){
        DriverZone.setSound(value);
        PassengerZone.setSound(value);
        BackseatZone.setSound(value);
        MiddleZone.setSound(value);
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
