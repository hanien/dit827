package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import androidx.fragment.app.Fragment;

public class LuxLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private ImageView Image;
    private TextView Label;
    private TextView SetText;
    private TextView luxValue;
    private TextView luxChangeValue;
    private ImageView _minusButton;
    private ImageView _plusButton;
    private LinearLayout SetLuxLayout;
    private double _desiredLux;
    double lux = 0;
    private boolean _plusMinusButtonClicked = false;


    public LuxLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
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

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        luxChangeValue = _view.findViewById(R.id.tempChangeValue);
        luxValue = _view.findViewById(R.id.tempValue);
        SetLuxLayout = _view.findViewById(R.id.SetTempLayout);
        Image = _view.findViewById(R.id.TempImage);
        Label = _view.findViewById(R.id.tempLabel);
        _minusButton = _view.findViewById(R.id.minus);
        _plusButton= _view.findViewById(R.id.plus);
    }

    private void setUpElements(){
        Label.setText("Current lux");
        Image.setImageResource(R.drawable.light);
        luxValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");
    }

    private void registerOnClickListeners(){
        _plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredLux++;
                PlusMinusButtonClicked(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
            }
        });
        _minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _desiredLux--;
                PlusMinusButtonClicked(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
            }
        });
    }

    @Override
    public void zoneIsSelected() {
        _desiredLux = lux;
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
            SetText.setVisibility(View.GONE);
            SetLuxLayout.setVisibility(View.VISIBLE);
            luxChangeValue.setVisibility(View.VISIBLE);
            updateLuxValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);

        }else{
            SetText.setVisibility(View.VISIBLE);
            luxChangeValue.setVisibility(View.GONE);
            SetLuxLayout.setVisibility(View.GONE);
        }
    }

    private void updateLuxValue(boolean Driver, boolean Passenger , boolean Back) {
        if((int)_desiredLux == (int)lux){
            _plusMinusButtonClicked = false;
        }
        lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr());
        int count = 1;
        if(Driver){
            lux = lux + Double.parseDouble(_baseViewModel.DriverZone.getIr());
            count++;
        }
        if(Passenger){
            lux = lux + Double.parseDouble(_baseViewModel.PassengerZone.getIr());
            count++;
        }
        if(Back){
            lux = lux + Double.parseDouble(_baseViewModel.PassengerZone.getIr());
            count++;
        }
        if(count ==4){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr());
            count = 1;
        }
        lux = lux/count;
        luxChangeValue.setText(String.valueOf((int)lux) + " lux");

        if(!_plusMinusButtonClicked){
            luxChangeValue.setTextSize(50);
            luxChangeValue.setText(String.valueOf((int)lux));
        }
        if(_plusMinusButtonClicked){
            PlusMinusButtonClicked(Driver,Passenger,Back);
        }
    }

    private void PlusMinusButtonClicked(boolean Driver,boolean Passenger,boolean Back){
        _plusMinusButtonClicked = true;
        luxChangeValue.setTextSize(25);

        luxChangeValue.setText("In progress...\n Changing Volume\n from " +(int)_desiredLux+ " to "+ String.valueOf((int)lux));
        if(Driver){
            _baseViewModel.DriverProfile.setIr(Double.toString(_desiredLux));
        }
        if(Passenger){
            _baseViewModel.PassengerProfile.setIr(Double.toString(_desiredLux));
        }
        if(Back) {
            _baseViewModel.BackProfile.setIr(Double.toString(_desiredLux));
        }
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
        luxValue.setText(_baseViewModel.MiddleZone.getIr()  + " lux");
        updateLuxValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
    }


}