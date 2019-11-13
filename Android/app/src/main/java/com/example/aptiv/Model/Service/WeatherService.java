package com.example.aptiv.Model.Service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherService {
    private MainActivity _activity;

    public  WeatherService(MainActivity activity){ _activity = activity; }


    public void GetWeather(final IVolleyCollback _collback) {
        RequestQueue queue = Volley.newRequestQueue(_activity);
        String url = _activity.getResources().getString(R.string.WeatherAPI);;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONObject mainObject = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String OutTemp = String.valueOf(mainObject.getDouble("temp"));
                    double temp_double = Double.parseDouble(OutTemp);
                    temp_double = Math.round(temp_double - 273.15);
                    _collback.OutTempreture(temp_double);
                }catch (Exception e){
                    Log.e(this.getClass().toString(), e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(this.getClass().toString(), error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

}
