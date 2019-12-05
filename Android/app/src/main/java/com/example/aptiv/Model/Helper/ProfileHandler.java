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
    //threshold constants
    private final double t_altitude = 0.5;
    private final double t_full = 30.0;
    private final double t_gain = 1.0;
    private final double t_humidity = 2.0;
    private final double t_ir = 5.0;
    private final double t_lux = 5.0;
    private final double t_pressure = 10.0;
    private final double t_temp = 0.5;
    private final double t_luminosity = 5.0;
    private final double t_sound = 1.0;
    private final double t_light = 5.0;

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
        //===============
        //UNSTABLE CODE: comment out only while working on it
        //===============
        //sampleZone(getProfileOfZone(zone), zone);
        //checkThresholds(zone);
    }

    public void onProfileChange(Profile profile, Zone zone) {
        if(checkZone(profile, zone).containsValue(Boolean.FALSE)){
            //TODO create code for custom messages
            _dashboardFragment.CreatePopupView(
                    zone.getName() == Zone.ZoneName.DRIVER,
                    zone.getName() == Zone.ZoneName.PASSENGER,
                    zone.getName() == Zone.ZoneName.BACK,
                    "dummy message", true
            );
        }
    }

    public void alignToZone(Profile profile, Zone zone){

    }

    private void sampleZone(Profile profile, Zone zone) {
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
        if(zone.getName().equals(Zone.ZoneName.DRIVER))
            _driveSample.add(zone.getAll());
        if(currentQueue.size() < 5) {
            currentQueue.add(zone.getAll());
        }
        else if (currentQueue.size() == 5){
            //sum the contents
            for(int i = 0; i < currentQueue.size(); i++){
                currentSum = sumMaps(currentSum, (HashMap<String, Double>)currentQueue.toArray()[i]);
            }

            //generate average
            HashMap<String, Double> avg = avgMap(currentSum);

            //check range of input
            HashMap<String, Boolean> hasError = checkZone(profile, avg);
            //if bad: make red

            //TODO might have to add a check whether popup already exists here.
            if(hasError.containsValue(Boolean.FALSE)){
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
            //add reading to queue
            currentQueue.remove();
            currentQueue.add(zone.getAll());
        }

    }

    private void checkThresholds(Zone zone) {
        //for each zone
        ////create dict for zone
        HashMap<String, Boolean> checkedZone = checkZone(getProfileOfZone(zone), zone);

        if(checkedZone.containsValue(Boolean.FALSE)){
            _dashboardFragment.toggleError(zone, Boolean.TRUE);
        } else {
            _dashboardFragment.toggleError(zone, Boolean.TRUE);
        }
    }

    //override that can use zone objects
    private HashMap<String, Boolean> checkZone(Profile profile, Zone zone){
        HashMap<String, Double> values = zone.getAll();
        return checkZone(profile, values);
    }

    private HashMap<String, Boolean> checkZone(Profile profile, HashMap<String, Double> values)
    {
        HashMap<String, Boolean> checkedZone = new HashMap<>(11);
        //compares each value against the threshold
        //puts true in dict entry if within threshold, false if not

        if(values.get("temperature").equals(null))
            checkedZone.put("temperature", compareThreshold(Double.parseDouble(profile.getTemperature()),
                                                            values.get("temperature"), t_temp));
        else
            checkedZone.put("temperature", true);

        if(values.get("humidity").equals(null))
            checkedZone.put("humidity", values.get("humidity") == null || compareThreshold(Double.parseDouble(profile.getTemperature()),
                                                                            values.get("humidity"), t_humidity));
        else
            checkedZone.put("humidity", true);

        if(values.get("gain").equals(null))
            checkedZone.put("gain", values.get("gain") == null || compareThreshold(Double.parseDouble(profile.getGain()),
                                                                    values.get("gain"), t_gain));
        else
            checkedZone.put("gain", true);

        if(values.get("luminosity").equals(null))
            checkedZone.put("luminosity", values.get("luminosity") == null || compareThreshold(Double.parseDouble(profile.getLuminosity()),
                                                                                values.get("luminosity"), t_luminosity));
        else
            checkedZone.put("luminosity", true);

        if(values.get("full").equals(null))
            checkedZone.put("full", values.get("full") == null || compareThreshold(Double.parseDouble(profile.getFull()),
                                                                    values.get("full"), t_full));
        else
            checkedZone.put("full", true);

        if(values.get("ir").equals(null))
            checkedZone.put("ir", values.get("ir") == null || compareThreshold(Double.parseDouble(profile.getIr()),
                                                                values.get("ir"), t_ir));
        else
            checkedZone.put("ir", true);

        if(values.get("pressure").equals(null))
            checkedZone.put("pressure", values.get("pressure") == null || compareThreshold(Double.parseDouble(profile.getPressure()),
                                                                            values.get("pressure"), t_pressure));
        else
            checkedZone.put("pressure", true);

        if(values.get("sound").equals(null))
            checkedZone.put("sound", values.get("sound") == null || compareThreshold(Double.parseDouble(profile.getSound()),
                                                                        values.get("sound"), t_sound));
        else
            checkedZone.put("sound", true);

        if(values.get("altitude").equals(null))
            checkedZone.put("altitude", values.get("altitude") == null || compareThreshold(Double.parseDouble(profile.getAltitiude()),
                                                                            values.get("altitude"), t_altitude));
        else
            checkedZone.put("altitude", true);

        if(values.get("light").equals(null))
            checkedZone.put("light", values.get("light") == null || compareThreshold(Double.parseDouble(profile.getLight()),
                                                                        values.get("light"), t_light));
        else
            checkedZone.put("light", true);

        if(values.get("ir").equals(null))
            checkedZone.put("lux", values.get("lux") == null || compareThreshold(Double.parseDouble(profile.getLux()),
                                                                    values.get("lux"), t_lux));
        else
            checkedZone.put("lux", true);
        return checkedZone;
    }


    private boolean compareThreshold(double target, double source, double threshold){
        boolean belowThreshold = target > source - threshold;
        boolean aboveThreshold = target < source + threshold;
        return belowThreshold && aboveThreshold;
    }

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


    public boolean ZonesValueHandler(Profile profile, Zone zone) {

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

    private HashMap<String, Double> sumMaps(HashMap<String, Double> sum, HashMap<String, Double> element){
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

    private HashMap <String, Double> avgMap(HashMap<String, Double> values ){
        java.util.Iterator hmIterator = values.entrySet().iterator();

        while(hmIterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) hmIterator.next();
            double newVal = ((double)entry.getValue()/5);
            values.put((String)entry.getKey(), newVal);
        }
        return values;
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

