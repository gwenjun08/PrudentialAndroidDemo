package com.dudu.framework.utils;

import android.app.Application;
import android.content.Context;

public class AppUtil {

    public static Application sApplication;

    public static Context getAppContext() {
        return sApplication != null ? sApplication.getApplicationContext() : null;
    }

    public static Application getApplication() {
        return sApplication;
    }
}
