package com.example.aptiv.View.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.R;
import com.example.aptiv.ViewModel.BaseViewModel;

public class DashboardFragment extends Fragment implements View.OnTouchListener {

    private MainActivity _owner;
    private View _view;
    private BaseViewModel _baseViewModel;
    public  DefaultLayoutFragment DefaultLayoutFragment;
    private SoundLayoutFragment SoundLayoutFragment;
    private TempLayoutFragment TempLayoutFragment;
    private AirpLayoutFragment AirpLayoutFragment;
    private HumidityLayoutFragment HumidityLayoutFragment;
    private LuxLayoutFragment LuxLayoutFragment;
    private SettingsLayoutFragment SettingsLayoutFragment;
    private DevicesHandler DevicesHandler;
    private IZoneSelection _callback;
    private ImageView _carMaskView;
    private ImageView _frontSeat;
    private ImageView _driverSeat;
    private ImageView _backSeat;
    private ImageView _frontSeatError;
    private ImageView _driverSeatError;
    private ImageView _backSeatError;

    public Boolean _frontSeatSelected = false;
    public Boolean _backSeatSelected = false;
    public Boolean _driverSeatSelected = false;

    public DashboardFragment(MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        SetupCarLayoutFragment();

        SetupButton();
        SetupEvents();
        _baseViewModel.SetDashboardFragment(this);

        return _view;
    }

    private void SetupButton() {
        _carMaskView = _view.findViewById(R.id.car_mask);
        _frontSeat = _view.findViewById(R.id.frontseat);
        _backSeat = _view.findViewById(R.id.backseat);
        _driverSeat = _view.findViewById(R.id.driverseat);

        _frontSeatError = _view.findViewById(R.id.frontseaterror);
        _backSeatError = _view.findViewById(R.id.backseaterror);
        _driverSeatError = _view.findViewById(R.id.driverseaterror);
    }

    private void SetupEvents() {
        _carMaskView.setOnTouchListener(this);
    }

    //setup default fragment with all values
    public void SetupCarLayoutFragment(){
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        DefaultLayoutFragment = new DefaultLayoutFragment(_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, DefaultLayoutFragment).commit();
        _callback = DefaultLayoutFragment;
    }

    //Open volume fragment
    public void OpenVolumeFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        SoundLayoutFragment = new SoundLayoutFragment(this,_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, SoundLayoutFragment).commit();
        _callback = SoundLayoutFragment;
    }

    //Open temp fragment
    public void OpenTempFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        TempLayoutFragment = new TempLayoutFragment(this,_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, TempLayoutFragment).commit();
        _callback = TempLayoutFragment;
    }

    //Open ap fragment
    public void OpenAPFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        AirpLayoutFragment = new AirpLayoutFragment(this,_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard,AirpLayoutFragment).commit();
        _callback = AirpLayoutFragment;
    }

    //Open humidity fragment
    public void OpenHumidityFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        HumidityLayoutFragment = new HumidityLayoutFragment(this,_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard,HumidityLayoutFragment).commit();
        _callback = HumidityLayoutFragment;
    }

    //Open lux fragment
    public void OpenLuxFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        LuxLayoutFragment = new LuxLayoutFragment(this,_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard,LuxLayoutFragment).commit();
        _callback = LuxLayoutFragment;
    }

    //Open settings fragment
    public void OpenSettingsFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        SettingsLayoutFragment = new SettingsLayoutFragment(this, _owner, _baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard, SettingsLayoutFragment).commit();
        _callback = SettingsLayoutFragment;
    }

    //Open Devices Handler fragment
    public void OpenDHFragment() {
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fm1.beginTransaction();
        DevicesHandler = new DevicesHandler(this,_owner,_baseViewModel);
        fragmentTransaction1.replace(R.id.fragmentPlaceHolderDashboard,DevicesHandler).commit();
    }

    //onTouch event that is connected to the car image to read which seat is seleted
    @Override
    public boolean onTouch (View v, MotionEvent ev) {
        final int action = ev.getAction();
        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_UP :
                int touchColor = getHotspotColor(R.id.car_mask, evX, evY,this);
                if (closeMatch (Color.RED, touchColor)) {
                    if(_driverSeatSelected){
                        _driverSeat.setVisibility(View.INVISIBLE);
                    }else{
                        _driverSeat.setVisibility(View.VISIBLE);
                    }
                    _driverSeatSelected = !_driverSeatSelected ;
                } else if(closeMatch (Color.BLUE, touchColor)){
                    if(_frontSeatSelected){
                        _frontSeat.setVisibility(View.INVISIBLE);
                    }else{
                        _frontSeat.setVisibility(View.VISIBLE);
                    }
                    _frontSeatSelected = !_frontSeatSelected;
                }else if(closeMatch (Color.YELLOW, touchColor)){
                    if(_backSeatSelected){
                        _backSeat.setVisibility(View.INVISIBLE);
                    }else{
                        _backSeat.setVisibility(View.VISIBLE);
                    }
                    _backSeatSelected = !_backSeatSelected;
                }else{
                    _frontSeatSelected = false;
                    _backSeatSelected = false;
                    _driverSeatSelected = false;
                    _frontSeat.setVisibility(View.INVISIBLE);
                    _backSeat.setVisibility(View.INVISIBLE);
                    _driverSeat.setVisibility(View.INVISIBLE);
                }
                _callback.zoneIsSelected();
                break;
        }
        return true;
    }

    public void toggleError(Zone zone, boolean show)
    {
        switch(zone.getName())
        {
            case DRIVER:
                if(show)
                {
                    _driverSeatError.setVisibility(View.VISIBLE);
                }
                else
                {
                    _driverSeatError.setVisibility(View.INVISIBLE);
                }
                break;
            case PASSENGER:
                if(show)
                { _frontSeatError.setVisibility(View.VISIBLE);
                }
                else
                {
                    _frontSeatError.setVisibility(View.INVISIBLE);
                }
                break;
            case BACK:
                if(show)
                {
                    _backSeatError.setVisibility(View.VISIBLE);
                }
                else
                {
                    _backSeatError.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                //TODO: behavior for invalid zone name. this should never happen.
                break;

        }
    }
    //seat selection in the car image is base on color
    //in the background of the car image there is img with 3 colors representing every single zone
    //check car_mask.png
    private int getHotspotColor (int hotspotId, int x, int y, Fragment g) {
        ImageView img = g.getView().findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    //check if color that has been selected is in the tolerance range
    private boolean closeMatch (int color1, int color2) {
        int tolerance = 50;
        if ((int) Math.abs (Color.red (color1) - Color.red (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance )
            return false;
        return true;
    }

}