package com.example.sem6_pmd_weathermobileapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {

    public static String getConfigValue(Context context, String name){
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        }
        catch (Resources.NotFoundException e){
            Log.e("Config", "Unable to find config file: " + e.getMessage());
        }
        catch (IOException e){
            Log.e("Config", "Unable to open config file.");
        }

        return null;
    }
}
