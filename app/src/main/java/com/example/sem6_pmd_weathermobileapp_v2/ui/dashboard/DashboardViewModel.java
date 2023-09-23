package com.example.sem6_pmd_weathermobileapp_v2.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sem6_pmd_weathermobileapp_v2.models.DailyForecast;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<List<DailyForecast>> dailyForecasts;

    public MutableLiveData<List<DailyForecast>> getDailyForecasts() {
        return dailyForecasts;
    }

    public DashboardViewModel() {
        dailyForecasts = new MutableLiveData<>();
    }

}