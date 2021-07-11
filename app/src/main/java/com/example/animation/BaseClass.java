package com.example.animation;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.animation.Settings.Setting;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;

import io.objectbox.BoxStore;

public class BaseClass extends Application {
    @Override
    public void onCreate() {
        Initializer.init(this);
        Setting.init(this);
        InternetAvailabilityChecker.init(this);
        super.onCreate();
    }
}
