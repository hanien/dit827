package com.example.aptiv.Model.Interface;

import com.example.aptiv.Model.Classe.Zone;

//interface to pass data from the AptiveService volley to BaseViewModel
public interface IVolleyCollback {
    void GetDriverReadings(Zone value);
    void GetPassengerReadings(Zone value);
    void GetAverageReadings(Zone value);
    void GetBackseatReadings(Zone value);
}
