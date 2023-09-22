package com.example.sem6_pmd_weathermobileapp_v2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sem6_pmd_weathermobileapp_v2.ConfigHelper;
import com.example.sem6_pmd_weathermobileapp_v2.MainActivity;
import com.example.sem6_pmd_weathermobileapp_v2.WeatherHelper;
import com.example.sem6_pmd_weathermobileapp_v2.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        final ImageView weatherImage = binding.weatherImage;
        homeViewModel.getImage().observe(getViewLifecycleOwner(), weatherImage::setImageDrawable);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}