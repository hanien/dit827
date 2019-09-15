package com.example.aptiv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.aptiv.MainActivity;
import com.example.aptiv.R;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private MainActivity _owner;
    private View _view;
    private ImageView _frontSeat;
    private ImageView _defaultCar;
    private ImageView _driverSeat;
    private ImageView _backSeat;
    private Boolean _frontSeatSelected = false;
    private Boolean _backSeatSelected = false;
    private Boolean _driverSeatSelected = false;

    public DashboardFragment(MainActivity Owner) {
        _owner = Owner;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SetupButton();
        SetupEvents();

        return _view;
    }

    private void SetupEvents() {
        _frontSeat.setOnClickListener(this);
        _defaultCar.setOnClickListener(this);
        _backSeat.setOnClickListener(this);
        _driverSeat.setOnClickListener(this);
    }

    private void SetupButton() {
        _frontSeat = _view.findViewById(R.id.frontseat);
        _defaultCar = _view.findViewById(R.id.default_car);
        _backSeat = _view.findViewById(R.id.backseat);
        _driverSeat = _view.findViewById(R.id.driverseat);
    }

    @Override
    public void onClick(View view){

        switch (view.getId()) {
            case R.id.frontseat:
                if(_frontSeatSelected){
                    _frontSeat.setImageResource(R.drawable.frontseat);
                }else{
                    _frontSeat.setImageResource(R.drawable.frontseatselected);
                }
                _frontSeatSelected = !_frontSeatSelected;
                break;
            case R.id.default_car:
                _frontSeatSelected = false;
                _backSeatSelected = false;
                _driverSeatSelected = false;
                _frontSeat.setImageResource(R.drawable.frontseat);
                _backSeat.setImageResource(R.drawable.backseat);
                _driverSeat.setImageResource(R.drawable.driverseat);
                break;
            case R.id.backseat:
                if(_backSeatSelected){
                    _backSeat.setImageResource(R.drawable.backseat);
                }else{
                    _backSeat.setImageResource(R.drawable.backseatselected);
                }
                _backSeatSelected = !_backSeatSelected;
                break;
            case R.id.driverseat:
                if(_driverSeatSelected){
                    _driverSeat.setImageResource(R.drawable.driverseat);
                }else{
                    _driverSeat.setImageResource(R.drawable.driverseatselected);
                }
                _driverSeatSelected = !_driverSeatSelected ;
                break;
        }
    }


}