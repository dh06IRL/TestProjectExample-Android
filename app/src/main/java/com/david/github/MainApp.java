package com.david.github;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.david.github.utils.Constants;

import io.fabric.sdk.android.Fabric;

/**
 * Created by davidhodge on 7/23/15.
 */
public class MainApp extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Constants.TAG, "onCreate");

        Fabric.with(this, new Crashlytics());
    }
}
