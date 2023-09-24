package com.example.sem6_pmd_weathermobileapp_v2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sem6_pmd_weathermobileapp_v2.ConfigHelper;
import com.example.sem6_pmd_weathermobileapp_v2.HourlyForecastAdapter;
import com.example.sem6_pmd_weathermobileapp_v2.MainActivity;
import com.example.sem6_pmd_weathermobileapp_v2.WeatherHelper;
import com.example.sem6_pmd_weathermobileapp_v2.databinding.FragmentHomeBinding;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView list = binding.forecastHourList;
        list.setLayoutManager(layoutManager);

        HourlyForecastAdapter hourlyForecastAdapter = new HourlyForecastAdapter();
        list.setAdapter(hourlyForecastAdapter);

        homeViewModel.getHourlyForecast().observe(getViewLifecycleOwner(), hourlyForecastAdapter::setHourlyForecasts);

        final TextView conditionText = binding.conditionText;
        homeViewModel.getCondition().observe(getViewLifecycleOwner(), conditionText::setText);

        final TextView regionCountry = binding.regionCountry;
        homeViewModel.getRegionCountry().observe(getViewLifecycleOwner(), regionCountry::setText);

        final TextView cityName = binding.cityName;
        homeViewModel.getCityName().observe(getViewLifecycleOwner(), cityName::setText);

        final TextView currentTemp = binding.currentTemp;
        homeViewModel.getCurrentTemp().observe(getViewLifecycleOwner(), currentTemp::setText);

        final TextView feelsLikeTemp = binding.feelsLikeTemp;
        homeViewModel.getFeelsLikeTemp().observe(getViewLifecycleOwner(), feelsLikeTemp::setText);

        final TextView humidity = binding.humidity;
        homeViewModel.getHumidity().observe(getViewLifecycleOwner(), humidity::setText);

        final TextView measurementHour = binding.measurementHour;
        homeViewModel.getMeasurementHour().observe(getViewLifecycleOwner(), measurementHour::setText);

        final ImageView weatherImage = binding.weatherImage;
        homeViewModel.getImage().observe(getViewLifecycleOwner(), weatherImage::setImageDrawable);

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;

        if (activity.getLocation() == null)
            activity.resetLocation();

        Button refreshButton = binding.refreshButton;

        refreshButton.setOnClickListener(view ->
                WeatherHelper.getCurrentWeather(homeViewModel, activity.getLocation(), root.getContext())
        );

        if (
                homeViewModel.getHourlyForecast().getValue() == null ||
                !Objects.equals(homeViewModel.getMeasurementUnit().getValue(), ConfigHelper.degrees_unit)
        )
            WeatherHelper.getCurrentWeather(homeViewModel, activity.getLocation(), root.getContext());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}