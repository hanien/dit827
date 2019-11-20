package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
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
    private LinearLayout SwitchContainer;
    private TextView SetText;
    private TextView TempValue;
    private TextView tempChangeValue;
    private ImageView _minusButton;
    private ImageView _plusButton;
    private LinearLayout SetTempLayout;
    private Switch TempTypeSwitch;
    private double  temp = 0;
    private double _desiredTemp;
    private boolean _plusMinusButtonClicked = false;

    public TempLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
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
        TempTypeSwitch = _view.findViewById(R.id.TempTypeSwitch);
        SwitchContainer = _view.findViewById(R.id.SwitchContainer);
        _minusButton = _view.findViewById(R.id.minus);
        _plusButton= _view.findViewById(R.id.plus);
    }

    private void setUpElements(){
        SwitchContainer.setVisibility(View.VISIBLE);
        TempTypeSwitch.setChecked(_baseViewModel.getTempType());
        double temp = Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
        temp = (_baseViewModel.getTempType()) ? ((1.8*temp))+32 : temp;
        String tempType = (_baseViewModel.getTempType()) ? _baseViewModel.getFahrenheit() : _baseViewModel.getCelsius();
        TempValue.setText( temp + tempType);
    }

    private void registerOnClickListeners(){
        TempTypeSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String tempType = (isChecked) ? _baseViewModel.getFahrenheit(): _baseViewModel.getCelsius();
                        _baseViewModel.tempType = isChecked;
                        updateTempValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
                    }
                }
        );
        _plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredTemp++;
                PlusMinusButtonClicked();
            }
        });
        _minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredTemp--;
                PlusMinusButtonClicked();
            }
        });
    }

    @Override
    public void zoneIsSelected() {
        _desiredTemp = temp;
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
              SetText.setVisibility(View.GONE);
              SetTempLayout.setVisibility(View.VISIBLE);
              tempChangeValue.setVisibility(View.VISIBLE);
              updateTempValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
        }else{
              SetText.setVisibility(View.VISIBLE);
              tempChangeValue.setVisibility(View.GONE);
              SetTempLayout.setVisibility(View.GONE);
        }
    }

    private void updateTempValue(boolean Driver, boolean Passenger , boolean Back) {
        Boolean tempType = _baseViewModel.getTempType();
        String fahrenheit = _baseViewModel.getFahrenheit();
        String celsius = _baseViewModel.getCelsius();
        setValueForZone(Driver,Passenger,Back);

        String typeString = ((tempType)) ? fahrenheit : celsius;
        TempValue.setText(_baseViewModel.MiddleZone.getTemperature() + typeString);
        if(!_plusMinusButtonClicked){
            tempChangeValue.setTextSize(50);
            tempChangeValue.setText(String.valueOf((int)temp) + typeString);
        }
    }

    private void setValueForZone(boolean Driver,boolean Passenger,boolean Back){
        if((int)_desiredTemp == (int)temp){
            _plusMinusButtonClicked = false;
        }
        Boolean tempType = _baseViewModel.getTempType();
        if(Driver && Passenger && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature());
        }
        else if(Driver && Passenger){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+  Double.parseDouble(_baseViewModel.PassengerZone.getTemperature()) +  Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = temp/3;
        }
        else if(Passenger && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+  Double.parseDouble(_baseViewModel.PassengerZone.getTemperature()) + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature());
            temp = temp/3;
        }
        else if(Driver && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature()) + Double.parseDouble(_baseViewModel.BackseatZone.getTemperature()) +  Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = temp/3;
        }
        else if(Driver){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+Double.parseDouble(_baseViewModel.DriverZone.getTemperature());
            temp = temp/2;
        }
        else if(Passenger){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+Double.parseDouble(_baseViewModel.PassengerZone.getTemperature());
            temp = temp/2;
        }
        else {
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getTemperature())+Double.parseDouble(_baseViewModel.BackseatZone.getTemperature());
            temp = temp/2;
        }
        temp = (tempType) ? (1.8*temp)+32 : temp;
        if(_plusMinusButtonClicked){
            PlusMinusButtonClicked();
        }
    }

    private void PlusMinusButtonClicked(){
        _plusMinusButtonClicked = true;
        Boolean tempType = _baseViewModel.getTempType();
        String fahrenheit = _baseViewModel.getFahrenheit();
        String celsius = _baseViewModel.getCelsius();
        String typeString = ((tempType)) ? fahrenheit : celsius;
        tempChangeValue.setTextSize(25);
        tempChangeValue.setText("In progress...\n Changing Tempreture\n from " +_desiredTemp+ " to "+ String.valueOf((int)temp) + typeString);
    }

    private void setUpTimer(){
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
        updateTempValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
    }
}