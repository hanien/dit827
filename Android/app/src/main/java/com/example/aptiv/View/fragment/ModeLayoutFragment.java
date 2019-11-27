package com.example.aptiv.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Mode;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ModeLayoutFragment extends Fragment{

    private MainActivity _owner;
    private SettingsLayoutFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private EditModeLayoutFragment EditModeLayoutFragment;

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

        setUpView();
        setUpElements();
        RegisterListener();

        return _view;
    }

    private void RegisterListener() {
        editBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        OpenEditModeFragment(_currentMode, _parentFragment);
                    }
                }
        );

        deleteBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        _parentFragment.deleteMode(_currentMode);
                        _owner.OpenSettingsFragment(_view);
                    }
                }
        );
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
    }

}