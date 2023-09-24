package com.example.sem6_pmd_weathermobileapp_v2.ui.daily_forecast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sem6_pmd_weathermobileapp_v2.models.DailyForecast;

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