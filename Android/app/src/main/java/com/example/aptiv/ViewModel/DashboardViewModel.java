package com.example.aptiv.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Helper.ObjectSerializer;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

public class DashboardViewModel extends BaseViewModel  {
    private MainActivity activity;
    public ArrayList<Profile> Profiles = new ArrayList<>();

    public DashboardViewModel(MainActivity activity) {
        super(activity);
        this.activity = activity;
    }
}
