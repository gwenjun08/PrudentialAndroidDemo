package com.dudu.prudential;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.dudu.framework.activities.BaseActivity;
import com.dudu.prudential.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusBar();
        initFragment();

        findViewById(R.id.back_btn).setOnClickListener(v -> {
            finish();
        });
    }

    private void initStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            WindowInsetsControllerCompat wic = new WindowInsetsControllerCompat(window, decorView);
            wic.setAppearanceLightStatusBars(true);
        }
    }
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transcation = fragmentManager.beginTransaction();
        MainFragment fragment = new MainFragment();
        transcation.add(R.id.content, fragment);
        transcation.commitNow();

    }
}