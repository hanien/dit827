package com.example.aptiv.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.aptiv.MainActivity;
import com.example.aptiv.R;

public class AudioFragment extends Fragment implements View.OnClickListener{

    private MainActivity _owner;

    private View _view;
    /*
      private MediaPlayer _mediaPlayer;
      private Button _playButton;
      private Button _pauseButton;
     */
    public AudioFragment(MainActivity Owner) {
        _owner = Owner;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_audio, container, false);

        SetupButton();
        SetupEvents();

        return _view;
    }

    private void SetupEvents() {
       // _playButton.setOnClickListener(this);
       // _pauseButton.setOnClickListener(this);
    }

    private void SetupButton() {
       // _playButton = (Button) _view.findViewById(R.id.start);
       // _pauseButton = (Button) _view.findViewById(R.id.stop);
    }

    @Override
    public void onClick(View view){
        /*
        switch (view.getId()){
            case R.id.start:
                _mediaPlayer = MediaPlayer.create(this, R.raw.music);
                _mediaPlayer.start();
                _mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(_owner, "The Song is Over", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.stop:
                if(_mediaPlayer!= null){
                    _mediaPlayer.pause();
                }
                break;
        }
        */

    }

}
