package com.example.aptiv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.aptiv.ViewModel.BaseViewModel;
import com.example.aptiv.adapter.ViewPagerAdapter;
import com.example.aptiv.fragment.DashboardFragment;
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

    public void addTabs(ViewPager viewPager) {
        _dashboardFragment = new DashboardFragment(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(_dashboardFragment, "Dashboard");
        viewPager.setAdapter(adapter);
    }
}
