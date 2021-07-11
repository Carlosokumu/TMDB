package com.example.animation;

import android.content.Context;

import io.objectbox.BoxStore;

public class Initializer {
    private static BoxStore boxStore;
    public static void init(Context context){
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }
    public static BoxStore getBoxStore(){
        return boxStore;
    }
}
