package com.example.aptiv.View.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Mode;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.View.adapter.CustomListAdapter;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ModeLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private SettingsLayoutFragment _parentFragment;
    private DashboardFragment _dashboardFragment;
    private View _view;
    private BaseViewModel _baseViewModel;

    private IZoneSelection _callback;

    private TextView modeLabel;

    public ModeLayoutFragment(SettingsLayoutFragment parentFragment, MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_modelayout, container, false);
        Context currContext = getActivity().getApplicationContext();

        setUpView();
        //setUpElements();
        //zoneIsSelected();
        //setUpTimer();



        return _view;
    }

    private void setUpView() {

        modeLabel = _view.findViewById(R.id.modeLabel);

    }

    private void setUpElements(){

        //luxValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");

    }


    @Override
    public void zoneIsSelected() {
        if (_dashboardFragment._backSeatSelected || _dashboardFragment._driverSeatSelected || _dashboardFragment._frontSeatSelected) {
            //SetText.setVisibility(View.GONE);
            //SetLuxLayout.setVisibility(View.VISIBLE);
            //luxChangeValue.setVisibility(View.VISIBLE);
            //updateLuxValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);

        } else {
            //SetText.setVisibility(View.VISIBLE);
            //luxChangeValue.setVisibility(View.GONE);
            //SetLuxLayout.setVisibility(View.GONE);


        }
    }

}