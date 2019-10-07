package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

public class DefaultLayoutFragment extends Fragment  implements View.OnClickListener {

    private View _view;
    private MainActivity _owner;
    private TextView _soundTextView;
    private BaseViewModel _baseViewModel;
    private CardView _volumeCard;

    public DefaultLayoutFragment(MainActivity Owner, BaseViewModel baseViewModel) {
        _owner = Owner;
        _baseViewModel = baseViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_defaultlayout, container, false);

        SetupEvents();
        UpdateData();
        SetupTimer();

        return _view;
    }

    private void SetupEvents() {
        _soundTextView = _view.findViewById(R.id.soundTextView);
        _volumeCard = _view.findViewById(R.id.VolumeCard);
    }

    private void SetupTimer(){
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                UpdateData();
                SetupTimer();
            }
        }.start();
    }

    private void UpdateData() {
        _baseViewModel.UpdateSoundValue();
    }

    public void SetUpSound(String val){
        _soundTextView.setText(val);
    }

    @Override
    public void onClick(View view){

        switch (view.getId()) {

            case R.id.soundImageView:
                _owner._dashboardFragment.OpenVolumeFragment();
                break;
            case R.id.VolumeCard:
                break;

        }

    }


}
