package com.example.aptiv.ViewModel;

import com.example.aptiv.Interface.IVolleyCollback;
import com.example.aptiv.MainActivity;
import com.example.aptiv.Service.AptivService;

import java.lang.reflect.Array;

public class BaseViewModel implements IVolleyCollback {

    private AptivService _aptiveService;
    private MainActivity _activity;

    public BaseViewModel(MainActivity activity){
        _activity = activity;
        _aptiveService = new AptivService(_activity);
        InitalizeViewModel();
    }

    public AptivService GetAptivService(){
        return _aptiveService;
    }

    public void InitalizeViewModel(){
        _aptiveService.GetAllMeasurements(this);
    }

    @Override
    public void GetSound(String value) {
        _activity._dashboardFragment.SetUpSound(value);
    }
}
