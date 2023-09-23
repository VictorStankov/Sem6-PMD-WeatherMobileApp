package com.example.sem6_pmd_weathermobileapp_v2.ui.daily_forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sem6_pmd_weathermobileapp_v2.ConfigHelper;
import com.example.sem6_pmd_weathermobileapp_v2.DailyForecastAdapter;
import com.example.sem6_pmd_weathermobileapp_v2.MainActivity;
import com.example.sem6_pmd_weathermobileapp_v2.WeatherHelper;
import com.example.sem6_pmd_weathermobileapp_v2.databinding.FragmentDailyForecastBinding;

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

        if (dailyForecastViewModel.getDailyForecasts().getValue() == null)
            WeatherHelper.getDailyForecast(dailyForecastViewModel, ConfigHelper.api_url, ConfigHelper.api_token, activity.getLocation(), root.getContext());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}