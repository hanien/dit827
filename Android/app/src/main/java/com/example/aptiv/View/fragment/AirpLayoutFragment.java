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

public class AirpLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private TextView Label;
    private TextView SetText;
    private TextView ApValue;
    private TextView ApChangeValue;
    private ImageView TempImage;
    private LinearLayout SetApLayout;
    private ImageView _minusButton;
    private ImageView _plusButton;
    private double _desiredAir;
    private boolean _plusMinusButtonClicked = false;
    double air = 0;

    public AirpLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
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

    private void registerOnClickListeners(){
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

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        ApChangeValue = _view.findViewById(R.id.tempChangeValue);
        ApValue = _view.findViewById(R.id.tempValue);
        SetApLayout = _view.findViewById(R.id.SetTempLayout);
        TempImage = _view.findViewById(R.id.TempImage);
        Label = _view.findViewById(R.id.tempLabel);
        _minusButton = _view.findViewById(R.id.minus);
        _plusButton= _view.findViewById(R.id.plus);
    }

    private void setUpElements(){
        Label.setText("Current air pressure");
        TempImage.setImageResource(R.drawable.ap);
        ApValue.setText(_baseViewModel.MiddleZone.getPressure() + " hPa");
    }

    @Override
    public void zoneIsSelected() {
        _desiredAir = air;
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
            SetText.setVisibility(View.GONE);
            SetApLayout.setVisibility(View.VISIBLE);
            ApChangeValue.setVisibility(View.VISIBLE);
            updateApValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
        }else{
            SetText.setVisibility(View.VISIBLE);
            ApChangeValue.setVisibility(View.GONE);
            SetApLayout.setVisibility(View.GONE);
        }
    }

    private void updateApValue(boolean Driver, boolean Passenger , boolean Back) {

        if((int)_desiredAir == (int)air){
            _plusMinusButtonClicked = false;
        }

        air =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure());

        int count = 1;
        if(Driver){
            air = air + Double.parseDouble(_baseViewModel.DriverZone.getPressure());
            count++;
        }
        if(Passenger){
            air = air + Double.parseDouble(_baseViewModel.PassengerZone.getPressure());
            count++;
        }
        if(Back){
            air = air + Double.parseDouble(_baseViewModel.BackseatZone.getPressure());
            count++;
        }
        if(count ==4){
            air =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure());
            count = 1;
        }
        air = air/count;
        ApChangeValue.setText(String.valueOf((int)air));

        if(!_plusMinusButtonClicked){
            ApChangeValue.setTextSize(50);
            ApChangeValue.setText(String.valueOf((int)air));
        }
        if(_plusMinusButtonClicked){
            PlusMinusButtonClicked(true,Driver,Passenger,Back);
        }
    }

    private boolean checkZoneDifferences(boolean plus,boolean driver, boolean passenger, boolean backseat){
        if(driver) {
            return DifferenceChecker.checkAirPressure(plus,
                    _baseViewModel.DriverZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.BackseatZone);
        }
        if(passenger){
            return DifferenceChecker.checkAirPressure(plus,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone,
                    _baseViewModel.BackseatZone);
        }
        if(backseat){
            return DifferenceChecker.checkAirPressure(plus,
                    _baseViewModel.BackseatZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone);
        }
        return true;
    }

    private void PlusMinusButtonClicked(boolean plus,boolean Driver,boolean Passenger,boolean Back){
        _plusMinusButtonClicked = true;

        if(checkZoneDifferences(plus,Driver, Passenger, Back))
        {
            if(plus){
                _desiredAir++;
            }
            else {
                _desiredAir--;
            }
            ApChangeValue.setTextSize(25);
            ApChangeValue.setText("In progress...\n Changing Air Pressure \n from " + String.valueOf((int)air) + " to "+ (int)_desiredAir);
            if(Driver){
                _baseViewModel.DriverProfile.setPressure(Double.toString(_desiredAir));
            }
            if(Passenger){
                _baseViewModel.PassengerProfile.setPressure(Double.toString(_desiredAir));
            }
            if(Back) {
                _baseViewModel.BackProfile.setPressure(Double.toString(_desiredAir));
            }
        }
        else{
            _parentFragment.CreatePopupView(Driver, Passenger, Back, "Air pressure is too different from other zones! Adjust other zones and try again.", false);
            //TODO
            //if yes: implement adjustment behavior
            //else: reset to original value
            }
    }

    private void setUpTimer(){
        new CountDownTimer(30000, 1000) {

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
        ApValue.setText(_baseViewModel.MiddleZone.getPressure()  + " hPa");
        updateApValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
    }


}