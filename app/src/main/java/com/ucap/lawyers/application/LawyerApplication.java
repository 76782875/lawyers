package com.ucap.lawyers.application;

import android.app.Application;
import android.util.Log;

/**
 * Created by wekingwang on 16/8/12.
 */
public class LawyerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("application", "程序入口");
    }
}
