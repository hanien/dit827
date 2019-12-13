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

public class DefaultLayoutFragment extends Fragment implements View.OnClickListener, IZoneSelection {

    private View _view;
    private MainActivity _owner;
    private TextView _soundTextView;
    private TextView _airPressureTextView;
    private TextView _humidityTextView;
    private TextView _luxTextView;
    private TextView _inTempTextView;
    private TextView _outTempTextView;
    private TextView _altitudeTextView;

    private BaseViewModel _baseViewModel;
    private CardView _volumeCard;
    private DashboardFragment _parentFragment;

    private double temp;
    private double sound;
    private double airP;
    private double humidity;
    private double lux;
    private double altitude;

    public DefaultLayoutFragment(DashboardFragment parentFragment, MainActivity Owner, BaseViewModel baseViewModel) {
        _parentFragment = parentFragment;
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
        _airPressureTextView = _view.findViewById(R.id.airPressureTextView);
        _humidityTextView = _view.findViewById(R.id.HumidityTextView);
        _luxTextView = _view.findViewById(R.id.lightTextView);
        _inTempTextView = _view.findViewById(R.id.InTempTextView);
        _outTempTextView = _view.findViewById(R.id.OutTempTextView);
        _altitudeTextView = _view.findViewById(R.id.altitudeTextView);
    }

    private void SetupTimer() {
        new CountDownTimer(4000, 1000) {

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
        zoneIsSelected();
    }

    private void SetTemperature(double temp) {
        String tempType = (_baseViewModel.getTempType()) ? _baseViewModel.getFahrenheit() : _baseViewModel.getCelsius();
        temp = (_baseViewModel.getTempType()) ? ((1.8 * temp)) + 32 : temp;

        double OutTemp = _baseViewModel.OutTemperature;
        OutTemp = (_baseViewModel.getTempType()) ? ((1.8 * OutTemp)) + 32 : OutTemp;

        _inTempTextView.setText(temp + tempType);
        _outTempTextView.setText(OutTemp + tempType);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.soundImageView:
                _owner._dashboardFragment.OpenVolumeFragment();
                break;

            case R.id.CardViewVolume:
                break;
        }

    }

    //When a zone is selected on the car
    //values need to be changed based on zone
    @Override
    public void zoneIsSelected() {
        temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
        sound = Double.parseDouble(_baseViewModel.MiddleZone.getSound());
        airP = Double.parseDouble(_baseViewModel.MiddleZone.getPressure());
        humidity = Double.parseDouble(_baseViewModel.MiddleZone.getHumidity());
        lux = Double.parseDouble(_baseViewModel.MiddleZone.getIr());
        altitude = Double.parseDouble(_baseViewModel.MiddleZone.getAltitude());

        int count = 1;

        if (_parentFragment._driverSeatSelected) {
            temp = temp + Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            sound = sound + Double.parseDouble(_baseViewModel.DriverZone.getSound());
            airP = airP + Double.parseDouble(_baseViewModel.DriverZone.getPressure());
            humidity = humidity + Double.parseDouble(_baseViewModel.DriverZone.getHumidity());
            lux = lux + Double.parseDouble(_baseViewModel.DriverZone.getIr());
            altitude = altitude + Double.parseDouble(_baseViewModel.DriverZone.getAltitude());
            count++;
        }
        if (_parentFragment._frontSeatSelected) {
            temp = temp + Double.parseDouble(_baseViewModel.PassengerZone.getTemperature());
            sound = sound + Double.parseDouble(_baseViewModel.PassengerZone.getSound());
            airP = airP + Double.parseDouble(_baseViewModel.PassengerZone.getPressure());
            humidity = humidity + Double.parseDouble(_baseViewModel.PassengerZone.getHumidity());
            lux = lux + Double.parseDouble(_baseViewModel.PassengerZone.getIr());
            altitude = altitude + Double.parseDouble(_baseViewModel.PassengerZone.getAltitude());
            count++;
        }
        if (_parentFragment._backSeatSelected) {
            temp = temp + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature());
            sound = sound + Double.parseDouble(_baseViewModel.BackseatZone.getSound());
            airP = airP + Double.parseDouble(_baseViewModel.BackseatZone.getPressure());
            humidity = humidity + Double.parseDouble(_baseViewModel.BackseatZone.getHumidity());
            lux = lux + Double.parseDouble(_baseViewModel.BackseatZone.getIr());
            altitude = altitude + Double.parseDouble(_baseViewModel.BackseatZone.getAltitude());
            count++;
        }
        if (count == 4) {
            temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
            sound = Double.parseDouble(_baseViewModel.MiddleZone.getSound());
            airP = Double.parseDouble(_baseViewModel.MiddleZone.getPressure());
            humidity = Double.parseDouble(_baseViewModel.MiddleZone.getHumidity());
            lux = Double.parseDouble(_baseViewModel.MiddleZone.getIr());
            altitude = Double.parseDouble(_baseViewModel.MiddleZone.getAltitude());
            count = 1;
        }

        temp = temp / count;
        sound = sound / count;
        airP = airP / count;
        humidity = humidity / count;
        lux = lux / count;
        altitude = altitude / count;

        SetTemperature(temp);
        _soundTextView.setText(String.valueOf(sound));
        _airPressureTextView.setText(String.valueOf(airP));
        _humidityTextView.setText(String.valueOf(humidity));
        _luxTextView.setText(String.valueOf(lux));
        _altitudeTextView.setText(String.valueOf(altitude));

    }
}
