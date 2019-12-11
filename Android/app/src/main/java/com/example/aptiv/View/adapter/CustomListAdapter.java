package com.example.aptiv.View.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aptiv.Model.Class.Mode;
import com.example.aptiv.Model.Helper.ProfileHandler;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.example.aptiv.ViewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Mode> {

    private Context mContext;
    private DashboardFragment _parentFragment;
    private List<Mode> modeList;
    private BaseViewModel _viewModel;
    MainActivity _owner;
    View _view;
    private int selectedPosition;
    private ProfileHandler _handler;

    public CustomListAdapter(@NonNull Context context, ArrayList<Mode> list, MainActivity Owner, View View, DashboardFragment parentFragment, BaseViewModel viewModel) {
        super(context, 0, list);
        mContext = context;
        modeList = list;
        _owner = Owner;
        _view = View;
        _parentFragment = parentFragment;
        _viewModel = viewModel;
        _handler = new ProfileHandler(_viewModel, _parentFragment, _viewModel.DriverZone, _viewModel.PassengerZone, _viewModel.MiddleZone, _viewModel.BackseatZone);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;

        if (listView == null)
            listView = LayoutInflater.from(mContext).inflate(R.layout.list_mode, parent, false);

        final Mode currentMode = modeList.get(position);
        TextView title = listView.findViewById(R.id.txtTitle);
        title.setText(currentMode.getTitle());

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _owner.OpenModeFragment(_view, currentMode);
            }
        });

        loadData();
        RadioButton radioButton = listView.findViewById(R.id.radiobutton);
        radioButton.setChecked(position == selectedPosition);
        radioButton.setTag(position);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
                saveData();

                boolean DriverSeat = _parentFragment == null ? false : _parentFragment._driverSeatSelected;
                boolean PassengerSeat = _parentFragment == null ? false : _parentFragment._frontSeatSelected;
                boolean BackSeat = _parentFragment == null ? false : _parentFragment._backSeatSelected;
                if (!DriverSeat && !PassengerSeat && !BackSeat) {
                    _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Sorry, please select a zone that you want to apply the mode on", false);
                } else {
                    if (DriverSeat) {
                        //check differences for every variable
                        //if all check out, set new profile

                        /*
                        _viewModel.DriverProfile.setIr(currentMode.getLux());
                        _viewModel.DriverProfile.setTemperature(currentMode.getTemp());
                        _viewModel.DriverProfile.setSound(currentMode.getVolume());
                        _viewModel.DriverProfile.setPressure(currentMode.getAirp());
                        _viewModel.DriverProfile.setHumidity(currentMode.getHumidity());
                        */

                        _viewModel.DriverProfile.setIr("1");
                        _viewModel.DriverProfile.setTemperature("1");
                        _viewModel.DriverProfile.setSound("1");
                        _viewModel.DriverProfile.setPressure("1");
                        _viewModel.DriverProfile.setHumidity("1");

                        if (!_handler.ZonesValueHandler(_viewModel.DriverProfile, _viewModel.DriverZone)) {
                            _parentFragment.CreatePopupView(false, false, false, "Can't change values for this profile because it's overlapping!", false);
                        }
                    }
                    if (PassengerSeat) {
                   /*
                        _viewModel.PassengerProfile.setIr(currentMode.getLux());
                        _viewModel.PassengerProfile.setTemperature(currentMode.getTemp());
                        _viewModel.PassengerProfile.setSound(currentMode.getVolume());
                        _viewModel.PassengerProfile.setPressure(currentMode.getAirp());
                        _viewModel.PassengerProfile.setHumidity(currentMode.getHumidity());

                    */

                        _viewModel.PassengerProfile.setIr("1");
                        _viewModel.PassengerProfile.setTemperature("1");
                        _viewModel.PassengerProfile.setSound("1");
                        _viewModel.PassengerProfile.setPressure("1");
                        _viewModel.PassengerProfile.setHumidity("1");

                        if (!_handler.ZonesValueHandler(_viewModel.PassengerProfile, _viewModel.PassengerZone)) {
                            _parentFragment.CreatePopupView(false, false, false, "Can't change values for this profile because it's overlapping!", false);
                        }
                    }
                    if (BackSeat) {
                        /*
                        _viewModel.BackProfile.setIr(currentMode.getLux());
                        _viewModel.BackProfile.setTemperature(currentMode.getTemp());
                        _viewModel.BackProfile.setSound(currentMode.getVolume());
                        _viewModel.BackProfile.setPressure(currentMode.getAirp());
                        _viewModel.BackProfile.setHumidity(currentMode.getHumidity());
                        */

                        _viewModel.BackProfile.setIr("1");
                        _viewModel.BackProfile.setTemperature("1");
                        _viewModel.BackProfile.setSound("1");
                        _viewModel.BackProfile.setPressure("1");
                        _viewModel.BackProfile.setHumidity("1");

                        if (!_handler.ZonesValueHandler(_viewModel.BackProfile, _viewModel.BackseatZone)) {
                            _parentFragment.CreatePopupView(false, false, false, "Can't change values for this profile because it's overlapping!", false);
                        }
                    }
                }
            }
        });

        return listView;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selected_pos", selectedPosition);
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        selectedPosition = sharedPreferences.getInt("selected_pos", 0);
    }
}