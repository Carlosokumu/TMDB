
package com.example.animation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.animation.Adapters.MoviesAdapter;
import com.example.animation.Interfaces.OnMovieListener;
import com.example.animation.Models.DialogData;
import com.example.animation.Models.Movie;
import com.example.animation.Models.MovieResponse;
import com.example.animation.Utils.Base;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends ThemeClass implements BottomNavigationView.OnNavigationItemSelectedListener, OnMovieListener, InternetConnectivityListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static Retrofit retrofit = null;
    private ViewPager2 viewPager = null;
    private  static  final int NUM_PAGES=1;
    ScreenSlidePagerAdapter screenSlidePagerAdapter;
    BottomNavigationView bottomNav;
    Toolbar toolbar;
    Box<DialogData> dialogDataBox;
    AlertDialog.Builder builder;
    Context context;
    User user;
    InternetAvailabilityChecker mInternetAvailabilityChecker;
    private final static String API_KEY = "c1b2a1c6bf4e3fd350bf40c3da4be1d3";
    private static final String BASE_URL="http://api.themoviedb.org/3/";
    MaterialProgressBar materialProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.recycler_view);
        context=getApplicationContext();
        BoxStore boxStore = Initializer.getBoxStore();
        dialogDataBox=Initializer.getBoxStore().boxFor(DialogData.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        materialProgressBar=findViewById(R.id.progressBar);
        materialProgressBar.setVisibility(View.VISIBLE);
        builder= new AlertDialog.Builder(MainActivity.this);
        builder.setView(R.layout.dialog_layout);
        builder.create();
        Box<User> userBox = boxStore.boxFor(User.class);
        screenSlidePagerAdapter=new ScreenSlidePagerAdapter(this);
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setActivated(false);
        user = userBox.query().build().findFirst();
        connectAndGetApiData();
    }

    //set the base url
    public void connectAndGetApiData() {
        if (retrofit == null) {
            // This method creates an instance of Retrofit
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                MoviesAdapter moviesAdapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
                materialProgressBar.setVisibility(View.GONE);
                viewPager.setAdapter(moviesAdapter);
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                builder.show();
            }
        });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem profileItem = menu.findItem(R.id.action_profile);
        Glide.with(this)
                .asBitmap()
                .load(user.getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        profileItem.setIcon(new BitmapDrawable(getResources(), resource));
                    }
                });
        return super.onPrepareOptionsMenu(menu);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                viewPager.setAdapter(screenSlidePagerAdapter);
                break;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                finish();
                break;

        }
        return true;
    }



    @Override
    public void movieListener(DialogData dialogData) {
        dialogDataBox.put(dialogData);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
            Toast.makeText(getApplicationContext(),"rec",Toast.LENGTH_SHORT).show();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new FavouriteFrag();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


}
