package com.example.sem6_pmd_weathermobileapp_v2.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sem6_pmd_weathermobileapp_v2.ConfigHelper;
import com.example.sem6_pmd_weathermobileapp_v2.MainActivity;
import com.example.sem6_pmd_weathermobileapp_v2.R;
import com.example.sem6_pmd_weathermobileapp_v2.databinding.FragmentSettingsBinding;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private final ArrayList<String> settingsList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView settingsMenu = binding.settingsMenu;

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;

        fillSettingsList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, R.layout.settings_item, settingsList);
        settingsMenu.setAdapter(adapter);

        settingsMenu.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i){
                case 0:
                    String a = adapterView.getItemAtPosition(i).toString();
                    char currentMeasurement = a.charAt(a.length() - 1);

                    activity.updateDegreeMeasurement(currentMeasurement == 'F'? "C" : "F");
                    fillSettingsList();
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
        return root;
    }

    private void fillSettingsList(){
        settingsList.clear();
        settingsList.add("Degree Style: " + ConfigHelper.degrees_unit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}