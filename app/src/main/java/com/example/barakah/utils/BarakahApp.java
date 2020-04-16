package com.example.barakah.utils;

import android.app.Application;

public class BarakahApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       // TypefaceUtil.overrideFont(getApplicationContext(), "fonts/tajawal.ttf", "fonts/tajawal.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}
