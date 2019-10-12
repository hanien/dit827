package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;

public class TempLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;

    private LinearLayout Set1;
    private LinearLayout Set2;
    private LinearLayout Set3;
    private LinearLayout Set4;

    public TempLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_templayout, container, false);

        setUpView();
        zoneIsSelected();

        return _view;
    }

    private void setUpView() {
        Set1 = _view.findViewById(R.id.Set1);
        Set2 = _view.findViewById(R.id.Set2);
        Set3 = _view.findViewById(R.id.Set3);
        Set4 = _view.findViewById(R.id.Set4);
    }


    @Override
    public void zoneIsSelected() {
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){

            Set1.setVisibility(View.VISIBLE);
            Set2.setVisibility(View.VISIBLE);
            Set3.setVisibility(View.VISIBLE);
            Set4.setVisibility(View.VISIBLE);
        }else{

            Set1.setVisibility(View.GONE);
            Set2.setVisibility(View.GONE);
            Set3.setVisibility(View.GONE);
            Set4.setVisibility(View.GONE);
        }
    }
}
