package com.example.aptiv.Model.Service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IAptivService;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.R;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

public class AptivService implements IAptivService {
    private MainActivity _activity;

    public  AptivService(MainActivity activity){ _activity = activity; }

    @Override
    public void GetDriverReadings(final IVolleyCollback _collback) {
        RequestQueue queue = Volley.newRequestQueue(_activity);
        String url = _activity.getResources().getString(R.string.GetDriverReadings);;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                String dataArray = null;
                try {
                    dataArray = response.getString("driver");
                } catch (JSONException e) {
                    Log.e(this.getClass().toString(), e.getMessage());
                }

                final Zone Zone = gson.fromJson(dataArray, Zone.class);
                _collback.GetDriverReadings(Zone);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(this.getClass().toString(), error.getMessage());
            }
        });
    }

    @Override
    public void GetPassengerReadings(final IVolleyCollback _collback) {
        RequestQueue queue = Volley.newRequestQueue(_activity);
        String url = _activity.getResources().getString(R.string.GetPassengerReadings);;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                String dataArray = null;
                try {
                    dataArray = response.getString("passenger");
                } catch (JSONException e) {
                    Log.e(this.getClass().toString(), e.getMessage());
                }

                final Zone Zone = gson.fromJson(dataArray, Zone.class);
                _collback.GetPassengerReadings(Zone);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(this.getClass().toString(), error.getMessage());
            }
        });
    }

    @Override
    public void GetAverageReadings(final IVolleyCollback _collback) {
        RequestQueue queue = Volley.newRequestQueue(_activity);
        String url = _activity.getResources().getString(R.string.GetAverageReadings);;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                String dataArray = null;
                try {
                    dataArray = response.getString("middle");
                } catch (JSONException e) {
                    Log.e(this.getClass().toString(), e.getMessage());
                }

                final Zone Zone = gson.fromJson(dataArray, Zone.class);
                _collback.GetAverageReadings(Zone);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(this.getClass().toString(), error.getMessage());
            }
        });
    }

    @Override
    public void GetBackseatReadings(final IVolleyCollback _collback) {
        RequestQueue queue = Volley.newRequestQueue(_activity);
        String url = _activity.getResources().getString(R.string.GetBackseatReadings);;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    String dataArray = null;
                    try {
                        dataArray = response.getString("backseat");
                    } catch (JSONException e) {
                        Log.e(this.getClass().toString(), e.getMessage());
                    }

                    final Zone Zone = gson.fromJson(dataArray, Zone.class);
                    _collback.GetBackseatReadings(Zone);

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
