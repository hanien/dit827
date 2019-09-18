package com.example.aptiv.ViewModel;

import com.example.aptiv.Service.AptivService;

import java.lang.reflect.Array;

public class BaseViewModel {

    private AptivService _aptiveService;
    public Array AllMeasurements;

    public BaseViewModel(){
        _aptiveService = new AptivService();
    }

    public AptivService GetAptivService(){
        return _aptiveService;
    }

    public void InitalizeViewModel(){
        AllMeasurements = _aptiveService.GetAllMeasurements();
    }
}
