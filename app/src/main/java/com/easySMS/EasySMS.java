package com.easySMS;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by poiuyt on 9/13/16.
 */
public class EasySMS extends Application {
    static Context context;

    public synchronized static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        context = getApplicationContext();

    }
}
