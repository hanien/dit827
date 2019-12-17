package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aptiv.Model.Class.Zone;
import com.example.aptiv.Model.Helper.ProfileHelper;
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
    String typeString;


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
        TempValue.setText(_baseViewModel.round(temp,1) + tempType);
    }

    private void registerOnClickListeners() {
        _plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusMinusButtonClicked(true,_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
            }
        });
        _minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusMinusButtonClicked(false,_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
            }
        });
    }

    private  boolean check = false;

    @Override
    public void zoneIsSelected() {
        _desiredTemp = temp;
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
              SetText.setVisibility(View.GONE);
              SetTempLayout.setVisibility(View.VISIBLE);
              tempChangeValue.setVisibility(View.VISIBLE);
              check = false;
              updateTempValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
        }else{
              SetText.setVisibility(View.VISIBLE);
              tempChangeValue.setVisibility(View.GONE);
              SetTempLayout.setVisibility(View.GONE);
              setUpElements();
        }
    }

    private void updateTempValue(boolean Driver, boolean Passenger, boolean Back) {
        Boolean tempType = _baseViewModel.getTempType();
        String fahrenheit = _baseViewModel.getFahrenheit();
        String celsius = _baseViewModel.getCelsius();
        typeString = ((tempType)) ? fahrenheit : celsius;

        setValueForZone(Driver, Passenger, Back);
        tempChangeValue.setText(String.valueOf((int)temp)+typeString);


      //  TempValue.setText(_baseViewModel.MiddleZone.getTemperature() + typeString);
        if (!_plusMinusButtonClicked) {
            tempChangeValue.setTextSize(50);
            tempChangeValue.setText(String.valueOf((int) temp) + typeString);
        }
        if(_plusMinusButtonClicked){
            setDesiredValue(Driver,Passenger,Back);
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
        temp = (tempType) ? (1.8*temp)+32 : temp;
        
        TempValue.setText(String.valueOf((int)temp)+typeString);

        if(check == false){
            _desiredTemp = temp;
            check = true;
        }


        if(_plusMinusButtonClicked){
            setDesiredValue(Driver,Passenger,Back);
        }
    }

    private boolean checkZoneDifferences(boolean increasing, boolean driver, boolean passenger, boolean backseat){
        if(driver) {
            Zone desiredVal = _baseViewModel.DriverZone.CloneZone();
            desiredVal.setTemperature(String.valueOf(_desiredTemp));
            return ProfileHelper.checkTemp(increasing,desiredVal,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.BackseatZone);
        }
        if(passenger){
            Zone desiredVal = _baseViewModel.PassengerZone.CloneZone();
            desiredVal.setTemperature(String.valueOf(_desiredTemp));
            return ProfileHelper.checkTemp(increasing,desiredVal,
                    _baseViewModel.DriverZone,
                    _baseViewModel.BackseatZone);
        }
        if(backseat){
            Zone desiredVal = _baseViewModel.BackseatZone.CloneZone();
            desiredVal.setTemperature(String.valueOf(_desiredTemp));
            return ProfileHelper.checkTemp(increasing,desiredVal,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone);
        }
        return true;
    }

    private void PlusMinusButtonClicked(boolean plus,boolean Driver,boolean Passenger,boolean Back){
        _plusMinusButtonClicked = true;

        if(checkZoneDifferences(plus,Driver, Passenger, Back)) {
            if(plus){
                _desiredTemp++;
            }
            else {
                _desiredTemp--;
            }
            setDesiredValue(Driver,Passenger,Back);
        } else {
            _parentFragment.CreatePopupView(Driver, Passenger, Back, "Temperature is too different from other zones! Adjust other zones and try again.", false,null);
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

    private void setDesiredValue(boolean Driver, boolean Passenger,boolean Back){
        Boolean tempType = _baseViewModel.getTempType();
        String fahrenheit = _baseViewModel.getFahrenheit();
        String celsius = _baseViewModel.getCelsius();
        String typeString = ((tempType)) ? fahrenheit : celsius;
        tempChangeValue.setTextSize(25);
        tempChangeValue.setText("Changing Tempreture\n to " + _baseViewModel.round(_desiredTemp,1) + typeString);
        if(Driver){
            _baseViewModel.DriverProfile.setTemperature(Double.toString(_desiredTemp));
        }
        if (Passenger) {
            _baseViewModel.PassengerProfile.setTemperature(Double.toString(_desiredTemp));
        }
        if (Back) {
            _baseViewModel.BackProfile.setTemperature(Double.toString(_desiredTemp));
        }
    }
}