package com.example.aptiv.View.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aptiv.Model.Classe.Mode;
import android.widget.TextView;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.View.adapter.CustomListAdapter;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SettingsLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;
    private ModeLayoutFragment ModeLayoutFragment;
    private AddModeLayoutFragment AddModeLayoutFragment;
    private IZoneSelection _callback;

    private ListView listView;
    private CustomListAdapter mAdapter;

    ArrayList<Mode> modesList;

    public SettingsLayoutFragment(DashboardFragment parentFragment, MainActivity Owner, BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_settingslayout, container, false);
        Context context = getActivity().getApplicationContext();

        setUpView();
        loadData(context);

        mAdapter = new CustomListAdapter(context, modesList, _owner, _view);
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return _view;
    }

    private void setUpView() {
        listView = (ListView) _view.findViewById(R.id.modes_list);
    }

    private void createList() {
        modesList.add(new Mode("Date mode", "1111", "12", "4", "29", "21"));
        modesList.add(new Mode("Reading mode", "2222", "22", "2", "22", "22"));
        modesList.add(new Mode("Party mode", "1111", "12", "4", "29", "21"));
        modesList.add(new Mode("Summer mode", "3333", "11", "33", "11", "11"));
        modesList.add(new Mode("Winter mode", "4444", "26", "13", "25", "25"));
        modesList.add(new Mode("Spring mode", "1111", "12", "4", "29", "21"));
        modesList.add(new Mode("Fall mode", "1111", "12", "4", "29", "21"));

        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(modesList);
        editor.putString("mode list", json);
        editor.apply();
    }

    private void loadData(Context context) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("mode list", null);
        Type type = new TypeToken<ArrayList<Mode>>() {
        }.getType();
        modesList = gson.fromJson(json, type);

        if (modesList == null) {
            modesList = new ArrayList<>();
            createList();
        }

    }

    //Open mode fragment
    public void OpenModeFragment(Mode _currentMode) {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        ModeLayoutFragment = new ModeLayoutFragment(this, _owner, _baseViewModel, _currentMode);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, ModeLayoutFragment).commit();
        _callback = ModeLayoutFragment;
    }

    //Open add mode fragment
    public void OpenAddModeFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        AddModeLayoutFragment = new AddModeLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, AddModeLayoutFragment).commit();
        _callback = AddModeLayoutFragment;
    }

    public void addNewMode(String title, String lux, String temp, String volume, String airp, String humidity) {
        modesList.add(new Mode(title, lux, temp, volume, airp, humidity));
        saveData();
    }

    public void editMode(Mode currentMode, String title, String lux, String temp, String volume, String airp, String humidity) {
        for (Mode mode : modesList) {
            if (mode.getTitle().equals(currentMode.getTitle())) {
                mode.setTitle(title);
                mode.setLux((lux));
                mode.setTemp(temp);
                mode.setVolume(volume);
                mode.setAirp(airp);
                mode.setHumidity(humidity);
                break;
            }
        }
        saveData();
    }

    public void deleteMode(Mode currentMode) {

        for (Mode mode : modesList) {
            if (mode.getTitle().equals(currentMode.getTitle())) {
                modesList.remove(mode);
                break;
            }
        }

        saveData();

    }

    @Override
    public void zoneIsSelected() {
        if (_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected) {

        } else {

        }
    }
}