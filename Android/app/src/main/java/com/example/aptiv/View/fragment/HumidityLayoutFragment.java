package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aptiv.Model.Helper.ProfileHelper;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;

public class HumidityLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private ImageView Image;
    private TextView SetText;
    private TextView HumidityValue;
    private TextView HumidityChangeValue;
    private TextView Label;
    private LinearLayout SetHumidityLayout;
    private ImageView _minusButton;
    private ImageView _plusButton;
    private double _desiredHumidity;
    double Humidity = 0;
    private boolean _plusMinusButtonClicked = false;

    public HumidityLayoutFragment(DashboardFragment parentFragment, MainActivity Owner, BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_templayout, container, false);

        setUpView();
        setUpElements();
        zoneIsSelected();
        setUpTimer();
        registerOnClickListeners();

        return _view;
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

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        HumidityChangeValue = _view.findViewById(R.id.tempChangeValue);
        HumidityValue = _view.findViewById(R.id.tempValue);
        SetHumidityLayout = _view.findViewById(R.id.SetTempLayout);
        Image = _view.findViewById(R.id.TempImage);
        Label = _view.findViewById(R.id.tempLabel);
        _minusButton = _view.findViewById(R.id.minus);
        _plusButton = _view.findViewById(R.id.plus);
    }

    private void setUpElements() {
        Label.setText("Current humidity");
        Image.setImageResource(R.drawable.humidity);
        HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity() + " %");
    }

    private  boolean check = false;
    @Override
    public void zoneIsSelected() {
        _desiredHumidity = Humidity;
        if (_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected) {
            SetText.setVisibility(View.GONE);
            SetHumidityLayout.setVisibility(View.VISIBLE);
            HumidityChangeValue.setVisibility(View.VISIBLE);

            check = false;
            updateHumidityValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);


        } else {
            SetText.setVisibility(View.VISIBLE);
            HumidityChangeValue.setVisibility(View.GONE);
            SetHumidityLayout.setVisibility(View.GONE);
            HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity() + " %");

        }
    }

    private void updateHumidityValue(boolean Driver, boolean Passenger, boolean Back) {

        if ((int) _desiredHumidity == (int) Humidity) {
            _plusMinusButtonClicked = false;
        }

        Humidity = Double.parseDouble(_baseViewModel.MiddleZone.getHumidity());

        int count = 1;
        if (Driver) {
            Humidity = Humidity + Double.parseDouble(_baseViewModel.DriverZone.getHumidity());
            count++;
        }
        if (Passenger) {
            Humidity = Humidity + Double.parseDouble(_baseViewModel.PassengerZone.getHumidity());
            count++;
        }
        if (Back) {
            Humidity = Humidity + Double.parseDouble(_baseViewModel.BackseatZone.getHumidity());
            count++;
        }
        if (count == 4) {
            Humidity = Double.parseDouble(_baseViewModel.MiddleZone.getHumidity());
            count = 1;
        }

        Humidity = Humidity/count;
        HumidityValue.setText(String.valueOf((int)Humidity)  +  " %");
        if(check == false){
            _desiredHumidity = Humidity;
            check = true;
        }
        HumidityChangeValue.setText(String.valueOf((int)Humidity)  +  " %");


        if (!_plusMinusButtonClicked) {
            HumidityChangeValue.setTextSize(50);
            HumidityChangeValue.setText(String.valueOf((int) Humidity + " %"));
        }
        if(_plusMinusButtonClicked){
            PlusMinusButtonClicked(true,Driver,Passenger,Back);
        }
    }


    private boolean checkZoneDifferences(boolean plus, boolean driver, boolean passenger, boolean backseat){
        if(driver) {
            return ProfileHelper.checkHumidity(plus,_baseViewModel.DriverZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.BackseatZone);

        }


        if(passenger){
            return ProfileHelper.checkHumidity(plus,_baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone,
                    _baseViewModel.BackseatZone);
        }
        if(backseat){

            return ProfileHelper.checkHumidity(plus,_baseViewModel.BackseatZone,
                    _baseViewModel.PassengerZone,
                    _baseViewModel.DriverZone);
        }
        return true;
    }

    private void PlusMinusButtonClicked(boolean plus,boolean Driver,boolean Passenger,boolean Back){
        _plusMinusButtonClicked = true;

        if(checkZoneDifferences(plus,Driver, Passenger, Back)){
            if(plus){
                _desiredHumidity++;
            }
            else {
                _desiredHumidity--;
            }

            HumidityChangeValue.setTextSize(25);

            HumidityChangeValue.setText("Changing Humidity \n to " + (int)_desiredHumidity );
            if(Driver){
                _baseViewModel.DriverProfile.setHumidity(Double.toString(_desiredHumidity));
            }
            if (Passenger) {
                _baseViewModel.PassengerProfile.setHumidity(Double.toString(_desiredHumidity));
            }
            if (Back) {
                _baseViewModel.BackProfile.setHumidity(Double.toString(_desiredHumidity));
            }
        } else {
            _parentFragment.CreatePopupView(Driver, Passenger, Back, "Humidity is too different from other zones! Adjust other zones and try again.", false,null);
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
        HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity() + " %");
        updateHumidityValue(_parentFragment._driverSeatSelected, _parentFragment._frontSeatSelected, _parentFragment._backSeatSelected);
    }


}
