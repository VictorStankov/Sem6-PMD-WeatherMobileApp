package com.example.sem6_pmd_weathermobileapp_v2.models;

import android.graphics.drawable.Drawable;

public class DailyForecast {
    public String date, maxTemperature, minTemperature, rainChance;
    public Drawable weatherImage;

    public DailyForecast(String date, String maxTemperature, String minTemperature, String rainChance, Drawable weatherImage) {
        this.date = date;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.rainChance = rainChance;
        this.weatherImage = weatherImage;
    }
}
