package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

public class DefaultLayoutFragment extends Fragment  implements View.OnClickListener , IZoneSelection{

    private View _view;
    private MainActivity _owner;
    private TextView _soundTextView;
    private TextView _airPressurTextView;
    private TextView _humidityTextView;
    private TextView _luxTextView;
    private TextView _inTempTextView;
    private TextView _outTempTextView;
    private TextView _altitudeTextView;

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

        SetupComponents();
        UpdateData();
        SetupTimer();

        return _view;
    }

    private void SetupComponents() {
        _soundTextView = _view.findViewById(R.id.soundTextView);
        _volumeCard = _view.findViewById(R.id.CardViewVolume);
        _airPressurTextView = _view.findViewById(R.id.airPressurTextView);
        _humidityTextView = _view.findViewById(R.id.HumidityTextView);
        _luxTextView = _view.findViewById(R.id.lightTextView);
        _inTempTextView = _view.findViewById(R.id.InTempTextView);
        _outTempTextView = _view.findViewById(R.id.OutTempTextView);
        _altitudeTextView = _view.findViewById(R.id.altitudeTextView);
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
        updateView();
        _baseViewModel.UpdateData();
    }

    private void updateView() {
        _soundTextView.setText(_baseViewModel.MiddleZone.getSound());
        _airPressurTextView.setText(_baseViewModel.MiddleZone.getPressure());
        _humidityTextView.setText(_baseViewModel.MiddleZone.getHumidity());
        _luxTextView.setText(_baseViewModel.MiddleZone.getIr());
        _altitudeTextView.setText(_baseViewModel.MiddleZone.getAltitiude());
        SetTempreture();
    }


    private void SetTempreture(){
        double temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
        String tempType = (_baseViewModel.getTempType()) ? _baseViewModel.getFahrenheit() : _baseViewModel.getCelsius();
        temp = (_baseViewModel.getTempType()) ? ((1.8*temp))+32 : temp;

        double OutTemp = _baseViewModel.OutTempreture;
        String outTempType = (_baseViewModel.getTempType()) ? _baseViewModel.getFahrenheit() : _baseViewModel.getCelsius();
        temp = (_baseViewModel.getTempType()) ? ((1.8*OutTemp))+32 : OutTemp;

        _inTempTextView.setText(temp + tempType);
        _outTempTextView.setText(outTempType + tempType);
    }

    @Override
    public void onClick(View view){

        switch (view.getId()) {
            case R.id.soundImageView:
                _owner._dashboardFragment.OpenVolumeFragment();
                break;

            case R.id.CardViewVolume:
                break;
        }

    }

    //When a zone is selected on the car
    //values needs to be changes base on zone
    @Override
    public void zoneIsSelected() {

    }
}
