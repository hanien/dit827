package com.example.aptiv.ViewModel;

import com.example.aptiv.Model.Classe.Profile;;
import com.example.aptiv.View.MainActivity;
import java.util.ArrayList;

public class DashboardViewModel extends BaseViewModel  {
    private MainActivity activity;
    public ArrayList<Profile> Profiles = new ArrayList<>();

    public DashboardViewModel(MainActivity activity) {
        super(activity);
        this.activity = activity;
    }
}
