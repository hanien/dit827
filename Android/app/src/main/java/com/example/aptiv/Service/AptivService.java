package com.example.aptiv.Service;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aptiv.Interface.IAptivService;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aptiv.Interface.IVolleyCollback;
import com.example.aptiv.MainActivity;
import com.example.aptiv.R;
import com.example.aptiv.ViewModel.BaseViewModel;

import java.lang.reflect.Array;

public class AptivService implements IAptivService {
    private MainActivity _activity;
    public  AptivService(MainActivity activity){ _activity = activity; }

    @Override
    public void GetAllMeasurements(final IVolleyCollback _collback) {
        RequestQueue queue = Volley.newRequestQueue(_activity);
        String url = _activity.getResources().getString(R.string.GetSoundURL);;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        _collback.GetSound(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("something bad happened");
            }
        });

        queue.add(stringRequest);
    }
}
