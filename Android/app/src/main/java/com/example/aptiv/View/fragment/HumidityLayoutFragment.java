package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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


    public HumidityLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
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
        HumidityChangeValue = _view.findViewById(R.id.tempChangeValue);
        HumidityValue = _view.findViewById(R.id.tempValue);
        SetHumidityLayout = _view.findViewById(R.id.SetTempLayout);
        Image = _view.findViewById(R.id.TempImage);
        Label = _view.findViewById(R.id.tempLabel);
    }

    private void setUpElements(){
        Label.setText("Current humidity");
        Image.setImageResource(R.drawable.humidity);
        HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity() + " %");
    }


    @Override
    public void zoneIsSelected() {
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
            SetText.setVisibility(View.GONE);
            SetHumidityLayout.setVisibility(View.VISIBLE);
            HumidityChangeValue.setVisibility(View.VISIBLE);
            updateHumidityValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);

        }else{
            SetText.setVisibility(View.VISIBLE);
            HumidityChangeValue.setVisibility(View.GONE);
            SetHumidityLayout.setVisibility(View.GONE);


        }
    }

    private void updateHumidityValue(boolean Driver, boolean Passenger , boolean Back) {
        double hum = 0;
        if(Driver && Passenger && Back){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity())+  Double.parseDouble(_baseViewModel.PassengerZone.getHumidity()) + Double.parseDouble(_baseViewModel.BackseatZone.getHumidity()) +  Double.parseDouble(_baseViewModel.DriverZone.getHumidity());
            hum = hum / 4;
        }
        else if(Driver && Passenger){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity())+  Double.parseDouble(_baseViewModel.PassengerZone.getHumidity()) +  Double.parseDouble(_baseViewModel.DriverZone.getHumidity());
            hum = hum / 3;
        }
        else if(Passenger && Back){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity())+  Double.parseDouble(_baseViewModel.PassengerZone.getHumidity()) + Double.parseDouble(_baseViewModel.BackseatZone.getHumidity());
            hum = hum / 3;
        }
        else if(Driver && Back){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity()) + Double.parseDouble(_baseViewModel.BackseatZone.getHumidity()) +  Double.parseDouble(_baseViewModel.DriverZone.getHumidity());
            hum = hum / 3;
        }
        else if(Driver){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity())+Double.parseDouble(_baseViewModel.DriverZone.getHumidity());
            hum = hum / 2;
        }
        else if(Passenger){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity())+Double.parseDouble(_baseViewModel.PassengerZone.getHumidity());
            hum = hum / 2;
        }
        else if(Back){
            hum =  Double.parseDouble(_baseViewModel.MiddleZone.getHumidity())+Double.parseDouble(_baseViewModel.BackseatZone.getHumidity());
            hum = hum / 2;
        }


        if(hum == 0){
            HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity() +  " %");
            HumidityChangeValue.setText(_baseViewModel.MiddleZone.getHumidity() +  " %");
        }
        else {
            HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity() +  " %");
            HumidityChangeValue.setText(String.valueOf((int)hum) + " %");

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
        HumidityValue.setText(_baseViewModel.MiddleZone.getHumidity()  + " %");
        updateHumidityValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
    }


}
