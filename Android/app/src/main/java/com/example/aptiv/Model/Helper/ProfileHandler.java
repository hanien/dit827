package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.example.aptiv.ViewModel.BaseViewModel;

import java.lang.ref.PhantomReference;
import java.sql.Driver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class ProfileHandler {
    private BaseViewModel _base;
    private DashboardFragment _dashboardFragment;
    private Zone _driver, _passenger, _mid, _back;
    private Queue<Map> _driveSample, _passSample, _midSample, _backSample;
    private HashMap<String, Double> _driveSum, _passSum, _midSum, _backSum;

    public ProfileHandler(BaseViewModel base, DashboardFragment dashboardFragment,
                          Zone driver, Zone passenger, Zone mid, Zone back) {
        _base = base;
        _dashboardFragment = dashboardFragment;
        _driver = driver;
        _passenger = passenger;
        _mid = mid;
        _back = back;
        _driveSample = new LinkedList<>();
        _passSample = new LinkedList<>();
        _midSample = new LinkedList<>();
        _backSample = new LinkedList<>();
        _driveSum = initEmptyMap();
        _passSum = initEmptyMap();
        _midSum = initEmptyMap();
        _backSum = initEmptyMap();

    }

    public void SetDashboardFragment(DashboardFragment fragment) {
        _dashboardFragment = fragment;
    }

    public void onDataFetched(Zone zone) {
        switch(zone.getName()){
            case DRIVER:
                _driver = zone;
                break;
            case PASSENGER:
                _passenger = zone;
                break;
            case MIDDLE:
                _mid = zone;
                break;
            case BACK:
                _back = zone;
                break;
        }
        sampleZone(getProfileOfZone(zone), zone);
    }


    public void alignToZone(Profile profile, Zone zone){

    }

    public void sampleZone(Profile profile, Zone zone) {
        Queue<Map> currentQueue = null;
        HashMap<String, Double> currentSum = null;

        switch(zone.getName()){
            case DRIVER:
                currentQueue = _driveSample;
                currentSum = _driveSum;
                break;
            case PASSENGER:
                currentQueue = _passSample;
                currentSum = _passSum;
                break;
            case MIDDLE:
                currentQueue = _midSample;
                currentSum = _midSum;
                break;
            case BACK:
                currentQueue = _backSample;
                currentSum = _backSum;
                break;
        }

        //if bad: make red
        //TODO: fix naming conventions a little
        if(!ProfileHelper.sampleZone(profile, zone, currentQueue, currentSum)){
            _dashboardFragment.toggleError(zone, Boolean.TRUE);
            _dashboardFragment.CreatePopupView(
                    zone.getName() == Zone.ZoneName.DRIVER,
                    zone.getName() == Zone.ZoneName.PASSENGER,
                    zone.getName() == Zone.ZoneName.BACK,
                    "dummy message", true
            );
        }
        else{
            _dashboardFragment.toggleError(zone, Boolean.FALSE);
        }
    }
/*
    private boolean checkTempLevel() {

        String  DriverTempLevel = _base.DriverZone.getTemperature();
        String  PassangerTempLevel = _base.DriverZone.getTemperature();
        String  BackTempLevel = _base.DriverZone.getTemperature();

        boolean DriverCheck = compareThreshold(Double.parseDouble(_base.DriverProfile.getTemperature()), Double.parseDouble(DriverTempLevel),t_temp);
        boolean PassangerCheck = compareThreshold(Double.parseDouble(_base.PassengerProfile.getTemperature()), Double.parseDouble(PassangerTempLevel),t_temp);
        boolean BackCheck = compareThreshold(Double.parseDouble(_base.BackProfile.getTemperature()), Double.parseDouble(BackTempLevel),t_temp);


        return DriverCheck && PassangerCheck && BackCheck;

    }

    private boolean checkSoundLevel() {

        String  DriverSoundLevel = _base.DriverZone.getSound();
        String  PassangerSoundLevel = _base.PassengerZone.getSound();
        String  BackSoundLevel = _base.BackseatZone.getSound();

        boolean DriverCheck = compareThreshold(Double.parseDouble(_base.DriverProfile.getSound()), Double.parseDouble(DriverSoundLevel),t_sound);
        boolean PassangerCheck = compareThreshold(Double.parseDouble(_base.PassengerProfile.getSound()), Double.parseDouble(PassangerSoundLevel),t_sound);
        boolean BackCheck = compareThreshold(Double.parseDouble(_base.BackProfile.getSound()), Double.parseDouble(BackSoundLevel),t_sound);


        return DriverCheck && PassangerCheck && BackCheck;

    }
*/

    public boolean ZonesValueHandler(Profile profile, Zone zone) {
        if(profile == null || zone == null){
            return false;
        }
        HashMap<String, Boolean> check = checkProfile(profile,zone);

        if (check.containsValue(Boolean.FALSE)) {


          return false;


        } else {

            ChangeZoneValues(profile, zone);
            return true;
        }
    }


    private HashMap<String, Boolean> checkProfile(Profile profile, Zone zone) {

        HashMap<String, Boolean> checkedProfile = new HashMap<>(11);


        checkedProfile.put("temperature", compareZonesAndProfile(zone.getTemperature(),profile.getTemperature()));

        checkedProfile.put("humidity", compareZonesAndProfile(zone.getHumidity(), profile.getHumidity()));

        checkedProfile.put("gain", compareZonesAndProfile(zone.getGain(), profile.getGain()));

        checkedProfile.put("luminosity", compareZonesAndProfile(zone.getLuminosity(), profile.getLuminosity()));

        checkedProfile.put("full", compareZonesAndProfile(zone.getFull(),profile.getFull()));

        checkedProfile.put("ir", compareZonesAndProfile(zone.getIr(),profile.getIr() ));

        checkedProfile.put("pressure", compareZonesAndProfile(zone.getPressure(),profile.getPressure()));

        checkedProfile.put("sound", compareZonesAndProfile(zone.getSound(),profile.getSound() ));

        checkedProfile.put("light", compareZonesAndProfile(zone.getLight(),profile.getLight()));

        checkedProfile.put("lux", compareZonesAndProfile(zone.getLux(), profile.getLux()));

        return checkedProfile;
    }

    private boolean compareZonesAndProfile(String zoneValue, String ProfileValue) {

        boolean result;

        if (ProfileValue == null || zoneValue == null){
            result = true;
        } else {

             result = (Double.parseDouble(ProfileValue) < (Double.parseDouble(zoneValue) + 5))
                    && (Double.parseDouble(ProfileValue) > (Double.parseDouble(zoneValue) - 5));
        }

      //  boolean result = (Double.parseDouble(zoneValue) == Double.parseDouble(ProfileValue));

        return result;
    }

    private void ChangeZoneValues(Profile profile, Zone zone) {

    zone.setTemperature(profile.getTemperature());
    zone.setHumidity(profile.getHumidity());
    zone.setGain(profile.getGain());
    zone.setLuminosity(profile.getLuminosity());
    zone.setFull(profile.getFull());
    zone.setIr(profile.getIr());
    zone.setPressure(profile.getPressure());
    zone.setSound(profile.getSound());
    zone.setLight(profile.getLight());
    zone.setLux(profile.getLux());

    }

    private HashMap<String, Double> initEmptyMap(){
        HashMap<String, Double> map = new HashMap<>(11);
        map.put("temperature", 0.0);
        map.put("humidity", 0.0);
        map.put("gain", 0.0);
        map.put("luminosity", 0.0);
        map.put("full", 0.0);
        map.put("ir", 0.0);
        map.put("pressure", 0.0);
        map.put("sound", 0.0);
        map.put("altitude", 0.0);
        map.put("light", 0.0);
        map.put("lux", 0.0);

        return map;
    }

    public Profile getProfileOfZone(Zone zone){
        switch(zone.getName())
        {
            case DRIVER:
                return _base.DriverProfile;
            case PASSENGER:
                return _base.PassengerProfile;
            case BACK:
                return _base.BackProfile;
            default:
                //TODO: add handling of invalid zone
                return null;
        }
    }
}

