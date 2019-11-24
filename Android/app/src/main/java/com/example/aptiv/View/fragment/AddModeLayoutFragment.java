package com.example.aptiv.View.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.aptiv.Model.Classe.Mode;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.View.adapter.CustomListAdapter;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class AddModeLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private SettingsLayoutFragment _parentFragment;
    private DashboardFragment _dashboardFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private ModeLayoutFragment ModeLayoutFragment;
    private AddModeLayoutFragment AddModeLayoutFragment;
    private IZoneSelection _callback;

    private ListView listView;
    private CustomListAdapter mAdapter;

    private EditText newName;
    private EditText newAirp;
    private EditText newHum;
    private EditText newLux;
    private EditText newTemp;
    private EditText newVolume;
    private ImageView addBtn;

    public AddModeLayoutFragment(SettingsLayoutFragment parentFragment, MainActivity Owner, BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_addmodelayout, container, false);
        //Context context = getActivity().getApplicationContext();

        setUpView();

        addBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //test
                        Log.d("CLICKED", "save button");

                        _parentFragment.addNewMode(
                                newName.getText().toString(),
                                newLux.getText().toString(),
                                newTemp.getText().toString(),
                                newVolume.getText().toString(),
                                newAirp.getText().toString(),
                                newHum.getText().toString()
                        );
                    }
                });

        return _view;
    }

    private void setUpView() {

        newName = _view.findViewById(R.id.newName);
        newAirp = _view.findViewById(R.id.newAirp);
        newHum = _view.findViewById(R.id.newHum);
        newLux = _view.findViewById(R.id.newLux);
        newTemp = _view.findViewById(R.id.newTemp);
        newVolume = _view.findViewById(R.id.newVolume);
        addBtn = _view.findViewById(R.id.addBtn);

    }

    @Override
    public void zoneIsSelected() {
        if (_dashboardFragment._backSeatSelected || _dashboardFragment._driverSeatSelected || _dashboardFragment._frontSeatSelected) {

        } else {

        }
    }
}