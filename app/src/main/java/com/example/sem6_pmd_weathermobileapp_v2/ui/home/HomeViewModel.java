package com.example.sem6_pmd_weathermobileapp_v2.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sem6_pmd_weathermobileapp_v2.models.HourlyForecast;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> regionCountry, cityName, condition, currentTemp, feelsLikeTemp, humidity, measurementUnit;
    private final MutableLiveData<Drawable> image;

    private final MutableLiveData<List<HourlyForecast>> hourlyForecast;

    public MutableLiveData<String> getRegionCountry() {
        return regionCountry;
    }

    public MutableLiveData<String> getCityName() {
        return cityName;
    }

    public MutableLiveData<String> getCondition() {
        return condition;
    }

    public MutableLiveData<String> getCurrentTemp() {
        return currentTemp;
    }

    public MutableLiveData<String> getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public MutableLiveData<String> getHumidity() {
        return humidity;
    }

    public MutableLiveData<Drawable> getImage() {
        return image;
    }

    public MutableLiveData<List<HourlyForecast>> getHourlyForecast() {
        return hourlyForecast;
    }

    public MutableLiveData<String> getMeasurementUnit() {
        return measurementUnit;
    }

    public HomeViewModel() {
        regionCountry = new MutableLiveData<>();
        cityName = new MutableLiveData<>();
        condition = new MutableLiveData<>();
        currentTemp = new MutableLiveData<>();
        feelsLikeTemp = new MutableLiveData<>();
        humidity = new MutableLiveData<>();
        image = new MutableLiveData<>();
        hourlyForecast = new MutableLiveData<>();
        measurementUnit = new MutableLiveData<>();
    }
}