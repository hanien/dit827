package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.sdsmdg.harjot.crollerTest.Croller;

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


    public AirpLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
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

        return _view;
    }

    private void setUpView() {
        SetText = _view.findViewById(R.id.SelectZoneTextView);
        ApChangeValue = _view.findViewById(R.id.tempChangeValue);
        ApValue = _view.findViewById(R.id.tempValue);
        SetApLayout = _view.findViewById(R.id.SetTempLayout);
        TempImage = _view.findViewById(R.id.TempImage);
        Label = _view.findViewById(R.id.tempLabel);
    }

    private void setUpElements(){
        Label.setText("Current air pressure");
        TempImage.setImageResource(R.drawable.ap);
        ApValue.setText(_baseViewModel.MiddleZone.getPressure() + " hPa");
    }


    @Override
    public void zoneIsSelected() {
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
        double AP = 0;
        if(Driver && Passenger && Back){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure())+  Double.parseDouble(_baseViewModel.PassengerZone.getPressure()) + Double.parseDouble(_baseViewModel.BackseatZone.getPressure()) +  Double.parseDouble(_baseViewModel.DriverZone.getPressure());
            AP = AP / 4;
        }
        else if(Driver && Passenger){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure())+  Double.parseDouble(_baseViewModel.PassengerZone.getPressure()) +  Double.parseDouble(_baseViewModel.DriverZone.getPressure());
            AP = AP / 3;
        }
        else if(Passenger && Back){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure())+  Double.parseDouble(_baseViewModel.PassengerZone.getPressure()) + Double.parseDouble(_baseViewModel.BackseatZone.getPressure());
            AP = AP / 3;
        }
        else if(Driver && Back){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure()) + Double.parseDouble(_baseViewModel.BackseatZone.getPressure()) +  Double.parseDouble(_baseViewModel.DriverZone.getPressure());
            AP = AP / 3;
        }
        else if(Driver){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure())+Double.parseDouble(_baseViewModel.DriverZone.getPressure());
            AP = AP / 2;
        }
        else if(Passenger){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure())+Double.parseDouble(_baseViewModel.PassengerZone.getPressure());
            AP = AP / 2;
        }
        else if(Back){
            AP =  Double.parseDouble(_baseViewModel.MiddleZone.getPressure())+Double.parseDouble(_baseViewModel.BackseatZone.getPressure());
            AP = AP / 2;
        }


        if(AP == 0){
            ApValue.setText(_baseViewModel.MiddleZone.getPressure() +  " hPa");
            ApChangeValue.setText(_baseViewModel.MiddleZone.getPressure() +  " hPa");
        }
        else {
            ApValue.setText(String.valueOf((int)AP) + " hPa");
            ApChangeValue.setText(String.valueOf((int)AP) + " hPa");

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