package com.example.animation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class ThemeClass extends AppCompatActivity {
    private final  int DARK=R.style.AppTheme_Dark;
    private String KEY_THEME = "THEME";
    private final  int LIGHT=R.style.AppTheme;
    public   int currentTheme=LIGHT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        currentTheme= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(KEY_THEME,currentTheme);
        super.onCreate(savedInstanceState);
    }
    public void setTheme(){
      setTheme(currentTheme);
    }
    public  void switchTheme(){
        switch (currentTheme){
            case LIGHT:
                currentTheme=DARK;
                break;
            case DARK:
                currentTheme=LIGHT;
                break;
        }
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt(KEY_THEME,currentTheme).apply();

    }
}
