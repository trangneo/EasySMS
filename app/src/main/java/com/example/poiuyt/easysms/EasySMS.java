package com.example.poiuyt.easysms;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by poiuyt on 9/13/16.
 */
public class EasySMS extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
