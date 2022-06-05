package com.dudu.framework.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

import com.dudu.framework.utils.AppUtil;

import androidx.annotation.NonNull;

public class AutoAdapter {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void init(Activity activity, Application application, final float designWidthDp) {
        if(application == null || designWidthDp <= 0) {
            return;
        }
        DisplayMetrics appDisplayMetics = application.getResources().getDisplayMetrics();
        if(sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetics.density;
            sNoncompatScaledDensity = appDisplayMetics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetics.widthPixels / designWidthDp;
        final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetics.density = targetDensity;
        appDisplayMetics.scaledDensity = targetScaledDensity;
        appDisplayMetics.densityDpi = targetDensityDpi;

        if(activity == null) {
            return;
        }
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi= targetDensityDpi;

        Log.i("demotag", "targetDensity -> " + targetDensity);
    }

    public static float dp2px(float dp) {
        Context appContext = AppUtil.getAppContext();
        if(appContext == null) {
            return dp;
        }
        return dp * appContext.getResources().getDisplayMetrics().density;
    }

}
