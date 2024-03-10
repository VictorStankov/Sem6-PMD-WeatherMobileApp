package com.sem6_pmd_weather_mobile_app;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

//import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sem6_pmd_weather_mobile_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void resetLocation(){
        location = LocationHelper.getLocation(this);
    }
    public void updateDegreeMeasurement(String newUnit){
        ConfigHelper.updateDegreeValue(this, newUnit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocationHelper.requestLocationPermission(this);

        super.onCreate(savedInstanceState);

        ConfigHelper.getConfigValues(this);
        location = LocationHelper.getLocation(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (getSupportActionBar() != null)
            this.getSupportActionBar().hide();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.recreate();
            }
            else {
                Toast.makeText(this, "Location permission is required for this app", Toast.LENGTH_SHORT).show();
            }
        }
    }
}