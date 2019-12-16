package com.example.aptiv.ViewModel;

import com.example.aptiv.Model.Class.Profile;
import com.example.aptiv.Model.Class.Zone;
import com.example.aptiv.Model.Interface.IVolleyCallback;
import com.example.aptiv.Model.Service.WeatherService;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.Model.Service.AptivService;
import com.example.aptiv.Model.Helper.ProfileHandler;
import com.example.aptiv.View.fragment.DashboardFragment;

import java.sql.Driver;


public class BaseViewModel implements IVolleyCallback {

    private AptivService _aptivService;
    private WeatherService _weatherService;
    private MainActivity _activity;
    private ProfileHandler _profileHandler;
    private DashboardFragment _dashboardFragment;

    //enum to differentiate between incoming zones
    public static Zone DriverZone = new Zone(Zone.ZoneName.DRIVER, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
    public static Zone PassengerZone = new Zone(Zone.ZoneName.PASSENGER, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
    public static Zone BackseatZone = new Zone(Zone.ZoneName.BACK, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
    public static Zone MiddleZone = new Zone(Zone.ZoneName.MIDDLE, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

    public static Profile DriverProfile = new Profile(null, null, null, null, null, null, null, null, null, null, null, null);
    public static Profile PassengerProfile = new Profile(null, null, null, null, null, null, null, null, null, null, null, null);
    public static Profile BackProfile = new Profile(null, null, null, null, null, null, null, null, null, null, null, null);

    public Double OutTemperature = 0.0;
    public Boolean isMuted = false;
    public Boolean tempType; //True here means that it is Fahrenheit
    private String fahrenheit;
    private String celsius;
    private String SoundValue;
    public BaseViewModel(MainActivity activity) {
        _activity = activity;

        _aptivService = new AptivService(_activity);
        _weatherService = new WeatherService(_activity);
        _profileHandler = new ProfileHandler(this, _dashboardFragment, DriverZone, PassengerZone,
                MiddleZone, BackseatZone);

        fahrenheit = "° F";
        celsius = "° C";
        tempType = false;
        _weatherService.GetWeather(this);

    }

    public void onProfileChange() {
        //_profileHandler.onProfileChange();
    }

    public void SetDashboardFragment(DashboardFragment fragment) {
        _dashboardFragment = fragment;
        _profileHandler.SetDashboardFragment(fragment);
    }

    public void InitData(){
        DriverProfile.setFromZone(DriverZone);
        PassengerProfile.setFromZone(PassengerZone);
        BackProfile.setFromZone(BackseatZone);
    }

    public void UpdateData() {
        _aptivService.GetAverageReadings(this);
        _aptivService.GetBackseatReadings(this);
        _aptivService.GetDriverReadings(this);
        _aptivService.GetPassengerReadings(this);
        if(DriverProfile.getTemperature() == null && DriverZone.getTemperature() != "0")
        {
            /*Very clunky fix that works around profiles not being initialized to current values on startup
            *Reason for clunkiness is that it only works at a specified point on startup
            *that was still not identified, as well as the server fetching only null values on first
            *fetch.*/
            InitData();
        }
    }

    @Override
    public void GetDriverReadings(Zone value) {
        DriverZone = value;
        DriverZone.setName(Zone.ZoneName.DRIVER);
        DriverZone.setSound(SoundValue);
        _profileHandler.onDataFetched(value);
    }

    @Override
    public void GetPassengerReadings(Zone value) {
        PassengerZone = value;
        PassengerZone.setName(Zone.ZoneName.PASSENGER);
        PassengerZone.setSound(SoundValue);
        _profileHandler.onDataFetched(value);
    }

    @Override
    public void GetAverageReadings(Zone value) {
        MiddleZone = value;
        MiddleZone.setName(Zone.ZoneName.MIDDLE);
        BackseatZone.setIr(MiddleZone.getIr());
        MiddleZone.setSound(SoundValue);
        _profileHandler.onDataFetched(value);
    }

    @Override
    public void OutTemperature(double temp) {
        OutTemperature = temp;
    }

    @Override
    public void GetBackseatReadings(Zone value) {
        BackseatZone = value;
        BackseatZone.setName(Zone.ZoneName.BACK);
        BackseatZone.setIr(MiddleZone.getIr());
        BackseatZone.setSound(SoundValue);
        _profileHandler.onDataFetched(value);
    }

    public String getFahrenheit() {
        return fahrenheit;
    }

    public Boolean getTempType() {
        return tempType;
    }

    public String getCelsius() {
        return celsius;
    }

    public void SetValues(String value) {
        SoundValue = value;
    }
}