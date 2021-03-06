package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Class.Profile;
import com.example.aptiv.Model.Class.Zone;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.example.aptiv.ViewModel.BaseViewModel;

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
    private Zone _oldDriverZone , _oldPassengerZone , _oldbackZone;

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

    private int driveCount = 0;
    private int passCount = 0;
    private int backCount = 0;

    public void onDataFetched(Zone zone) {
        switch (zone.getName()) {
            case DRIVER:
                _driver = zone;
                sampleZone(_base.DriverProfile, zone);
                if(_oldDriverZone == null){
                    _oldDriverZone = zone;
                }else{
                    HashMap<String, Boolean> hasError = ProfileHelper.checkZoneDifference(_driver, _oldDriverZone);
                    if(hasError.containsValue(Boolean.FALSE)){
                        driveCount++;
                    }
                    else{
                        driveCount = 0;
                    }
                    if(driveCount > 1){
                        CreateNotificationForZoneDifference(true,false,false,hasError,_driver,_oldDriverZone);
                        driveCount = 0;
                    }
                    if(driveCount == 0){
                        _oldDriverZone = _driver;
                    }
                }
                break;
            case PASSENGER:
                _passenger = zone;
                sampleZone(_base.PassengerProfile, zone);
                if(_oldPassengerZone == null){
                    _oldPassengerZone = zone;
                }else{
                    HashMap<String, Boolean> hasError = ProfileHelper.checkZoneDifference(_passenger, _oldPassengerZone);
                    if(hasError.containsValue(Boolean.FALSE)){
                        passCount++;
                    }else{
                        passCount = 0;
                    }
                    if(passCount > 1){
                        CreateNotificationForZoneDifference(false,true,false,hasError,_passenger,_oldPassengerZone);
                        passCount = 0;
                    }
                    if(passCount == 0){
                        _oldPassengerZone = _passenger;
                    }
                }
                break;
            case MIDDLE:
                _mid = zone;
                break;
            case BACK:
                _back = zone;
                sampleZone(_base.BackProfile, zone);
                if(_oldbackZone == null){
                    _oldbackZone = zone;
                }else{
                    HashMap<String, Boolean> hasError = ProfileHelper.checkZoneDifference(_back, _oldbackZone);
                    if(hasError.containsValue(Boolean.FALSE)){
                        backCount++;
                    }
                    else{
                        backCount=0;
                    }
                    if(backCount > 1){
                        CreateNotificationForZoneDifference(false,false,true,hasError,_passenger,_oldPassengerZone);
                        backCount=0;
                    }
                    if(backCount == 0){
                        _oldbackZone = _back;
                    }
                }
                break;
        }
    }

    private void CreateNotificationForZoneDifference(boolean Driver, boolean Passenger , boolean Back ,HashMap<String, Boolean> hasError ,Zone z ,Zone oldZ ){
        if(hasError.containsValue(Boolean.FALSE)){
            Zone z1 = z;
            Zone oldZ1 = oldZ;
            _dashboardFragment.CreateTempPopupView(Driver,Passenger,Back);
        }
    }

    public void sampleZone(Profile profile, Zone zone) {
        Queue<Map> currentQueue = null;
        HashMap<String, Double> currentSum = null;
        profile = null;
        switch (zone.getName()) {
            case DRIVER:
                currentQueue = _driveSample;
                currentSum = _driveSum;
                profile = _base.DriverProfile;
                break;
            case PASSENGER:
                currentQueue = _passSample;
                currentSum = _passSum;
                profile = _base.PassengerProfile;
                break;
            case BACK:
                currentQueue = _backSample;
                currentSum = _backSum;
                profile = _base.BackProfile;
                break;
            case MIDDLE:
                return;
        }


        //if bad: make red
        //TODO: fix naming conventions a little
        if(!ProfileHelper.sampleZone(profile, zone, currentQueue, currentSum)){
            _dashboardFragment.toggleError(zone, Boolean.TRUE);
        }
        else{
            _dashboardFragment.toggleError(zone, Boolean.FALSE);
        }
    }

    public boolean ZonesValueHandler(Profile profile, Zone zone) {
        if (profile == null || zone == null) {
            return false;
        }
        HashMap<String, Boolean> check = checkProfile(profile, zone);

        if (check.containsValue(Boolean.FALSE)) {


            return false;


        } else {

            ChangeZoneValues(profile, zone);
            return true;
        }
    }


    private HashMap<String, Boolean> checkProfile(Profile profile, Zone zone) {

        HashMap<String, Boolean> checkedProfile = new HashMap<>(11);


        checkedProfile.put("temperature", compareZonesAndProfile(zone.getTemperature(), profile.getTemperature()));

        checkedProfile.put("humidity", compareZonesAndProfile(zone.getHumidity(), profile.getHumidity()));

        checkedProfile.put("gain", compareZonesAndProfile(zone.getGain(), profile.getGain()));

        checkedProfile.put("luminosity", compareZonesAndProfile(zone.getLuminosity(), profile.getLuminosity()));

        checkedProfile.put("full", compareZonesAndProfile(zone.getFull(), profile.getFull()));

        checkedProfile.put("ir", compareZonesAndProfile(zone.getIr(), profile.getIr()));

        checkedProfile.put("pressure", compareZonesAndProfile(zone.getPressure(), profile.getPressure()));

        checkedProfile.put("sound", compareZonesAndProfile(zone.getSound(), profile.getSound()));

        checkedProfile.put("light", compareZonesAndProfile(zone.getLight(), profile.getLight()));

        checkedProfile.put("lux", compareZonesAndProfile(zone.getLux(), profile.getLux()));

        return checkedProfile;
    }

    private boolean compareZonesAndProfile(String zoneValue, String ProfileValue) {

        boolean result;

        if (ProfileValue == null || zoneValue == null) {
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

    private HashMap<String, Double> sumMaps(HashMap<String, Double> sum, HashMap<String, Double> element) {
        sum.put("temperature", (sum.get("temperature") + element.get("temperature")));
        sum.put("humidity", (sum.get("humidity") + element.get("humidity")));
        sum.put("gain", (sum.get("gain") + element.get("gain")));
        sum.put("luminosity", (sum.get("luminosity") + element.get("luminosity")));
        sum.put("full", (sum.get("full") + element.get("full")));
        sum.put("ir", (sum.get("ir") + element.get("ir")));
        sum.put("pressure", (sum.get("pressure") + element.get("pressure")));
        sum.put("sound", (sum.get("sound") + element.get("sound")));
        sum.put("altitude", (sum.get("altitude") + element.get("altitude")));
        sum.put("light", (sum.get("light") + element.get("light")));
        sum.put("lux", (sum.get("lux") + element.get("lux")));
        //TODO maybe use line below instead?
        // element.forEach((k, v) -> sum.merge(k, v, Double::sum);
        return sum;
    }

    private HashMap<String, Double> avgMap(HashMap<String, Double> values) {
        java.util.Iterator hmIterator = values.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) hmIterator.next();
            double newVal = ((double) entry.getValue() / 5);
            values.put((String) entry.getKey(), newVal);
        }
        return values;
    }

    private HashMap<String, Double> initEmptyMap() {
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

    public Profile getProfileOfZone(Zone zone) {
        switch (zone.getName()) {
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

