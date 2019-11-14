package com.example.aptiv.View;
import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Helper.ObjectSerializer;
import com.example.aptiv.ViewModel.DashboardViewModel;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.aptiv.R;
import com.example.aptiv.View.fragment.TempLayoutFragment;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.example.aptiv.View.adapter.ViewPagerAdapter;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private DashboardViewModel _viewModel;
    public DashboardFragment _dashboardFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCenter.start(getApplication(), "98489794-5ff9-4db1-bc55-a9d9fbea5220",Analytics.class, Crashes.class);
        SetupActivity();
        LoadProfileData();
    }

    private void SetupActivity(){
        setContentView(R.layout.activity_main);
        _viewModel = new DashboardViewModel(this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager( viewPager );
    }

    @Override
    protected void onDestroy() {
        SaveProfileData();
        super.onDestroy();

    }
    private void SaveProfileData(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String banana = ObjectSerializer.serialize(_viewModel.Profiles);
        editor.putString("ProfileKey", banana);
        editor.commit();
    }

    private void LoadProfileData(){
        SharedPreferences sharedPref2 = getPreferences(Context.MODE_PRIVATE);
        String bytes = sharedPref2.getString("ProfileKey",null);
        if(bytes != null){
            _viewModel.Profiles = (ArrayList<Profile>)ObjectSerializer.deserialize(bytes);
        }else{
            _viewModel.Profiles = new ArrayList<>();
        }
    }

    //initial design was with tabs, kept this code for possible future improvements
    //it has been set to invisible from xml file
    public void addTabs(ViewPager viewPager) {
        _dashboardFragment = new DashboardFragment(this , _viewModel);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(_dashboardFragment, "Dashboard");
        viewPager.setAdapter(adapter);
    }

    //TODO: those should be moved to fragment, for now keep it here ,there is a bug i cant figure it out
    public void OpenVolumeFragment(View v) {
        _dashboardFragment.OpenVolumeFragment();
    }

    //TODO: those should be moved to fragment, for now keep it here ,there is a bug i cant figure it out
    public void CloseFragment(View v){
        _dashboardFragment.SetupCarLayoutFragment();
    }

    public void OpenTempFragment(View v) {
        _dashboardFragment.OpenTempFragment();
    }

    public void OpenAPFragment(View v) {
        _dashboardFragment.OpenAPFragment();
    }

    public void OpenHumidityFragment(View v) {
        _dashboardFragment.OpenHumidityFragment();
    }

    public void OpenLuxFragment(View v) {
        _dashboardFragment.OpenLuxFragment();
    }

    public void OpenSettingsFragment(View v) {
        _dashboardFragment.OpenSettingsFragment();
    }
}
