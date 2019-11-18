package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Classe.Zone;
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

    //enum to differentiate btwn incoming zones
    public enum ZoneName {
        DRIVER,
        PASSENGER,
        MIDDLE,
        BACK
    }

    private BaseViewModel _base;
    private Zone _driver, _passenger, _mid, _back;

    public ProfileHandler(BaseViewModel base){
        _base = base;
    }

    public void onDataFetched(ZoneName name, Zone zone) {
        //store current readings
        switch(name){
            case DRIVER:
                _driver = zone;
                break;
            case BACK:
                _back = zone;
                break;
            case MIDDLE:
                _mid = zone;
                break;
            case PASSENGER:
                _passenger = zone;
                break;
            default:
                //TODO add behavior for invalid zone - this should not be possible
                break;
        }

        //check each zone thresholds
        checkThresholds();
    }

    private void checkThresholds() {
        //take profile
        //dummy profile until profiles are implemented
        Profile profile = new Profile("0", "0", "0","0","0","0","0","0","0","0","0","0");

        //for each zone
        ////create dict for zone
        HashMap<String, Boolean> driver = checkZone(profile, _driver);


        ////check each value in zone
        ////compare value against threshold
        ////put true or false in dict

    }

    private HashMap<String, Boolean> checkZone(Profile profile, Zone zone){
        HashMap<String, Boolean> checkedZone = new HashMap<String, Boolean>(11);
        //TODO: maybe see if we can convert to doubles when we gather the data?
        checkedZone.put("temperature", (Double.parseDouble(zone.getTemperature()) > Double.parseDouble(profile.getTemperature()) - 0.5 && Double.parseDouble(zone.getTemperature()) < Double.parseDouble(profile.getTemperature()) + 0.5));
        return null;
    }
}
