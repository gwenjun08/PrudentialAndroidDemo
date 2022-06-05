package com.dudu.framework.activities;

import android.os.Bundle;

import com.dudu.framework.BuildConfig;
import com.dudu.framework.adapter.AutoAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoAdapter.init(this, this.getApplication(), BuildConfig.designWidthDp);
    }
}
