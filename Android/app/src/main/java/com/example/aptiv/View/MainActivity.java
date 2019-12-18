package com.example.aptiv.View;

import com.example.aptiv.Model.Class.Mode;
import com.example.aptiv.Model.Helper.ReceiverService;
import com.example.aptiv.ViewModel.DashboardViewModel;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.aptiv.R;
import com.example.aptiv.View.adapter.ViewPagerAdapter;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private DashboardViewModel _viewModel;
    public DashboardFragment _dashboardFragment;
    public boolean IOTEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCenter.start(getApplication(), "98489794-5ff9-4db1-bc55-a9d9fbea5220", Analytics.class, Crashes.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SetupActivity();
    }

    private void SetupActivity() {
        setContentView(R.layout.activity_main);
        _viewModel = new DashboardViewModel(this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //initial design was with tabs, kept this code for possible future improvements
    //it has been set to invisible from xml file
    public void addTabs(ViewPager viewPager) {
        _dashboardFragment = new DashboardFragment(this, _viewModel);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(_dashboardFragment, "Dashboard");
        viewPager.setAdapter(adapter);
    }

    //TODO: these should be moved to fragment, for now keep it here, there is a bug I can't figure out
    public void OpenVolumeFragment(View v) {
        _dashboardFragment.OpenVolumeFragment();
    }

    public void CloseFragment(View v) {
        _dashboardFragment.SetupCarLayoutFragment();
    }

    public void CloseModeFragment(View v) {
        _dashboardFragment.OpenSettingsFragment();
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

    public void OpenModeFragment(View v, Mode currentMode) {
        _dashboardFragment.OpenModeFragment(currentMode);
    }

    public void OpenAddModeFragment(View v) {
        _dashboardFragment.OpenAddModeFragment();
    }

    public void OpenDHFragment(View v) {
        _dashboardFragment.OpenDHFragment();
    }

    public void CloseDHFragment(View v) {
        _dashboardFragment.SetupCarLayoutFragment();
    }


    public void startReceiverService() {
        Intent serviceIntent = new Intent(this, ReceiverService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopReceiverService() {
        Intent serviceIntent = new Intent(this, ReceiverService.class);
        this.stopService(serviceIntent);
    }
}
