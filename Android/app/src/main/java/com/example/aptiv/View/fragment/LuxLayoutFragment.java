package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.sdsmdg.harjot.crollerTest.Croller;

import androidx.fragment.app.Fragment;

public class LuxLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;

    private TextView SetText;
    private TextView luxValue;
    private TextView luxChangeValue;

    private LinearLayout SetLuxLayout;


    public LuxLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_luxlayout, container, false);

        setUpView();
        setUpElements();
        zoneIsSelected();
        setUpTimer();

        return _view;
    }

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        luxChangeValue = _view.findViewById(R.id.luxChangeValue);
        luxValue = _view.findViewById(R.id.luxValue);
        SetLuxLayout = _view.findViewById(R.id.SetLuxLayout);

    }

    private void setUpElements(){

        luxValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");

    }


    @Override
    public void zoneIsSelected() {
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
        double lux = 0;
        if(Driver && Passenger && Back){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr())+  Double.parseDouble(_baseViewModel.PassengerZone.getIr()) + Double.parseDouble(_baseViewModel.BackseatZone.getIr()) +  Double.parseDouble(_baseViewModel.DriverZone.getIr());
            lux = lux / 4;
        }
        else if(Driver && Passenger){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr())+  Double.parseDouble(_baseViewModel.PassengerZone.getIr()) +  Double.parseDouble(_baseViewModel.DriverZone.getIr());
            lux = lux / 3;
        }
        else if(Passenger && Back){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr()) + Double.parseDouble(_baseViewModel.PassengerZone.getIr()) + Double.parseDouble(_baseViewModel.BackseatZone.getIr());
            lux = lux / 3;
        }
        else if(Driver && Back){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr()) + Double.parseDouble(_baseViewModel.BackseatZone.getIr()) +  Double.parseDouble(_baseViewModel.DriverZone.getIr());
            lux = lux / 3;
        }
        else if(Driver){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr()) + Double.parseDouble(_baseViewModel.DriverZone.getIr());
            lux = lux / 2;
        }
        else if(Passenger){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr()) + Double.parseDouble(_baseViewModel.PassengerZone.getIr());
            lux = lux / 2;
        }
        else if(Back){
            lux =  Double.parseDouble(_baseViewModel.MiddleZone.getIr()) + Double.parseDouble(_baseViewModel.BackseatZone.getIr());
            lux = lux / 2;
        }


        if(lux == 0){
            luxValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");
            luxChangeValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");
        }
        else {
            luxValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");
            luxChangeValue.setText(String.valueOf((int)lux) + " lux");

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