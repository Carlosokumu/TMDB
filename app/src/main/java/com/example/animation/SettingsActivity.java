package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.objectbox.Box;

public class SettingsActivity extends ThemeClass {
    ImageView imageUser;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme();
        setContentView(R.layout.activity_settings);
        Box<User> userBox =Initializer.getBoxStore().boxFor(User.class);
        user = userBox.query().build().findFirst();
        imageUser=findViewById(R.id.imageUser);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new SettingsFragment())
                    .commit();
        }
        Glide.with(this).load(user.imageUrl).placeholder(R.drawable.progress_animation).into(imageUser);
    }
}