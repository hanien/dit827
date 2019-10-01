package com.example.sensor.sensorsapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.InputQueue;
import android.widget.TextView;
import android.util.Log;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    private VoiceSensor voiceSensor;

    private Handler Handler = new Handler();
    private PowerManager.WakeLock WakeLock;

    private Sensor Light;
    private Sensor Temp;

    private boolean Recording = false;


    TextView LightView;
    TextView TempView;
    TextView VoiceView;



    private Runnable SleepTask = new Runnable() {
        public void run() {
            voiceSensor.start();
        }
    };

    private Runnable PollTask = new Runnable() {
        public void run() {

            double amp = voiceSensor.getAmplitude();
            VoiceView.setText("Voice value: " + amp);

            Handler.postDelayed(PollTask, 300);

        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LightView = findViewById(R.id.lightView);
        TempView = findViewById(R.id.tempView);
        VoiceView = findViewById(R.id.voiceView);

        voiceSensor = new VoiceSensor();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        RegisterLightSensor(sensorManager);
        RegisterTempSensor(sensorManager);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        WakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,"AppName:MainActivity");
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (!Recording) {
            Recording = true;
            startRec();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopRec();

    }

    private void startRec() {
        voiceSensor.start();

        if (!WakeLock.isHeld()) {
            WakeLock.acquire();
        }

        Handler.postDelayed(PollTask, 300);
    }

    private void stopRec() {

        if (WakeLock.isHeld()) {
            WakeLock.release();
        }
        Handler.removeCallbacks(SleepTask);
        Handler.removeCallbacks(PollTask);

        voiceSensor.stop();
        VoiceView.setText("--Stop--");

        Recording = false;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == sensor.TYPE_LIGHT) {

            LightView.setText("Light Value: " + event.values[0]);
        }

        if (sensor.getType() == sensor.TYPE_AMBIENT_TEMPERATURE) {

            TempView.setText("Temperature Value: " + event.values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void RegisterLightSensor(SensorManager sensorManager) {

        Light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (Light != null) {

            sensorManager.registerListener(this, Light, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            LightView.setText("Light Sensor Not Available!");
            Log.d(TAG, "Sensor Not Available!");
        }

    }

    private void RegisterTempSensor(SensorManager sensorManager) {

        Temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (Temp != null) {

            sensorManager.registerListener(this, Temp, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            TempView.setText("Temp Sensor Not Available!");
            Log.d(TAG, "Sensor Not Available!");
        }

    }



}
