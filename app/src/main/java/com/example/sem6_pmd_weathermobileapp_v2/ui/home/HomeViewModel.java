package com.example.sem6_pmd_weathermobileapp_v2.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> regionCountry, cityname, condition, currentTemp, feelsLikeTemp;
    private final MutableLiveData<Drawable> image;

    public MutableLiveData<String> getRegionCountry() {
        return regionCountry;
    }

    public MutableLiveData<String> getCityName() {
        return cityname;
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

    public MutableLiveData<Drawable> getImage() {
        return image;
    }

    public HomeViewModel() {
        regionCountry = new MutableLiveData<>();
        cityname = new MutableLiveData<>();
        condition = new MutableLiveData<>();
        currentTemp = new MutableLiveData<>();
        feelsLikeTemp = new MutableLiveData<>();
        image = new MutableLiveData<>();
    }
}