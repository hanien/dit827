package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aptiv.Model.Helper.DifferenceChecker;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;

public class TempLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private TextView SetText;
    private TextView TempValue;
    private TextView tempChangeValue;
    private ImageView _minusButton;
    private ImageView _plusButton;
    private LinearLayout SetTempLayout;
    private double temp = 0;
    private double _desiredTemp;
    private boolean _plusMinusButtonClicked = false;


    public TempLayoutFragment(DashboardFragment parentFragment, MainActivity Owner, BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_templayout, container, false);

        setUpView();
        setUpElements();
        zoneIsSelected();
        setUpTimer();
        registerOnClickListeners();

        return _view;
    }

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        tempChangeValue = _view.findViewById(R.id.tempChangeValue);
        TempValue = _view.findViewById(R.id.tempValue);
        SetTempLayout = _view.findViewById(R.id.SetTempLayout);
        _minusButton = _view.findViewById(R.id.minus);
        _plusButton = _view.findViewById(R.id.plus);

    }

    private void setUpElements() {
        double temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
        temp = (_baseViewModel.getTempType()) ? ((1.8 * temp)) + 32 : temp;
        String tempType = (_baseViewModel.getTempType()) ? _baseViewModel.getFahrenheit() : _baseViewModel.getCelsius();
        TempValue.setText(temp + tempType);
    }

    private void registerOnClickListeners() {
        _plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredTemp++;
                PlusMinusButtonClicked(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
            }
        });
        _minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredTemp--;
                PlusMinusButtonClicked(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
            }
        });
    }

    @Override
    public void zoneIsSelected() {
        _desiredTemp = temp;
        if (_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected) {
            SetText.setVisibility(View.GONE);
            SetTempLayout.setVisibility(View.VISIBLE);
            tempChangeValue.setVisibility(View.VISIBLE);
            updateTempValue(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
        } else {
            SetText.setVisibility(View.VISIBLE);
            tempChangeValue.setVisibility(View.GONE);
            SetTempLayout.setVisibility(View.GONE);
        }
    }

    private void updateTempValue(boolean Driver, boolean Passenger, boolean Back) {
        Boolean tempType = _baseViewModel.getTempType();
        String fahrenheit = _baseViewModel.getFahrenheit();
        String celsius = _baseViewModel.getCelsius();
        setValueForZone(Driver, Passenger, Back);

        String typeString = ((tempType)) ? fahrenheit : celsius;
        TempValue.setText(_baseViewModel.MiddleZone.getTemperature() + typeString);
        if (!_plusMinusButtonClicked) {
            tempChangeValue.setTextSize(50);
            tempChangeValue.setText(String.valueOf((int) temp) + typeString);
        }
    }

    private void setValueForZone(boolean Driver, boolean Passenger, boolean Back) {
        if ((int) _desiredTemp == (int) temp) {
            _plusMinusButtonClicked = false;
        }
        Boolean tempType = _baseViewModel.getTempType();
        temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
        int count = 1;
        //TODO check and compare against other zones

        if (Driver) {
            temp = temp + Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            count++;
        }
        if (Passenger) {
            temp = temp + Double.parseDouble(_baseViewModel.PassengerZone.getTemperature());
            count++;
        }
        if (Back) {
            temp = temp + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature());
            count++;
        }
        if (count == 4) {
            temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
            count = 1;
        }
        temp = temp / count;

        temp = (tempType) ? (1.8 * temp) + 32 : temp;
        if (_plusMinusButtonClicked) {
            PlusMinusButtonClicked(Driver, Passenger, Back);
        }
    }

    private boolean checkZoneDifferences(boolean driver, boolean passenger, boolean backseat) {
        if (driver) {
            return DifferenceChecker.checkTemp(_baseViewModel.DriverZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.BackseatZone);
        }
        if (passenger) {
            return DifferenceChecker.checkTemp(_baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone,
                    _baseViewModel.BackseatZone);
        }
        if (backseat) {
            return DifferenceChecker.checkTemp(_baseViewModel.BackseatZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone);
        }
        return true;
    }

    private void PlusMinusButtonClicked(boolean Driver, boolean Passenger, boolean Back) {
        _plusMinusButtonClicked = true;
        Boolean tempType = _baseViewModel.getTempType();
        String fahrenheit = _baseViewModel.getFahrenheit();
        String celsius = _baseViewModel.getCelsius();
        String typeString = ((tempType)) ? fahrenheit : celsius;

        if (checkZoneDifferences(Driver, Passenger, Back)) {
            tempChangeValue.setTextSize(25);
            tempChangeValue.setText("In progress...\n Changing Temperature\n from " + String.valueOf((int) temp) + " to " + _desiredTemp + typeString);
            if (Driver) {
                _baseViewModel.DriverProfile.setTemperature(Double.toString(_desiredTemp));
            }
            if (Passenger) {
                _baseViewModel.PassengerProfile.setTemperature(Double.toString(_desiredTemp));
            }
            if (Back) {
                _baseViewModel.BackProfile.setTemperature(Double.toString(_desiredTemp));
            }
        } else {
            _parentFragment.CreatePopupView(Driver, Passenger, Back, "Temperature is too different from other zones! Adjust other zones and try again.", false);
            //TODO
            //if yes: implement adjustment behavior
            //else: reset to original value
        }
    }

    private void setUpTimer() {
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                UpdateData();
                setUpTimer();
            }
        }.start();
    }

    private void UpdateData() {
        _baseViewModel.UpdateData();
        updateView();
    }

    private void updateView() {
        updateTempValue(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
    }
}