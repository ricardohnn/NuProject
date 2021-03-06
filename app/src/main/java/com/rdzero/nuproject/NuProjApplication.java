package com.rdzero.nuproject;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by ricardohnn on 2015-12-24.
 */
public class NuProjApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
        JodaTimeAndroid.init(this);
    }
}
