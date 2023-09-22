package com.example.sem6_pmd_weathermobileapp_v2;

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
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.viewbinding.ViewBinding;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sem6_pmd_weathermobileapp_v2.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class WeatherHelper {

    public static void getCurrentWeather(HomeViewModel hvm, String api_base_url, String api_token, Location location, Context ctx) {

        if (location != null) {
            JsonObjectRequest weather_api = new JsonObjectRequest(
                    Request.Method.GET,
                    api_base_url + "forecast.json?key=" + api_token + "&q=" + location.getLatitude() + "," + location.getLongitude() + "&lang=en&days=2&aqi=no&alerts=no",
                    null,
                    response -> {
                        try {
                            JSONObject loc = response.getJSONObject("location");
                            JSONObject current = response.getJSONObject("current");
                            JSONObject condition = current.getJSONObject("condition");
                            JSONArray forecastDay = response.getJSONObject("forecast").getJSONArray("forecastday");

                            String regCountryText = loc.getString("region") + ", " + loc.getString("country");
                            String icon = condition.getString("icon");
                            boolean is_day = current.getInt("is_day") == 1;

                            String displayIcon = (is_day ? "d" : "n") + icon.substring(icon.lastIndexOf('/') + 1, icon.lastIndexOf('/') + 4);

                            hvm.getRegionCountry().setValue(regCountryText);
                            hvm.getCityName().setValue(loc.getString("name"));
                            hvm.getCondition().setValue(condition.getString("text"));
                            hvm.getCurrentTemp().setValue(current.getString("temp_c") + "° C");
                            hvm.getFeelsLikeTemp().setValue(current.getString("feelslike_c") + "° C");
                            hvm.getImage().setValue(AppCompatResources.getDrawable(
                                    ctx,
                                    ctx.getResources().getIdentifier(
                                            displayIcon,
                                            "drawable",
                                            ctx.getPackageName()
                                    )
                            ));


    //                        LocalDateTime datetime = LocalDateTime.now();
    //
    //                        for (int i = 0; i < forecastDay.length(); ++i) {
    //                            JSONObject forecastDayObj = forecastDay.getJSONObject(i);
    //                            JSONArray forecastHour = forecastDayObj.getJSONArray("hour");
    //
    //                            for (int j = 0; j < forecastHour.length(); ++j) {
    //                                JSONObject hour = forecastHour.getJSONObject(j);
    //                                LocalDateTime hour_time = LocalDateTime.parse(hour.getString("time"), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    //
    //                                if (
    //                                        (
    //                                                hour_time.isBefore(datetime) && hour_time.getHour() != datetime.getHour()
    //                                        )
    //                                                || hour_time.isAfter(datetime.plusDays(1))
    //                                                || Duration.between(hour_time, datetime.truncatedTo(ChronoUnit.HOURS)).toHours() % 2 != 0
    //                                )
    //                                    continue;
    //
    //                                HorizontalScrollView aa = activity.findViewById(R.id.test);
    //                                LinearLayout test = activity.findViewById(R.id.linear_layout);
    //                                test.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    //
    //                                LinearLayout forecastElement = new LinearLayout(activity);
    //                                forecastElement.setBackground(AppCompatResources.getDrawable(activity, R.drawable.empty_tall_divider));
    //                                forecastElement.setPadding(10, 5, 10, 5);
    //                                forecastElement.setOrientation(LinearLayout.VERTICAL);
    //
    //                                TextView time = new TextView(activity);
    //                                time.setText(hour.getString("time").split(" ")[1]);
    //                                time.setGravity(Gravity.CENTER);
    //
    //                                TextView forecastCondition = new TextView(activity);
    //                                forecastCondition.setText(hour.getString("temp_c") + "° C");
    //                                forecastCondition.setGravity(Gravity.CENTER);
    //
    //                                boolean forecastIsDay = hour.getInt("is_day") == 1;
    //                                String forecastIcon = hour.getJSONObject("condition").getString("icon");
    //                                String forecastIconName = (forecastIsDay ? "d" : "n") + forecastIcon.substring(
    //                                        forecastIcon.lastIndexOf('/') + 1,
    //                                        forecastIcon.lastIndexOf('/') + 4
    //                                );
    //
    //                                ImageView forecastImage = new ImageView(activity);
    //                                forecastImage.setImageDrawable(
    //                                        AppCompatResources.getDrawable(
    //                                                activity,
    //                                                activity.getResources().getIdentifier(
    //                                                        forecastIconName,
    //                                                        "drawable",
    //                                                        activity.getPackageName()
    //                                                )
    //                                        )
    //                                );
    //                                forecastImage.setTag(forecastIcon);
    //
    //                                forecastElement.addView(time);
    //                                forecastElement.addView(forecastImage);
    //                                forecastElement.addView(forecastCondition);
    //                                test.addView(forecastElement);
    //                            }
    //                        }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(weather_api);
        }
    }
}
