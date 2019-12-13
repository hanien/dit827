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
import com.example.aptiv.Model.Class.Profile;
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
                Profile tempProfile = new Profile(null, null, null, null, null, null, null, null, null, null, null, null);
                setValues(tempProfile,currentMode);

                if (!DriverSeat && !PassengerSeat && !BackSeat) {
                    _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Sorry, please select a zone that you want to apply the mode on", false, null);
                } else {
                    if (DriverSeat && PassengerSeat && BackSeat) {
                        setValues(_viewModel.DriverProfile, currentMode);
                        setValues(_viewModel.PassengerProfile, currentMode);
                        setValues(_viewModel.BackProfile, currentMode);
                    }
                    else if (DriverSeat && PassengerSeat) {
                        if (!_handler.ZonesValueHandler(tempProfile, _viewModel.BackseatZone)) {
                            _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Can't change values for this profile because it's overlapping in the back seat!", true, currentMode);
                        } else {
                            setValues(_viewModel.DriverProfile, currentMode);
                            setValues(_viewModel.PassengerProfile, currentMode);
                        }
                    }
                    else if (PassengerSeat && BackSeat) {
                        if (!_handler.ZonesValueHandler(tempProfile, _viewModel.DriverZone)) {
                            _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Can't change values for this profile because it's overlapping in the driver seat!", true, currentMode);
                        } else {
                            setValues(_viewModel.PassengerProfile, currentMode);
                            setValues(_viewModel.BackProfile, currentMode);
                        }
                    }
                    else if(DriverSeat && BackSeat){
                        if (!_handler.ZonesValueHandler(tempProfile, _viewModel.PassengerZone) ) {
                            _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Can't change values for this profile because it's overlapping in the passenger seat!", true, currentMode);
                        } else {
                            setValues(_viewModel.DriverProfile, currentMode);
                        }
                    }
                    else if (DriverSeat) {
                        if (!_handler.ZonesValueHandler(tempProfile, _viewModel.PassengerZone) && !_handler.ZonesValueHandler(tempProfile, _viewModel.BackseatZone)) {
                            _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Can't change values for this profile because it's overlapping in the passenger or driver seat!", true, currentMode);
                        } else {
                            setValues(_viewModel.DriverProfile, currentMode);
                        }
                    }
                    else if (PassengerSeat) {
                        if (!_handler.ZonesValueHandler(tempProfile, _viewModel.DriverZone) && !_handler.ZonesValueHandler(tempProfile, _viewModel.BackseatZone)) {
                            _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Can't change values for this profile because it's overlapping in the driver or back seat!", true, currentMode);
                        } else {
                            setValues(_viewModel.PassengerProfile, currentMode);
                        }
                    }
                    else if (BackSeat) {
                        if (!_handler.ZonesValueHandler(tempProfile, _viewModel.PassengerZone) && !_handler.ZonesValueHandler(tempProfile, _viewModel.DriverZone)) {
                            _parentFragment.CreatePopupView(DriverSeat, PassengerSeat, BackSeat, "Can't change values for this profile because it's overlapping in the passenger or driver seat!", true, currentMode);
                        } else {
                            setValues(_viewModel.BackProfile, currentMode);
                        }
                    }
                }

            }
        });

        return listView;
    }

    private void setValues(Profile p , Mode m){
        p.setIr(m.getLux().isEmpty() ?  null : m.getLux() );
        p.setTemperature(m.getTemp().isEmpty() ?  null :m.getTemp());
        p.setSound(m.getVolume().isEmpty() ?  null : m.getVolume());
        p.setPressure(m.getAirp().isEmpty() ?  null : m.getAirp());
        p.setHumidity(m.getHumidity().isEmpty() ?  null : m.getHumidity());
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