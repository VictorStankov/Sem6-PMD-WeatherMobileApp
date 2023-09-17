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
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;


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
            TextView regionCountry = activity.findViewById(R.id.region_country);
            TextView cityName = activity.findViewById(R.id.city_name);
            TextView conditionText = activity.findViewById(R.id.conditionText);
            TextView curTempText = activity.findViewById(R.id.cur_temp_text);
            TextView currentTemp = activity.findViewById(R.id.current_temp);
            TextView feelsLikeText = activity.findViewById(R.id.feels_like_text);
            TextView feelsLikeTemp = activity.findViewById(R.id.feels_like_temp);
            ImageView weatherImage = activity.findViewById(R.id.weatherImage);

            JsonObjectRequest weather_api = new JsonObjectRequest(
                    api_base_url + "forecast.json?key=" + api_token + "&q=" + location.getLatitude() + "," + location.getLongitude() + "&lang=en&days=2&aqi=no&alerts=no",
                    null,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject location = response.getJSONObject("location");
                        JSONObject current = response.getJSONObject("current");
                        JSONObject condition = current.getJSONObject("condition");
                        JSONArray forecastDay = response.getJSONObject("forecast").getJSONArray("forecastday");

                        String regCountryText = location.getString("region") + ", " + location.getString("country");
                        String icon = condition.getString("icon");
                        boolean is_day = current.getInt("is_day") == 1;

                        String displayIcon = (is_day ? "d" : "n") + icon.substring(icon.lastIndexOf('/') + 1, icon.lastIndexOf('/') + 4);

                        weatherImage.setImageDrawable(
                                AppCompatResources.getDrawable(
                                        activity,
                                        activity.getResources().getIdentifier(
                                                displayIcon,
                                                "drawable",
                                                activity.getPackageName()
                                        )
                                )
                        );
                        weatherImage.setTag(displayIcon);

                        regionCountry.setText(regCountryText);
                        cityName.setText(location.getString("name"));
                        conditionText.setText(condition.getString("text"));
                        curTempText.setText("Current Temperature:");
                        currentTemp.setText(current.getString("temp_c") + "° C");
                        feelsLikeText.setText("Feels like:");
                        feelsLikeTemp.setText(current.getString("feelslike_c") + "° C");

                        LocalDateTime datetime = LocalDateTime.now();

                        for (int i = 0; i < forecastDay.length(); ++i) {
                            JSONObject forecastDayObj = forecastDay.getJSONObject(i);
                            JSONArray forecastHour = forecastDayObj.getJSONArray("hour");

                            for (int j = 0; j < forecastHour.length(); ++j) {
                                JSONObject hour = forecastHour.getJSONObject(j);
                                LocalDateTime hour_time = LocalDateTime.parse(hour.getString("time"), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));

                                if (
                                        (
                                                hour_time.isBefore(datetime) && hour_time.getHour() != datetime.getHour()
                                        )
                                                || hour_time.isAfter(datetime.plusDays(1))
                                                || Duration.between(hour_time, datetime.truncatedTo(ChronoUnit.HOURS)).toHours() % 2 != 0
                                )
                                    continue;

                                LinearLayout test = activity.findViewById(R.id.test);
                                test.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

                                LinearLayout forecastElement = new LinearLayout(activity);
                                forecastElement.setBackground(AppCompatResources.getDrawable(activity, R.drawable.empty_tall_divider));
                                forecastElement.setPadding(10, 5, 10, 5);
                                forecastElement.setOrientation(LinearLayout.VERTICAL);

                                TextView time = new TextView(activity);
                                time.setText(hour.getString("time").split(" ")[1]);
                                time.setGravity(Gravity.CENTER);

                                TextView forecastCondition = new TextView(activity);
                                forecastCondition.setText(hour.getString("temp_c") + "° C");
                                forecastCondition.setGravity(Gravity.CENTER);

                                boolean forecastIsDay = hour.getInt("is_day") == 1;
                                String forecastIcon = hour.getJSONObject("condition").getString("icon");
                                String forecastIconName = (forecastIsDay ? "d" : "n") + forecastIcon.substring(
                                        forecastIcon.lastIndexOf('/') + 1,
                                        forecastIcon.lastIndexOf('/') + 4
                                );

                                ImageView forecastImage = new ImageView(activity);
                                forecastImage.setImageDrawable(
                                        AppCompatResources.getDrawable(
                                                activity,
                                                activity.getResources().getIdentifier(
                                                        forecastIconName,
                                                        "drawable",
                                                        activity.getPackageName()
                                                )
                                        )
                                );
                                forecastImage.setTag(forecastIcon);

                                forecastElement.addView(time);
                                forecastElement.addView(forecastImage);
                                forecastElement.addView(forecastCondition);
                                test.addView(forecastElement);
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(weather_api);
        }
    }
}
