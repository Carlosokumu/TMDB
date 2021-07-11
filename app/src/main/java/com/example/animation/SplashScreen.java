package com.example.animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.animation.Settings.Setting;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import io.objectbox.Box;

public class SplashScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    Button btnLogin;
    GoogleApiClient googleApi;
    Box<User> userBox;
    //request code
    private static  final int  RC_SIGN_IN=201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //configure it to request email and user id
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApi=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        if(Setting.isLoggedIn()){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }
    public void googleSign(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApi);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //check if request code is equal to yours
        if (requestCode ==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            //we are going to save the name and image into an object box
            userBox = Initializer.getBoxStore().boxFor(User.class);
            String name = acct.getDisplayName();
            String image = String.valueOf(acct.getPhotoUrl());
            //putting it into an object_box
            userBox.put(new User(name, image));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Setting.setLoggedInSharedPref(true);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onClick(View v) {
        googleSign();
    }


}