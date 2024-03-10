package com.sem6_pmd_weather_mobile_app.ui.daily_forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sem6_pmd_weather_mobile_app.ConfigHelper;
import com.sem6_pmd_weather_mobile_app.MainActivity;
import com.sem6_pmd_weather_mobile_app.WeatherHelper;
import com.sem6_pmd_weather_mobile_app.databinding.FragmentDailyForecastBinding;

import java.util.Objects;

public class DailyForecastFragment extends Fragment {

    private FragmentDailyForecastBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DailyForecastViewModel dailyForecastViewModel =
                new ViewModelProvider(this).get(DailyForecastViewModel.class);

        binding = FragmentDailyForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView list = binding.forecastDayList;
        list.setLayoutManager(layoutManager);

        DailyForecastAdapter dailyForecastAdapter = new DailyForecastAdapter();
        list.setAdapter(dailyForecastAdapter);

        dailyForecastViewModel.getDailyForecasts().observe(getViewLifecycleOwner(), dailyForecastAdapter::setDailyForecasts);

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;

        if (activity.getLocation() == null)
            activity.resetLocation();

        if (
                dailyForecastViewModel.getDailyForecasts().getValue() == null ||
                !Objects.equals(dailyForecastViewModel.getMeasurementUnit().getValue(), ConfigHelper.degrees_unit)
        )
            WeatherHelper.getDailyForecast(dailyForecastViewModel, activity.getLocation(), root.getContext());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}