package com.example.aptiv.View.fragment;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    MessageListener mMessageListener;
    Message mActiveMessage;


     Button muteBtn;
     Button unmuteBtn;
     Button HvolumeBtn;
     Button LvolumeBtn ;

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
       unmuteBtn = _view.findViewById(R.id.unmutebtn);
    HvolumeBtn = _view.findViewById(R.id.highbtn);
       LvolumeBtn = _view.findViewById(R.id.lowbtn);

        muteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MutePublish();
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



        unmuteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                UnmutePublish();
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

    @Override
    public void onStop() {

        Nearby.getMessagesClient(_owner).unpublish(mActiveMessage);
        Nearby.getMessagesClient(_owner).unsubscribe(mMessageListener);

        super.onStop();
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
        unmuteBtn.setEnabled(false);
        HvolumeBtn.setEnabled(false);
        LvolumeBtn.setEnabled(false);

    }

    public void ShowBtns() {

        muteBtn.setEnabled(true);
        unmuteBtn.setEnabled(true);
        HvolumeBtn.setEnabled(true);
        LvolumeBtn.setEnabled(true);

    }


}