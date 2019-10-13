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

    private TextView SetText;

    private LinearLayout SetTemp1;
    private LinearLayout SetTemp2;
    private LinearLayout SetTemp3;

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
        SetText = _view.findViewById(R.id.ZoneSelectText);
        SetTemp1 = _view.findViewById(R.id.setTemp1);
        SetTemp2 = _view.findViewById(R.id.setTemp2);
        SetTemp3 = _view.findViewById(R.id.setTemp3);

    }


    @Override
    public void zoneIsSelected() {
        if(_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected ){
            SetText.setVisibility(View.GONE);
            SetTemp1.setVisibility(View.VISIBLE);
            SetTemp2.setVisibility(View.VISIBLE);
            SetTemp3.setVisibility(View.VISIBLE);

        }else{
            SetText.setVisibility(View.VISIBLE);
            SetTemp1.setVisibility(View.GONE);
            SetTemp2.setVisibility(View.GONE);
            SetTemp3.setVisibility(View.GONE);

        }
    }
}
