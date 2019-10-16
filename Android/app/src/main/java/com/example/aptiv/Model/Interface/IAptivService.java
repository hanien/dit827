package com.example.aptiv.Model.Interface;

//interface for AptiveService, that will connect to our server to fetch data
public interface IAptivService{
    void GetDriverReadings(IVolleyCollback value);
    void GetPassengerReadings(IVolleyCollback value);
    void GetAverageReadings(IVolleyCollback value);
    void GetBackseatReadings(IVolleyCollback value);
}
