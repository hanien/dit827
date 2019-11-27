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
        sampleZone(null, zone);
        checkThresholds(null, zone);
    }

    public void onProfileChange(Profile profile, Zone zone) {
        if(checkZone(profile, zone).containsValue(Boolean.FALSE)){
            //TODO add popup here
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

            if(hasError.containsValue(Boolean.FALSE)){
                _dashboardFragment.toggleError(zone, Boolean.TRUE);
            }
            else{
                _dashboardFragment.toggleError(zone, Boolean.TRUE);
            }
            //add reading to queue
            currentQueue.remove();
            currentQueue.add(zone.getAll());
        }

    }

    private void checkThresholds(Profile profile, Zone zone) {
        //for each zone
        ////create dict for zone
        HashMap<String, Boolean> checkedZone = checkZone(profile, zone);

        if(checkedZone.containsValue(Boolean.FALSE)){
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
        } else {
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
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
        //TODO: maybe see if we can convert to doubles when we gather the data?
        checkedZone.put("temperature", compareThreshold(values.get("temperature"), t_temp));

        checkedZone.put("humidity", compareThreshold(values.get("humidity"), t_humidity));

        checkedZone.put("gain", compareThreshold(values.get("gain"), t_gain));

        checkedZone.put("luminosity", compareThreshold(values.get("luminosity"), t_luminosity));

        checkedZone.put("full", compareThreshold(values.get("full"), t_full));

        checkedZone.put("ir", compareThreshold(values.get("ir"), t_ir));

        checkedZone.put("pressure", compareThreshold(values.get("pressure"), t_pressure));

        checkedZone.put("sound", compareThreshold(values.get("sound"), t_sound));

        checkedZone.put("altitude", compareThreshold(values.get("altitude"), t_altitude));

        checkedZone.put("light", compareThreshold(values.get("light"), t_light));

        checkedZone.put("lux", compareThreshold(values.get("lux"), t_lux));

        return checkedZone;
    }


    private boolean compareThreshold(double value, double threshold){
        boolean belowThreshold = value > value - threshold;
        boolean aboveThreshold = value < value + threshold;
        return belowThreshold && aboveThreshold;
    }

    private boolean checkTempLevel() {

        String  DriverTempLevel = _base.DriverZone.getTemperature();
        String  PassangerTempLevel = _base.DriverZone.getTemperature();
        String  BackTempLevel = _base.DriverZone.getTemperature();

        boolean DriverCheck = compareThreshold(Double.parseDouble(DriverTempLevel),t_temp);
        boolean PassangerCheck = compareThreshold(Double.parseDouble(PassangerTempLevel),t_temp);
        boolean BackCheck = compareThreshold(Double.parseDouble(BackTempLevel),t_temp);


        return DriverCheck && PassangerCheck && BackCheck;

    }

    private boolean checkSoundLevel() {

        String  DriverSoundLevel = _base.DriverZone.getSound();
        String  PassangerSoundLevel = _base.DriverZone.getSound();
        String  BackSoundLevel = _base.DriverZone.getSound();

        boolean DriverCheck = compareThreshold(Double.parseDouble(DriverSoundLevel),t_sound);
        boolean PassangerCheck = compareThreshold(Double.parseDouble(PassangerSoundLevel),t_sound);
        boolean BackCheck = compareThreshold(Double.parseDouble(BackSoundLevel),t_sound);


        return DriverCheck && PassangerCheck && BackCheck;

    }


    private void ZonesValueHandler(Profile profile, Zone zone) {

        HashMap<String, Boolean> check = checkProfile(profile,zone);

        if (check.containsValue(Boolean.FALSE)) {


          // _dashboardFragment.popUp();


        } else {

            ChangeZoneValues(profile, zone);

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

        boolean result =  (Double.parseDouble(ProfileValue) < (Double.parseDouble(zoneValue) + 5 ) )
                &&  (Double.parseDouble(ProfileValue) >  (Double.parseDouble(zoneValue) - 5 ));
        
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
}

