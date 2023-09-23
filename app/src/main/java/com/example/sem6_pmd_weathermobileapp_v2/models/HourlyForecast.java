package com.example.sem6_pmd_weathermobileapp_v2.models;

import android.graphics.drawable.Drawable;

public class HourlyForecast {
    public String temp, rain_chance, hour;
    public Drawable weatherImage, rainImage;

    public HourlyForecast(String temp, String rain_chance, String hour, Drawable weatherImage, Drawable rainImage) {
        this.temp = temp;
        this.rain_chance = rain_chance;
        this.hour = hour;
        this.weatherImage = weatherImage;
        this.rainImage = rainImage;
    }
}
