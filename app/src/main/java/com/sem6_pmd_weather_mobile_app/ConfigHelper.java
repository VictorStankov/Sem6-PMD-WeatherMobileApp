package com.sem6_pmd_weather_mobile_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {
    public static String api_url;
    public static String api_token;
    public static String degrees_unit;

    public static void getConfigValues(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        degrees_unit = sharedPreferences.getString("degrees_unit", "C");

        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            api_url = properties.getProperty("api_url");
            api_token = properties.getProperty("api_token");
        }
        catch (Resources.NotFoundException e){
            Log.e("Config", "Unable to find config file: " + e.getMessage());
        }
        catch (IOException e){
            Log.e("Config", "Unable to open config file.");
        }
    }

    public static void updateDegreeValue(Context context, String newUnit) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("degrees_unit", newUnit).apply();
        degrees_unit = sharedPreferences.getString("degrees_unit", "C");
    }
}
