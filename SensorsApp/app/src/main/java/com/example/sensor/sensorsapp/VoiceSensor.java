package com.example.sensor.sensorsapp;

import java.io.IOException;
import android.media.MediaRecorder;

public class VoiceSensor {

    private MediaRecorder Recorder = null;

    public void start() {

        if (Recorder == null) {

            Recorder = new MediaRecorder();
            Recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            Recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            Recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            Recorder.setOutputFile("/dev/null");

            try {
                Recorder.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Recorder.start();
        }
    }


    public void stop() {
        if (Recorder != null) {
            Recorder.stop();
            Recorder.release();
            Recorder = null;
        }
    }

    public double getAmplitude() {
        if (Recorder != null)
            return  (Recorder.getMaxAmplitude()/2700.0);
        else
            return 0;

    }

}