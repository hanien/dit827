package com.example.aptiv.ViewModel;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.Model.Service.WeatherService;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.Model.Service.AptivService;
import com.example.aptiv.Model.Helper.ProfileHandler;
import com.example.aptiv.View.fragment.DashboardFragment;


public class BaseViewModel implements IVolleyCollback {

    private AptivService _aptivService;
    private WeatherService _weatherService;
    private MainActivity _activity;
    private ProfileHandler _profileHandler;
    private DashboardFragment _dashboardFragment;

    //enum to differentiate btwn incoming zones
    public static Zone DriverZone = new Zone(Zone.ZoneName.DRIVER, "0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public static Zone PassengerZone = new Zone(Zone.ZoneName.PASSENGER, "0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public static Zone BackseatZone = new Zone(Zone.ZoneName.BACK, "0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");
    public static Zone MiddleZone = new Zone(Zone.ZoneName.MIDDLE, "0", "0", "0", "0", "0", "0", "0", "0","0", "0","0");

    public Profile DriverProfile = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
    public Profile PassengerProfile = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);
    public Profile BackProfile = new Profile(null,null,null,null,null,null,null,null,null,null,null,null);

    public Double OutTempreture = 0.0;
    public Boolean isMuted = false;
    public Boolean tempType; //True here means that it is Fahrenheit
    private String fahrenheit;
    private String celsius;

    public BaseViewModel(MainActivity activity){
        _activity = activity;

        _aptivService = new AptivService(_activity);
        _weatherService = new WeatherService(_activity);
        _profileHandler = new ProfileHandler(this, _dashboardFragment, DriverZone, PassengerZone,
                                                MiddleZone, BackseatZone);

        fahrenheit = "° F";
        celsius = "° C";
        tempType = false;
        _weatherService.GetWeather(this);

        UpdateData();
        DriverProfile.setFromZone(DriverZone);
        PassengerProfile.setFromZone(PassengerZone);
        BackProfile.setFromZone(BackseatZone);
    }

    public void onProfileChange(){
        //_profileHandler.onProfileChange();
    }
    public void SetDashboardFragment(DashboardFragment fragment){
        _dashboardFragment = fragment;
        _profileHandler.SetDashboardFragment(fragment);
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
        DriverZone.setName(Zone.ZoneName.DRIVER);
        _profileHandler.onDataFetched(value);
    }

    @Override
    public void GetPassengerReadings(Zone value) {
        PassengerZone = value;
        PassengerZone.setName(Zone.ZoneName.PASSENGER);
        _profileHandler.onDataFetched(value);
    }

    @Override
    public void GetAverageReadings(Zone value) {
        MiddleZone = value;
        MiddleZone.setName(Zone.ZoneName.MIDDLE);
        BackseatZone.setIr(MiddleZone.getIr());
        _profileHandler.onDataFetched(value);
    }

    @Override
    public void OutTempreture(double temp) {
        OutTempreture = temp;
    }

    @Override
    public void GetBackseatReadings(Zone value) {
        BackseatZone = value;
        BackseatZone.setName(Zone.ZoneName.BACK);
        BackseatZone.setIr(MiddleZone.getIr());
        _profileHandler.onDataFetched(value);
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