package com.example.sensor.sensorsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.util.Log;

import android.os.Handler;
import android.os.PowerManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.content.ContextCompat;

public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    private VoiceSensor voiceSensor;

    private RequestQueue queue;

    private Sensor Light;
    private Sensor Temp;
    private Message mActiveMessage;

    private double lightvalue;
    private double tempvalue;
    private double amp;

    private Handler Handler = new Handler();
    private PowerManager.WakeLock WakeLock;

    private boolean Recording = false;

   // private String url = "https://dit827aptiv.herokuapp.com/api/sensors/driver";

    private TextView LightView;
    private TextView TempView;
    private TextView VoiceView;


    private Runnable SleepTask = new Runnable() {
        public void run() {
            voiceSensor.start();
        }
    };

    private Runnable PollTask = new Runnable() {
        public void run() {

            amp = voiceSensor.getAmplitude();
            int amp_Fixed = (int) Math.floor(amp * 100);
            amp_Fixed = Math.abs( amp_Fixed );
            amp_Fixed =((int) Math.log( amp_Fixed )) * 20;

            VoiceView.setText("Voice value: " + amp_Fixed + "dB");
            Publish(String.valueOf(amp_Fixed));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Unpublish();
                }
            }, 3000);
/*
            queue = Volley.newRequestQueue(MainActivity.this);

            JSONObject param = new JSONObject();
            try {
                param.put("type", "sound");
                param.put("value", String.valueOf(amp));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest soundPatch = new JsonObjectRequest(Request.Method.PATCH, url , param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            queue.add(soundPatch);
*/
            Handler.postDelayed(PollTask, 300);

        }
    };
    public void Publish(String message) {
        mActiveMessage = new Message(message.getBytes());
        Nearby.getMessagesClient(this).publish(mActiveMessage);
    }

    public void Unpublish() {
        Nearby.getMessagesClient(this).unpublish(mActiveMessage);
    }


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
        VoiceView.setText("-- Stop Rec! --");

        Recording = false;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

         Sensor sensor = event.sensor;


        if (sensor.getType() == sensor.TYPE_LIGHT) {

            lightvalue = event.values[0];
            LightView.setText("Light Value: " + lightvalue);

/*
            queue = Volley.newRequestQueue(this);

            JSONObject param = new JSONObject();
            try {
                param.put("type", "light");
                param.put("value", String.valueOf(lightvalue));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest lightPatch = new JsonObjectRequest(Request.Method.PATCH, url + "/light", param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            queue.add(lightPatch);

*/
        }

        if (sensor.getType() == sensor.TYPE_AMBIENT_TEMPERATURE) {

            tempvalue = event.values[0];
            TempView.setText("Temperature Value: " + tempvalue);
/*
            queue = Volley.newRequestQueue(this);

            JSONObject param = new JSONObject();
            try {
                param.put("type", "temp");
                param.put("value", String.valueOf(tempvalue));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest tempPatch = new JsonObjectRequest(Request.Method.PATCH, url + "/temp", param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            queue.add(tempPatch);
*/
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

    public void startReceiverService(View v) {
        Intent serviceIntent = new Intent(this, ReceiverService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopReceiverService(View v) {
        Intent serviceIntent = new Intent(this, ReceiverService.class);
        stopService(serviceIntent);
    }



}
