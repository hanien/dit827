package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.example.aptiv.ViewModel.BaseViewModel;

import java.lang.ref.PhantomReference;
import java.sql.Driver;
import java.util.HashMap;

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

    public ProfileHandler(BaseViewModel base, DashboardFragment dashboardFragment,
                          Zone driver, Zone passenger, Zone mid, Zone back) {
        _base = base;
        _dashboardFragment = dashboardFragment;
        _driver = driver;
        _passenger = passenger;
        _mid = mid;
        _back = back;

    }

    public void SetDashboardFragment(DashboardFragment fragment) {
        _dashboardFragment = fragment;
        checkThresholds();
    }

    public void onDataFetched() {
        checkThresholds();
    }

    private void checkThresholds() {
        //take profile
        //dummy profile until profiles are implemented
        Profile profile = new Profile("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

        //for each zone
        ////create dict for zone
        HashMap<String, Boolean> driver = checkZone(profile, _driver);

        if (driver.containsValue(Boolean.FALSE)) {
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
        } else {
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
        }
    }

    private HashMap<String, Boolean> checkZone(Profile profile, Zone zone) {
        HashMap<String, Boolean> checkedZone = new HashMap<>(11);

        //compares each value against the threshold
        //puts true in dict entry if within threshold, false if not
        //TODO: maybe see if we can convert to doubles when we gather the data?
        checkedZone.put("temperature", compareThreshold(zone.getTemperature(), t_temp));

        checkedZone.put("humidity", compareThreshold(zone.getHumidity(), t_humidity));

        checkedZone.put("gain", compareThreshold(zone.getGain(), t_gain));

        checkedZone.put("luminosity", compareThreshold(zone.getLuminosity(), t_luminosity));

        checkedZone.put("full", compareThreshold(zone.getFull(), t_full));

        checkedZone.put("ir", compareThreshold(zone.getIr(), t_ir));

        checkedZone.put("pressure", compareThreshold(zone.getPressure(), t_pressure));

        checkedZone.put("sound", compareThreshold(zone.getSound(), t_sound));

        checkedZone.put("altitude", compareThreshold(zone.getAltitiude(), t_altitude));

        checkedZone.put("light", compareThreshold(zone.getLight(), t_light));

        checkedZone.put("lux", compareThreshold(zone.getLux(), t_lux));

        return checkedZone;
    }

    private boolean compareThreshold(String value, double threshold) {
        boolean belowThreshold = Double.parseDouble(value) > Double.parseDouble(value) - threshold;
        boolean aboveThreshold = Double.parseDouble(value) < Double.parseDouble(value) + threshold;

        return belowThreshold && aboveThreshold;
    }



    private boolean checkTempLevel() {

        String  DriverTempLevel = _base.DriverZone.getTemperature();
        String  PassangerTempLevel = _base.DriverZone.getTemperature();
        String  BackTempLevel = _base.DriverZone.getTemperature();

        boolean DriverCheck = compareThreshold(DriverTempLevel,t_temp);
        boolean PassangerCheck = compareThreshold(PassangerTempLevel,t_temp);
        boolean BackCheck = compareThreshold(BackTempLevel,t_temp);


        return DriverCheck && PassangerCheck && BackCheck;

    }

    private boolean checkSoundLevel() {

        String  DriverSoundLevel = _base.DriverZone.getSound();
        String  PassangerSoundLevel = _base.DriverZone.getSound();
        String  BackSoundLevel = _base.DriverZone.getSound();

        boolean DriverCheck = compareThreshold(DriverSoundLevel,t_sound);
        boolean PassangerCheck = compareThreshold(PassangerSoundLevel,t_sound);
        boolean BackCheck = compareThreshold(BackSoundLevel,t_sound);


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



}