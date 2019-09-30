package com.example.sensor.sensorsapp;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;

    private Sensor Light;
    private Sensor Temp;

    TextView LightView;
    TextView TempView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LightView = findViewById(R.id.lightView);
        TempView = findViewById(R.id.tempView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        RegisterLightSensor(sensorManager);
        RegisterTempSensor(sensorManager);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == sensor.TYPE_LIGHT) {

            LightView.setText("Light Value: " + event.values[0]);
        }

        if (sensor.getType() == sensor.TYPE_AMBIENT_TEMPERATURE) {

            LightView.setText("Temperature Value: " + event.values[0]);
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

            Log.d(TAG, "Sensor Not Available!");
        }

    }

    private void RegisterTempSensor(SensorManager sensorManager) {

        Temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (Temp != null) {

            sensorManager.registerListener(this, Temp, SensorManager.SENSOR_DELAY_NORMAL);
        } else {

            Log.d(TAG, "Sensor Not Available!");
        }

    }


}
