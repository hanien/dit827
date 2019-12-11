package com.example.aptiv.Model.Interface;

import com.example.aptiv.Model.Class.Zone;

//interface to pass data from the AptivService volley to BaseViewModel
public interface IVolleyCallback {
    void GetDriverReadings(Zone value);

    void GetPassengerReadings(Zone value);

    void GetAverageReadings(Zone value);

    void GetBackseatReadings(Zone value);

    void OutTemperature(double temp);
}
