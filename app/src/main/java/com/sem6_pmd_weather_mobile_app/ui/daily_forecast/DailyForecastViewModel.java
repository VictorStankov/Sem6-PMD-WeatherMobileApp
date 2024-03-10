package com.sem6_pmd_weather_mobile_app.ui.daily_forecast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sem6_pmd_weather_mobile_app.models.DailyForecast;

import java.util.List;

public class DailyForecastViewModel extends ViewModel {

    private final MutableLiveData<List<DailyForecast>> dailyForecasts;
    private final MutableLiveData<String> measurementUnit;

    public MutableLiveData<List<DailyForecast>> getDailyForecasts() {
        return dailyForecasts;
    }

    public MutableLiveData<String> getMeasurementUnit() {
        return measurementUnit;
    }

    public DailyForecastViewModel() {
        dailyForecasts = new MutableLiveData<>();
        measurementUnit = new MutableLiveData<>();
    }

}