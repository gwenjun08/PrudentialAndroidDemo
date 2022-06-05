package com.dudu.prudential;

import android.app.Application;

import com.dudu.framework.utils.AppUtil;

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.sApplication = this;
    }
}
