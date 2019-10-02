package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aptiv.View.MainActivity;
import com.example.aptiv.R;
import com.example.aptiv.ViewModel.BaseViewModel;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private MainActivity _owner;
    private View _view;
    private TextView _soundTextView;
    private BaseViewModel _baseViewModel;

    public DashboardFragment(MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        SetupCarLayoutFragment();
        SetupTimer();
        SetupButton();
        SetupEvents();

        return _view;
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

    private void SetupCarLayoutFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        CarLayoutFragment CarFragment = new CarLayoutFragment(_owner);
        fragmentTransaction.replace(R.id.fragmentPlaceHolderDashboard, CarFragment).commit();
    }

    private void SetupEvents() {
        _soundTextView = _view.findViewById(R.id.soundTextView);
    }

    private void SetupButton() {

    }

    @Override
    public void onClick(View view){
        /*
        switch (view.getId()) {
            case R.id.frontseat:

                break;

        }

        */
    }

    public void SetUpSound(String val){
        _soundTextView.setText(val);
    }




}