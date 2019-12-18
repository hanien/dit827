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
    public static String SoundValue = "0";

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
        _aptivService.GetDriverReadings(this);
        _aptivService.GetPassengerReadings(this);
        _aptivService.GetBackseatReadings(this);
    }

    @Override
    public void GetDriverReadings(Zone value) {
        DriverZone = value;
        DriverZone.setName(Zone.ZoneName.DRIVER);
        DriverZone.setSound(SoundValue);
        Zone temp =  getAVG(value);
        _profileHandler.onDataFetched(temp);
    }

    @Override
    public void GetPassengerReadings(Zone value) {
        PassengerZone = value;
        PassengerZone.setName(Zone.ZoneName.PASSENGER);
        PassengerZone.setSound(SoundValue);
        Zone temp =  getAVG(value);
        _profileHandler.onDataFetched(temp);
    }

    @Override
    public void GetAverageReadings(Zone value) {
        MiddleZone = value;
        MiddleZone.setName(Zone.ZoneName.MIDDLE);
        BackseatZone.setIr(MiddleZone.getIr());
        BackseatZone.setGain(MiddleZone.getGain());
        BackseatZone.setFull(MiddleZone.getFull());
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
        BackseatZone.setGain(MiddleZone.getGain());
        BackseatZone.setFull(MiddleZone.getFull());
        BackseatZone.setSound(SoundValue);
        Zone temp =  getAVG(value);
        _profileHandler.onDataFetched(temp);
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


    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private Zone getAVG(Zone z){
        Zone tempZ;
        if(MiddleZone.getTemperature() == null || Double.valueOf(MiddleZone.getTemperature()) == 0.0  &&
                MiddleZone.getHumidity() == null || Double.valueOf(MiddleZone.getHumidity()) == 0.0 &&
                MiddleZone.getGain() == null || Double.valueOf(MiddleZone.getGain()) == 0.0 &&
                MiddleZone.getLuminosity() == null || Double.valueOf(MiddleZone.getLuminosity()) == 0.0 &&
                MiddleZone.getFull() == null || Double.valueOf(MiddleZone.getFull()) == 0.0 &&
                MiddleZone.getIr() == null || Double.valueOf(MiddleZone.getIr()) == 0.0 &&
                MiddleZone.getPressure() == null || Double.valueOf(MiddleZone.getPressure()) == 0.0 &&
                MiddleZone.getSound() == null || Double.valueOf(MiddleZone.getSound()) == 0.0 &&
                MiddleZone.getAltitude() == null || Double.valueOf(MiddleZone.getAltitude()) == 0.0 &&
                MiddleZone.getLight() == null || Double.valueOf(MiddleZone.getLight()) == 0.0 &&
                MiddleZone.getLux() == null || Double.valueOf(MiddleZone.getLux()) == 0.0 ){
                return z;
        }
        double temperature = round((Double.valueOf(z.getTemperature()) + Double.valueOf(MiddleZone.getTemperature())) / 2,1);
        double humidity = round((Double.valueOf(z.getHumidity()) + Double.valueOf(MiddleZone.getHumidity())) / 2,1);
        double gain = round((Double.valueOf(z.getGain()) + Double.valueOf(MiddleZone.getGain())) / 2,1);
        double luminosity = round((Double.valueOf(z.getLuminosity()) + Double.valueOf(MiddleZone.getLuminosity())) / 2,1);
        double full = round((Double.valueOf(z.getFull()) + Double.valueOf(MiddleZone.getFull())) / 2,1);
        double ir = round((Double.valueOf(z.getIr()) + Double.valueOf(MiddleZone.getIr())) / 2,1);
        double pressure = round((Double.valueOf(z.getPressure()) + Double.valueOf(MiddleZone.getPressure())) / 2,1);
        double sound = round((Double.valueOf(z.getSound()) + Double.valueOf(MiddleZone.getSound())) / 2,1);
        double altitude = round((Double.valueOf(z.getAltitude()) + Double.valueOf(MiddleZone.getAltitude())) / 2,1);
        double light = round((Double.valueOf(z.getLight()) + Double.valueOf(MiddleZone.getLight())) / 2,1);
        double lux = round((Double.valueOf(z.getLux()) + Double.valueOf(MiddleZone.getLux())) / 2,1);

        if(z.getName() == Zone.ZoneName.DRIVER){
            tempZ = new Zone(Zone.ZoneName.DRIVER,String.valueOf(temperature),String.valueOf(humidity),String.valueOf(gain),String.valueOf(luminosity),String.valueOf(full),String.valueOf(ir),String.valueOf(pressure),String.valueOf(sound),String.valueOf(altitude),String.valueOf(light),String.valueOf(lux));
        }
        else if(z.getName() == Zone.ZoneName.PASSENGER){
            tempZ = new Zone(Zone.ZoneName.PASSENGER ,String.valueOf(temperature),String.valueOf(humidity),String.valueOf(gain),String.valueOf(luminosity),String.valueOf(full),String.valueOf(ir),String.valueOf(pressure),String.valueOf(sound),String.valueOf(altitude),String.valueOf(light),String.valueOf(lux));
        }
        else{
            tempZ = new Zone(Zone.ZoneName.BACK ,String.valueOf(temperature),String.valueOf(humidity),String.valueOf(gain),String.valueOf(luminosity),String.valueOf(full),String.valueOf(ir),String.valueOf(pressure),String.valueOf(sound),String.valueOf(altitude),String.valueOf(light),String.valueOf(lux));
        }
        return tempZ;
    }
}