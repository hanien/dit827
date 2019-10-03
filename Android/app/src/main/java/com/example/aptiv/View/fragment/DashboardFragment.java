package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aptiv.View.MainActivity;
import com.example.aptiv.R;
import com.example.aptiv.ViewModel.BaseViewModel;

public class DashboardFragment extends Fragment {

    private MainActivity _owner;
    private View _view;
    private BaseViewModel _baseViewModel;
    public  CarLayoutFragment CarLayoutFragment;
    public  DefaultLayoutFragment DefaultLayoutFragment;
    public SoundLayoutFragment SoundLayoutFragment;
    public DashboardFragment(MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        SetupCarLayoutFragment();

        return _view;
    }

    private void SetupCarLayoutFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        CarLayoutFragment = new CarLayoutFragment(_owner);
        fragmentTransaction.replace(R.id.fragmentPlaceHolderCar, CarLayoutFragment).commit();

        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        DefaultLayoutFragment = new DefaultLayoutFragment(_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, DefaultLayoutFragment).commit();

    }

    public void OpenVolumeFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        SoundLayoutFragment = new SoundLayoutFragment(_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, SoundLayoutFragment).commit();
    }

    public void CloseVolumeFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        DefaultLayoutFragment = new DefaultLayoutFragment(_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, DefaultLayoutFragment).commit();
    }
}