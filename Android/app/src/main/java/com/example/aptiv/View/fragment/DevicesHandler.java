package com.example.aptiv.View.fragment;

import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;


import androidx.fragment.app.Fragment;

public class DevicesHandler extends Fragment {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private TextView MuteText;

    MessageListener mMessageListener;
    Message mActiveMessage;


    ImageButton muteBtn;
    ImageButton HvolumeBtn;
    ImageButton LvolumeBtn;

    public DevicesHandler(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.pop_up, container, false);

        muteBtn = _view.findViewById(R.id.mutebtn);
        HvolumeBtn = _view.findViewById(R.id.highbtn);
        LvolumeBtn = _view.findViewById(R.id.lowbtn);
        MuteText = _view.findViewById(R.id.MuteText);
        muteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(_baseViewModel.isMuted){
                    UnmutePublish();
                    muteBtn.setImageResource(R.drawable.speaker);
                    MuteText.setText("Mute");
                }
                else {
                    MutePublish();
                    muteBtn.setImageResource(R.drawable.muted);
                    MuteText.setText("Unmute");
                }
                _baseViewModel.isMuted = !_baseViewModel.isMuted;

                HideBtns();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Unpublish();
                        ShowBtns();
                    }
                }, 3000);

            }
        });


        HvolumeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                HighVPublish();
                HideBtns();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Unpublish();
                        ShowBtns();
                    }
                }, 3000);

            }
        });



        LvolumeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LowVPublish();
                HideBtns();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Unpublish();
                        ShowBtns();
                    }
                }, 3000);

            }
        });

        return _view;
    }



    public void Publish(String message) {
        mActiveMessage = new Message(message.getBytes());
        Nearby.getMessagesClient(_owner).publish(mActiveMessage);
    }

    public void Unpublish() {
        Nearby.getMessagesClient(_owner).unpublish(mActiveMessage);

    }

    public void MutePublish() {

        Publish("Mute");

    }

    public void UnmutePublish() {

        Publish("Unmute");

    }

    public void HighVPublish() {

        Publish("HVolume");

    }

    public void LowVPublish() {

        Publish("LVolume");

    }

    public void HideBtns() {

        muteBtn.setEnabled(false);
        //unmuteBtn.setEnabled(false);
        HvolumeBtn.setEnabled(false);
        LvolumeBtn.setEnabled(false);

    }

    public void ShowBtns() {

        muteBtn.setEnabled(true);
        HvolumeBtn.setEnabled(true);
        LvolumeBtn.setEnabled(true);

    }


}