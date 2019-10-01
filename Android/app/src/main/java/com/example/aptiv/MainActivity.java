package com.example.aptiv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.aptiv.Interface.IVolleyCollback;
import com.example.aptiv.ViewModel.BaseViewModel;
import com.example.aptiv.adapter.ViewPagerAdapter;
import com.example.aptiv.fragment.AudioFragment;
import com.example.aptiv.fragment.DashboardFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements IVolleyCollback {

    private BaseViewModel _viewModel;
    private DashboardFragment _dashboardFragment;
    private AudioFragment _audioFragment;

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
        _audioFragment = new AudioFragment(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(_dashboardFragment, "Dashboard");
        adapter.addFrag(_audioFragment, "Audio");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void GetSound(String value) {
        _dashboardFragment.SetUpSound(value);
_   }
}
