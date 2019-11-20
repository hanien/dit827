package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.example.aptiv.ViewModel.BaseViewModel;

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

    private BaseViewModel _base;
    private DashboardFragment _dashboardFragment;
    private Zone _driver, _passenger, _mid, _back;

    public ProfileHandler(BaseViewModel base, DashboardFragment dashboardFragment,
                          Zone driver, Zone passenger, Zone mid, Zone back){
        _base = base;
        _dashboardFragment = dashboardFragment;
        _driver = driver;
        _passenger = passenger;
        _mid = mid;
        _back = back;
    }

    public void onDataFetched() {
        checkThresholds();
    }

    private void checkThresholds() {
        //take profile
        //dummy profile until profiles are implemented
        Profile profile = new Profile("0", "0", "0","0","0","0","0","0","0","0","0","0");

        //for each zone
        ////create dict for zone
        HashMap<String, Boolean> driver = checkZone(profile, _driver);

        if(driver.containsValue(Boolean.FALSE)){
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
        }
        else{
            _dashboardFragment.toggleError(_driver, Boolean.TRUE);
        }
    }

    private HashMap<String, Boolean> checkZone(Profile profile, Zone zone){
        HashMap<String, Boolean> checkedZone = new HashMap<>(11);

        //compares each value against the threshold
        //puts true in dict entry if within threshold, false if not
        //TODO: maybe see if we can convert to doubles when we gather the data?
        checkedZone.put("temperature", (Double.parseDouble(zone.getTemperature()) > Double.parseDouble(profile.getTemperature()) - 0.5
                                        && Double.parseDouble(zone.getTemperature()) < Double.parseDouble(profile.getTemperature()) + 0.5));

        checkedZone.put("humidity", (Double.parseDouble(zone.getHumidity()) > Double.parseDouble(profile.getHumidity()) - 0.5
                                        && Double.parseDouble(zone.getHumidity()) < Double.parseDouble(profile.getHumidity()) + 0.5));

        checkedZone.put("gain", (Double.parseDouble(zone.getGain()) > Double.parseDouble(profile.getGain()) - 0.5
                                        && Double.parseDouble(zone.getGain()) < Double.parseDouble(profile.getGain()) + 0.5));

        checkedZone.put("luminosity", (Double.parseDouble(zone.getLuminosity()) > Double.parseDouble(profile.getLuminosity()) - 0.5
                                        && Double.parseDouble(zone.getLuminosity()) < Double.parseDouble(profile.getLuminosity()) + 0.5));

        checkedZone.put("full", (Double.parseDouble(zone.getFull()) > Double.parseDouble(profile.getFull()) - 0.5
                                        && Double.parseDouble(zone.getTemperature()) < Double.parseDouble(profile.getFull()) + 0.5));

        checkedZone.put("ir", (Double.parseDouble(zone.getIr()) > Double.parseDouble(profile.getIr()) - 0.5
                                        && Double.parseDouble(zone.getIr()) < Double.parseDouble(profile.getIr()) + 0.5));

        checkedZone.put("pressure", (Double.parseDouble(zone.getPressure()) > Double.parseDouble(profile.getPressure()) - 0.5
                                        && Double.parseDouble(zone.getPressure()) < Double.parseDouble(profile.getPressure()) + 0.5));

        checkedZone.put("sound", (Double.parseDouble(zone.getSound()) > Double.parseDouble(profile.getSound()) - 0.5
                                        && Double.parseDouble(zone.getSound()) < Double.parseDouble(profile.getSound()) + 0.5));

        checkedZone.put("altitude", (Double.parseDouble(zone.getAltitiude()) > Double.parseDouble(profile.getAltitiude()) - 0.5
                                        && Double.parseDouble(zone.getAltitiude()) < Double.parseDouble(profile.getAltitiude()) + 0.5));

        checkedZone.put("light", (Double.parseDouble(zone.getLight()) > Double.parseDouble(profile.getLight()) - 0.5
                                        && Double.parseDouble(zone.getLight()) < Double.parseDouble(profile.getLight()) + 0.5));

        checkedZone.put("lux", (Double.parseDouble(zone.getLux()) > Double.parseDouble(profile.getLux()) - 0.5
                                        && Double.parseDouble(zone.getLux()) < Double.parseDouble(profile.getLux()) + 0.5));

        return checkedZone;
    }
}
