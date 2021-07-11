package com.example.animation.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

public class Setting {
    public static final String SHARED_PREF_NAME = "settings";
    public static final String LOGGED_IN_SHARED_PREF = "loggedin";
    public static SharedPreferences settings;
    public static void init(@NonNull Context context) {
        settings= context.getSharedPreferences(SHARED_PREF_NAME, 0);
    }
    public static void setLoggedInSharedPref(boolean loggedIn) {
        settings.edit()
                .putBoolean(LOGGED_IN_SHARED_PREF, loggedIn)
                .apply();
    }
    public static boolean isLoggedIn() {
        return settings.getBoolean(LOGGED_IN_SHARED_PREF, false);
    }
}
