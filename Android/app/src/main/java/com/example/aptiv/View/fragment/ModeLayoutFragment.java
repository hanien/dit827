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
import android.widget.TextView;

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

public class ModeLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private SettingsLayoutFragment _parentFragment;
    private DashboardFragment _dashboardFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private EditModeLayoutFragment EditModeLayoutFragment;
    private AddModeLayoutFragment AddModeLayoutFragment;
    private IZoneSelection _callback;

    private ListView listView;
    private CustomListAdapter mAdapter;
    private Mode _currentMode;

    private TextView modeLabel;
    private TextView modeAirp;
    private TextView modeHumidity;
    private TextView modeLux;
    private TextView modeTemp;
    private TextView modeVolume;

    private ImageView editBtn;
    private ImageView deleteBtn;

    public ModeLayoutFragment(SettingsLayoutFragment parentFragment, MainActivity Owner, BaseViewModel viewModel, Mode currentMode) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
        _currentMode = currentMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_modelayout, container, false);
        //Context context = getActivity().getApplicationContext();

        setUpView();
        setUpElements();

        editBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //test
                        Log.d("CLICKED", "edit button");

                        Log.i("info", _currentMode.getTitle());

                       // _owner.OpenEditModeFragment(_view, _currentMode);
                        OpenEditModeFragment(_currentMode, _parentFragment);
                    }
                }
        );

        deleteBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //test
                        Log.d("CLICKED", "delete button");

                        _parentFragment.deleteMode(_currentMode);

                        _owner.OpenSettingsFragment(_view);
                    }
                }
        );

        return _view;
    }

    private void setUpView() {

        modeLabel = _view.findViewById(R.id.modeLabel);
        modeAirp = _view.findViewById(R.id.modeAirp);
        modeHumidity = _view.findViewById(R.id.modeHum);
        modeLux = _view.findViewById(R.id.modeLux);
        modeTemp = _view.findViewById(R.id.modeTemp);
        modeVolume = _view.findViewById(R.id.modeVolume);
        editBtn = _view.findViewById(R.id.editBtn);
        deleteBtn = _view.findViewById(R.id.deleteBtn);

    }

    private void setUpElements(){

        modeLabel.setText(_currentMode.getTitle());
        modeAirp.setText(_currentMode.getAirp());
        modeHumidity.setText(_currentMode.getHumidity());
        modeLux.setText(_currentMode.getLux());
        modeTemp.setText(_currentMode.getTemp());
        modeVolume.setText(_currentMode.getVolume());

    }

    //Open edit mode fragment
    public void OpenEditModeFragment(Mode cMode, SettingsLayoutFragment settingsFragment) {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        EditModeLayoutFragment = new EditModeLayoutFragment(this,_owner,_baseViewModel, cMode, settingsFragment);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard,EditModeLayoutFragment).commit();
        _callback = EditModeLayoutFragment;
    }

    @Override
    public void zoneIsSelected() {
        if (_dashboardFragment._backSeatSelected || _dashboardFragment._driverSeatSelected || _dashboardFragment._frontSeatSelected) {

        } else {

        }
    }


}