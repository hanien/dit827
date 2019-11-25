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

public class EditModeLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private ModeLayoutFragment _parentFragment;
    private DashboardFragment _dashboardFragment;
    private SettingsLayoutFragment _settingsLayoutFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private ModeLayoutFragment ModeLayoutFragment;
    private AddModeLayoutFragment AddModeLayoutFragment;
    private IZoneSelection _callback;
    private Mode _currentMode;

    private ListView listView;
    private CustomListAdapter mAdapter;

    private EditText editName;
    private EditText editAirp;
    private EditText editHum;
    private EditText editLux;
    private EditText editTemp;
    private EditText editVolume;
    private ImageView saveBtn;

    public EditModeLayoutFragment(ModeLayoutFragment parentFragment, MainActivity Owner, BaseViewModel viewModel, Mode currentMode, SettingsLayoutFragment settingsFragment) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
        _currentMode = currentMode;
        _settingsLayoutFragment = settingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_editmodelayout, container, false);
        //Context context = getActivity().getApplicationContext();

        setUpView();

        return _view;
    }

    private void setUpView() {

        editName = _view.findViewById(R.id.editName);
        editAirp = _view.findViewById(R.id.editAirp);
        editHum = _view.findViewById(R.id.editHum);
        editLux = _view.findViewById(R.id.editLux);
        editTemp = _view.findViewById(R.id.editTemp);
        editVolume = _view.findViewById(R.id.editVolume);
        saveBtn = _view.findViewById(R.id.saveBtn);

        editName.setText(_currentMode.getTitle());
        editAirp.setText(_currentMode.getAirp());
        editHum.setText(_currentMode.getHumidity());
        editLux.setText(_currentMode.getLux());
        editTemp.setText(_currentMode.getTemp());
        editVolume.setText(_currentMode.getVolume());

        saveBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //test
                        Log.d("CLICKED", "save button");

                        _settingsLayoutFragment.editMode(
                                _currentMode,
                                editName.getText().toString(),
                                editLux.getText().toString(),
                                editTemp.getText().toString(),
                                editVolume.getText().toString(),
                                editAirp.getText().toString(),
                                editHum.getText().toString()
                        );



                        _owner.OpenSettingsFragment(_view);
                    }
                }
        );

    }

    @Override
    public void zoneIsSelected() {
        if (_dashboardFragment._backSeatSelected || _dashboardFragment._driverSeatSelected || _dashboardFragment._frontSeatSelected) {

        } else {

        }
    }
}