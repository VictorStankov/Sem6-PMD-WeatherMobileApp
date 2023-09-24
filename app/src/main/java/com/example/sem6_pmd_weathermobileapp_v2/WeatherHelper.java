package com.example.sem6_pmd_weathermobileapp_v2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;

import androidx.appcompat.content.res.AppCompatResources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sem6_pmd_weathermobileapp_v2.models.DailyForecast;
import com.example.sem6_pmd_weathermobileapp_v2.models.HourlyForecast;
import com.example.sem6_pmd_weathermobileapp_v2.ui.daily_forecast.DailyForecastViewModel;
import com.example.sem6_pmd_weathermobileapp_v2.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class WeatherHelper {

    public static void getCurrentWeather(HomeViewModel hvm, String api_base_url, String api_token, Location location, Context ctx) {

        List<HourlyForecast> hourlyForecasts = new ArrayList<>();
        String degrees = ConfigHelper.degrees;

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
                            hvm.getCurrentTemp().setValue(current.getString("temp_" + degrees.toLowerCase()) + "° " + degrees);
                            hvm.getFeelsLikeTemp().setValue(current.getString("feelslike_" + degrees.toLowerCase()) + "° " + degrees);
                            hvm.getHumidity().setValue(current.getString("humidity") + "%");
                            hvm.getImage().setValue(AppCompatResources.getDrawable(
                                    ctx,
                                    ctx.getResources().getIdentifier(
                                            displayIcon,
                                            "drawable",
                                            ctx.getPackageName()
                                    )
                            ));


                            LocalDateTime datetime = LocalDateTime.now();

                            for (int i = 0; i < forecastDay.length(); ++i) {
                                JSONObject forecastDayObj = forecastDay.getJSONObject(i);
                                JSONArray forecastHour = forecastDayObj.getJSONArray("hour");

                                for (int j = 0; j < forecastHour.length(); ++j) {
                                    JSONObject hour = forecastHour.getJSONObject(j);
                                    LocalDateTime hour_time = LocalDateTime.parse(hour.getString("time"), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));

                                    long delta = Duration.between(datetime.truncatedTo(ChronoUnit.HOURS), hour_time).toHours();

                                    if (delta > 10 || delta <= 0)
                                        continue;

                                    boolean forecastIsDay = hour.getInt("is_day") == 1;
                                    String forecastIcon = hour.getJSONObject("condition").getString("icon");
                                    String forecastIconName = (forecastIsDay ? "d" : "n") + forecastIcon.substring(
                                            forecastIcon.lastIndexOf('/') + 1,
                                            forecastIcon.lastIndexOf('/') + 4
                                    );

                                    Drawable image = AppCompatResources.getDrawable(
                                            ctx,
                                            ctx.getResources().getIdentifier(
                                                    forecastIconName,
                                                    "drawable",
                                                    ctx.getPackageName()
                                            )
                                    );

                                    hourlyForecasts.add(
                                        new HourlyForecast(
                                            hour.getString("temp_" + degrees.toLowerCase()) + "° " + degrees,
                                                hour.getString("chance_of_rain") + "%",
                                                hour.getString("time").split(" ")[1],
                                                image,
                                                AppCompatResources.getDrawable(ctx, R.drawable.raindrop)
                                        )
                                    );

                                }

                            }

                            hvm.getHourlyForecast().setValue(hourlyForecasts);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }, null);

            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(weather_api);
        }
    }

    public static void getDailyForecast(DailyForecastViewModel dfvm, String api_base_url, String api_token, Location location, Context ctx){
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        String degrees = ConfigHelper.degrees;

        if (location == null)
            return;

        JsonObjectRequest weather_api = new JsonObjectRequest(
                Request.Method.GET,
                api_base_url + "forecast.json?key=" + api_token + "&q=" + location.getLatitude() + "," + location.getLongitude() + "&lang=en&days=10&aqi=no&alerts=no",
                null,
                response -> {
                    try {
                        JSONArray forecastDay = response.getJSONObject("forecast").getJSONArray("forecastday");

                        for (int i = 0; i < forecastDay.length(); ++i) {
                            JSONObject forecastDayObj = forecastDay.getJSONObject(i);
                            JSONObject day = forecastDayObj.getJSONObject("day");

                            String forecastIcon = day.getJSONObject("condition").getString("icon");
                            String forecastIconName = "d" + forecastIcon.substring(
                                    forecastIcon.lastIndexOf('/') + 1,
                                    forecastIcon.lastIndexOf('/') + 4
                            );

                            Drawable image = AppCompatResources.getDrawable(
                                    ctx,
                                    ctx.getResources().getIdentifier(
                                            forecastIconName,
                                            "drawable",
                                            ctx.getPackageName()
                                    )
                            );

                            dailyForecasts.add(
                                new DailyForecast(
                                        forecastDayObj.getString("date"),
                                        day.getString("maxtemp_" + degrees.toLowerCase()) + "° " + degrees,
                                        day.getString("mintemp_" + degrees.toLowerCase()) + "° " + degrees,
                                        day.getString("daily_chance_of_rain") + "%",
                                        image
                                )
                            );

                        }

                        dfvm.getDailyForecasts().setValue(dailyForecasts);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(weather_api);
    }
}
