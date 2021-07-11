package com.example.animation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import androidx.core.util.ObjectsCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_main);
        loadSummaries("");
    }
    private void loadSummaries(String key) {
        ListPreference preference = (ListPreference) findPreference("THEME");
        preference.setSummary(preference.getEntry());

        if (ObjectsCompat.equals(key, "THEME")) {
            requireActivity().recreate();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        new ThemeClass().switchTheme();
        loadSummaries(key);
    }

    @Override
    public void onResume() {
        PreferenceManager.getDefaultSharedPreferences(requireActivity()).registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        PreferenceManager.getDefaultSharedPreferences(requireActivity()).unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
