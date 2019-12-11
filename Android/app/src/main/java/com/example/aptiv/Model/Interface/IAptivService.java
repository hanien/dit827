package com.example.aptiv.Model.Interface;

//interface for AptivService, that will connect to our server to fetch data
public interface IAptivService {
    void GetDriverReadings(IVolleyCallback value);

    void GetPassengerReadings(IVolleyCallback value);

    void GetAverageReadings(IVolleyCallback value);

    void GetBackseatReadings(IVolleyCallback value);
}
