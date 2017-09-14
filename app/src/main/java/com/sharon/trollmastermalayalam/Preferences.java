package com.sharon.trollmastermalayalam;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean("first", true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean("first", isFirstTime);
        editor.commit();
    }

    public boolean getPremiumInfo() {
        return preferences.getBoolean("premium", false);
    }

    public void putPremiumInfo(boolean bool) {
        editor.putBoolean("premium", bool);
        editor.commit();
    }

    public boolean getCheckPref(String key) {
        return preferences.getBoolean(key, false);
    }

    public void putCheckPref(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }


}
