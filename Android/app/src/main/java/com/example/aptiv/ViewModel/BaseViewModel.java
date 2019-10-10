package com.example.aptiv.ViewModel;

import android.renderscript.Sampler;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.Model.Service.AptivService;

import java.util.List;

public class BaseViewModel implements IVolleyCollback {

    private AptivService _aptiveService;
    private MainActivity _activity;
    public Zone DriverZone;
    public Zone PassengerZone;
    public Zone BackseatZone;
    public Zone MiddleZone;

    public BaseViewModel(MainActivity activity){
        _activity = activity;
        _aptiveService = new AptivService(_activity);
        UpdateData();
    }

    public void UpdateData() {
        _aptiveService.GetAverageReadings(this);
        _aptiveService.GetBackseatReadings(this);
        _aptiveService.GetDriverReadings(this);
        _aptiveService.GetPassengerReadings(this);
    }

    @Override
    public void GetDriverReadings(Zone value) {
        DriverZone = value;
    }

    @Override
    public void GetPassengerReadings(Zone value) {
        PassengerZone = value;
    }

    @Override
    public void GetAverageReadings(Zone value) {
        MiddleZone = value;
    }

    @Override
    public void GetBackseatReadings(Zone value) {
        BackseatZone = value;
    }
}
