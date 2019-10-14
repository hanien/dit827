package com.example.aptiv.ViewModel;

import android.renderscript.Sampler;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.Model.Service.WeatherService;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.Model.Service.AptivService;

import java.util.List;

public class BaseViewModel implements IVolleyCollback {

    private AptivService _aptivService;
    private WeatherService _weatherService;
    private MainActivity _activity;
    public Zone DriverZone;
    public Zone PassengerZone;
    public Zone BackseatZone;
    public Zone MiddleZone;
    public Double OutTempreture;

    public BaseViewModel(MainActivity activity){
        _activity = activity;

        _aptivService = new AptivService(_activity);
        _weatherService = new WeatherService(_activity);

        UpdateData();
    }

    public void UpdateData() {
        _aptivService.GetAverageReadings(this);
        _aptivService.GetBackseatReadings(this);
        _aptivService.GetDriverReadings(this);
        _aptivService.GetPassengerReadings(this);
        _weatherService.GetWeather(this);

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
    public void OutTempreture(double temp) {
        OutTempreture = temp;
    }

    @Override
    public void GetBackseatReadings(Zone value) {
        BackseatZone = value;
    }
}
