package com.example.aptiv.ViewModel;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.Model.Service.WeatherService;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.Model.Service.AptivService;


public class BaseViewModel implements IVolleyCollback {

    private AptivService _aptivService;
    private WeatherService _weatherService;
    private MainActivity _activity;
    public Zone DriverZone = new Zone("0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public Zone PassengerZone = new Zone("0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public Zone BackseatZone = new Zone("0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public Zone MiddleZone = new Zone("0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public Profile DriverProfile = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
    public Profile PassengerProfile = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
    public Profile BackProfile = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
    public Double OutTempreture = 0.0;
    public Boolean tempType; //True here means that it is Fahrenheit
    private String fahrenheit;
    private String celsius;

    public BaseViewModel(MainActivity activity){
        _activity = activity;

        _aptivService = new AptivService(_activity);
        _weatherService = new WeatherService(_activity);
        fahrenheit = "° F";
        celsius = "° C";
        tempType = false;
        _weatherService.GetWeather(this);

        UpdateData();
    }

    public void UpdateData() {
        _aptivService.GetAverageReadings(this);
        _aptivService.GetBackseatReadings(this);
        _aptivService.GetDriverReadings(this);
        _aptivService.GetPassengerReadings(this);
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

    public String getFahrenheit(){
        return fahrenheit;
    }

    public Boolean getTempType(){
        return tempType;
    }
    public String getCelsius(){
        return celsius;
    }


}