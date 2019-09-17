package com.example.aptiv.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.aptiv.MainActivity;
import com.example.aptiv.R;
import com.google.android.material.tabs.TabLayout;

import static com.example.aptiv.R.id.viewPager;

public class AudioFragment extends Fragment implements View.OnClickListener{

    private MainActivity _owner;
    private View _view;

    public AudioFragment(MainActivity Owner) {
        _owner = Owner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_audio, container, false);

        SetupCarLayoutFragment();
        SetupButton();
        SetupEvents();

        return _view;
    }

    private void SetupCarLayoutFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        CarLayoutFragment CarFragment = new CarLayoutFragment(_owner);
        fragmentTransaction.replace(R.id.fragmentPlaceHolderAudio, CarFragment).commit();
    }

    private void SetupEvents() {
    }

    private void SetupButton() {

    }

    @Override
    public void onClick(View view){
        /*
        switch (view.getId()){
            case R.id.default_car:
                break;
        }

         */
    }
}
