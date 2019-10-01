package com.example.aptiv.ViewModel;

import com.example.aptiv.MainActivity;
import com.example.aptiv.Service.AptivService;

import java.lang.reflect.Array;

public class BaseViewModel {

    private AptivService _aptiveService;
    private MainActivity _activity;
    public String AllMeasurements;

    public BaseViewModel(MainActivity activity){
        _activity = activity;
        _aptiveService = new AptivService(_activity);
        InitalizeViewModel();
    }

    public AptivService GetAptivService(){
        return _aptiveService;
    }

    public void InitalizeViewModel(){
        AllMeasurements = _aptiveService.GetAllMeasurements(_activity);
    }
}
