package com.example.sem6_pmd_weathermobileapp_v2;

import android.content.Context;
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
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            api_url = properties.getProperty("api_url");
            api_token = properties.getProperty("api_token");
            degrees_unit = properties.getProperty("degrees_unit");
        }
        catch (Resources.NotFoundException e){
            Log.e("Config", "Unable to find config file: " + e.getMessage());
        }
        catch (IOException e){
            Log.e("Config", "Unable to open config file.");
        }
    }

    public static void updateDegreeValue(Context context, String newUnit){
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            properties.setProperty("degrees", newUnit);
            degrees_unit =properties.getProperty("degrees");
        }
        catch (Resources.NotFoundException e){
            Log.e("Config", "Unable to find config file: " + e.getMessage());
        }
        catch (IOException e){
            Log.e("Config", "Unable to open config file.");
        }
    }
}
