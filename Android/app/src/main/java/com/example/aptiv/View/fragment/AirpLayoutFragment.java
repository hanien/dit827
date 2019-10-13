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

public class AirpLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;

    private TextView SetText;
    private TextView SetAPText;
    private LinearLayout SetAP;


    public AirpLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_airplayout, container, false);

        setUpView();
        zoneIsSelected();

        return _view;
    }

    private void setUpView() {
        SetText = _view.findViewById(R.id.ZoneSelectText);
        SetAPText = _view.findViewById(R.id.setAP_value);
        SetAP = _view.findViewById(R.id.selectAPlayout);

    }


    @Override
    public void zoneIsSelected() {
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
            SetText.setVisibility(View.GONE);
            SetAPText.setVisibility(View.VISIBLE);
            SetAP.setVisibility(View.VISIBLE);

        }else{
            SetText.setVisibility(View.VISIBLE);
            SetAPText.setVisibility(View.GONE);
            SetAP.setVisibility(View.GONE);

        }
    }
}
