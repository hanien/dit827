/*
package com.example.aptiv.View.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aptiv.Model.Classe.Mode;
import com.example.aptiv.Model.Interface.IZoneSelection;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.View.adapter.CustomListAdapter;
import com.example.aptiv.ViewModel.BaseViewModel;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ModeLayoutFragment extends Fragment implements IZoneSelection {

    private MainActivity _owner;
    private DashboardFragment _parentFragment;
    private View _view;
    private BaseViewModel _baseViewModel;

    private IZoneSelection _callback;

    //private LinearLayout SetLuxLayout;

    private ListView listView;
    private CustomListAdapter mAdapter;

    public ModeLayoutFragment(DashboardFragment parentFragment,MainActivity Owner , BaseViewModel viewModel) {
        _owner = Owner;
        _baseViewModel = viewModel;
        _parentFragment = parentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_settingslayout, container, false);
        Context currContext = getActivity().getApplicationContext();

        setUpView();
        //setUpElements();
        //zoneIsSelected();
        //setUpTimer();

        createList(currContext);
        return _view;
    }

    private void setUpView() {
        //SetText = _view.findViewById(R.id.SelectZoneTextView);
        //luxChangeValue = _view.findViewById(R.id.luxChangeValue);
        //luxValue = _view.findViewById(R.id.luxValue);
        //SetLuxLayout = _view.findViewById(R.id.SetLuxLayout);
        listView = (ListView) _view.findViewById(R.id.modes_list);

    }

    private void createList(Context currContext){
/*
        ArrayList<Mode> modesList = new ArrayList<>();
        modesList.add(new Mode("Date mode"));
        modesList.add(new Mode("Reading mode"));
        modesList.add(new Mode("Summer mode"));
        modesList.add(new Mode("Winter mode"));
        modesList.add(new Mode("Mode 1"));
        modesList.add(new Mode("Mode 2"));
        modesList.add(new Mode("Mode 3"));
        modesList.add(new Mode("Mode 4"));
        modesList.add(new Mode("Mode 5"));
        modesList.add(new Mode("Mode 6"));

        mAdapter = new CustomListAdapter(currContext,modesList);
        listView.setAdapter(mAdapter);

        //return _view;

    }

    private void setUpElements(){

        //luxValue.setText(_baseViewModel.MiddleZone.getIr() + " lux");

    }


    @Override
    public void zoneIsSelected() {
        if (_parentFragment._backSeatSelected || _parentFragment._driverSeatSelected || _parentFragment._frontSeatSelected) {
            //SetText.setVisibility(View.GONE);
            //SetLuxLayout.setVisibility(View.VISIBLE);
            //luxChangeValue.setVisibility(View.VISIBLE);
            //updateLuxValue(_parentFragment._driverSeatSelected ,_parentFragment._frontSeatSelected ,_parentFragment._backSeatSelected);

        } else {
            //SetText.setVisibility(View.VISIBLE);
            //luxChangeValue.setVisibility(View.GONE);
            //SetLuxLayout.setVisibility(View.GONE);


        }
    }

}
*/
