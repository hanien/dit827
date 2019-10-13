package com.example.aptiv.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.aptiv.R;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.example.aptiv.View.adapter.ViewPagerAdapter;
import com.example.aptiv.View.fragment.DashboardFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity  {

    private BaseViewModel _viewModel;
    public DashboardFragment _dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _viewModel = new BaseViewModel(this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager( viewPager );

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
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
    public void CloseVolumeFragment(View v){
        _dashboardFragment.SetupCarLayoutFragment();
    }

    public void OpenTempFragment(View v) {
        _dashboardFragment.OpenTempFragment();
    }

    public void OpenAPFragment(View v) {
        _dashboardFragment.OpenAPFragment();
    }
}
