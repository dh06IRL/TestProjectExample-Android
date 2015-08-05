package com.david.github;

import android.app.Application;
import android.util.Log;

import com.david.github.utils.Constants;

/**
 * Created by davidhodge on 7/23/15.
 */
public class MainApp extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Constants.TAG, "onCreate");

    }
}
