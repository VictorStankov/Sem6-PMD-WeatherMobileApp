package com.example.sem6_pmd_weathermobileapp_v2.ui.daily_forecast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sem6_pmd_weathermobileapp_v2.models.DailyForecast;

import java.util.List;

public class DailyForecastViewModel extends ViewModel {

    private final MutableLiveData<List<DailyForecast>> dailyForecasts;

    public MutableLiveData<List<DailyForecast>> getDailyForecasts() {
        return dailyForecasts;
    }

    public DailyForecastViewModel() {
        dailyForecasts = new MutableLiveData<>();
    }

}