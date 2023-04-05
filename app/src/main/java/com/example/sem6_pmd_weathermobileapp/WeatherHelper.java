package com.example.sem6_pmd_weathermobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class WeatherHelper {

    private static final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private static boolean locationPermissionIsGiven(Context context) {
        return ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity activity) {
        if (!locationPermissionIsGiven(activity))
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
    }

    @SuppressLint("MissingPermission")
    public static Location getLocation(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationPermissionIsGiven(context))
            return null;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location location =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null)
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);

        return location;
    }

    public static void getWeatherInformation(Activity activity, String api_base_url, String api_token) {
        Location location = getLocation(activity);

        if (location != null) {
                TextView weather_information = activity.findViewById(R.id.city_name);
                JsonObjectRequest weather_api = new JsonObjectRequest(api_base_url + "current.json?key=" + api_token + "&q=" + location.getLatitude() + "," + location.getLongitude(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weather_information.setText(response.getJSONObject("location").getString("region"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        weather_information.setText(error.getMessage());
                    }
                });

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(weather_api);
        }
    }
}
