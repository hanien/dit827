package com.example.aptiv.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.aptiv.MainActivity;
import com.example.aptiv.R;

public class CarLayoutFragment extends Fragment implements View.OnTouchListener{

    private ImageView _carMaskView;
    private ImageView _frontSeat;
    private ImageView _driverSeat;
    private ImageView _backSeat;
    private Boolean _frontSeatSelected = false;
    private Boolean _backSeatSelected = false;
    private Boolean _driverSeatSelected = false;
    private View _view;
    private MainActivity _owner;

    public CarLayoutFragment(MainActivity Owner) {
        _owner = Owner;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_carlayout, container, false);

        SetupButton();
        SetupEvents();

        return _view;
    }

    private void SetupButton() {
        _carMaskView = _view.findViewById(R.id.car_mask);
        _frontSeat = _view.findViewById(R.id.frontseat);
        _backSeat = _view.findViewById(R.id.backseat);
        _driverSeat = _view.findViewById(R.id.driverseat);
    }

    private void SetupEvents() {
        _carMaskView.setOnTouchListener(this);
    }


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
                break;
        }
        return true;
    }

    private int getHotspotColor (int hotspotId, int x, int y, Fragment g) {
        ImageView img = g.getView().findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

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