package com.example.aptiv.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aptiv.MainActivity;
import com.example.aptiv.R;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private MainActivity _owner;
    private View _view;


    public DashboardFragment(MainActivity Owner) {
        _owner = Owner;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SetupCarLayoutFragment();
        SetupButton();
        SetupEvents();

        return _view;
    }

    private void SetupCarLayoutFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        CarLayoutFragment CarFragment = new CarLayoutFragment(_owner);
        fragmentTransaction.replace(R.id.fragmentPlaceHolderDashboard, CarFragment).commit();
    }

    private void SetupEvents() {

    }

    private void SetupButton() {

    }

    @Override
    public void onClick(View view){
        /*
        switch (view.getId()) {
            case R.id.frontseat:

                break;

        }

        */
    }


}