package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.sdsmdg.harjot.crollerTest.Croller;

public class SoundLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private TextView _textView;
    private TextView _zoneTextView;
    private LinearLayout _zoneControllerLayout;
    private Croller _defaultController;
    private Croller _zoneController;


    public SoundLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_soundlayout, container, false);

        setUpView();
        setUpElements();
        zoneIsSelected();
        setUpTimer();

        return _view;
    }

    private void setUpView() {
        _textView = _view.findViewById(R.id.TempratureTextView);
        _zoneControllerLayout = _view.findViewById(R.id.zoneCrollerLayout);
        _defaultController = _view.findViewById(R.id.DefaultController);
        _zoneController = _view.findViewById(R.id.ZoneController);
        _zoneTextView = _view.findViewById(R.id.SelectZoneTextView);
    }

    private void setUpElements(){
        _zoneTextView.setText("Please click on specific zone to change value in it");
        _textView.setText(_baseViewModel.MiddleZone.getSound());
        _defaultController.setLabel(String.valueOf(_baseViewModel.MiddleZone.getSound()));
        _defaultController.setProgress(Integer.valueOf(_baseViewModel.MiddleZone.getSound()));
        _defaultController.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                _defaultController.setLabel(String.valueOf(progress));
                _defaultController.setProgress(progress);
            }
        });
        _zoneController.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                _zoneController.setLabel(String.valueOf(progress));
                _zoneController.setProgress(progress);
            }
        });
    }

    //When a zone is selected on the car
    //values needs to be changes base on zone
    @Override
    public void zoneIsSelected() {
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
            _zoneTextView.setVisibility(View.GONE);
            _zoneControllerLayout.setVisibility(View.VISIBLE);
            updateSoundValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
        }else{
            _zoneTextView.setVisibility(View.VISIBLE);
            _zoneControllerLayout.setVisibility(View.GONE);
        }
    }

    //calculate average temp base on zone that is selected
    private void updateSoundValue(boolean Driver, boolean Passenger , boolean Back) {
        double temp = 0;
        if(Driver && Passenger && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound())+  Double.parseDouble(_baseViewModel.PassengerZone.getSound()) + Double.parseDouble(_baseViewModel.BackseatZone.getSound()) +  Double.parseDouble(_baseViewModel.DriverZone.getSound());
            temp = temp / 4;
        }
        else if(Driver && Passenger){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound())+  Double.parseDouble(_baseViewModel.PassengerZone.getSound()) +  Double.parseDouble(_baseViewModel.DriverZone.getSound());
            temp = temp / 3;
        }
        else if(Passenger && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound())+  Double.parseDouble(_baseViewModel.PassengerZone.getSound()) + Double.parseDouble(_baseViewModel.BackseatZone.getSound());
            temp = temp / 3;
        }
        else if(Driver && Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound()) + Double.parseDouble(_baseViewModel.BackseatZone.getSound()) +  Double.parseDouble(_baseViewModel.DriverZone.getSound());
            temp = temp / 3;
        }
        else if(Driver){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound())+Double.parseDouble(_baseViewModel.DriverZone.getSound());
            temp = temp / 2;
        }
        else if(Passenger){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound())+Double.parseDouble(_baseViewModel.PassengerZone.getSound());
            temp = temp / 2;
        }
        else if(Back){
            temp =  Double.parseDouble(_baseViewModel.MiddleZone.getSound())+Double.parseDouble(_baseViewModel.BackseatZone.getSound());
            temp = temp / 2;
        }
        _zoneController.setProgress((int)temp);
        _zoneController.setLabel(String.valueOf((int)temp));
    }

    //region Timer method
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
        _textView.setText(_baseViewModel.MiddleZone.getSound());
        updateSoundValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);
    }
    //endregion
}
