package com.example.sem6_pmd_weathermobileapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sem6_pmd_weathermobileapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private String api_url;
    private String api_token;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WeatherHelper.requestLocationPermission(this);

        super.onCreate(savedInstanceState);

        api_url = ConfigHelper.getConfigValue(this, "api_url");
        api_token = ConfigHelper.getConfigValue(this, "api_token");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        WeatherHelper.getWeatherInformation(MainActivity.this, api_url, api_token);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refreshButton) {
            WeatherHelper.getWeatherInformation(MainActivity.this, api_url, api_token);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("cityName", ((TextView)findViewById(R.id.city_name)).getText().toString());
        outState.putString("regionCountry", ((TextView)findViewById(R.id.region_country)).getText().toString());
        outState.putString("conditionText", ((TextView)findViewById(R.id.conditionText)).getText().toString());
        outState.putString("curTempText", ((TextView)findViewById(R.id.cur_temp_text)).getText().toString());
        outState.putString("currentTemp", ((TextView)findViewById(R.id.current_temp)).getText().toString());
        outState.putString("feelsLikeText", ((TextView)findViewById(R.id.feels_like_text)).getText().toString());
        outState.putString("feelsLikeTemp", ((TextView)findViewById(R.id.feels_like_temp)).getText().toString());
        outState.putString("weatherImage", (String)((ImageView)findViewById(R.id.weatherImage)).getTag());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ((TextView)findViewById(R.id.city_name)).setText(savedInstanceState.getString("cityName"));
        ((TextView)findViewById(R.id.region_country)).setText(savedInstanceState.getString("regionCountry"));
        ((TextView)findViewById(R.id.conditionText)).setText(savedInstanceState.getString("conditionText"));
        ((TextView)findViewById(R.id.cur_temp_text)).setText(savedInstanceState.getString("curTempText"));
        ((TextView)findViewById(R.id.current_temp)).setText(savedInstanceState.getString("currentTemp"));
        ((TextView)findViewById(R.id.feels_like_text)).setText(savedInstanceState.getString("feelsLikeText"));
        ((TextView)findViewById(R.id.feels_like_temp)).setText(savedInstanceState.getString("feelsLikeTemp"));
        ImageView image = findViewById(R.id.weatherImage);
        image.setImageDrawable(AppCompatResources.getDrawable(
                this,
                this.getResources().getIdentifier(
                        savedInstanceState.getString("weatherImage"),
                        "drawable",
                        this.getPackageName()
                )
        ));
        image.setTag(savedInstanceState.getString("weatherImage"));
    }
}
