package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.google.common.collect;

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
    private Queue<Zone> _driveSample, _passSample, _midSample, _backSample;
    private HashMap<String, Double> _driveSum, _passSum, _midSum, _backSum;

    public ProfileHandler(BaseViewModel base, DashboardFragment dashboardFragment,
                          Zone driver, Zone passenger, Zone mid, Zone back){
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
        //TODO init empty lists jesus fucking christ please kill me
        _driveSum = initEmptyMap();
        _passSum = initEmptyMap();
        _midSum = initEmptyMap();
        _backSum = initEmptyMap();
    }

    public void SetDashboardFragment(DashboardFragment fragment){
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

    private void sampleZone(Profile profile, Zone zone) {
        Queue<Zone> currentQueue = null;
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
            currentQueue.add(zone);
        }
        else if (currentQueue.size() == 5){
            //sum the contents
            for(int i = 0; i < currentQueue.size(); i++){
                currentSum = sumMaps(currentSum, (HashMap<String, Double>)currentQueue.toArray()[i]);
            }

            //generate average
            HashMap<String, Double> avg = avgMap(currentSum);

            //check range of input
            checkZone(profile, avg);
            //if bad: make red

            //add reading to queue
            currentQueue.remove();
            currentQueue.add(zone);
        }

    }

    private void checkThresholds(Profile profile, Zone zone) {
        //for each zone
        ////create dict for zone
        HashMap<String, Boolean> checkedZone = checkZone(profile, zone);

        if(checkedZone.containsValue(Boolean.FALSE)){
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
        }
        else{
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
