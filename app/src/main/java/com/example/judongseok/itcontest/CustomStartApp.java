package com.example.judongseok.itcontest;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class CustomStartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "nsl.ttf"))
                .addBold(Typekit.createFromAsset(this, "nsb.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "nsl.ttf"));// "fonts/폰트.ttf"
    }
}