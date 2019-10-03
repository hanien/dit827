package com.example.aptiv.ViewModel;

import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.Model.Service.AptivService;

public class BaseViewModel implements IVolleyCollback {

    private AptivService _aptiveService;
    private MainActivity _activity;

    public BaseViewModel(MainActivity activity){
        _activity = activity;
        _aptiveService = new AptivService(_activity);
    }

    public AptivService GetAptivService(){
        return _aptiveService;
    }


    @Override
    public void GetSound(String value) {
        _activity._dashboardFragment.DefaultLayoutFragment.SetUpSound(value);
    }

    public void UpdateSoundValue() {
        _aptiveService.GetSound(this);
    }
}
