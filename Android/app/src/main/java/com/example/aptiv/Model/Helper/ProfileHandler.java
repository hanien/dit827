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
        switch (zone.getName()) {
            case DRIVER:
                _driver = zone;
                sampleZone(_base.DriverProfile, zone);
                break;
            case PASSENGER:
                _passenger = zone;
                sampleZone(_base.PassengerProfile, zone);
                break;
            case MIDDLE:
                _mid = zone;
                break;
            case BACK:
                _back = zone;
                sampleZone(_base.BackProfile, zone);
                break;
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
            //ChangeZoneValues(profile, zone);
            return true;
        }
    }


    private HashMap<String, Boolean> checkProfile(Profile profile, Zone zone) {

        HashMap<String, Boolean> checkedProfile = new HashMap<>(11);

        checkedProfile.put( "temperature", profile.getTemperature() =="0" || profile.getTemperature() == null ? true : compareZonesAndProfile(zone.getTemperature(), profile.getTemperature()));

        checkedProfile.put("humidity", profile.getHumidity() =="0" || profile.getHumidity() == null ? true : compareZonesAndProfile(zone.getHumidity(), profile.getHumidity()));

        checkedProfile.put("gain", profile.getGain() =="0" || profile.getGain() == null ? true : compareZonesAndProfile(zone.getGain(), profile.getGain()));

        checkedProfile.put("luminosity", profile.getLuminosity() =="0" || profile.getLuminosity() == null ? true : compareZonesAndProfile(zone.getLuminosity(), profile.getLuminosity()));

        checkedProfile.put("full",  profile.getFull() =="0" || profile.getFull() == null ? true : compareZonesAndProfile(zone.getFull(), profile.getFull()));

        checkedProfile.put("ir",  profile.getIr() =="0"|| profile.getIr() == null ? true : compareZonesAndProfile(zone.getIr(), profile.getIr()));

        checkedProfile.put("pressure", profile.getPressure() =="0" || profile.getPressure() == null ? true : compareZonesAndProfile(zone.getPressure(), profile.getPressure()));

        checkedProfile.put("sound",  profile.getSound() =="0" || profile.getSound() == null ? true : compareZonesAndProfile(zone.getSound(), profile.getSound()));

        checkedProfile.put("light",   profile.getLight() =="0" || profile.getLight() == null ? true : compareZonesAndProfile(zone.getLight(), profile.getLight()));

        checkedProfile.put("lux",  profile.getLux() =="0" || profile.getLux() == null ? true : compareZonesAndProfile(zone.getLux(), profile.getLux()));

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

        profile.setTemperature(zone.getTemperature());
        profile.setHumidity(zone.getHumidity());
        profile.setGain(zone.getGain());
        profile.setLuminosity(zone.getLuminosity());
        profile.setFull(zone.getFull());
        profile.setIr(zone.getIr());
        profile.setPressure(zone.getPressure());
        profile.setSound(zone.getSound());
        profile.setLight(zone.getLight());
        profile.setLux(zone.getLux());

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

